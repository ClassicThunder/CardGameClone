package com.mygdx.game.deckengine;

import com.mygdx.game.deckengine.cards.Card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DrawPile {

    private final List<Card> cards = new ArrayList<>();

    public DrawPile() {

    }

    public void ClearCards() {

        this.cards.clear();
    }

    public int GetCards() {

        return this.cards.size();
    }

    public int Size() {

        return this.cards.size();
    }

    public void SetPile(List<Card> cards) {

        this.cards.clear();
        List<Card> tempCards = new ArrayList<>(cards);
        Collections.shuffle(tempCards);
        this.cards.addAll(tempCards);
    }

    public Card DrawTopCard() {

        return this.cards.remove(0);
    }
}
