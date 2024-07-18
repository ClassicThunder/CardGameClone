package classicthunder.combat.card

import classicthunder.card.Card
import classicthunder.character.CharacterStats
import classicthunder.combat.hand.Hand
import classicthunder.combat.layout.DeckLayout
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.PolygonRegion
import com.badlogic.gdx.graphics.g2d.PolygonSprite
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Vector2

internal class CardActor(private val deckLayout: DeckLayout, private val card: Card) {

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
        val verts = floatArrayOf(
            0f, 0f,
            Hand.CARD_WIDTH, 0f,
            Hand.CARD_WIDTH, Hand.CARD_HEIGHT, 0f, Hand.CARD_HEIGHT
        )
        val tris = shortArrayOf(0, 1, 2, 0, 2, 3)
        val polyRegion = PolygonRegion(textureRegion, verts, tris)
        polygon = PolygonSprite(polyRegion)
        resetToDrawPosition()
    }

    // ##### Effects ##### //
    fun getEnergyCost(): Int {
        return card.getEnergyCost()
    }

    fun canApplyEffects(stats: CharacterStats): Boolean {
        return card.canPlay(stats)
    }

    fun applyEffects(stats: CharacterStats?) {
        card.play(stats!!)
    }

    // ##### Input ##### //
    fun reset() {
        isGrabbed = false
        isPlayable = false
        isNotPlayable = false
    }

    fun resetToDrawPosition() {
        reset()
        actualPosition.set(
            deckLayout.drawPosition.location,
            deckLayout.drawPosition.size,
            deckLayout.drawPosition.rotation
        )
    }

    fun setGrabbed(isGrabbed: Boolean) {
        this.isGrabbed = isGrabbed
    }

    fun setIsPlayable(isPlayable: Boolean) {
        this.isPlayable = isPlayable
    }

    fun setIsNotPlayable(isNotPlayable: Boolean) {
        this.isNotPlayable = isNotPlayable
    }

    fun containsMouse(mouseLocation: Vector2): Boolean {
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
    fun getActualLocation(): Vector2 {
        return actualPosition.location
    }

    fun setRestingPosition(location: Vector2, rotation: Float, size: Vector2) {
        restingPosition.set(location, size, rotation)
    }

    fun setDragPosition(location: Vector2) {
        dragPosition.set(location, restingPosition.size, 0.0f)
    }

    // ##### Life Cycle ##### //
    fun update() {
        if (isGrabbed) {
            actualPosition.lerpTowards(dragPosition, 0.35f)
        } else {
            actualPosition.lerpTowards(restingPosition, 0.10f)
        }
        val location = actualPosition.location
        val size = actualPosition.size
        val rotation = actualPosition.rotation
        polygon.setOrigin(size.x / 2f, size.y / 2f)
        polygon.setSize(size.x, size.y)
        polygon.rotation = rotation
        polygon.setPosition(
            location.x - size.x / 2f,
            location.y - size.y / 2f
        )
    }

    fun draw(batch: PolygonSpriteBatch?, alpha: Float) {
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