version = "1.1.0"
description = "add an option to copy images to your clipboard"

aliucord {
    // Changelog of your plugin
    changelog.set(
        """
        # 1.1.0
        * rewrite image copying entirely, so nothing is saved in the internal storage
        * implement image type detection for proper mime typing
        """.trimIndent(),
    )
    deploy.set(true)

    // Builds and deploys this plugin but excludes it from global plugin repositories.
    // Set this if the plugin has reached EOL but a last update should still occur.
    // deployHidden.set(true)
}
