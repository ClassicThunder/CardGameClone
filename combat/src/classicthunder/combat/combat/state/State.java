package classicthunder.combat.combat.state;

import classicthunder.combat.combat.energy.Energy;
import classicthunder.combat.combat.hand.Discarder;
import classicthunder.combat.combat.hand.Hand;
import classicthunder.combat.combat.pile.DiscardPile;
import classicthunder.combat.combat.pile.DrawPile;

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
