package com.badlogic.drop;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class BuildingMenu extends ApplicationAdapter {
    private Stage stage;
    private Table menuTable;
    private boolean isMenuVisible = false;
    private float menuWidth = 200f; // Width of the menu
    private float menuSpeed = 5f;   // Speed of menu sliding

    @Override
    public void create() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        // Skin for UI elements
        Skin skin = new Skin(Gdx.files.internal("uiskin.json"));

        // Button to open/close the menu
        TextButton toggleButton = new TextButton("Menu", skin);
        toggleButton.setPosition(100, 100);
        toggleButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                isMenuVisible = !isMenuVisible; // Toggle menu visibility
            }
        });

        // Create the menu table
        menuTable = new Table();
        menuTable.setVisible(false); // Start with menu hidden
        menuTable.setWidth(menuWidth);
        menuTable.setHeight(Gdx.graphics.getHeight());
        menuTable.setPosition(Gdx.graphics.getWidth(), 0); // Start off-screen to the right

        // Asset to display (e.g., an image)
        Texture assetTexture = new Texture(Gdx.files.internal("lectureHall.png"));
        Image assetImage = new Image(assetTexture);
        menuTable.add(assetImage).expand().center();

        // Add button and menu to the stage
        stage.addActor(toggleButton);
        stage.addActor(menuTable);
    }

    @Override
    public void render() {
        // Update menu position
        if (isMenuVisible) {
            // Slide in from the right
            if (menuTable.getX() > Gdx.graphics.getWidth() - menuWidth) {
                menuTable.setPosition(menuTable.getX() - menuSpeed, 0);
            } else {
                menuTable.setPosition(Gdx.graphics.getWidth() - menuWidth, 0);
            }
            menuTable.setVisible(true);
        } else {
            // Slide out to the right
            if (menuTable.getX() < Gdx.graphics.getWidth()) {
                menuTable.setPosition(menuTable.getX() + menuSpeed, 0);
            } else {
                menuTable.setVisible(false);
            }
        }

        // Clear the screen and draw the stage
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
