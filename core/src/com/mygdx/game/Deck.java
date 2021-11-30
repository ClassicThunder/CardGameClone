package com.mygdx.game;

import com.mygdx.game.deckengine.cards.Card;

import java.util.ArrayList;
import java.util.List;

public class Deck {

    private List<Card> cards = new ArrayList<>();

    public Deck() {

    }

    public void AddCard(Card card) {

        cards.add(card);
    }

    public void AddCards(List<Card> cards) {

        cards.addAll(cards);
    }

    public List<Card> GetCards() {

        return cards;
    }
}
