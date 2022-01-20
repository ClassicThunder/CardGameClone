package com.mygdx.game.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Label extends StaticEntity {

    private final BitmapFont bitmapFont;
    private String text = "";
    private float textCenterX = 0f;
    private float textCenterY = 0f;

    public Label(BitmapFont bitmapFont, Texture texture, Vector2 center, Vector2 size) {

        super(texture, center, size);

        this.bitmapFont = bitmapFont;
        this.setText("0");
    }

    public String getText() {

        return text;
    }

    public void setText(String text) {

        this.text = text;

        GlyphLayout layout = new GlyphLayout(bitmapFont, text);
        this.textCenterX = super.center.x - (layout.width / 2.0f);
        this.textCenterY = super.center.y + (super.size.y / 2.0f) + (layout.height / 2.0f);
    }

    public void Draw(SpriteBatch batch) {

        if (texture != null) {
            super.Draw(batch);
        }

        bitmapFont.draw(batch, this.text, this.textCenterX, this.textCenterY);
    }
}
