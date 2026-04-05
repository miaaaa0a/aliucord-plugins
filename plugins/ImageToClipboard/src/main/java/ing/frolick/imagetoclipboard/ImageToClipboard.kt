package ing.frolick.imagetoclipboard

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat

import com.aliucord.Logger
import com.aliucord.Utils
import com.aliucord.Utils.appContext
import com.aliucord.annotations.AliucordPlugin
import com.aliucord.entities.Plugin
import com.aliucord.patcher.Hook
import com.aliucord.utils.ViewUtils.findViewById
import com.discord.app.AppFragment
import com.discord.databinding.WidgetMediaBinding
import com.discord.utilities.color.ColorCompat
import com.discord.widgets.media.WidgetMedia

import com.google.android.material.appbar.AppBarLayout
import com.lytefast.flexinput.R
import java.io.File
import java.net.URL


@SuppressWarnings("unused")
@AliucordPlugin
class ImageToClipboard : Plugin() {
    @SuppressLint("RestrictedApi")
    override fun start(context: Context) {
        patcher.patch(
            WidgetMedia::class.java, "onViewBoundOrOnResume", arrayOf(),
            Hook {
                Logger().info("called")
                val widgetMedia = it.thisObject as WidgetMedia
                val binding = WidgetMedia.`access$getBinding$p`(widgetMedia)
                val field = WidgetMediaBinding::class.java.getDeclaredField("b").apply { isAccessible = true }
                val appBarLayout = field.get(binding) as AppBarLayout
                val menuMediaGroup = appBarLayout.findViewById<TextView>("menu_media_download").parent as ViewGroup

                //Logger().info("CHILDREN COUNT: ${actionBar.childCount}")
                //val menuMediaGroup = actionBar.getChildAt(0) as ViewGroup

                val ctx = menuMediaGroup.context
                val copyBtn = TextView(ctx, null, 0, R.i.UiKit_ImageButton).apply {
                    setCompoundDrawablesWithIntrinsicBounds(
                        ContextCompat
                            .getDrawable(ctx, R.e.ic_copy_24dp)!!
                            .mutate()
                            .apply {
                                setTint(ColorCompat.getThemedColor(ctx, R.b.colorTextNormal))
                            },
                        null,
                        null,
                        null,
                    )
                    setOnClickListener {
                        val imageUrl = (widgetMedia as AppFragment).mostRecentIntent
                            .getStringExtra("INTENT_MEDIA_URL") ?: return@setOnClickListener

                        Utils.showToast("copying...", false)

                        // this is pure magic don't ask
                        Thread {
                            try {
                                val bitmap = android.graphics.BitmapFactory.decodeStream(
                                    URL(imageUrl).openStream()
                                )
                                val resolver = context.contentResolver

                                val uri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                                    val values = ContentValues().apply {
                                        put(MediaStore.Images.Media.DISPLAY_NAME, "clipboard_tmp_${System.currentTimeMillis()}.jpg")
                                        put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
                                        put(MediaStore.Images.Media.IS_PENDING, 1)
                                    }
                                    val imgUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)!!
                                    resolver.openOutputStream(imgUri)?.use { out ->
                                        bitmap.compress(Bitmap.CompressFormat.JPEG, 95, out)
                                    }
                                    resolver.update(imgUri, ContentValues().apply {
                                        put(MediaStore.Images.Media.IS_PENDING, 0)
                                    }, null, null)
                                    imgUri
                                } else {
                                    val file = File(context.externalCacheDir, "clipboard_tmp.jpg")
                                    file.outputStream().use { bitmap.compress(Bitmap.CompressFormat.JPEG, 95, it) }
                                    Uri.fromFile(file)
                                }

                                val clip = ClipData.newUri(resolver, "image", uri)
                                (appContext.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager)
                                    .setPrimaryClip(clip)

                                Utils.mainThread.post { Utils.showToast("copied the image!", false) }
                            } catch (e: Exception) {
                                Logger().error("itc error: ${e.message}", e)
                                Utils.mainThread.post { Utils.showToast("failed to copy >.<", false) }
                            }
                        }.start()
                    }
                }
                if (appBarLayout.findViewWithTag<View>("copy_btn") == null) {
                    copyBtn.tag = "copy_btn"
                    menuMediaGroup.addView(copyBtn)
                }
            },
        )
    }

    override fun stop(context: Context) {
        patcher.unpatchAll()
    }
}
