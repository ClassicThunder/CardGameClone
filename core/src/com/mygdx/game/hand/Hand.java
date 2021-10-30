package com.mygdx.game.hand;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.cards.Card;

import java.util.ArrayList;
import java.util.List;

public class Hand {

    final int MAX_CARDS = 11;
    private final List<Card> cardsInHand = new ArrayList<>(MAX_CARDS);

    private final float centerX;
    private final float maxWidth;

    private final CardBezier cardBezier;

    static public final float CARD_WIDTH = 678;
    static public final float CARD_HEIGHT = 874;

    private final Vector2 cardSize = new Vector2(CARD_WIDTH, CARD_HEIGHT);

    public Hand(float centerX, float maxWidth, float cardScale) {

        this.centerX = centerX;
        this.maxWidth = maxWidth;

        this.cardSize.scl(cardScale);

        Rectangle cardArea = new Rectangle(centerX - (maxWidth / 2f), 0, maxWidth, 200);
        this.cardBezier = new CardBezier(cardArea, this.cardSize);
    }

    public void AddCard(Card card) {
        cardsInHand.add(0, card);
    }

    public void Update(final Vector2 mouseLocation) {

        for (Card card: cardsInHand) {
            card.Reset();
        }

        this.cardBezier.FindRestingPosition(cardsInHand);

        for (Card card: cardsInHand) {
            card.Update();
        }

        for (int x = cardsInHand.size() - 1; x >= 0; x--) {
            if (cardsInHand.get(x).ContainsMouse(mouseLocation)) {
                break;
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

        for (Card card: cardsInHand) {
            card.Draw(polygonBatch);
        }
    }
}
