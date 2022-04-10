package classicthunder.combat.card

import classicthunder.combat.hand.Hand
import classicthunder.combat.layout.DeckLayout
import classicthunder.components.card.Card
import classicthunder.components.character.CharacterStats
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.PolygonRegion
import com.badlogic.gdx.graphics.g2d.PolygonSprite
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Vector2

class CardActor(private val deckLayout: DeckLayout, private val card: Card) {

    private val polygon: PolygonSprite

    //
    private var isGrabbed = false
    private var isPlayable = false
    private var isNotPlayable = false

    //
    private val actualPosition = Position()
    private val restingPosition = Position()
    private val dragPosition = Position()

    init {
        val textureRegion = TextureRegion(card.texture)
        val verts = floatArrayOf(0f, 0f,
                Hand.CARD_WIDTH, 0f,
                Hand.CARD_WIDTH, Hand.CARD_HEIGHT, 0f, Hand.CARD_HEIGHT)
        val tris = shortArrayOf(0, 1, 2, 0, 2, 3)
        val polyRegion = PolygonRegion(textureRegion, verts, tris)
        polygon = PolygonSprite(polyRegion)
        ResetToDrawPosition()
    }

    // ##### Effects ##### //
    fun GetEnergyCost(): Int {
        return card.energyCost
    }

    fun CanApplyEffects(stats: CharacterStats?): Boolean {
        return card.CanApplyEffects(stats!!)
    }

    fun ApplyEffects(stats: CharacterStats?) {
        card.ApplyEffects(stats!!)
    }

    // ##### Input ##### //
    fun Reset() {
        isGrabbed = false
        isPlayable = false
        isNotPlayable = false
    }

    fun ResetToDrawPosition() {
        Reset()
        actualPosition.set(
                deckLayout.drawPosition.location,
                deckLayout.drawPosition.size,
                deckLayout.drawPosition.rotation)
    }

    fun SetGrabbed(isGrabbed: Boolean) {
        this.isGrabbed = isGrabbed
    }

    fun SetIsPlayable(isPlayable: Boolean) {
        this.isPlayable = isPlayable
    }

    fun SetIsNotPlayable(isNotPlayable: Boolean) {
        this.isNotPlayable = isNotPlayable
    }

    fun ContainsMouse(mouseLocation: Vector2): Boolean {
        val vertices = polygon.vertices
        var lastX = vertices[vertices.size - 5]
        var lastY = vertices[vertices.size - 4]
        val pointX = mouseLocation.x
        val pointY = mouseLocation.y
        var oddNodes = false
        var i = 0
        while (i < vertices.size) {
            val vertX = vertices[i]
            val vertY = vertices[i + 1]
            if (vertY < pointY && lastY >= pointY || lastY < pointY && vertY >= pointY) {
                if (vertX + (pointY - vertY) / (lastY - vertY) * (lastX - vertX) < pointX) oddNodes = !oddNodes
            }
            lastX = vertX
            lastY = vertY
            i += 5
        }
        return oddNodes
    }

    // ##### Layout ##### //
    fun GetActualLocation(): Vector2? {
        return actualPosition.location
    }

    fun SetRestingPosition(location: Vector2?, rotation: Float, size: Vector2?) {
        restingPosition.set(location, size, rotation)
    }

    fun SetDragPosition(location: Vector2?) {
        dragPosition.set(location, restingPosition.size, 0.0f)
    }

    // ##### Life Cycle ##### //
    fun Update() {
        if (isGrabbed) {
            actualPosition.lerpTowards(dragPosition, 0.35f)
        } else {
            actualPosition.lerpTowards(restingPosition, 0.10f)
        }
        val location = actualPosition.location
        val size = actualPosition.size
        val rotation = actualPosition.rotation
        polygon.setOrigin(size!!.x / 2f, size.y / 2f)
        polygon.setSize(size.x, size.y)
        polygon.rotation = rotation
        polygon.setPosition(
                location!!.x - size.x / 2f,
                location.y - size.y / 2f)
    }

    fun Draw(batch: PolygonSpriteBatch?, alpha: Float) {
        if (isPlayable) {
            polygon.color = Color.GREEN
        } else if (isNotPlayable) {
            polygon.color = Color.RED
        } else {
            polygon.color = Color.WHITE
        }
        polygon.draw(batch, alpha)
    }
}