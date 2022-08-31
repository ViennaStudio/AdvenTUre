package com.viennastudio.adventure.hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Align;
import com.viennastudio.adventure.util.KeyMap;
import com.viennastudio.adventure.util.Timer;

public class ChatBox extends Actor {
    private static final Texture background = new Texture(Gdx.files.internal("skin/HUD/ChatboxBG.png"));
    private final String text;
    private final BitmapFont font;
    private final String npcName;
    private final float chatBoxX = 320f;
    private final float chatBoxWidth = 640f;
    private final float chatBoxHeight = 240f;
    private int currChar = 0;
    private int startChar = 0;
    private final GlyphLayout layout;
    private final Timer timer;
    private boolean paused = false;
    private boolean finished = false;


    public ChatBox(BitmapFont font, String npcName, String text) {
        this.font = font;
        this.text = text;
        this.npcName = npcName;
        this.layout = new GlyphLayout(font, npcName);
        timer = new Timer(60, true);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        float npcNameX = chatBoxX + (chatBoxWidth - layout.width) / 2;
        float chatBoxY = 20f;
        float npcNameY = chatBoxY + chatBoxHeight - 20f;

        if (currChar == text.length() && !finished) {
            drawContinueMessage(batch, "Press SPACE to end!");
        }

        if (currChar >= text.length() && Gdx.input.isKeyPressed(KeyMap.CONTINUE_CHATBOX_KEY)) {
            finished = true;
        }

        if (!finished) {
            //Draw background
            batch.draw(background, chatBoxX, chatBoxY);

            //Draw text
            font.setColor(Color.BLUE);
            font.draw(batch, npcName, npcNameX, npcNameY);

            //Draw text letter after letter
            drawLetters(batch, Gdx.graphics.getDeltaTime());
        }
    }

    private void drawLetters(Batch batch, float delta) {
        final float startLetterX = chatBoxX + 20f;
        float yPosLetter = chatBoxHeight - layout.height - 30f;
        boolean maxCharsReached = currChar >= text.length();

        if (paused) {
            drawContinueMessage(batch,"Press SPACE to continue!");
        }

        if (paused && Gdx.input.isKeyPressed(KeyMap.CONTINUE_CHATBOX_KEY)) {
            paused = false;
            startChar = currChar;
        }

        if (timer.tick(delta) && !maxCharsReached && !paused) {
            currChar++;
        }

        GlyphLayout layoutText = new GlyphLayout(font, text.substring(startChar, currChar), Color.WHITE, chatBoxWidth - 20, Align.left, true);
        font.draw(batch, layoutText, startLetterX, yPosLetter);

        if (layoutText.height > chatBoxHeight - 100f && !paused) {
            paused = true;
        }
    }

    private void drawContinueMessage(Batch batch, String message) {
        font.setColor(Color.WHITE);
        font.getData().setScale(0.5f);
        GlyphLayout layoutContinue = new GlyphLayout(font, message, Color.WHITE, chatBoxWidth, Align.center, false);
        font.draw(batch, layoutContinue, chatBoxX, chatBoxHeight + 40f);
        font.getData().setScale(1f);
    }

}
