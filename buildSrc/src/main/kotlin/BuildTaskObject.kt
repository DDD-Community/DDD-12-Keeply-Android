interface BuildTask {
    companion object {
        const val DEBUG = "debug"
        const val RELEASE = "release"
    }

    val isMinifyEnabled: Boolean
    val isDebuggable: Boolean
}

object BuildTaskDebug : BuildTask {
    override val isMinifyEnabled = false
    override val isDebuggable = true
}

object BuildTaskRelease : BuildTask {
    override val isMinifyEnabled = false
    override val isDebuggable = false
}
