package com.badlogic.drop;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Source;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Payload;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Target;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.Actor;


public class BuildingMenu {
    private Stage stage;
    private Skin skin;
    private DragAndDrop dragAndDrop;
    private Image buildingAsset1;
    private Image buildingAsset2;
    private Main main;  // Reference to Main class for TileManager access
    private Actor dropTargetActor;

    public BuildingMenu(Skin skin, Stage stage, Main main) {
        this.skin = skin;
        this.stage = stage;
        this.main = main;
        this.dragAndDrop = new DragAndDrop();

        // Load textures for assets
        buildingAsset1 = new Image(new Texture("lectureHall.png"));
        buildingAsset2 = new Image(new Texture("pub.png"));

        setupMenu();
        setupDragAndDrop();
    }

    private void setupMenu() {
        Table table = new Table();
        table.setFillParent(true);
        table.top().left();  // Place menu at top-left corner

        // Add assets to the table
        table.add(buildingAsset1).size(100, 100).pad(10);
        table.row();
        table.add(buildingAsset2).size(100, 100).pad(10);

        stage.addActor(table);
        // Create a drop target actor representing the map area
        dropTargetActor = new Actor();
        dropTargetActor.setBounds(0, 0, stage.getWidth(), stage.getHeight());  // Set size to cover map area
        stage.addActor(dropTargetActor);
    }

    private void setupDragAndDrop() {
        // Define draggable items for both assets
        createDragSource(buildingAsset1, "building1.png");
        createDragSource(buildingAsset2, "building2.png");

        // Define drop target for the game world
        dragAndDrop.addTarget(new Target(dropTargetActor) {
            @Override
            public boolean drag(Source source, Payload payload, float x, float y, int pointer) {
                return true;  // Allow drop anywhere on the map
            }

            @Override
            public void drop(Source source, Payload payload, float x, float y, int pointer) {
                Vector2 worldCoords = main.viewport.unproject(new Vector2(x, y));  // Convert to world coordinates
                String textureLocation = (String) payload.getObject();

                // Create a new Building at the drop location with the specified texture
                Building building = new Building(worldCoords.x, worldCoords.y, textureLocation);
                main.tileManager.addBuilding(building);
            }
        });
    }

    private void createDragSource(final Image asset, final String textureLocation) {
        dragAndDrop.addSource(new Source(asset) {
            @Override
            public Payload dragStart(InputEvent event, float x, float y, int pointer) {
                Payload payload = new Payload();
                payload.setObject(textureLocation);  // Store texture location in payload

                // Optionally add visuals for drag-and-drop
                Image dragActor = new Image(asset.getDrawable());
                dragActor.setSize(80, 80); // Resize as needed
                payload.setDragActor(dragActor);
                return payload;
            }
        });
    }

    public Stage getStage() {
        return stage;
    }
}
