package classicthunder.combat.combat.hand;

import classicthunder.combat.combat.card.CardActor;
import classicthunder.combat.combat.layout.DeckLayout;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;

public class Hand {

    static public final float CARD_WIDTH = 678;
    static public final float CARD_HEIGHT = 874;
    final int MAX_CARDS = 11;

    private final List<CardActor> cardsInHand = new ArrayList<>(MAX_CARDS);
    private final CardBezier cardBezier;

    public Hand(DeckLayout layout, float cardScale) {

        Vector2 cardSize = new Vector2(CARD_WIDTH, CARD_HEIGHT);
        cardSize.scl(cardScale);

        Rectangle cardArea = new Rectangle(
                layout.getCenterWidth() - (layout.getHandWidth() / 2f), 0,
                layout.getHandWidth(), 200);
        this.cardBezier = new CardBezier(cardArea, cardSize);
    }

    // Card Info
    public void AddCard(CardActor card) {

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
    public CardActor GrabCard(Vector2 mouse) {

        for (int x = cardsInHand.size() - 1; x >= 0; x--) {
            CardActor currentCard = cardsInHand.get(x);
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

    public void DiscardCard(Discarder discard, CardActor card) {

        discard.AddCard(card);
        cardsInHand.remove(card);
        this.cardBezier.FindRestingPosition(cardsInHand);
    }

    public void ResetCards() {

        for (CardActor card : cardsInHand) {
            card.Reset();
        }
    }

    // LifeCycle
    public void Update() {

        for (CardActor card : cardsInHand) {
            card.Update();
        }
    }

    public void Draw(PolygonSpriteBatch polygonBatch) {

        for (CardActor card : cardsInHand) {
            card.Draw(polygonBatch, 1.0f);
        }
    }

    public void Draw(SpriteBatch batch) {

    }
}
