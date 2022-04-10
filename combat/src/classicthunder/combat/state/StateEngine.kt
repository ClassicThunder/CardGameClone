package classicthunder.combat.state

import classicthunder.combat.CombatEngine

class StateEngine(
    engine: CombatEngine,
    startingState: EngineState,
    private val stateMap: Map<EngineState, State> = mapOf(
        EngineState.PlayerControl to PlayerControlState(engine.hand, engine.discarder, engine.energy),
        EngineState.Discarding to DiscardingState(engine.hand, engine.discarder),
        EngineState.Drawing to DrawingState(engine.hand, engine.drawPile, engine.discardPile, engine.discarder),
        EngineState.EnemyTurn to EnemyTurnState(engine.player, engine.enemy),
        EngineState.Done to DoneState(engine.discarder)))
{
    var currentState: EngineState = startingState
        private set
    var desiredState: EngineState = startingState
        private set

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
