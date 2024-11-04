package com.badlogic.drop;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.InputMultiplexer;

import java.awt.*;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main implements ApplicationListener {
    FitViewport viewport;

    InputManager inputManager;

    Vector2 clickPos;

    float timerValue = 300;

    public static Texture backgroundImage;

    public TileManager tileManager;

    private SpriteBatch batch;
    public int STAGE = 0;
    private Stage startMenuStage;
    private Skin skin;
    private OrthographicCamera camera;
    public BuildingMenu buildingMenu;
    private InputMultiplexer inputMultiplexer;


    @Override
    public void create() {
        STAGE = 0;
        camera = new OrthographicCamera();
        viewport = new FitViewport(1920, 1080, camera);

        // Initialize batch and input manager
        batch = new SpriteBatch();
        inputManager = new InputManager(viewport, camera);

        // Load background image
        backgroundImage = new Texture(Gdx.files.internal("map.png"));

        tileManager = new TileManager(48, 27);
        inputManager.tileManager = tileManager;
        for (int x=47; x>42; x--){
            for (int y = 26; y>20; y--){
                tileManager.LockTile(x, y);
            }
        }
        // Setup start menu stage
        setupStartMenu();

        // Initialize InputMultiplexer but set only the start menu initially
        inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(startMenuStage);
        Gdx.input.setInputProcessor(inputMultiplexer);
    }
    private void setupStartMenu() {
        startMenuStage = new Stage(new ScreenViewport());
        skin = new Skin(Gdx.files.internal("uiskin.json"));

        TextButton startButton = new TextButton("Start", skin);

        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Change the stage when the start button is clicked
                STAGE = 1;
                buildingMenu = new BuildingMenu(skin, inputManager);
                inputMultiplexer.clear();
                inputMultiplexer.addProcessor(buildingMenu.getStage());
                inputMultiplexer.addProcessor(inputManager);
                Gdx.input.setInputProcessor(inputMultiplexer);
            }
        });

        // Create a table to center the button
        Table table = new Table();
        table.setFillParent(true);
        table.center();
        table.add(startButton).width(500).height(500);
        startMenuStage.addActor(table);
    }

    @Override
    public void render() {
        if (STAGE == 0) {
            renderStartMenu();
        } else if (STAGE == 1) {
            renderMainGame();
        }
    }

    private void renderStartMenu() {
        // Clear the screen for the start menu
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Render the start menu stage
        startMenuStage.act(Gdx.graphics.getDeltaTime());
        startMenuStage.draw();
    }
    private void renderMainGame() {

        // Update the camera
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        // Clear the screen
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Draw the background and tiles
        batch.begin();
        batch.draw(backgroundImage, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());
        batch.end();

        // Render the tiles
        tileManager.RenderTiles(batch);
        buildingMenu.render(batch);
        // Update and render the timer
        timerValue -= Gdx.graphics.getDeltaTime();
        int time = Math.round(timerValue);
        batch.begin();
        BitmapFont font = new BitmapFont();
        font.getData().setScale(5, 5);
        font.draw(batch,time < 60 ? ("" + time) : ("" + time / 60 + ":" + (time % 60 != 0 ? (time % 60) : "00")), 250, 250);
        batch.end();

        batch.begin();
        BitmapFont buildingFont = new BitmapFont();
        buildingFont.getData().setScale(3.5f, 3.5f);
        buildingFont.draw(batch, "Lecture Halls: " + inputManager.lectureCount + "\n" +
                "Accomodations: " + inputManager.accommodationCount + "\n" +
                "Labs: " + inputManager.labCount + "\n" +
                "Restaurants: " + inputManager.restaurantCount + "\n" +
                "Gyms: " + inputManager.gymCount + "\n" +
                "Paths: " + inputManager.pathCount

            , 75, 1000);
        batch.end();


        for (int i = 0; i < inputManager.people.size(); i++) {
            inputManager.people.get(i).render(batch);
        }

        // Render tiles and buildings
        tileManager.RenderTiles(batch);
        for (Building building : tileManager.getBuildings()) {
            building.render(batch);
        }


    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }


    @Override
    public void pause() {
        // Invoked when your application is paused.
    }

    @Override
    public void resume() {
        // Invoked when your application is resumed after pause.
    }

    @Override
    public void dispose() {
        batch.dispose();
        backgroundImage.dispose();
        startMenuStage.dispose();
        skin.dispose();
    }
}
