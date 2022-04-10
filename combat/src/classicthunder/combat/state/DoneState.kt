package classicthunder.combat.state

import classicthunder.combat.hand.Discarder

class DoneState(
    private val discarder: Discarder) : State
{
    override fun enter() {}

    override fun update(): EngineState {
        discarder.Update()

        return EngineState.Done
    }

    override fun exit() {}
}