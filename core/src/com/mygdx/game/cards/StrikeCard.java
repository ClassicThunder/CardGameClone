package com.mygdx.game.cards;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.character.CharacterStats;

public class StrikeCard extends Card {

    public StrikeCard(Texture img) {
        super(img);
    }

    @Override
    public void ApplyEffects(CharacterStats stats) {
        stats.AdjustHealth(-6);
    }
}
