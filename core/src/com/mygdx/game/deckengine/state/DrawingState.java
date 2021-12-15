package com.mygdx.game.deckengine.state;

import com.mygdx.game.deckengine.energy.Energy;
import com.mygdx.game.deckengine.hand.Discarder;
import com.mygdx.game.deckengine.hand.Hand;
import com.mygdx.game.deckengine.pile.DiscardPile;
import com.mygdx.game.deckengine.pile.DrawPile;

public class DrawingState extends State {

    private final int DRAW_TIMER = 20;
    int timer = 0;

    public DrawingState(Hand hand, DrawPile drawPile, DiscardPile discardPile, Discarder discarder, Energy energy) {

        super(hand, drawPile, discardPile, discarder, energy);
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
