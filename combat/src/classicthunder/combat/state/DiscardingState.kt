package classicthunder.combat.state

import classicthunder.combat.hand.Discarder
import classicthunder.combat.hand.Hand

internal class DiscardingState(private val hand: Hand, private val discarder: Discarder) : State {
    override fun enter() {
        hand.discardHand(discarder)
    }

    override fun update(): EngineState {
        hand.update()
        discarder.update()
        return if (discarder.getCardCount() == 0) {
            EngineState.EnemyTurn
        } else EngineState.Discarding
    }

    override fun exit() {}
}