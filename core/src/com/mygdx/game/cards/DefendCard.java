package com.mygdx.game.cards;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.character.CharacterStats;

public class DefendCard extends Card {

    public DefendCard(Texture img) {
        super(img);
    }

    @Override
    public void ApplyEffects(CharacterStats stats) {
        stats.AdjustBlock(5);
    }
}
