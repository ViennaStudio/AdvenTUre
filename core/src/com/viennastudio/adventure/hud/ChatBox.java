package com.viennastudio.adventure.hud;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.viennastudio.adventure.Constants;

public class ChatBox {

    private static int startPosX = 20;
    private static int startPosY = 20;
    private static int endPosX = Constants.WINDOW_WIDTH - 20;
    private static int endPosY = Constants.WINDOW_HEIGHT / 3;
    private static int typingSpeed = 5;
    private static int maxCharacters = 50; //Unsure about this one
    private static int maxLines = 5;
    private static Texture background;
    private String text;
    private BitmapFont font;

    public ChatBox(BitmapFont font, String text) {
        this.font = font;
        this.text = text;
    }

    public void draw() {
        //Draw background
        //Draw text
    }

}
