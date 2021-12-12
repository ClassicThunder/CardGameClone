package com.mygdx.game.deckengine.pile;

import com.mygdx.game.deckengine.card.Card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DrawPile {

    private final List<Card> cards = new ArrayList<>();
    private final PileFunction pileFunction;

    public DrawPile(PileFunction onUpdate) {

        this.pileFunction = onUpdate;
    }

    public void ClearCards() {

        this.cards.clear();

        pileFunction.onCardCountChanged(0);
    }

    public int GetCardCount() {

        return this.cards.size();
    }

    public void SetPile(List<Card> cards) {

        this.cards.clear();

        List<Card> tempCards = new ArrayList<>(cards);
        Collections.shuffle(tempCards);
        this.cards.addAll(tempCards);

        pileFunction.onCardCountChanged(this.cards.size());
    }

    public Card DrawTopCard() {

        Card topCard = this.cards.remove(0);

        pileFunction.onCardCountChanged(this.cards.size());

        return topCard;
    }
}
