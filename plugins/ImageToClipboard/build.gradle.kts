version = "1.2.0"
description = "add an option to copy images to your clipboard"

aliucord {
    // Changelog of your plugin
    changelog.set(
        """
        # 1.2.0
        * completely rewrite mime type detection
        """.trimIndent(),
    )
    deploy.set(true)

    // Builds and deploys this plugin but excludes it from global plugin repositories.
    // Set this if the plugin has reached EOL but a last update should still occur.
    // deployHidden.set(true)
}
