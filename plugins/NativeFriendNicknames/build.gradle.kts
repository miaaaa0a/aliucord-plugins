version = "1.1.0"
description = "[wip] a plugin that backports the friend nickname feature"

aliucord {
    // Changelog of your plugin
    changelog.set(
        """
        # 1.1.0
        * implement editing of the nicknames
        """.trimIndent(),
    )
    deploy.set(false)

    // Builds and deploys this plugin but excludes it from global plugin repositories.
    // Set this if the plugin has reached EOL but a last update should still occur.
    // deployHidden.set(true)
}
