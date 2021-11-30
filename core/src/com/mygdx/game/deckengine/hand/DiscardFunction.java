package com.mygdx.game.deckengine.hand;

import com.mygdx.game.deckengine.cards.Card;

import java.util.List;

public interface DiscardFunction {
    void onDiscard(List<Card> cards);
}
