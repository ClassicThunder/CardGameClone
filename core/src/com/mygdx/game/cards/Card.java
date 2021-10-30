package com.mygdx.game.cards;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.hand.Hand;


public class Card {

    private final PolygonSprite polygon;

    private boolean isHover = false;

    private Vector2 restingLocation = new Vector2();
    private Vector2 restingSize = new Vector2();
    private float restingRotation = 0.0f;

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
    }

    public void Reset() {

        this.isHover = false;
    }

    // Figure out where things should go
    public void SetRestingPosition(Vector2 location, float rotation, Vector2 size) {

        this.restingLocation = location;
        this.restingRotation = rotation;
        this.restingSize = size;
    }

    // Mouse Detection
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

    // Update
    public void Update() {

        polygon.setOrigin(restingSize.x / 2f, restingSize.y / 2f);
        polygon.setSize(restingSize.x, restingSize.y);
        polygon.setRotation(restingRotation);
        polygon.setPosition(restingLocation.x - restingSize.x / 2f, restingLocation.y - restingSize.y / 2f);
    }

    // Draw
    public void Draw(PolygonSpriteBatch batch) {

        if (isHover) {
            polygon.draw(batch, 0.2f);
        } else {
            polygon.draw(batch);
        }
    }
}
