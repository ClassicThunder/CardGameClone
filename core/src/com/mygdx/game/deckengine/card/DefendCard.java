package com.mygdx.game.deckengine.card;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.character.CharacterStats;
import com.mygdx.game.character.CharacterType;

public class DefendCard extends Card {

    public DefendCard(CardLayout cardLayout, Texture img) {
        super(cardLayout, img);
    }

    @Override
    public boolean CanApplyEffects(CharacterStats stats) {
        return (stats.getCharacterType() == CharacterType.PLAYER);
    }

    @Override
    public void ApplyEffects(CharacterStats stats) {
        stats.AdjustBlock(5);
    }
}
