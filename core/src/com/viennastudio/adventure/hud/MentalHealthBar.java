package com.viennastudio.adventure.hud;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Align;
import com.viennastudio.adventure.entities.Player;

public class MentalHealthBar extends Group {
    private final Player player;
    private MentalHealthBarStyle style;
    private final Image background;
    private final Image bar;
    private final Label label;
    public MentalHealthBar(Player player, Skin skin) {
        this(player, skin, "default");
    }

    public MentalHealthBar(Player player, Skin skin, String styleName) {
        this.player = player;
        style = skin.get(styleName, MentalHealthBarStyle.class);

        background = new Image(style.background);
        background.setSize(getWidth(), getHeight());
        addActor(background);

        bar = new Image(style.bar);
        bar.setSize(getHealthBarWidth(), getHeight());
        addActor(bar);

        label = new Label(getText(), new Label.LabelStyle(style.font, style.fontColor));
        label.setSize(getWidth(), getHeight());
        label.setAlignment(Align.center);
        addActor(label);
    }

    @Override
    public void act(float delta) {
        bar.setWidth(getHealthBarWidth());
        label.setText(getText());
        super.act(delta);
    }

    public void setFontScale(float scale) {
        label.setFontScale(scale);
    }

    @Override
    protected void sizeChanged() {
        background.setSize(getWidth(), getHeight());
        bar.setSize(getHealthBarWidth(), getHeight());
        label.setSize(getWidth(), getHeight());
    }

    static public class MentalHealthBarStyle {
        public BitmapFont font;
        public Color fontColor;
        public Drawable background;
        public Drawable bar;
        public float borderWidth;

        public MentalHealthBarStyle() {
        }

        public MentalHealthBarStyle(BitmapFont font, Color fontColor, Drawable background, Drawable bar, float borderWidth) {
            this.font = font;
            this.fontColor = fontColor;
            this.background = background;
            this.bar = bar;
            this.borderWidth = borderWidth;
        }
    }

    private float getHealthBarWidth() {
        return getWidth() * player.getMentalHealthPercentage();
    }

    private String getText() {
        return player.getMentalHealth() + "/" + player.getMaxMentalHealth();
    }
}
