package com.mygdx.game.deckengine.card;

public class CardLayout {

    private final CardPosition drawPosition;
    private final CardPosition discardPosition;

    public CardLayout(CardPosition drawPosition, CardPosition discardPosition) {

        this.drawPosition = drawPosition;
        this.discardPosition = discardPosition;
    }

    public CardPosition getDrawPosition() {
        return drawPosition;
    }

    public CardPosition getDiscardPosition() {
        return discardPosition;
    }
}
