version = "1.0.0"
description = "[wip] a plugin that backports the friend nickname feature"

aliucord {
    // Changelog of your plugin
    changelog.set(
        """
        # 1.0.0
        * initial release!
        * display function implemented
        """.trimIndent(),
    )
    deploy.set(false)

    // Builds and deploys this plugin but excludes it from global plugin repositories.
    // Set this if the plugin has reached EOL but a last update should still occur.
    // deployHidden.set(true)
}
