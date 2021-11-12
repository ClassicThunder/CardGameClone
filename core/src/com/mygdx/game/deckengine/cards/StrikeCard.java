package com.mygdx.game.deckengine.cards;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.character.CharacterStats;
import com.mygdx.game.character.CharacterType;

public class StrikeCard extends Card {

    public StrikeCard(Texture img) {
        super(img);
    }

    @Override
    public boolean CanApplyEffects(CharacterStats stats) {

        return (stats.getCharacterType() == CharacterType.ENEMY);
    }

    @Override
    public void ApplyEffects(CharacterStats stats) {
        stats.AdjustHealth(-6);
    }
}
