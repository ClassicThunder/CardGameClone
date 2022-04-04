package classicthunder.combat.combat;

import classicthunder.combat.cards.Card;
import classicthunder.combat.cards.Deck;
import classicthunder.combat.combat.card.CardActor;
import classicthunder.combat.combat.character.CharacterEntity;
import classicthunder.combat.combat.character.CharacterStats;
import classicthunder.combat.combat.energy.Energy;
import classicthunder.combat.combat.energy.EnergyFunction;
import classicthunder.combat.combat.hand.DiscardFunction;
import classicthunder.combat.combat.hand.Discarder;
import classicthunder.combat.combat.hand.Hand;
import classicthunder.combat.combat.layout.DeckLayout;
import classicthunder.combat.combat.pile.DiscardPile;
import classicthunder.combat.combat.pile.DrawPile;
import classicthunder.combat.combat.pile.PileFunction;
import classicthunder.combat.combat.state.*;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CombatEngine {

    final Energy energy;
    final Deck deck;
    final Hand hand;
    final Discarder discarder;
    final DrawPile drawPile;
    final DiscardPile discardPile;

    final DeckLayout layout;

    // Input
    private final CombatEngineInputProcessor gameInputProcessor;
    public InputProcessor getGameInputProcessor() {
        return gameInputProcessor.ip;
    }

    // State
    Map<EngineState, State> stateMapping = new HashMap<>();
    EngineState currentEngineState = EngineState.PlayerControl;
    State currentState;

    public CombatEngine(DeckLayout layout,
                        Deck deck,
                        CharacterEntity player,
                        CharacterEntity enemy,
                        PileFunction drawPileUpdate,
                        PileFunction discardPileUpdate,
                        EnergyFunction energyUpdate) {

        this.layout = layout;

        // Make a snapshot of the deck as actors
        List<CardActor> actors = new ArrayList<>();
        this.deck = deck;
        for (Card c : deck.GetCards()) {
            CardActor cardActor = new CardActor(layout, c);
            actors.add(cardActor);
        }


        discarder = new Discarder(layout.getDiscardPosition(),
                new DiscardFunction() {
                    @Override
                    public void onDiscard(List<CardActor> cards) {

                        for (CardActor card : cards) {
                            card.ResetToDrawPosition();
                        }

                        discardPile.AddCards(cards);
                    }
                });

        hand = new Hand(layout, 0.35f);

        gameInputProcessor = new CombatEngineInputProcessor(this, player, enemy);

        drawPile = new DrawPile(drawPileUpdate);
        drawPile.SetPile(actors);

        discardPile = new DiscardPile(discardPileUpdate);

        for (int i = 0; i < 5; i++) {
            hand.AddCard(drawPile.DrawTopCard());
        }

        energy = new Energy(3, energyUpdate);

        stateMapping.put(
                EngineState.PlayerControl,
                new PlayerControlState(hand, drawPile, discardPile, discarder, energy));
        stateMapping.put(
                EngineState.EndingTurn,
                new EndingTurnState(hand, drawPile, discardPile, discarder, energy));
        stateMapping.put(
                EngineState.Drawing,
                new DrawingState(hand, drawPile, discardPile, discarder, energy));

        currentState = stateMapping.get(EngineState.PlayerControl);
        currentState.Enter();
    }

    boolean canPlayCard(CharacterStats character, CardActor card) {

        if (energy.GetEnergy() >= card.GetEnergyCost()) {
            return card.CanApplyEffects(character);
        }

        return false;
    }

    void playCard(CharacterStats character, CardActor card) {

        energy.AlterEnergy(-card.GetEnergyCost());
        card.ApplyEffects(character);
        hand.DiscardCard(discarder, card);
    }

    private void updateState(EngineState desiredState) {

        if (desiredState != currentEngineState) {

            currentEngineState = desiredState;

            currentState.Exit();
            currentState = stateMapping.get(currentEngineState);
            currentState.Enter();
        }
    }

    // Turn Management
    public void requestEndTurn() {

        if (currentEngineState == EngineState.PlayerControl) {
            updateState(EngineState.EndingTurn);
        }
    }

    // Life Cycle
    public void Reset() {

        hand.ClearCards();
        drawPile.ClearCards();
        discardPile.ClearCards();
        discarder.ClearCards();
    }

    public void Update() {

        EngineState desiredState = this.currentState.Update();
        updateState(desiredState);
    }

    public void Draw(SpriteBatch batch, PolygonSpriteBatch polygonBatch) {

        hand.Draw(batch);
        hand.Draw(polygonBatch);

        discarder.Draw(polygonBatch);
    }
}
