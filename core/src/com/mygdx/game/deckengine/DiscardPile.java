package com.mygdx.game.deckengine;

import com.mygdx.game.deckengine.cards.Card;

import java.util.ArrayList;
import java.util.List;

public class DiscardPile {

    private final List<Card> cards = new ArrayList<>();

    public DiscardPile() {

    }

    public void AddCard(Card card) {

        this.cards.add(card);
    }

    public void AddCards(List<Card> cards) {

        this.cards.addAll(cards);
    }

    public int Size() {

        return this.cards.size();
    }

    public List<Card> Empty() {

        List<Card> tempCards = new ArrayList<>(this.cards);
        this.cards.clear();

        return tempCards;
    }
}
