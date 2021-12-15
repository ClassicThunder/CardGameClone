package com.mygdx.game.deckengine.hand;

import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.GameContent;
import com.mygdx.game.deckengine.card.Card;

import java.util.ArrayList;
import java.util.List;

public class Hand {

    static public final float CARD_WIDTH = 678;
    static public final float CARD_HEIGHT = 874;
    final int MAX_CARDS = 11;

    private final List<Card> cardsInHand = new ArrayList<>(MAX_CARDS);
    private final GameContent content;
    private final CardBezier cardBezier;

    public Hand(GameContent content, float centerX, float maxWidth, float cardScale) {

        this.content = content;

        Vector2 cardSize = new Vector2(CARD_WIDTH, CARD_HEIGHT);
        cardSize.scl(cardScale);

        Rectangle cardArea = new Rectangle(centerX - (maxWidth / 2f), 0, maxWidth, 200);
        this.cardBezier = new CardBezier(cardArea, cardSize);
    }

    // Card Info
    public void AddCard(Card card) {

        cardsInHand.add(0, card);
        this.cardBezier.FindRestingPosition(cardsInHand);
    }

    public void ClearCards() {

        cardsInHand.clear();
    }

    public int GetCardCount() {

        return cardsInHand.size();
    }

    // Collision Detection
    public Card GrabCard(Vector2 mouse) {

        for (int x = cardsInHand.size() - 1; x >= 0; x--) {
            Card currentCard = cardsInHand.get(x);
            if (currentCard.ContainsMouse(mouse)) {

                currentCard.SetGrabbed(true);
                currentCard.SetDragPosition(mouse);

                return currentCard;
            }
        }

        return null;
    }

    public void DiscardHand(Discarder discard) {

        discard.AddCards(cardsInHand);
        cardsInHand.clear();
    }

    public void DiscardCard(Discarder discard, Card card) {

        discard.AddCard(card);
        cardsInHand.remove(card);
        this.cardBezier.FindRestingPosition(cardsInHand);
    }

    public void ResetCards() {

        for (Card card : cardsInHand) {
            card.Reset();
        }
    }

    // LifeCycle
    public void Update() {

        for (Card card : cardsInHand) {
            card.Update();
        }
    }

    public void Draw(PolygonSpriteBatch polygonBatch) {

        for (Card card : cardsInHand) {
            card.Draw(polygonBatch, 1.0f);
        }
    }

    public void Draw(SpriteBatch batch) {

        int x = 0;
        for (Card card : cardsInHand) {

            content.GetDebugFont().draw(batch, "[" + x + "]",
                    card.GetActualLocation().x,
                    card.GetActualLocation().y + (card.GetActualSize().y / 2f) + 25f);
            x++;
        }
    }
}
