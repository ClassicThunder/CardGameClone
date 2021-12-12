package com.mygdx.game.deckengine.pile;

import com.mygdx.game.deckengine.card.Card;

import java.util.ArrayList;
import java.util.List;

public class DiscardPile {

    private final List<Card> cards = new ArrayList<>();
    private final PileFunction pileFunction;

    public DiscardPile(PileFunction onUpdate) {

        this.pileFunction = onUpdate;
    }

    public void ClearCards() {

        this.cards.clear();

        pileFunction.onCardCountChanged(0);
    }

    public void AddCard(Card card) {

        this.cards.add(card);

        pileFunction.onCardCountChanged(this.cards.size());
    }

    public void AddCards(List<Card> cards) {

        this.cards.addAll(cards);

        pileFunction.onCardCountChanged(this.cards.size());
    }

    public int GetCardCount() {

        return this.cards.size();
    }

    public List<Card> Empty() {

        List<Card> tempCards = new ArrayList<>(this.cards);
        this.cards.clear();

        pileFunction.onCardCountChanged(0);

        return tempCards;
    }
}
