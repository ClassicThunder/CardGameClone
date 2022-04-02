package com.mygdx.game.deckengine;

import com.mygdx.game.deckengine.card.Card;

import java.util.ArrayList;
import java.util.List;

class Deck {

    private final List<Card> cards = new ArrayList<>();

    Deck() {

    }

    void AddCard(Card card) {

        this.cards.add(card);
    }

    void AddCards(List<Card> cards) {

        this.cards.addAll(cards);
    }

    List<Card> GetCards() {

        return this.cards;
    }
}
