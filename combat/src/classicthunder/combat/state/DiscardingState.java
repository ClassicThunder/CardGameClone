package classicthunder.combat.state;

import classicthunder.combat.hand.Discarder;
import classicthunder.combat.hand.Hand;

public class DiscardingState implements State {

    private final Hand hand;
    private final Discarder discarder;

    public DiscardingState(Hand hand, Discarder discarder) {

        this.hand = hand;
        this.discarder = discarder;
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
            return EngineState.EnemyTurn;
        }

        return EngineState.Discarding;
    }

    @Override
    public void Exit() {

    }
}
