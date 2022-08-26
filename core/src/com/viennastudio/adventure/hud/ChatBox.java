package com.viennastudio.adventure.hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.viennastudio.adventure.util.Timer;

public class ChatBox {
    private static final Texture background = new Texture(Gdx.files.internal("HUD_Graphics/ChatboxBG.png"));
    private final String text;
    private final BitmapFont font;
    private final SpriteBatch batch;
    private final String npcName;
    private final int maxCharsPerLine = 29;
    private final float chatBoxX = 320f;
    private final float chatBoxY = 20f;
    private final float chatBoxWidth = 640f;
    private final float chatBoxHeight = 240f;
    private int currChar = 0;
    private int lineCount = 0;
    private int maxLines = 6;
    private final GlyphLayout layout;
    private final Timer timer;


    public ChatBox(SpriteBatch batch, BitmapFont font, String npcName, String text) {
        this.batch = batch;
        this.font = font;
        this.text = text;
        this.npcName = npcName;
        this.layout = new GlyphLayout(font, npcName);
        timer = new Timer(200, false);
    }

    public void draw(float delta) {
        float npcNameX = chatBoxX + (chatBoxWidth - layout.width) / 2;
        float npcNameY = chatBoxY + chatBoxHeight - 20f;

        //Determine the number of lines needed to display the text

        //Draw background
        batch.draw(background, chatBoxX, chatBoxY);
        //Draw text
        font.setColor(Color.BLUE);
        font.draw(batch, npcName, npcNameX, npcNameY);

        //Draw text letter after letter
        drawLetters(delta);
    }

    public void drawLetters(float delta) {
        final float startLetterX = chatBoxX + 20f;
        float yPosLetter = chatBoxHeight - layout.height - 20f;

        timer.start();
        boolean b = timer.tick(delta);
        if (b) {
            currChar++;
        }

        if (currChar % maxCharsPerLine == 0) {
            lineCount++;
            yPosLetter -= layout.height;
        }

        font.setColor(Color.WHITE);
        font.draw(batch, text.substring(0, currChar), startLetterX, yPosLetter);
    }

}
