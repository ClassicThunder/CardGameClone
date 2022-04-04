package classicthunder.combat.pile;

import classicthunder.combat.card.CardActor;

import java.util.ArrayList;
import java.util.List;

public class DiscardPile {

    private final List<CardActor> cards = new ArrayList<>();
    private final PileFunction pileFunction;

    public DiscardPile(PileFunction onUpdate) {

        this.pileFunction = onUpdate;
    }

    public void ClearCards() {

        this.cards.clear();

        pileFunction.onCardCountChanged(0);
    }

    public void AddCard(CardActor card) {

        this.cards.add(card);

        pileFunction.onCardCountChanged(this.cards.size());
    }

    public void AddCards(List<CardActor> cards) {

        this.cards.addAll(cards);

        pileFunction.onCardCountChanged(this.cards.size());
    }

    public int GetCardCount() {

        return this.cards.size();
    }

    public List<CardActor> Empty() {

        List<CardActor> tempCards = new ArrayList<>(this.cards);
        this.cards.clear();

        pileFunction.onCardCountChanged(0);

        return tempCards;
    }
}
