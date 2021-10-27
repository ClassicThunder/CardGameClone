package com.mygdx.game.hand;

import com.badlogic.gdx.math.Bezier;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.cards.Card;

import java.util.List;

public class CardBezier {

    private final Vector2 reuse = new Vector2();

    private final float centerX;
    private final float paddedLeft;
    private final float paddedRight;

    public CardBezier(Rectangle area, Vector2 cardSize) {

        centerX = area.x + (area.width / 2);

        float width = area.width - cardSize.x;

        paddedLeft = centerX - (width / 2f);
        paddedRight = centerX + (width / 2f);
    }

    public void LayoutCards(List<Card> cardsInHand) {

        float cardCount = (float)(cardsInHand.size() - 1);

        for (int x = 0; x < cardsInHand.size(); x++) {

            float pct = (float)x / cardCount;

            Vector2 location = Bezier.quadratic(reuse, pct,
                    new Vector2(paddedLeft, 0),
                    new Vector2(centerX , 100f),
                    new Vector2(paddedRight, 0),
                    new Vector2());

            float maxY = Bezier.quadratic(reuse, 0.5f,
                    new Vector2(paddedLeft, 0),
                    new Vector2(centerX , 100f),
                    new Vector2(paddedRight, 0),
                    new Vector2()).y;

            float rotation = (maxY - location.y) * .25f;

            if (location.x > centerX) {
                rotation *= -1f;
            }

//            cardsInHand.get(x).Update();
        }
    }
}
