package com.mygdx.game.deckengine.state;

import com.mygdx.game.deckengine.DiscardPile;
import com.mygdx.game.deckengine.DrawPile;
import com.mygdx.game.deckengine.hand.Discarder;
import com.mygdx.game.deckengine.hand.Hand;

public class PlayerControlState extends State {

    public PlayerControlState(Hand hand, DrawPile drawPile, DiscardPile discardPile, Discarder discarder) {

        super(hand, drawPile, discardPile, discarder);
    }

    @Override
    public void Enter() {

    }

    @Override
    public EngineState Update() {

        hand.Update();
        discarder.Update();

        if (hand.GetCardCount() == 0) {
            return EngineState.Shuffling;
        }

        return EngineState.PlayerControl;
    }

    @Override
    public void Exit() {

    }
}
