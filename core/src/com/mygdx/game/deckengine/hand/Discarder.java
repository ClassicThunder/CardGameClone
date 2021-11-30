package com.mygdx.game.deckengine.hand;

import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.deckengine.cards.Card;
import com.mygdx.game.deckengine.cards.CardPosition;

import java.util.ArrayList;
import java.util.List;

public class Discarder {

    private final List<Card> cardsBeingDiscarded = new ArrayList<>();
    private final CardPosition discardCardPosition;

    private final DiscardFunction discardFunction;

    public Discarder(CardPosition discardCardPosition, DiscardFunction discardFunction) {

        this.discardCardPosition = discardCardPosition;
        this.discardFunction = discardFunction;
    }

    public void AddCards(List<Card> cards) {

        for (Card card : cards) {
            card.Reset();
            card.SetRestingPosition(
                    discardCardPosition.getLocation(),
                    discardCardPosition.getRotation(),
                    discardCardPosition.getSize());
        }

        cardsBeingDiscarded.addAll(cards);
    }

    public void AddCard(Card card) {

        card.Reset();
        card.SetRestingPosition(
                discardCardPosition.getLocation(),
                discardCardPosition.getRotation(),
                discardCardPosition.getSize());

        cardsBeingDiscarded.add(card);
    }

    public void Update() {

        List<Card> settledCards = new ArrayList<>();

        for (Card card : cardsBeingDiscarded) {
            card.Update();

            if (card.GetActualLocation()
                    .dst(discardCardPosition.getLocation()) < 10.0f) {
                settledCards.add(card);
            }
        }

        cardsBeingDiscarded.removeAll(settledCards);
        if (settledCards.size() > 0) {
            discardFunction.onDiscard(settledCards);
        }
    }

    public void Draw(PolygonSpriteBatch batch) {

        for (Card card : cardsBeingDiscarded) {

            float alpha;
            Vector2 location = card.GetActualLocation();

            float dst = Math.abs(location.dst(discardCardPosition.getLocation()));
            if (dst < 100) {
                alpha = Interpolation.slowFast.apply(dst / 100.0f);
            } else {
                alpha = 1.0f;
            }

            card.Draw(batch, alpha);
        }
    }
}
