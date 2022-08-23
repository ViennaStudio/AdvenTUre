package com.viennastudio.adventure.hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.TimeUtils;

public class ChatBox {

    private static final float timePerLetter = 1000000f;
    private static int maxCharacters = 250; //Unsure about this one
    private static int maxLines = 5;
    private static final Texture background = new Texture(Gdx.files.internal("HUD_Graphics/ChatboxBG.png"));
    private final String text;
    private final char[] textArray;
    private final BitmapFont font;
    private final SpriteBatch batch;
    private final String npcName;

    public ChatBox(SpriteBatch batch, BitmapFont font, String npcName, String text) {
        this.batch = batch;
        this.font = font;
        this.text = text;
        textArray = text.toCharArray();
        this.npcName = npcName;
    }

    public void draw() {
        float chatBoxX = 320f;
        float chatBoxY = 20f;
        float chatboxWidth = 640f;
        float chatboxHeight = 240f;
        GlyphLayout layout = new GlyphLayout(font, npcName);
        float npcNameX = chatBoxX + (chatboxWidth - layout.width) / 2;
        float npcNameY = chatBoxY + chatboxHeight - 20f;

        //Determine the number of lines needed to display the text
        int linesNeeded = (int) Math.ceil(textArray.length / maxCharacters);

        //Draw background
        batch.draw(background, chatBoxX, chatBoxY);
        //Draw text
        font.draw(batch, npcName, npcNameX, npcNameY);

        //Draw text letter after letter
        long timeSinceLastLetter = 0;
        float letterX = chatBoxX + 20f;
        float letterY = chatBoxY + chatboxHeight - layout.height - 10f;
        int currentLines = 0;
        int currentChar = 0;
        while (currentChar < textArray.length) {
            if (TimeUtils.timeSinceMillis(timeSinceLastLetter) > timePerLetter) {
                timeSinceLastLetter = 0;
                    font.draw(batch, String.valueOf(textArray[currentChar]), letterX, letterY);
                    letterX += 20;
                    currentChar++;
                } else {
                    timeSinceLastLetter += Gdx.graphics.getDeltaTime();
                }
            }
        }

    }
