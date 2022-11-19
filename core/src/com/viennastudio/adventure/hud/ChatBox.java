package com.viennastudio.adventure.hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Align;
import com.viennastudio.adventure.util.KeyMap;
import com.viennastudio.adventure.util.Timer;

public class ChatBox extends Group {
    private final ChatBoxStyle style;
    private final String text;
    private int currChar = 0;
    private int startChar = 0;
    private int lastWhiteSpace = 0;
    private final Label messageLabel;
    private final Label continueLabel;
    private final float MAX_MSG_HEIGHT;
    private final Timer timer;
    private boolean paused = false;
    private boolean finished = false;


    public ChatBox(Skin skin, String styleName, String npcName, String text) {
        this.style = skin.get(styleName, ChatBoxStyle.class);
        this.text = text;
        timer = new Timer(60, true);

        setBounds(320, 0, 640, 240);

        Image image = new Image(skin, "ChatboxBG");
        Label npcLabel = new Label(npcName, skin, "ACCENT_2_LIGHT");
        npcLabel.setPosition(getWidth() / 2f, getHeight() - style.borderWidth, Align.center | Align.top);

        messageLabel = new Label("", skin);
        messageLabel.setPosition(style.borderWidth, 1.5f * style.borderWidth, Align.left);
        messageLabel.setWidth(getWidth() - 2 * style.borderWidth);
        messageLabel.setHeight(getHeight() - 3 * style.borderWidth - npcLabel.getHeight());
        messageLabel.setWrap(true);

        continueLabel = new Label("", skin, "default");
        continueLabel.setFontScale(0.5f);
        // Set width to width of new width with small text
        continueLabel.setWidth(continueLabel.getPrefWidth());
        continueLabel.setPosition(getWidth() / 2f, getHeight() + style.borderWidth, Align.center);
        addActor(image);
        addActor(npcLabel);
        addActor(messageLabel);
        addActor(continueLabel);

        MAX_MSG_HEIGHT = getHeight() - 3 * style.borderWidth - npcLabel.getHeight();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        if (currChar == text.length() && !finished) {
            drawContinueMessage("Press SPACE to end!");
        }

        if (currChar >= text.length() && Gdx.input.isKeyPressed(KeyMap.CONTINUE_CHATBOX_KEY)) {
            finished = true;
            remove();
        }

        if (!finished) {
            drawLetters(Gdx.graphics.getDeltaTime());
        }
    }

    private void drawLetters(float delta) {
        boolean maxCharsReached = currChar >= text.length();

        if (paused) {
            drawContinueMessage("Press SPACE to continue!");

            if (Gdx.input.isKeyPressed(KeyMap.CONTINUE_CHATBOX_KEY)) {
                paused = false;
                continueLabel.setText("");
                startChar = currChar;
            }
        }

        if (timer.tick(delta) && !maxCharsReached && !paused) {
            if (Character.isWhitespace(text.charAt(currChar))) lastWhiteSpace = currChar;
            messageLabel.setText(text.substring(startChar, currChar));
            currChar++;
        }

        if (messageLabel.getGlyphLayout().height > MAX_MSG_HEIGHT) {
            // remove last added char
            currChar = lastWhiteSpace;
            messageLabel.setText(text.substring(startChar, ++currChar));
            paused = true;
        }
    }

    private void drawContinueMessage(String message) {
        continueLabel.setText(message);
        // Set width to width of new width with small text
        continueLabel.setWidth(continueLabel.getPrefWidth());
        continueLabel.setPosition(getWidth() / 2f, getHeight() + style.borderWidth, Align.center);
    }

    static public class ChatBoxStyle {
        public BitmapFont font;
        public Drawable background;
        public float borderWidth;

        public ChatBoxStyle() {
        }

        public ChatBoxStyle(BitmapFont font, Drawable background, float borderWidth) {
            this.font = font;
            this.background = background;
            this.borderWidth = borderWidth;
        }

        public ChatBoxStyle(Label.LabelStyle style) {
            font = style.font;
            background = style.background;
        }
    }
}
