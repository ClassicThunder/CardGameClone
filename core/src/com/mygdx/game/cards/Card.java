package com.mygdx.game.cards;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.hand.Hand;


public class Card {

    private final Sprite backingSprite;
    private final PolygonSprite polygon;

    private boolean isHover = false;

    public Card(Texture img) {

        TextureRegion textureRegion = new TextureRegion(img);
        float[] verts = new float[] {
                0, 0,
                Hand.CARD_WIDTH, 0,
                Hand.CARD_WIDTH, Hand.CARD_HEIGHT,
                0, Hand.CARD_HEIGHT};
        short[] tris = new short[] { 0, 1, 2, 0, 2, 3};

        PolygonRegion polyRegion = new PolygonRegion(textureRegion, verts, tris);

        this.polygon = new PolygonSprite(polyRegion);

        this.backingSprite = new Sprite(img);
    }

    public void Update() {

        this.isHover = false;
    }

    public boolean ContainsMouse(final Vector2 mouseLocation) {

        final float[] vertices = this.polygon.getVertices();

        float lastX = vertices[vertices.length - 5];
        float lastY = vertices[vertices.length - 4];

        float pointX = mouseLocation.x;
        float pointY = mouseLocation.y;

        boolean oddNodes = false;
        for (int i = 0; i < vertices.length; i += 5) {

            float vertX = vertices[i];
            float vertY = vertices[i + 1];

            if ((vertY < pointY && lastY >= pointY) || (lastY < pointY && vertY >= pointY)) {
                if (vertX + (pointY - vertY) / (lastY - vertY) * (lastX - vertX) < pointX) oddNodes = !oddNodes;
            }

            lastX = vertX;
            lastY = vertY;
        }

        this.isHover = oddNodes;
        return oddNodes;
    }

    public void Draw(PolygonSpriteBatch batch, final Vector2 location, final Vector2 size, final float rotation) {

        polygon.setOrigin(
                size.x / 2f,
                size.y / 2f);
        polygon.setSize(size.x, size.y);

        if (isHover) {
            polygon.setRotation(rotation + 180);
        } else {
            polygon.setRotation(rotation);
        }

        polygon.setPosition(
                location.x - size.x / 2f,
                location.y - size.y / 2f);
        polygon.draw(batch);
    }

    public void Draw(SpriteBatch batch, final Vector2 location, final Vector2 size, final float rotation) {

        backingSprite.setOriginCenter();
        backingSprite.setSize(size.x, size.y);
        backingSprite.setRotation(rotation);
        backingSprite.setOriginBasedPosition(location.x, location.y);
        backingSprite.draw(batch);
    }
}
