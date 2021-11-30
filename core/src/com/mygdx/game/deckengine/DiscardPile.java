package com.mygdx.game.deckengine;

import com.mygdx.game.deckengine.cards.Card;

import java.util.ArrayList;
import java.util.List;

public class DiscardPile {

    private List<Card> cards = new ArrayList<>();

    public DiscardPile() {

    }

    public void AddCard(Card card) {

        cards.add(card);
    }

    public void AddCards(List<Card> cards) {

        cards.addAll(cards);
    }

    public List<Card> Empty() {

        List<Card> tempCards = new ArrayList<>(cards);
        cards.clear();
        return tempCards;
    }
}
