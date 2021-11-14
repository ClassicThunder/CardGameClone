package com.mygdx.game.deckengine.hand;

import com.mygdx.game.deckengine.cards.Card;

import java.util.ArrayList;
import java.util.List;

public class Discarded {

    private final List<Card> cardsBeingDiscarded = new ArrayList<>();

    public void AddCards(List<Card> cards) {
        cardsBeingDiscarded.addAll(cards);
    }

    public void AddCard(Card card) {
        cardsBeingDiscarded.add(card);
    }

    public void Update() {

    }

    public void Draw() {

    }
}
