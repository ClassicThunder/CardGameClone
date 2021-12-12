package com.mygdx.game.deckengine;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Deck;
import com.mygdx.game.GameContent;
import com.mygdx.game.DeckEngineInputProcessor;
import com.mygdx.game.character.CharacterEntity;
import com.mygdx.game.deckengine.cards.*;
import com.mygdx.game.deckengine.hand.DiscardFunction;
import com.mygdx.game.deckengine.hand.Discarder;
import com.mygdx.game.deckengine.hand.Hand;
import com.mygdx.game.deckengine.state.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DeckEngine {

    final private CardLayout cardLayout;
    final private Discarder discarder;

    final private Deck deck;

    final private Hand hand;
    final private DrawPile drawPile;
    final private DiscardPile discardPile;

    private EngineState currentEngineState = EngineState.PlayerControl;
    private State currentState;
    final private Map<EngineState, State> stateMapping = new HashMap<>();

    private final DeckEngineInputProcessor gameInputProcessor;

    public DeckEngine(float center, float handWidth,
                      Vector2 drawLocation, Vector2 discardLocation,
                      GameContent content, CharacterEntity player, CharacterEntity enemy) {

        cardLayout = new CardLayout(
                new CardPosition(drawLocation, new Vector2(100, 150), 0f),
                new CardPosition(discardLocation, new Vector2(100, 150), 0f));

        this.discarder = new Discarder(cardLayout.getDiscardPosition(),
                new DiscardFunction() {
                    @Override
                    public void onDiscard(List<Card> cards) {
                        discardPile.AddCards(cards);

                        // Shuffle if the hand is empty.
                        if (hand.GetCardCount() == 0) {
                            System.out.println("BOO!");

                            drawPile.SetPile(discardPile.Empty());
                        }
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

        gameInputProcessor = new DeckEngineInputProcessor(hand, discarder, player, enemy);
        gameInputProcessor.Activate();

        drawPile = new DrawPile();
        drawPile.SetPile(deck.GetCards());

        discardPile = new DiscardPile();

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

        currentState = stateMapping.get(EngineState.PlayerControl);
    }

    public void Reset() {

        this.hand.ClearCards();
        this.drawPile.ClearCards();
        this.discardPile.ClearCards();
        this.discarder.ClearCards();
    }

    public void Update() {

        EngineState desiredState = this.currentState.Update();

        if (desiredState != currentEngineState) {
            this.currentState.Exit();
            this.currentState = stateMapping.get(desiredState);
            this.currentState.Enter();
        }
    }

    public void Draw(SpriteBatch batch, PolygonSpriteBatch polygonBatch) {

        hand.Draw(batch);
        hand.Draw(polygonBatch);

        discarder.Draw(polygonBatch);
    }
}
