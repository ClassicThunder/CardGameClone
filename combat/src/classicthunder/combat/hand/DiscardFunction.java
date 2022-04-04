package classicthunder.combat.hand;

import classicthunder.combat.card.CardActor;

import java.util.List;

public interface DiscardFunction {
    void onDiscard(List<CardActor> cards);
}
