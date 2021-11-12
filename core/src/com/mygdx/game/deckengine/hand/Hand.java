package com.mygdx.game.deckengine.hand;

import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.deckengine.cards.Card;

import java.util.ArrayList;
import java.util.List;

public class Hand {

    final int MAX_CARDS = 11;
    private final List<Card> cardsInHand = new ArrayList<>(MAX_CARDS);

    private final CardBezier cardBezier;
    private final Rectangle cardArea;

    static public final float CARD_WIDTH = 678;
    static public final float CARD_HEIGHT = 874;

    private final Vector2 cardSize = new Vector2(CARD_WIDTH, CARD_HEIGHT);

    public Hand(float centerX, float maxWidth, float cardScale) {

        this.cardSize.scl(cardScale);

        this.cardArea = new Rectangle(centerX - (maxWidth / 2f), 0, maxWidth, 200);
        this.cardBezier = new CardBezier(cardArea, this.cardSize);
    }

    // Card Info
    public void AddCard(Card card) {
        cardsInHand.add(0, card);
        this.cardBezier.FindRestingPosition(cardsInHand);
    }

    public void ClearCards() {
        cardsInHand.clear();
        this.cardBezier.FindRestingPosition(cardsInHand);
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

    public void ResetCards() {

        for (Card card : cardsInHand) {
            card.Reset();
        }
    }

    // LifeCycle
    public void Update(final Vector2 mouseLocation) {

        for (Card card : cardsInHand) {
            card.Update();
        }
    }

    public void Draw(PolygonSpriteBatch polygonBatch) {

        for (Card card : cardsInHand) {
            card.Draw(polygonBatch);
        }
    }
}
