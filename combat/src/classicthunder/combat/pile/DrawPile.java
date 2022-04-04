package classicthunder.combat.pile;

import classicthunder.combat.card.CardActor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DrawPile {

    private final List<CardActor> cards = new ArrayList<>();
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

    public void SetPile(List<CardActor> cards) {

        this.cards.clear();

        List<CardActor> tempCards = new ArrayList<>(cards);
        Collections.shuffle(tempCards);
        this.cards.addAll(tempCards);

        pileFunction.onCardCountChanged(this.cards.size());
    }

    public CardActor DrawTopCard() {

        CardActor topCard = this.cards.remove(0);

        pileFunction.onCardCountChanged(this.cards.size());

        return topCard;
    }
}
