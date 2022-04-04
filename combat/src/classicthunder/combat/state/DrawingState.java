package classicthunder.combat.state;

import classicthunder.combat.hand.Discarder;
import classicthunder.combat.hand.Hand;
import classicthunder.combat.pile.DiscardPile;
import classicthunder.combat.pile.DrawPile;

public class DrawingState implements State {

    private final Hand hand;
    private final DrawPile drawPile;
    private final DiscardPile discardPile;
    private final Discarder discarder;

    private final int DRAW_TIMER = 20;
    int timer = 0;

    public DrawingState(Hand hand, DrawPile drawPile, DiscardPile discardPile, Discarder discarder) {

        this.hand = hand;
        this.drawPile = drawPile;
        this.discardPile = discardPile;
        this.discarder = discarder;
    }

    @Override
    public void Enter() {

        timer = 0;
    }

    @Override
    public EngineState Update() {

        if (timer >= DRAW_TIMER && hand.GetCardCount() < 5) {

            if (drawPile.GetCardCount() == 0) {
                drawPile.SetPile(discardPile.Empty());
            }

            hand.AddCard(drawPile.DrawTopCard());
            timer = 0;
        }

        timer++;

        hand.Update();
        discarder.Update();

        if (hand.GetCardCount() == 5 && timer == 20) {
            return EngineState.PlayerControl;
        }

        return EngineState.Drawing;
    }

    @Override
    public void Exit() {

    }
}
