package com.mygdx.game.deckengine.state;

import com.mygdx.game.deckengine.energy.Energy;
import com.mygdx.game.deckengine.hand.Discarder;
import com.mygdx.game.deckengine.hand.Hand;
import com.mygdx.game.deckengine.pile.DiscardPile;
import com.mygdx.game.deckengine.pile.DrawPile;

public class PlayerControlState extends State {

    public PlayerControlState(Hand hand, DrawPile drawPile, DiscardPile discardPile, Discarder discarder, Energy energy) {

        super(hand, drawPile, discardPile, discarder, energy);
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
