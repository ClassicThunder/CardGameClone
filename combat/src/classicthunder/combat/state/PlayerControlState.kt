package classicthunder.combat.state

import classicthunder.combat.energy.Energy
import classicthunder.combat.hand.Discarder
import classicthunder.combat.hand.Hand

class PlayerControlState(
    private val hand: Hand,
    private val discarder: Discarder,
    private val energy: Energy) : State
{
    override fun enter() {
        energy.resetToBaseAmount()
    }

    override fun update(): EngineState {
        hand.Update()
        discarder.Update()
        return EngineState.PlayerControl
    }

    override fun exit() {}
}