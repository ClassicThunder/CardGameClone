package com.mygdx.game.deckengine.state;

import com.mygdx.game.deckengine.energy.Energy;
import com.mygdx.game.deckengine.hand.Discarder;
import com.mygdx.game.deckengine.hand.Hand;
import com.mygdx.game.deckengine.pile.DiscardPile;
import com.mygdx.game.deckengine.pile.DrawPile;

public abstract class State {

    final protected Hand hand;
    final protected DrawPile drawPile;
    final protected DiscardPile discardPile;
    final protected Discarder discarder;
    final protected Energy energy;

    protected State(Hand hand, DrawPile drawPile, DiscardPile discardPile, Discarder discarder, Energy energy) {

        this.hand = hand;
        this.drawPile = drawPile;
        this.discardPile = discardPile;
        this.discarder = discarder;
        this.energy = energy;
    }

    public abstract void Enter();

    public abstract EngineState Update();

    public abstract void Exit();
}
