version = "1.0.1"
description = "add an option to copy images to your clipboard"

aliucord {
    // Changelog of your plugin
    changelog.set(
        """
        # 1.0.1
        * display copy button only on images
        """.trimIndent(),
    )
    deploy.set(true)

    // Builds and deploys this plugin but excludes it from global plugin repositories.
    // Set this if the plugin has reached EOL but a last update should still occur.
    // deployHidden.set(true)
}
