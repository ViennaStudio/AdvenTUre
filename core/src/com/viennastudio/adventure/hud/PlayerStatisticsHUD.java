package com.viennastudio.adventure.hud;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Align;
import com.viennastudio.adventure.util.Constants;
import com.viennastudio.adventure.entities.Player;

public class PlayerStatisticsHUD {
    private final Player player;
    private final ShapeRenderer renderer;
    private final BitmapFont font;
    private final SpriteBatch spriteBatch;
    private final float staticWidth = 350f;

    public PlayerStatisticsHUD(Player player, BitmapFont font, SpriteBatch spriteBatch, ShapeRenderer renderer) {
        this.player = player;
        this.font = font;
        this.spriteBatch = spriteBatch;
        this.renderer = renderer;
    }

    public void drawHudText() {
        this.font.getData().setScale(0.66f);
        drawTextHealthBar();
        drawECTS();
        this.font.getData().setScale(1f);
    }

    public void drawHudGraphics() {
        drawMentalHealthBar();
    }

    private void drawTextHealthBar() {
        float startingPosText = 7f;
        float startingPosBarText = 205f;
        this.font.setColor(Color.WHITE);
        this.font.draw(this.spriteBatch, "Mental Health: ", startingPosText, Constants.WINDOW_HEIGHT - 10f);
        this.font.setColor(Color.BLACK);
        GlyphLayout layout = new GlyphLayout(this.font, this.player.getMentalHealth() + "/" + this.player.getMaxMentalHealth(), Color.BLACK, staticWidth, Align.center, false);
        this.font.draw(this.spriteBatch, layout, startingPosBarText, Constants.WINDOW_HEIGHT - 10f);
    }

    private void drawECTS() {
        float startingPos = 205f + staticWidth;
        GlyphLayout layout = new GlyphLayout(this.font, "ECTS: " + player.getECTS() + "/" + Constants.MAX_ECTS, Color.WHITE, Constants.WINDOW_WIDTH - startingPos, Align.center, false);
        this.font.setColor(Color.WHITE);
        this.font.draw(this.spriteBatch, layout, startingPos, Constants.WINDOW_HEIGHT - 10f);
    }

    private void drawMentalHealthBar() {
        float startingPos = 205f;
        float dynamicWidth = ((float) player.getMentalHealth() / player.getMaxMentalHealth() * staticWidth);
        float height = 30f;
        // Red Bar beneith the green
        this.renderer.setColor(Color.RED);
        this.renderer.rect(startingPos, Constants.WINDOW_HEIGHT - height, staticWidth, height);
        // Green Bar
        this.renderer.setColor(Color.GREEN);
        this.renderer.rect(startingPos, Constants.WINDOW_HEIGHT - height, dynamicWidth, height);
        // Outline
        this.renderer.setColor(Color.WHITE);
        this.renderer.setAutoShapeType(true);
        this.renderer.set(ShapeRenderer.ShapeType.Line);
        this.renderer.rect(startingPos, Constants.WINDOW_HEIGHT - height, staticWidth, height);
    }
}
