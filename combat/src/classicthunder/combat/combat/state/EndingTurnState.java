package classicthunder.combat.combat.state;

import classicthunder.combat.combat.energy.Energy;
import classicthunder.combat.combat.hand.Discarder;
import classicthunder.combat.combat.hand.Hand;
import classicthunder.combat.combat.pile.DiscardPile;
import classicthunder.combat.combat.pile.DrawPile;
public class EndingTurnState extends State {

    public EndingTurnState(Hand hand, DrawPile drawPile, DiscardPile discardPile, Discarder discarder, Energy energy) {

        super(hand, drawPile, discardPile, discarder, energy);
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
