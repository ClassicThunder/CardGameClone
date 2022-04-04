package classicthunder.combat;

import classicthunder.cards.Card;
import classicthunder.cards.Deck;
import classicthunder.combat.card.CardActor;
import classicthunder.combat.character.CharacterActor;
import classicthunder.combat.character.CharacterStats;
import classicthunder.combat.energy.Energy;
import classicthunder.combat.energy.EnergyFunction;
import classicthunder.combat.hand.DiscardFunction;
import classicthunder.combat.hand.Discarder;
import classicthunder.combat.hand.Hand;
import classicthunder.combat.layout.DeckLayout;
import classicthunder.combat.pile.DiscardPile;
import classicthunder.combat.pile.DrawPile;
import classicthunder.combat.pile.PileFunction;
import classicthunder.combat.state.*;
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
                        CharacterActor player,
                        CharacterActor enemy,
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

        stateMapping.put(EngineState.PlayerControl, new PlayerControlState(hand, discarder, energy));
        stateMapping.put(EngineState.Discarding, new DiscardingState(hand, discarder));
        stateMapping.put(EngineState.Drawing, new DrawingState(hand, drawPile, discardPile, discarder));
        stateMapping.put(EngineState.EnemyTurn, new EnemyTurnState(player, enemy));

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
            updateState(EngineState.Discarding);
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
