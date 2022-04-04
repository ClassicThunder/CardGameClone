package classicthunder.cards;

import java.util.ArrayList;
import java.util.List;

public class Deck {

    private final List<Card> cards;

    public Deck() {
        cards = new ArrayList<>();
    }

    public Deck(List<Card> cards) {
        this.cards = cards;
    }

    public void AddCard(Card card) {

        this.cards.add(card);
    }

    public void AddCards(List<Card> cards) {

        this.cards.addAll(cards);
    }

    public List<Card> GetCards() {

        return this.cards;
    }
}
