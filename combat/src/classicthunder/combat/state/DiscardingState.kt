package classicthunder.combat.state

import classicthunder.combat.hand.Discarder
import classicthunder.combat.hand.Hand

class DiscardingState(private val hand: Hand, private val discarder: Discarder) : State {
    override fun enter() {
        hand.DiscardHand(discarder)
    }

    override fun update(): EngineState {
        hand.Update()
        discarder.Update()
        return if (discarder.GetCardCount() == 0) {
            EngineState.EnemyTurn
        } else EngineState.Discarding
    }

    override fun exit() {}
}