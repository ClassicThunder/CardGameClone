package com.mygdx.game.deckengine.cards;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.PolygonSprite;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.character.CharacterStats;
import com.mygdx.game.deckengine.hand.Hand;

public abstract class Card {

    private final PolygonSprite polygon;

    private boolean isGrabbed = false;

    private boolean isPlayable = false;
    private boolean isNotPlayable = false;

    // Actual
    private Vector2 actualLocation;
    private Vector2 actualSize;
    private float actualRotation;
    private float alpha;

    // Resting
    private Vector2 restingLocation = new Vector2();
    private Vector2 restingSize = new Vector2();
    private float restingRotation = 0.0f;

    // Drag
    private Vector2 dragLocation = new Vector2();

    public Card(CardLayout cardLayout, Texture img) {

        TextureRegion textureRegion = new TextureRegion(img);
        float[] verts = new float[]{
                0, 0,
                Hand.CARD_WIDTH, 0,
                Hand.CARD_WIDTH, Hand.CARD_HEIGHT,
                0, Hand.CARD_HEIGHT};
        short[] tris = new short[]{0, 1, 2, 0, 2, 3};

        PolygonRegion polyRegion = new PolygonRegion(textureRegion, verts, tris);

        this.polygon = new PolygonSprite(polyRegion);

        this.actualLocation = cardLayout.getDrawPosition().getLocation();
        this.actualRotation = cardLayout.getDrawPosition().getRotation();
        this.actualSize = cardLayout.getDrawPosition().getSize();
    }

    // ##### Effects ##### //
    public abstract boolean CanApplyEffects(CharacterStats stats);

    public abstract void ApplyEffects(CharacterStats stats);

    // ##### Input ##### //
    public void Reset() {
        this.isGrabbed = false;
        this.isPlayable = false;
        this.isNotPlayable = false;
    }

    public void SetGrabbed(boolean isGrabbed) {
        this.isGrabbed = isGrabbed;
    }

    public boolean GetIsGrabbed() {
        return this.isGrabbed;
    }

    public void SetIsPlayable(boolean isPlayable) {
        this.isPlayable = isPlayable;
    }

    public void SetIsNotPlayable(boolean isNotPlayable) {
        this.isNotPlayable = isNotPlayable;
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
    public Vector2 GetActualLocation() {

        return this.actualLocation.cpy();
    }

    public Vector2 GetActualSize() {

        return this.actualSize.cpy();
    }

    public void SetRestingPosition(Vector2 location, float rotation, Vector2 size) {

        this.restingLocation = location;
        this.restingRotation = rotation;
        this.restingSize = size;
    }

    public void SetRestingPositionCenter(Vector2 location, float rotation, Vector2 size) {

        this.restingLocation = new Vector2(
                location.x + (size.x / 2.0f),
                location.y + (size.y / 2.0f));
        this.restingRotation = rotation;
        this.restingSize = size;
    }

    public void SetDragPosition(Vector2 location) {

        this.dragLocation = location;
    }

    // ##### Life Cycle ##### //
    public void Update() {

        final float lerpAmount;

        if (this.isGrabbed) {
            lerpAmount = 0.35f;
        } else {
            lerpAmount = 0.10f;
        }

        if (isGrabbed) {
            actualRotation = MathUtils.lerp(actualRotation, 0, lerpAmount);
            actualLocation = actualLocation.lerp(dragLocation, lerpAmount);
            actualSize = actualSize.lerp(restingSize, lerpAmount);
        } else {
            actualRotation = MathUtils.lerp(actualRotation, restingRotation, lerpAmount);
            actualLocation = actualLocation.lerp(restingLocation, lerpAmount);
            actualSize = actualSize.lerp(restingSize, lerpAmount);
        }

        polygon.setOrigin(actualSize.x / 2f, actualSize.y / 2f);
        polygon.setSize(actualSize.x, actualSize.y);

        polygon.setRotation(actualRotation);
        polygon.setPosition(
                actualLocation.x - actualSize.x / 2f,
                actualLocation.y - actualSize.y / 2f);
    }

    public void Draw(PolygonSpriteBatch batch, float alpha) {

        if (isPlayable) {
            polygon.setColor(Color.GREEN);
        } else if (isNotPlayable) {
            polygon.setColor(Color.RED);
        } else {
            polygon.setColor(Color.WHITE);
        }

        polygon.draw(batch, alpha);
    }
}
