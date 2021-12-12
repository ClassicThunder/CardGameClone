package com.mygdx.game.deckengine.hand;

import com.mygdx.game.deckengine.card.Card;

import java.util.List;

public interface DiscardFunction {
    void onDiscard(List<Card> cards);
}
