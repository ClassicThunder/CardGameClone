package classicthunder.combat.character

import classicthunder.components.character.AICharacter
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.Vector2

class AICharacterActor(
    override val character: AICharacter,
    texture: Texture,
    location: Vector2) : CharacterActor(character, texture, location)