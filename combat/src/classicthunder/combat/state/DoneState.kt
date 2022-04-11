package classicthunder.combat.state

import classicthunder.combat.hand.Discarder

internal class DoneState(
    private val discarder: Discarder
) : State {
    override fun enter() {}

    override fun update(): EngineState {
        discarder.update()

        return EngineState.Done
    }

    override fun exit() {}
}