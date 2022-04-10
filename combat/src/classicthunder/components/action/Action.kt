package classicthunder.components.action

import com.badlogic.gdx.graphics.Color

/**
 * Actions represent a one time event such as an attack, adding a stack of poison, or adding block.
 */
abstract class Action(val flashColor: Color, val flashLength: Int)