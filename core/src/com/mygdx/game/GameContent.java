package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.mygdx.game.texture.TextureFactory;

import java.util.HashMap;
import java.util.Map;

public class GameContent {

    private BitmapFont debugFont;
    private Texture debugTexture;

    private Map<String, Texture> textureMap;

    public BitmapFont GetDebugFont() {

        return debugFont;
    }

    public Texture GetDebugTexture() {
        return debugTexture;
    }

    public Texture GetTexture(String key) {

        return textureMap.get(key);
    }

    public void Load() {

        debugFont = new BitmapFont();
        debugTexture = TextureFactory.Generate(Color.RED);

        textureMap = new HashMap<>();
        textureMap.put("CARD_DEFEND", new Texture("Defend_R.png"));
        textureMap.put("CARD_STRIKE", new Texture("Strike_R.png"));

        textureMap.put("ENEMY", new Texture("Book_of_stabbing_pretty.png"));
        textureMap.put("PLAYER", new Texture("Silent.png"));

        textureMap.put("UX_DRAW", new Texture("Draw.png"));
        textureMap.put("UX_DISCARD", new Texture("Discard.png"));
        textureMap.put("UX_ENERGY", new Texture("GreenEnergy.png"));
    }

    public void Unload() {

        debugFont.dispose();
        debugTexture.dispose();

        for (Map.Entry<String, Texture> e : textureMap.entrySet()) {
            e.getValue().dispose();
        }
    }
}
