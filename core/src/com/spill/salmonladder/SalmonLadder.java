package com.spill.salmonladder;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.spill.salmonladder.Scenes.ScreenStart;

public class SalmonLadder extends Game implements ApplicationListener {

    // THIS IS THE NUMBER OF PIXELS PER TILE / SPRITES
    public static final float PIXEL_PER_METER = 32f;

    @Override
    public void create() {

        this.setScreen(new ScreenStart());

    }

    @Override
    public void render() {

        super.render();

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void dispose() {

    }
}
