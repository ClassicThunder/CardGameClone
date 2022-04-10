package classicthunder.combat.state

interface State {
    fun enter()
    fun update(): EngineState
    fun exit()
}