package com.badlogic.drop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import java.util.concurrent.TimeoutException;

public class BuildingMenu {
    private Stage stage;
    private Skin skin;
    private InputManager inputManager;  // Reference to the GameState class

    public BuildingMenu(Skin skin, InputManager inputManager) {
        this.skin = skin;
        this.inputManager =  inputManager;
        this.stage = new Stage();

        setupMenu();
    }

    private void setupMenu() {
        Table table = new Table();
        table.setFillParent(true);
        table.top().right();
        stage.addActor(table);

        // Define your assets and their corresponding actions
        String[] assetNames = {"Lecture Hall", "Accommodation", "Pub", "Gym", "Lab"};
        int[] assetValues = {1, 2, 3, 4, 5}; // Values to change in GameState

        for (int i = 0; i < assetNames.length; i++) {
            TextButton button = new TextButton(assetNames[i], skin);
            final int index = i;  // To use in the listener
            button.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    inputManager.updateBuildingNum(assetValues[index]);
                    // variable in GameState
                }
            });
            table.add(button).pad(10).fillX();
            table.row(); // Move to next row
        }
    }

    public void render(SpriteBatch spriteBatch) {
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    public Stage getStage() {
        return stage;
    }
}
