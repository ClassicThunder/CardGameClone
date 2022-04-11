package classicthunder.combat.state

import classicthunder.combat.energy.Energy
import classicthunder.combat.hand.Discarder
import classicthunder.combat.hand.Hand

internal class PlayerControlState(
    private val hand: Hand,
    private val discarder: Discarder,
    private val energy: Energy) : State
{
    override fun enter() {
        energy.resetToBaseAmount()
    }

    override fun update(): EngineState {
        hand.Update()
        discarder.update()
        return EngineState.PlayerControl
    }

    override fun exit() {}
}