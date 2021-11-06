package com.mygdx.game.cards;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.hand.Hand;


public class Card {

    private final PolygonSprite polygon;

    private boolean isGrabbed = false;

    // Actual
    private Vector2 actualLocation = new Vector2();
    private Vector2 actualSize = new Vector2();
    private float actualRotation = 0.0f;

    // Resting
    private Vector2 restingLocation = new Vector2();
    private Vector2 restingSize = new Vector2();
    private float restingRotation = 0.0f;

    // Drag
    private Vector2 dragLocation = new Vector2();

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

    // ##### Input ##### //
    public void Reset() {
        this.isGrabbed = false;
    }

    public void SetGrabbed(boolean isGrabbed) {
        this.isGrabbed = isGrabbed;
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

        return oddNodes;
    }

    // ##### Layout ##### //
    public void SetRestingPosition(Vector2 location, float rotation, Vector2 size) {

        this.restingLocation = location;
        this.restingRotation = rotation;
        this.restingSize = size;

        this.actualLocation = new Vector2(this.restingLocation);
        this.actualRotation = this.restingRotation;
        this.actualSize = new Vector2(this.restingSize);
    }

    public void SetDragPosition(Vector2 location) {

        this.dragLocation = location;
    }

    // ##### Life Cycle ##### //
    public void Update() {

        final float lerpAmount = 0.3f;

        polygon.setOrigin(actualSize.x / 2f, actualSize.y / 2f);
        polygon.setSize(actualSize.x, actualSize.y);

        if (isGrabbed) {
            actualRotation = MathUtils.lerp(actualRotation, 0, lerpAmount);
            actualLocation = actualLocation.lerp(dragLocation, lerpAmount);

        } else {
            actualRotation = MathUtils.lerp(actualRotation, restingRotation, lerpAmount);
            actualLocation = actualLocation.lerp(restingLocation, lerpAmount);
        }

        polygon.setRotation(actualRotation);
        polygon.setPosition(actualLocation.x - actualSize.x / 2f, actualLocation.y - actualSize.y / 2f);
    }

    // Draw
    public void Draw(PolygonSpriteBatch batch) {

        polygon.draw(batch);
    }
}
