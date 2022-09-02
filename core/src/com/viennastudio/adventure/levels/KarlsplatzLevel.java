package com.viennastudio.adventure.levels;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import com.viennastudio.adventure.AdvenTUreGame;
import com.viennastudio.adventure.hud.ChatBox;
import com.viennastudio.adventure.level.Level;
import com.viennastudio.adventure.level.LevelConfig;

public class KarlsplatzLevel extends Level {
    ChatBox chatBox;

    public KarlsplatzLevel(AdvenTUreGame game) {
        super(game, new LevelConfig(
                new int[]{0, 1},
                new int[]{2, 3},
                1,
                new Vector2(17, 11),
                "tiles/Karlsplatz.tmx"
        ));
    }

    @Override
    protected void afterShow() {
        //ChatBox
        String text = "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.";
        chatBox = new ChatBox(game.skin, "default","Julian", text);
        game.UIStage.addActor(chatBox);
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        chatBox.setPosition(game.UIStage.getWidth() / 2, 0, Align.bottom | Align.center);
    }
}
