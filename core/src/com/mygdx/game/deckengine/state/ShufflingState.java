package com.mygdx.game.deckengine.state;

import com.mygdx.game.deckengine.hand.Discarder;
import com.mygdx.game.deckengine.hand.Hand;
import com.mygdx.game.deckengine.pile.DiscardPile;
import com.mygdx.game.deckengine.pile.DrawPile;

public class ShufflingState extends State {

    public ShufflingState(Hand hand, DrawPile drawPile, DiscardPile discardPile, Discarder discarder) {

        super(hand, drawPile, discardPile, discarder);
    }

    @Override
    public void Enter() {

    }

    @Override
    public EngineState Update() {

        hand.Update();
        discarder.Update();

        return EngineState.Shuffling;
    }

    @Override
    public void Exit() {

    }
}
