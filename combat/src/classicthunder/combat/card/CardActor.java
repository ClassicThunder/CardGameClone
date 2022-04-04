package classicthunder.combat.card;

import classicthunder.cards.Card;
import classicthunder.combat.character.CharacterStats;
import classicthunder.combat.hand.Hand;
import classicthunder.combat.layout.DeckLayout;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.PolygonSprite;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class CardActor {

    private final Card card;

    private final PolygonSprite polygon;
    private final DeckLayout deckLayout;

    //
    private boolean isGrabbed = false;
    private boolean isPlayable = false;
    private boolean isNotPlayable = false;

    //
    private final Position actualPosition = new Position();
    private final Position restingPosition = new Position();
    private final Position dragPosition = new Position();

    public CardActor(DeckLayout deckLayout, Card card) {

        this.deckLayout = deckLayout;
        this.card = card;

        TextureRegion textureRegion = new TextureRegion(card.getTexture());
        float[] verts = new float[]{
                0, 0,
                Hand.CARD_WIDTH, 0,
                Hand.CARD_WIDTH, Hand.CARD_HEIGHT,
                0, Hand.CARD_HEIGHT};
        short[] tris = new short[]{0, 1, 2, 0, 2, 3};

        PolygonRegion polyRegion = new PolygonRegion(textureRegion, verts, tris);

        this.polygon = new PolygonSprite(polyRegion);

        ResetToDrawPosition();
    }

    // ##### Effects ##### //
    public int GetEnergyCost() {
        return this.card.getEnergyCost();
    }

    public boolean CanApplyEffects(CharacterStats stats) {
        return this.card.CanApplyEffects(stats);
    }

    public void ApplyEffects(CharacterStats stats) {
        this.card.ApplyEffects(stats);
    }

    // ##### Input ##### //
    public void Reset() {

        this.isGrabbed = false;
        this.isPlayable = false;
        this.isNotPlayable = false;
    }

    public void ResetToDrawPosition() {

        Reset();

        actualPosition.Set(
                deckLayout.getDrawPosition().getLocation(),
                deckLayout.getDrawPosition().getSize(),
                deckLayout.getDrawPosition().getRotation());
    }

    public void SetGrabbed(boolean isGrabbed) {
        this.isGrabbed = isGrabbed;
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

        return this.actualPosition.getLocation();
    }

    public void SetRestingPosition(Vector2 location, float rotation, Vector2 size) {

        this.restingPosition.Set(location, size, rotation);
    }

    public void SetDragPosition(Vector2 location) {

        this.dragPosition.Set(location, this.restingPosition.getSize(), 0.0f);
    }

    // ##### Life Cycle ##### //
    public void Update() {

        if (isGrabbed) {
            actualPosition.LerpTowards(dragPosition, 0.35f);
        } else {
            actualPosition.LerpTowards(restingPosition, 0.10f);
        }

        Vector2 location = actualPosition.getLocation();
        Vector2 size = actualPosition.getSize();
        float rotation = actualPosition.getRotation();

        polygon.setOrigin(size.x / 2f, size.y / 2f);
        polygon.setSize(size.x, size.y);

        polygon.setRotation(rotation);
        polygon.setPosition(
                location.x - size.x / 2f,
                location.y - size.y / 2f);
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
