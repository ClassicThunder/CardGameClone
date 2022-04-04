package classicthunder.combat.combat.hand;

import classicthunder.combat.combat.card.CardActor;

import java.util.List;

public interface DiscardFunction {
    void onDiscard(List<CardActor> cards);
}
