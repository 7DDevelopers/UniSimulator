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
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
/**
 * Main game class implementing ApplicationListener for handling game setup, rendering, and user input.
 * Manages the main game stages, including the start menu and the main game screen.
 */
public class Main implements ApplicationListener {

    /**
     * Viewport for scaling the game world.
     */
    FitViewport viewport;

    /**
     * Manages user input.
     */
    InputManager inputManager;

    /**
     * Position of the latest user click.
     */
    Vector2 clickPos;

    /**
     * Game timer in seconds.
     */
    float timerValue = 300;

    /**
     * Game score, to be implemented later
     */
    float score = 50;
    /**
     * Background image for the game map.
     */
    public static Texture backgroundImage;
    /**
     * Background image for the menu.
     */
    public static Texture menuBackground;

    /**
     * Manages the grid and tiles in the game.
     */
    public TileManager tileManager;

    boolean gameActive = true;
    private SpriteBatch batch;

    private BitmapFont font;
    private BitmapFont buildingFont;

    /**
     * Represents the current stage of the game (0: Start Menu, 1: Main Game).
     */
    public int STAGE = 0;

    private Stage startMenuStage;
    private Skin skin;
    private OrthographicCamera camera;

    /**
     * Handles the building selection menu.
     */
    public BuildingMenu buildingMenu;

    private InputMultiplexer inputMultiplexer;

    @Override
    public void create() {
        STAGE = 0;
        camera = new OrthographicCamera();
        viewport = new FitViewport(1920, 1080, camera);

        // Initialize fonts
        font = new BitmapFont();
        font.getData().setScale(5, 5);

        buildingFont = new BitmapFont();
        buildingFont.getData().setScale(3.5f, 3.5f);

        // Initialize batch and input manager
        batch = new SpriteBatch();
        inputManager = new InputManager(viewport, camera);

        // Load background images
        backgroundImage = new Texture(Gdx.files.internal("map.png"));
        menuBackground = new Texture(Gdx.files.internal("menuBackground.png"));

        // Initialize TileManager and lock some tiles
        tileManager = new TileManager(48, 27);
        inputManager.tileManager = tileManager;
        for (int x = 47; x > 42; x--) {
            for (int y = 26; y > 20; y--) {
                tileManager.LockTile(x, y);
            }
        }

        // Set up the start menu
        setupStartMenu();

        // Initialize InputMultiplexer and set the start menu stage initially
        inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(startMenuStage);
        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    /**
     * Configures the start menu screen with a start button to begin the game.
     */
    private void setupStartMenu() {

        startMenuStage = new Stage(new ScreenViewport());
        skin = new Skin(Gdx.files.internal("uiskin.json"));

        // Create a title label
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = new BitmapFont(); // Use a custom font if available
        labelStyle.font.getData().setScale(10.0f); // Scale the font for a larger title

        Label titleLabel = new Label("UniSim", labelStyle);

        TextButton startButton = new TextButton("Start", skin);
        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Change to main game stage when the start button is clicked
                STAGE = 1;
                buildingMenu = new BuildingMenu(skin, inputManager);
                inputMultiplexer.clear();
                inputMultiplexer.addProcessor(buildingMenu.getStage());
                inputMultiplexer.addProcessor(inputManager);
                Gdx.input.setInputProcessor(inputMultiplexer);
            }
        });

        // Center the start button on the screen using a table
        Table table = new Table();
        table.setFillParent(true);
        table.center();
        table.add(titleLabel).padBottom(300).row();
        table.add(startButton).width(200).height(150);
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

    /**
     * Renders the start menu stage.
     */
    private void renderStartMenu() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Begin the batch and draw the background
        batch.begin();
        batch.draw(menuBackground, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());
        batch.end();

        startMenuStage.act(Gdx.graphics.getDeltaTime());
        startMenuStage.draw();

    }

    /**
     * Renders the main game, including the background, tiles, buildings, and timer.
     */
    private void renderMainGame() {
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        // Clear the screen and draw the background
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(backgroundImage, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());
        batch.end();

        // Render tiles and building menu
        tileManager.RenderTiles(batch);
        buildingMenu.render(batch);

        timer();

        // Render building statistics
        batch.begin();
        buildingFont.getData().setScale(3.5f, 3.5f);
        buildingFont.draw(batch, "Lecture Halls: " + inputManager.lectureCount + "\n" +
            "Accommodations: " + inputManager.accommodationCount + "\n" +
            "Labs: " + inputManager.labCount + "\n" +
            "Restaurants: " + inputManager.restaurantCount + "\n" +
            "Gyms: " + inputManager.gymCount + "\n" +
            "Paths: " + inputManager.pathCount, 75, 1000);
        batch.end();

        // Render each person in the game
        for (Person person : inputManager.people) {
            person.render(batch);
        }

        // Render each building in the game
        for (Building building : tileManager.getBuildings()) {
            building.render(batch);
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        try{
            buildingMenu.resize(width, height);}
        catch(Exception e) {
        }

    }


    /**
     * Updates and renders the countdown timer in the format "M:SS", where "M" is the minutes and "SS" is the seconds.
     * When the timer reaches zero, displays the score popup and closes the game after a delay.
     */
    public void timer() {
        // Update the timer value
        timerValue -= Gdx.graphics.getDeltaTime();

        // Check if the timer has reached zero or below
        if (timerValue <= 0) {
            timerValue = 0;  // Set to zero to avoid negative values

            // Show the score popup with a score

            // Add a delay to allow the popup to display before exiting
            Gdx.app.postRunnable(() -> {
                try {
                    Thread.sleep(3000); // Wait 3 seconds before closing
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Gdx.app.exit();   // Close the game
            });
        }

        // Calculate minutes and seconds from the timer value
        int time = Math.round(timerValue);
        int minutes = time / 60;
        int seconds = time % 60;

        // Begin rendering the timer
        batch.begin();
        font.getData().setScale(5, 5);

        // Display time in "M:SS" format, with seconds always two digits
        font.draw(batch, String.format("%d:%02d", minutes, seconds), 250, 250);

        batch.end();
    }

    /**
     * Calculates the player's score.
     *
     * @return The calculated score as an integer.
     */
    public int calculateScore() {
        // Implement your score calculation logic here
        return 100; // Placeholder score, replace with actual calculation
    }


    @Override
    public void pause() {
        // Invoked when the application is paused.
    }

    @Override
    public void resume() {
        // Invoked when the application is resumed after pause.
    }

    @Override
    public void dispose() {
        batch.dispose();
        backgroundImage.dispose();
        menuBackground.dispose(); // Dispose of menu background
        startMenuStage.dispose();
        skin.dispose();

        // Dispose of fonts if they are initialized outside render loop
        if (font != null) font.dispose();
        if (buildingFont != null) buildingFont.dispose();

        // Dispose of buildingMenu and tileManager if necessary
        if (buildingMenu != null) buildingMenu.dispose();
        if (tileManager != null) tileManager.dispose();
    }
}
