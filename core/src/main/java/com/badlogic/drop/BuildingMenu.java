package com.badlogic.drop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

/**
 * Represents the in-game menu for selecting and placing buildings.
 * Provides buttons for different building types and updates the game state
 * via the InputManager when a building type is selected.
 */
public class BuildingMenu {

    private Stage stage;
    private Skin skin;
    private InputManager inputManager;  // Reference to handle game state changes

    /**
     * Constructs the BuildingMenu.
     *
     * @param skin The UI skin used for styling the buttons and other UI components.
     * @param inputManager The InputManager responsible for handling building-related inputs.
     */
    public BuildingMenu(Skin skin, InputManager inputManager) {
        this.skin = skin;
        this.inputManager = inputManager;
        this.stage = new Stage();

        // Set up the building selection menu with buttons
        setupMenu();
    }

    /**
     * Adjusts the stage's viewport when the screen is resized.
     *
     * @param width  The new width of the screen.
     * @param height The new height of the screen.
     */
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    /**
     * Configures the menu layout with buttons for each building type.
     * Each button updates the corresponding building count in the InputManager.
     */
    private void setupMenu() {
        Table table = new Table();
        table.setFillParent(true);
        table.top().right();
        stage.addActor(table);

        // Define assets (buildings) and their corresponding values
        String[] assetNames = {"Lecture Hall", "Accommodation", "Pub", "Gym", "Lab", "Path", "Deselect"};
        int[] assetValues = {1, 2, 3, 4, 5, 6, 0};  // Values used to identify building types in InputManager

        // Set a fixed width and height for each button
        int buttonWidth = 150;
        int buttonHeight = 40;

        // Create buttons for each building type and set up listeners
        for (int i = 0; i < assetNames.length; i++) {
            TextButton button = new TextButton(assetNames[i], skin);
            final int index = i;  // Final index for use in the click listener

            button.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    // Update building count in InputManager based on the clicked button
                    inputManager.updateBuildingNum(assetValues[index]);
                }
            });

            // Set fixed size for the button and add it to the table
            table.add(button).width(buttonWidth).height(buttonHeight).pad(10);
            table.row();  // Move to the next row in the table layout
        }
    }

    /**
     * Renders the building menu.
     *
     * @param spriteBatch The SpriteBatch used for rendering.
     */
    public void render(SpriteBatch spriteBatch) {
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    /**
     * Returns the stage containing the building menu.
     *
     * @return The Stage object containing the menu UI elements.
     */
    public Stage getStage() {
        return stage;
    }

    /**
     * Disposes of resources used by the BuildingMenu.
     */
    public void dispose() {
        if (stage != null) {
            stage.dispose();
        }
    }
}
