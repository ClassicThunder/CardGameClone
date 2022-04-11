package classicthunder.combat.state

import classicthunder.combat.hand.Discarder
import classicthunder.combat.hand.Hand
import classicthunder.combat.pile.DiscardPile
import classicthunder.combat.pile.DrawPile

internal class DrawingState(
    private val hand: Hand,
    private val drawPile: DrawPile,
    private val discardPile: DiscardPile,
    private val discarder: Discarder
) : State {
    private val DRAW_TIMER = 20
    var timer = 0
    override fun enter() {
        timer = 0
    }

    override fun update(): EngineState {
        if (timer >= DRAW_TIMER && hand.getCardCount() < 5) {
            if (drawPile.getCardCount() == 0) {
                drawPile.setPile(discardPile.empty())
            }
            hand.addCard(drawPile.drawTopCard())
            timer = 0
        }
        timer++
        hand.update()
        discarder.update()
        return if (hand.getCardCount() == 5 && timer == 20) {
            EngineState.PlayerControl
        } else EngineState.Drawing
    }

    override fun exit() {}
}