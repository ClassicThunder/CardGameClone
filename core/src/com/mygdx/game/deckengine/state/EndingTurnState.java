package com.mygdx.game.deckengine.state;

import com.mygdx.game.deckengine.hand.Discarder;
import com.mygdx.game.deckengine.hand.Hand;
import com.mygdx.game.deckengine.pile.DiscardPile;
import com.mygdx.game.deckengine.pile.DrawPile;

/**
 * 1. Discard all the cards
 * 2. Draw
 * 3. If draw runs out of cards Shuffle
 * 4. Start new turn
 */
public class EndingTurnState extends State {

    public EndingTurnState(Hand hand, DrawPile drawPile, DiscardPile discardPile, Discarder discarder) {

        super(hand, drawPile, discardPile, discarder);
    }

    @Override
    public void Enter() {

        hand.DiscardHand(discarder);
    }

    @Override
    public EngineState Update() {

        hand.Update();
        discarder.Update();

        if (discarder.GetCardCount() == 0) {
            return EngineState.Drawing;
        }

        return EngineState.EndingTurn;
    }

    @Override
    public void Exit() {

    }
}
