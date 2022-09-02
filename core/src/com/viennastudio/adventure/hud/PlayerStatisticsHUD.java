package com.viennastudio.adventure.hud;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Align;
import com.viennastudio.adventure.AdvenTUreGame;
import com.viennastudio.adventure.util.Constants;
import com.viennastudio.adventure.entities.Player;

public class PlayerStatisticsHUD extends Group {
    private final Player player;
    private final ShapeRenderer renderer;
    private final BitmapFont font;
    private final SpriteBatch spriteBatch;
    private final MentalHealthBar mentalHealthBar;
    private final float staticWidth = 350f;

    public PlayerStatisticsHUD(AdvenTUreGame game, Player player, BitmapFont font, SpriteBatch spriteBatch, ShapeRenderer renderer) {
        this.player = player;
        this.font = font;
        this.spriteBatch = spriteBatch;
        this.renderer = renderer;

        mentalHealthBar = new MentalHealthBar(player, game.skin);
        mentalHealthBar.setSize(200, getHeight());
        mentalHealthBar.setFontScale(0.66f);
        addActor(mentalHealthBar);
    }

    @Override
    protected void sizeChanged() {
        mentalHealthBar.setSize(200, getHeight());
    }

    public void drawHudText() {
        this.font.getData().setScale(0.66f);
        drawTextHealthBar();
        drawECTS();
        this.font.getData().setScale(1f);
    }

    public void drawHudGraphics() {

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
}
