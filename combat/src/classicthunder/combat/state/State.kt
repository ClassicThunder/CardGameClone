package classicthunder.combat.state

internal interface State {
    fun enter()
    fun update(): EngineState
    fun exit()
}