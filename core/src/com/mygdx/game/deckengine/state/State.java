package com.mygdx.game.deckengine.state;

import com.mygdx.game.deckengine.DiscardPile;
import com.mygdx.game.deckengine.DrawPile;
import com.mygdx.game.deckengine.hand.Discarder;
import com.mygdx.game.deckengine.hand.Hand;

public abstract class State {

    final protected Hand hand;
    final protected DrawPile drawPile;
    final protected DiscardPile discardPile;;
    final protected Discarder discarder;

    protected State(Hand hand, DrawPile drawPile, DiscardPile discardPile, Discarder discarder) {

        this.hand = hand;
        this.drawPile = drawPile;
        this.discardPile = discardPile;
        this.discarder = discarder;
    }

    public abstract void Enter();

    public abstract EngineState Update();

    public abstract void Exit();
}
