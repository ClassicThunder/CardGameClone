package com.mygdx.game.deckengine;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Deck;
import com.mygdx.game.GameContent;
import com.mygdx.game.character.CharacterEntity;
import com.mygdx.game.deckengine.card.*;
import com.mygdx.game.deckengine.hand.DiscardFunction;
import com.mygdx.game.deckengine.hand.Discarder;
import com.mygdx.game.deckengine.hand.Hand;
import com.mygdx.game.deckengine.pile.DiscardPile;
import com.mygdx.game.deckengine.pile.DrawPile;
import com.mygdx.game.deckengine.pile.PileFunction;
import com.mygdx.game.deckengine.state.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DeckEngine {

    final Deck deck;
    final Hand hand;
    final Discarder discarder;
    final DrawPile drawPile;
    final DiscardPile discardPile;
    final private CardLayout cardLayout;
    final private Map<EngineState, State> stateMapping = new HashMap<>();
    private final DeckEngineInputProcessor gameInputProcessor;
    private EngineState currentEngineState = EngineState.PlayerControl;
    private State currentState;

    public DeckEngine(float center, float handWidth,
                      Vector2 drawLocation, Vector2 discardLocation,
                      GameContent content, CharacterEntity player, CharacterEntity enemy,
                      PileFunction drawPileUpdate, PileFunction discardPileUpdate) {

        cardLayout = new CardLayout(
                new CardPosition(drawLocation, new Vector2(100, 150), 0f),
                new CardPosition(discardLocation, new Vector2(100, 150), 0f));

        discarder = new Discarder(cardLayout.getDiscardPosition(),
                new DiscardFunction() {
                    @Override
                    public void onDiscard(List<Card> cards) {

                        for (Card card : cards) {
                            card.ResetToDrawPosition();
                        }

                        discardPile.AddCards(cards);
                    }
                });

        Texture s = content.GetTexture("CARD_STRIKE");
        Texture d = content.GetTexture("CARD_DEFEND");

        deck = new Deck();
        deck.AddCard(new StrikeCard(cardLayout, s));
        deck.AddCard(new StrikeCard(cardLayout, s));
        deck.AddCard(new StrikeCard(cardLayout, s));
        deck.AddCard(new StrikeCard(cardLayout, s));
        deck.AddCard(new StrikeCard(cardLayout, s));
        deck.AddCard(new StrikeCard(cardLayout, s));
        deck.AddCard(new DefendCard(cardLayout, d));
        deck.AddCard(new DefendCard(cardLayout, d));
        deck.AddCard(new DefendCard(cardLayout, d));
        deck.AddCard(new DefendCard(cardLayout, d));

        hand = new Hand(content, cardLayout, center, handWidth, 0.35f);

        gameInputProcessor = new DeckEngineInputProcessor(this, player, enemy);
        gameInputProcessor.Activate();

        drawPile = new DrawPile(drawPileUpdate);
        drawPile.SetPile(deck.GetCards());

        discardPile = new DiscardPile(discardPileUpdate);

        for (int i = 0; i < 5; i++) {
            hand.AddCard(drawPile.DrawTopCard());
        }

        stateMapping.put(
                EngineState.PlayerControl,
                new PlayerControlState(hand, drawPile, discardPile, discarder));
        stateMapping.put(
                EngineState.Shuffling,
                new ShufflingState(hand, drawPile, discardPile, discarder));
        stateMapping.put(
                EngineState.Discarding,
                new DiscardingState(hand, drawPile, discardPile, discarder));
        stateMapping.put(
                EngineState.EndingTurn,
                new EndingTurnState(hand, drawPile, discardPile, discarder));
        stateMapping.put(
                EngineState.Drawing,
                new DrawingState(hand, drawPile, discardPile, discarder));

        currentState = stateMapping.get(EngineState.PlayerControl);
    }

    void requestEndTurn() {

        if (currentEngineState == EngineState.PlayerControl) {
            updateState(EngineState.EndingTurn);
        }
    }

    private void updateState(EngineState desiredState) {

        if (desiredState != currentEngineState) {

            currentEngineState = desiredState;

            currentState.Exit();
            currentState = stateMapping.get(currentEngineState);
            currentState.Enter();
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
