package com.mygdx.game.deckengine.hand;

import com.badlogic.gdx.math.Bezier;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.deckengine.cards.Card;

import java.util.List;

public class CardBezier {

    private final Vector2 cardSize;

    private final float centerX;
    private final float paddedLeft;
    private final float paddedRight;

    public CardBezier(Rectangle area, Vector2 cardSize) {

        this.cardSize = cardSize;

        centerX = area.x + (area.width / 2);

        float width = area.width - cardSize.x;

        paddedLeft = centerX - (width / 2f);
        paddedRight = centerX + (width / 2f);
    }

    public void FindRestingPosition(List<Card> cardsInHand) {

        if (cardsInHand.size() == 0) {
            return;
        }

        // increment should be bigger up to a point
        float increment = (1f / 11f);

        if (cardsInHand.size() % 2 == 0) {

            float leftIncrement = 0.5f - (((cardsInHand.size() - 1) * increment) / 2f);

            for (int x = 0; x < cardsInHand.size(); x++) {

                float xIncrement = leftIncrement + (x * increment);
                layoutCard(cardsInHand.get(x), xIncrement);
            }

        } else {

            if (cardsInHand.size() == 1) {
                layoutCard(cardsInHand.get(0), 0.5f);
            } else {
                int centerCard = (cardsInHand.size() - 1) / 2;

                layoutCard(cardsInHand.get(centerCard), 0.5f);

                for (int x = 1; x <= centerCard; x++) {

                    int backCard = centerCard - x;
                    float backPct = 0.5f - (increment * x);
                    layoutCard(cardsInHand.get(backCard), backPct);

                    int forwardCard = centerCard + x;
                    float forwardPct = 0.5f + (increment * x);
                    layoutCard(cardsInHand.get(forwardCard), forwardPct);
                }
            }
        }
    }

    private void layoutCard(Card card, float pct) {

        Vector2 location = Bezier.quadratic(new Vector2(), pct,
                new Vector2(paddedLeft, 0),
                new Vector2(centerX, 100f),
                new Vector2(paddedRight, 0),
                new Vector2());

        float maxY = Bezier.quadratic(new Vector2(), 0.5f,
                new Vector2(paddedLeft, 0),
                new Vector2(centerX, 100f),
                new Vector2(paddedRight, 0),
                new Vector2()).y;

        float rotation = (maxY - location.y) * .25f;

        if (location.x > centerX) {
            rotation *= -1f;
        }

        card.SetRestingPosition(location, rotation, cardSize);
    }
}
