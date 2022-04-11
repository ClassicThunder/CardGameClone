package classicthunder.combat

import classicthunder.combat.card.CardActor
import classicthunder.combat.character.CharacterActor
import classicthunder.combat.state.EngineState
import com.badlogic.gdx.Input
import com.badlogic.gdx.InputProcessor
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3

internal class CombatEngineInputProcessor(
    private val deckEngine: CombatEngine,
    player: CharacterActor,
    enemy: CharacterActor)
{
    val ip: InputProcessor

    init {
        ip = object : InputProcessor
        {
            var grabbedCard: CardActor? = null

            override fun keyDown(keycode: Int): Boolean {
                return false
            }

            override fun keyUp(keycode: Int): Boolean {
                if (!isPlayerControl()) {
                    return true
                }

                if (keycode == Input.Keys.ENTER) {
                    deckEngine.requestEndTurn()
                    return true
                }
                return false
            }

            override fun keyTyped(character: Char): Boolean {
                return false
            }

            override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
                if (!isPlayerControl()) {
                    return true
                }

                val mouse = unproject(screenX, screenY)
                grabbedCard = deckEngine.hand.GrabCard(mouse)
                return true
            }

            override fun touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
                if (!isPlayerControl()) {
                    return true
                }

                val mouse = unproject(screenX, screenY)
                if (grabbedCard != null) {
                    if (player.containsMouse(mouse) &&
                            deckEngine.canPlayCard(player.character.characterStats, grabbedCard!!)) {
                        deckEngine.playCard(player.character.characterStats, grabbedCard!!)
                    }
                    if (enemy.containsMouse(mouse) &&
                            deckEngine.canPlayCard(enemy.character.characterStats, grabbedCard!!)) {
                        deckEngine.playCard(enemy.character.characterStats, grabbedCard!!)
                    }
                }
                deckEngine.hand.ResetCards()
                return true
            }

            override fun touchDragged(screenX: Int, screenY: Int, pointer: Int): Boolean {
                if (!isPlayerControl()) {
                    return true
                }
                
                val mouse = unproject(screenX, screenY)
                if (grabbedCard != null) {
                    grabbedCard!!.setDragPosition(mouse)
                    if (player.containsMouse(mouse)) {
                        if (deckEngine.canPlayCard(player.character.characterStats, grabbedCard!!)) {
                            grabbedCard!!.setIsPlayable(true)
                        } else {
                            grabbedCard!!.setIsNotPlayable(true)
                        }
                    } else if (enemy.containsMouse(mouse)) {
                        if (deckEngine.canPlayCard(enemy.character.characterStats, grabbedCard!!)) {
                            grabbedCard!!.setIsPlayable(true)
                        } else {
                            grabbedCard!!.setIsNotPlayable(true)
                        }
                    } else {
                        grabbedCard!!.setIsPlayable(false)
                        grabbedCard!!.setIsNotPlayable(false)
                    }
                }
                return true
            }

            override fun mouseMoved(screenX: Int, screenY: Int): Boolean {
                return false
            }

            override fun scrolled(amountX: Float, amountY: Float): Boolean {
                return false
            }
        }
    }

    private fun isPlayerControl(): Boolean {
        return  deckEngine.state.currentState == EngineState.PlayerControl
    }

    private fun unproject(x: Int, y: Int): Vector2 {
        val base = Vector3(x.toFloat(), y.toFloat(), 0.0f)
        val unproject = deckEngine.layout.viewport.unproject(base)
        val mouse = Vector2(unproject.x, unproject.y)

        return mouse
    }
}