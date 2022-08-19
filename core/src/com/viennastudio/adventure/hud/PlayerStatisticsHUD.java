package com.viennastudio.adventure.hud;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.viennastudio.adventure.Constants;

public class PlayerStatisticsHUD {
    private final int xStartCoordinate;
    private int playerHealth;
    private BitmapFont font;

    public PlayerStatisticsHUD(int playerHealth) {
        xStartCoordinate = 200;
        this.playerHealth = playerHealth;
        font = new BitmapFont();
    }

    private void drawInside(ShapeRenderer renderer, int mentalHealth) {
        this.playerHealth = mentalHealth;
        renderer.setColor(Color.RED);
        renderer.rect(xStartCoordinate, Constants.WINDOW_HEIGHT - 21, 200, 21);
        renderer.setColor(Color.GREEN);
        renderer.rect(xStartCoordinate, Constants.WINDOW_HEIGHT - 21, this.playerHealth * 2, 21);
    }

    private void drawOutside(ShapeRenderer renderer) {
        renderer.setColor(Color.WHITE);
        renderer.rect(xStartCoordinate, Constants.WINDOW_HEIGHT - 21, 200, 21);
    }

    private void drawMentalHealthBar(ShapeRenderer renderer, int mentalHealth, SpriteBatch batch, BitmapFont font) {
        drawText(batch, font);
        drawInside(renderer, mentalHealth);
        renderer.setAutoShapeType(true);
        renderer.set(ShapeRenderer.ShapeType.Line);
        drawOutside(renderer);
    }

    private void drawText(SpriteBatch batch, BitmapFont font) {
        font.getData().setScale(0.5f);
        font.draw(batch, "Mental Health: " + this.playerHealth, 5, Constants.WINDOW_HEIGHT - 6);
    }

    public void drawPlayerStatistics(ShapeRenderer renderer, int mentalHealth, SpriteBatch batch, BitmapFont font) {
        drawMentalHealthBar(renderer, mentalHealth, batch, font);
    }





}
