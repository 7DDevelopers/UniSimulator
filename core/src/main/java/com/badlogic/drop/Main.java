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

        // Setup start menu stage
        setupStartMenu();

        // Set initial input processor to the start menu stage
        Gdx.input.setInputProcessor(startMenuStage);
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
                Gdx.input.setInputProcessor(inputManager);  // Set InputManager for the game stage
                System.out.println("Game State changed to: " + STAGE);
            }
        });

        // Create a table to center the button
        Table table = new Table();
        table.setFillParent(true);
        table.center();
        table.add(startButton).width(200).height(60);
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

        // Update and render the timer
        timerValue -= Gdx.graphics.getDeltaTime();
        int time = Math.round(timerValue);
        batch.begin();
        BitmapFont font = new BitmapFont();
        font.getData().setScale(5, 5);
        font.draw(batch,time < 60 ? ("" + time) : ("" + time / 60 + ":" + (time % 60 != 0 ? (time % 60) : "00")), 250, 250);
        batch.end();

        //Building count
        batch.begin();
        BitmapFont buildingFont = new BitmapFont();
        buildingFont.getData().setScale(5, 5);
        buildingFont.draw(batch, "Buildings: " + tileManager.buildings.size(), 250, 960);
        batch.end();

        for (int i = 0; i < inputManager.people.size(); i++) {
            inputManager.people.get(i).render(batch);
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