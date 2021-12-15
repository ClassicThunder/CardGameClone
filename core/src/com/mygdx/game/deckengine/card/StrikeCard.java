package com.mygdx.game.deckengine.card;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.character.CharacterStats;
import com.mygdx.game.character.CharacterType;

public class StrikeCard extends Card {

    public StrikeCard(CardLayout cardLayout, Texture img) {
        super(cardLayout, img);
    }

    @Override
    public int GetEnergyCost() {
        return 1;
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
