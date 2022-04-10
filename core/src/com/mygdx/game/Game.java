package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.mygdx.game.screens.CombatScreen;
import com.mygdx.game.screens.TestScreen;
import de.eskalon.commons.core.ManagedGame;
import de.eskalon.commons.screen.ManagedScreen;
import de.eskalon.commons.screen.transition.ScreenTransition;
import de.eskalon.commons.screen.transition.impl.BlendingTransition;

public class Game extends ManagedGame<ManagedScreen, ScreenTransition> {

    private SpriteBatch batch;

    @Override
    public void create() {
        super.create();
        this.batch = new SpriteBatch();

        this.screenManager.addScreen("combat", new CombatScreen());
        this.screenManager.addScreen("test", new TestScreen());

        BlendingTransition blendingTransition = new BlendingTransition(batch, 1F, Interpolation.pow2In);
        screenManager.addScreenTransition("blending_transition", blendingTransition);

        this.screenManager.pushScreen("combat", null);
//        this.screenManager.pushScreen("test", null);
    }
}
