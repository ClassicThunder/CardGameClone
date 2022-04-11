package classicthunder.combat.state

import classicthunder.combat.CombatEngine

internal class StateEngine(engine: CombatEngine)
{
    private val stateMap: Map<EngineState, State> = mapOf(
        EngineState.PlayerControl to PlayerControlState(engine.hand, engine.discarder, engine.energy),
        EngineState.Discarding to DiscardingState(engine.hand, engine.discarder),
        EngineState.Drawing to DrawingState(engine.hand, engine.drawPile, engine.discardPile, engine.discarder),
        EngineState.EnemyTurn to EnemyTurnState(engine.playerActor, engine.enemyActor),
        EngineState.Done to DoneState(engine.discarder))

    internal var currentState: EngineState = EngineState.PlayerControl
        private set
    private var desiredState: EngineState = EngineState.PlayerControl

    init {
        stateMap[currentState]!!.enter()
    }

    fun forceState(state: EngineState) {
        desiredState = state
        update()
    }

    fun update() {
        if (desiredState != currentState) {

            stateMap[currentState]!!.exit()
            currentState = desiredState
            stateMap[currentState]!!.enter()
        }

        desiredState = stateMap[currentState]!!.update()
    }
}
