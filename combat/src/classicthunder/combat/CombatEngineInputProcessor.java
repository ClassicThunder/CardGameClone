package classicthunder.combat;

import classicthunder.combat.card.CardActor;
import classicthunder.combat.character.CharacterActor;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class CombatEngineInputProcessor {

    final InputProcessor ip;

    public CombatEngineInputProcessor(final CombatEngine deckEngine,
                                      final CharacterActor player,
                                      final CharacterActor enemy) {

        ip = new InputProcessor() {

            CardActor grabbedCard = null;

            @Override
            public boolean keyDown(int keycode) {
                return false;
            }

            @Override
            public boolean keyUp(int keycode) {

                if (keycode == Input.Keys.ENTER) {
                    deckEngine.requestEndTurn();
                    return true;
                }

                return false;
            }

            @Override
            public boolean keyTyped(char character) {

                return false;
            }

            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {

                Vector3 base = new Vector3(screenX, screenY, 0);
                Vector3 unproject = deckEngine.layout.getViewport().unproject(base);
                Vector2 mouse = new Vector2(unproject.x, unproject.y);

                grabbedCard = deckEngine.hand.GrabCard(mouse);

                return true;
            }

            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {

                Vector3 base = new Vector3(screenX, screenY, 0);
                Vector3 unproject = deckEngine.layout.getViewport().unproject(base);
                Vector2 mouse = new Vector2(unproject.x, unproject.y);

                if (grabbedCard != null) {

                    if (player.ContainsMouse(mouse) &&
                            deckEngine.canPlayCard(player.GetStats(), grabbedCard)) {
                        deckEngine.playCard(player.GetStats(), grabbedCard);
                    }

                    if (enemy.ContainsMouse(mouse) &&
                            deckEngine.canPlayCard(enemy.GetStats(), grabbedCard)) {
                        deckEngine.playCard(enemy.GetStats(), grabbedCard);
                    }
                }

                deckEngine.hand.ResetCards();

                return true;
            }

            @Override
            public boolean touchDragged(int screenX, int screenY, int pointer) {

                Vector3 base = new Vector3(screenX, screenY, 0);
                Vector3 unproject = deckEngine.layout.getViewport().unproject(base);
                Vector2 mouse = new Vector2(unproject.x, unproject.y);

                if (grabbedCard != null) {

                    grabbedCard.SetDragPosition(mouse);

                    if (player.ContainsMouse(mouse)) {
                        if (deckEngine.canPlayCard(player.GetStats(), grabbedCard)) {
                            grabbedCard.SetIsPlayable(true);
                        } else {
                            grabbedCard.SetIsNotPlayable(true);
                        }
                    } else if (enemy.ContainsMouse(mouse)) {
                        if (deckEngine.canPlayCard(enemy.GetStats(), grabbedCard)) {
                            grabbedCard.SetIsPlayable(true);
                        } else {
                            grabbedCard.SetIsNotPlayable(true);
                        }
                    } else {
                        grabbedCard.SetIsPlayable(false);
                        grabbedCard.SetIsNotPlayable(false);
                    }
                }

                return true;
            }

            @Override
            public boolean mouseMoved(int screenX, int screenY) {
                return false;
            }

            @Override
            public boolean scrolled(float amountX, float amountY) {
                return false;
            }
        };
    }
}