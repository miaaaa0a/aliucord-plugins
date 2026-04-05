version = "1.0.2"
description = "add an option to copy images to your clipboard"

aliucord {
    // Changelog of your plugin
    changelog.set(
        """
        # 1.0.2
        * add removal of previously copied images (still have to keep the current one)
        * fix detection of image files
        """.trimIndent(),
    )
    deploy.set(true)

    // Builds and deploys this plugin but excludes it from global plugin repositories.
    // Set this if the plugin has reached EOL but a last update should still occur.
    // deployHidden.set(true)
}
