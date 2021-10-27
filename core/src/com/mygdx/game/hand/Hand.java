package com.mygdx.game.hand;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Bezier;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.cards.Card;

import java.util.ArrayList;
import java.util.List;

public class Hand {

    final int MAX_CARDS = 11;
    private final List<Card> cardsInHand = new ArrayList<>(MAX_CARDS);

    private final float centerX;
    private final float maxWidth;

    static public final float CARD_WIDTH = 678;
    static public final float CARD_HEIGHT = 874;

    private final Vector2 cardSize = new Vector2(CARD_WIDTH, CARD_HEIGHT);

    public Hand(float centerX, float maxWidth, float cardScale) {

        this.centerX = centerX;
        this.maxWidth = maxWidth;

        this.cardSize.scl(cardScale);
    }

    public void AddCard(Card card) {
        cardsInHand.add(0, card);
    }

    private Vector2 getCardLocationBezier(float interpolationAmount) {

        float cardSpace = (cardSize.x * 0.55f);
        float size = (cardSpace * (cardsInHand.size() - 1));
        Vector2 left = new Vector2(centerX - (size / 2), 0);
        Vector2 right = new Vector2(centerX + (size / 2), 0);

        Vector2 location = Bezier.cubic(new Vector2(), interpolationAmount,
                left, new Vector2(centerX, 100f),
                right, new Vector2(centerX, 100f),
                new Vector2());

        return location;
    }

    public void Update(final Vector2 mouseLocation) {

        for (Card card: cardsInHand) {
            card.Update();
        }

        for (int x = cardsInHand.size() - 1; x >= 0; x--) {
            if (cardsInHand.get(x).ContainsMouse(mouseLocation)) {
                return;
            }
        }

    }

    public void Draw(SpriteBatch batch, Texture debugTexture) {

        float width = maxWidth - cardSize.x;
        float left = centerX - (width / 2f);
        float right = centerX + (width / 2f);

        batch.draw(debugTexture, left, 0f, width, 200f);
    }

    public void Draw(PolygonSpriteBatch polygonBatch) {

        float width = maxWidth - cardSize.x;
        float left = centerX - (width / 2f);
        float right = centerX + (width / 2f);

        for (int x = 0; x < cardsInHand.size(); x++) {

            float pct = (float)x / (float)(cardsInHand.size() - 1);

            Vector2 location = Bezier.quadratic(new Vector2(), pct,
                    new Vector2(left, 0),
                    new Vector2(centerX , 100f),
                    new Vector2(right, 0),
                    new Vector2());

            float maxY = Bezier.quadratic(new Vector2(), 0.5f,
                    new Vector2(left, 0),
                    new Vector2(centerX , 100f),
                    new Vector2(right, 0),
                    new Vector2()).y;

            float rotation = (maxY - location.y) * .25f;
            if (location.x > centerX) {
                rotation *= -1f;
            }

            cardsInHand.get(x).Draw(polygonBatch, location, cardSize, rotation);
        }
    }
}
