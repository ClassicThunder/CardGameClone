package classicthunder.combat.character

import classicthunder.character.AINPC
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.Vector2

internal class AICharacterActor(
    override val character: AINPC,
    texture: Texture,
    location: Vector2
) : CharacterActor(character, texture, location)