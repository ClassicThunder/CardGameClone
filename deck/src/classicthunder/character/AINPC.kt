package classicthunder.character

import com.badlogic.gdx.graphics.Texture

abstract class AINPC(
    texture: Texture,
    characterStats: CharacterStats
) : NPC(texture, characterStats)
{
    abstract fun takeTurn(): Int
}