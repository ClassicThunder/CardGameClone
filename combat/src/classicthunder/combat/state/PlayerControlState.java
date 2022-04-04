package classicthunder.combat.state;

import classicthunder.combat.energy.Energy;
import classicthunder.combat.hand.Discarder;
import classicthunder.combat.hand.Hand;

public class PlayerControlState implements State {

    private final Hand hand;
    private final Discarder discarder;
    private final Energy energy;

    public PlayerControlState(Hand hand, Discarder discarder, Energy energy) {

        this.hand = hand;
        this.discarder = discarder;
        this.energy = energy;
    }

    @Override
    public void Enter() {

        energy.ResetToBaseAmount();
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
