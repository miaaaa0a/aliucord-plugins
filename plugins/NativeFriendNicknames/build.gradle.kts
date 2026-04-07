version = "1.1.4"
description = "a plugin that backports the friend nickname feature"

aliucord {
    // Changelog of your plugin
    changelog.set(
        """
        # 1.1.4
        * fixed null appearing on deleted accounts and some bots
        * fixed the edit nickname dialog sometimes not appearing
        * finally changed the pencil icon in the user actions dialog to be the right color
        """.trimIndent(),
    )
    deploy.set(true)

    // Builds and deploys this plugin but excludes it from global plugin repositories.
    // Set this if the plugin has reached EOL but a last update should still occur.
    // deployHidden.set(true)
}
