package classicthunder.combat.combat.hand;

import classicthunder.combat.combat.card.CardActor;
import classicthunder.combat.combat.card.Position;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;

public class Discarder {

    private final List<CardActor> cardsBeingDiscarded = new ArrayList<>();
    private final Position discardCardPosition;

    private final DiscardFunction discardFunction;

    public Discarder(Position discardCardPosition, DiscardFunction discardFunction) {

        this.discardCardPosition = discardCardPosition;
        this.discardFunction = discardFunction;
    }

    public int GetCardCount() {

        return cardsBeingDiscarded.size();
    }

    public void ClearCards() {

        this.cardsBeingDiscarded.clear();
    }

    public void AddCards(List<CardActor> cards) {

        for (CardActor card : cards) {
            card.Reset();
            card.SetRestingPosition(
                    discardCardPosition.getLocation(),
                    discardCardPosition.getRotation(),
                    discardCardPosition.getSize());
        }

        cardsBeingDiscarded.addAll(cards);
    }

    public void AddCard(CardActor card) {

        card.Reset();
        card.SetRestingPosition(
                discardCardPosition.getLocation(),
                discardCardPosition.getRotation(),
                discardCardPosition.getSize());

        cardsBeingDiscarded.add(card);
    }

    public void Update() {

        List<CardActor> settledCards = new ArrayList<>();

        for (CardActor card : cardsBeingDiscarded) {
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

        for (CardActor card : cardsBeingDiscarded) {

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
