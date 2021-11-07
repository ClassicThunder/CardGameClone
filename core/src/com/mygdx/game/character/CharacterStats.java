package com.mygdx.game.character;

public class CharacterStats {

    private int health;
    private int block;

    public void AdjustHealth(int delta) {
        this.health += delta;
    }

    public int GetHealth() {
        return this.health;
    }

    public void AdjustBlock(int delta) {
        this.block += delta;
    }

    public int GetBlock() {
        return this.block;
    }

    public CharacterStats(int startingHealth) {

        this.health = startingHealth;
        this.block = 0;
    }
}
