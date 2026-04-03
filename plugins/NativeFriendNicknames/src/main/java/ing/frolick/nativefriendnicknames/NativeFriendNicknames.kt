package ing.frolick.nativefriendnicknames

import android.content.Context

import com.aliucord.annotations.AliucordPlugin
import com.aliucord.api.GatewayAPI
import com.aliucord.entities.Plugin
import com.aliucord.patcher.*
import com.discord.api.channel.`ChannelUtils$getDisplayName$1`
import com.discord.models.user.CoreUser
import com.discord.api.user.User

internal class Relationship(val id: Long, val nickname: String?)
internal class Ready(val relationships: List<Relationship>)
object Stores {
    val friendNicknames = StoreRelationshipNick()
}

@SuppressWarnings("unused")
@AliucordPlugin
class NativeFriendNicknames : Plugin() {
    override fun start(context: Context) {
        Stores.friendNicknames.setup()

        patcher.after<CoreUser>("getGlobalName") {
            val nickname = Stores.friendNicknames.getNickname(this.id) ?: return@after
            it.result = nickname
        }

        // this gets globalName from a different api, so I have to patch it out manually
        Patcher.addPatch(`ChannelUtils$getDisplayName$1`::class.java.getDeclaredMethod("invoke", Any::class.java), Hook {
            val nickname = Stores.friendNicknames.getNickname((it.args[0] as User).id) ?: return@Hook
            it.result = nickname
        })
    }

    override fun stop(context: Context) {
        patcher.unpatchAll()
    }
}

class StoreRelationshipNick(var data: MutableMap<Long, String?> = mutableMapOf<Long, String?>()) {
    fun setup() {
        GatewayAPI.onEvent<Ready>("READY") { event ->
            data = event.relationships.associate { relationship ->
                relationship.id to relationship.nickname
            } as MutableMap<Long, String?>
        }
        GatewayAPI.onEvent<Relationship>("RELATIONSHIP_ADD") { event ->
            data[event.id] = event.nickname
        }
        GatewayAPI.onEvent<Relationship>("RELATIONSHIP_UPDATE") { event ->
            data[event.id] = event.nickname
        }
        GatewayAPI.onEvent<Relationship>("RELATIONSHIP_REMOVE") { event ->
            data.remove(event.id)
        }
    }
    fun getNickname(id: Long): String? = data[id]
}
