package classicthunder.combat.combat.state;

import classicthunder.combat.combat.energy.Energy;
import classicthunder.combat.combat.hand.Discarder;
import classicthunder.combat.combat.hand.Hand;
import classicthunder.combat.combat.pile.DiscardPile;
import classicthunder.combat.combat.pile.DrawPile;

public class DiscardingState extends State {

    public DiscardingState(Hand hand, DrawPile drawPile, DiscardPile discardPile, Discarder discarder, Energy energy) {

        super(hand, drawPile, discardPile, discarder, energy);
    }

    @Override
    public void Enter() {

    }

    @Override
    public EngineState Update() {

        hand.Update();
        discarder.Update();

        return EngineState.PlayerControl;
    }

    @Override
    public void Exit() {

    }
}
