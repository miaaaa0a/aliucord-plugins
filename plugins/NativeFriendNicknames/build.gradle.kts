version = "1.1.6"
description = "a plugin that backports the friend nickname feature"

aliucord {
    // Changelog of your plugin
    changelog.set(
        """
        # 1.1.6
        * don't display friend nickname in servers
        """.trimIndent(),
    )
    deploy.set(true)

    // Builds and deploys this plugin but excludes it from global plugin repositories.
    // Set this if the plugin has reached EOL but a last update should still occur.
    // deployHidden.set(true)
}
