package com.mygdx.game.utils;

public class Timer {

    private int framesElapsed;
    private final int framesTriggered;

    private final TimerFunction timerFunction;

    public Timer(int framesTriggered, TimerFunction onTrigger) {

        this.framesTriggered = framesTriggered;
        this.framesElapsed = 0;

        this.timerFunction = onTrigger;
    }

    public void Update() {

        framesElapsed++;

        if (framesElapsed == framesTriggered) {

            timerFunction.onTrigger();

            framesElapsed = 0;
        }
    }
}
