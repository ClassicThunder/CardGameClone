package classicthunder.combat.state;

import classicthunder.combat.character.CharacterActor;

public class EnemyTurnState implements State {

    private final CharacterActor player;
    private final CharacterActor enemy;

    private final int TURN_TIMER = 60 * 5;
    int timer = 0;

    public EnemyTurnState(CharacterActor player, CharacterActor enemy) {

        this.player = player;
        this.enemy = enemy;
    }

    @Override
    public void Enter() {

        timer = 0;
    }

    @Override
    public EngineState Update() {

        timer++;

        if (timer >= TURN_TIMER) {
            return EngineState.Drawing;
        }

        return EngineState.EnemyTurn;
    }

    @Override
    public void Exit() {

    }
}
