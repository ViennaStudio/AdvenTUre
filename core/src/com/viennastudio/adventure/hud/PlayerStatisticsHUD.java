package com.viennastudio.adventure.hud;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.viennastudio.adventure.Constants;
import com.viennastudio.adventure.entities.Player;

public class PlayerStatisticsHUD {
    private Player player;
    private ShapeRenderer renderer;
    private BitmapFont font;
    private SpriteBatch spriteBatch;

    public PlayerStatisticsHUD(Player player, BitmapFont font, SpriteBatch spriteBatch, ShapeRenderer renderer) {
        this.player = player;
        this.font = font;
        this.font.getData().setScale(0.66f);
        this.spriteBatch = spriteBatch;
        this.renderer = renderer;
    }

    public void drawHudText() {
       drawTextHealthBar();
       drawECTS();
    }

    public void drawHudGraphics() {
        drawMentalHealthBar();
    }

    private void drawTextHealthBar() {
        this.font.setColor(Color.WHITE);
        this.font.draw(this.spriteBatch, "Mental Health: ", 5, Constants.WINDOW_HEIGHT - 10);
        this.font.setColor(Color.BLACK);
        this.font.draw(this.spriteBatch, Integer.toString(player.getMentalHealth()), 285, Constants.WINDOW_HEIGHT - 10);
    }

    private void drawECTS() {
        this.font.setColor(Color.WHITE);
        this.font.draw(this.spriteBatch, "ECTS: " + this.player.getECTS() + "/" + Constants.MAX_ECTS, 500, Constants.WINDOW_HEIGHT - 10);
    }

    private void drawMentalHealthBar() {
        int startingPos = 202;
        // Red Bar beneith the green
        this.renderer.setColor(Color.RED);
        this.renderer.rect(startingPos, Constants.WINDOW_HEIGHT - 30, 200, 30);
        // Green Bar
        this.renderer.setColor(Color.GREEN);
        this.renderer.rect(startingPos, Constants.WINDOW_HEIGHT - 30, player.getMentalHealth() * 2, 30);
        // Outline
        this.renderer.setColor(Color.WHITE);
        this.renderer.setAutoShapeType(true);
        this.renderer.set(ShapeRenderer.ShapeType.Line);
        this.renderer.rect(startingPos, Constants.WINDOW_HEIGHT - 30, 200, 30);
    }

}
