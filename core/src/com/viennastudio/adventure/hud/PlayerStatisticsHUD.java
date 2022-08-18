package com.viennastudio.adventure.hud;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.viennastudio.adventure.Constants;

public class PlayerStatisticsHUD {

    public PlayerStatisticsHUD() {}

    public void drawInside(ShapeRenderer renderer) {
        renderer.setColor(Color.RED);
        renderer.rect(1, Constants.WINDOW_HEIGHT - 21, 200, 30);
        renderer.setColor(Color.GREEN);
        renderer.rect(1, Constants.WINDOW_HEIGHT - 21, 200, 30);
    }

    public void drawOutside(ShapeRenderer renderer) {
        renderer.setColor(Color.WHITE);
        renderer.rect(1, Constants.WINDOW_HEIGHT - 21, 200, 30);
    }


}
