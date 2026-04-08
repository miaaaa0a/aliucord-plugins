version = "1.1.5"
description = "a plugin that backports the friend nickname feature"

aliucord {
    // Changelog of your plugin
    changelog.set(
        """
        # 1.1.5
        * prevent nullable user from leaking into discord's api
        """.trimIndent(),
    )
    deploy.set(true)

    // Builds and deploys this plugin but excludes it from global plugin repositories.
    // Set this if the plugin has reached EOL but a last update should still occur.
    // deployHidden.set(true)
}
