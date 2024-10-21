package com.badlogic.drop;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import org.w3c.dom.Text;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.scenes.scene2d.InputEvent;

import java.awt.*;
import java.awt.image.renderable.RenderContext;
import java.sql.Time;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main implements ApplicationListener {
    FitViewport viewport;

    InputManager inputManager;

    Vector2 clickPos;

    float timerValue = 0;

    public static Texture backgroundImage;

    public TileManager tileManager;

    private SpriteBatch batch;

    public int STAGE = 0;
    private Stage startMenuStage;
    private Skin skin;

    @Override
    public void create() {
        batch = new SpriteBatch();
        startMenuStage = new Stage(new ScreenViewport());

        skin = new Skin(Gdx.files.internal("uiskin.json"));

        Gdx.input.setInputProcessor(startMenuStage);

        TextButton startButton = new TextButton("Start", skin);

        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                STAGE = 1;
                System.out.println("Game State changed to: " + STAGE);  // For debugging
            }
        });

        Table table = new Table();
        table.setFillParent(true);
        table.center();

        table.add(startButton).width(200).height(60);

        startMenuStage.addActor(table);
        viewport = new FitViewport(800, 450);

        inputManager = new InputManager((OrthographicCamera)viewport.getCamera());
        //viewport.getCamera().update();
        //Gdx.app.log("debug", "" + ((OrthographicCamera)viewport.getCamera()).zoom);

        Gdx.input.setInputProcessor(inputManager);

        batch = new SpriteBatch();
        // Load the background image
        backgroundImage = new Texture(Gdx.files.internal("map.png"));

        tileManager = new TileManager(13,8);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void render() {
        if(STAGE == 1){mainGame();}
        if(STAGE==0){startMenu();}

    }//
    public void startMenu(){
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Draw the stage
        startMenuStage.act(Gdx.graphics.getDeltaTime());
        startMenuStage.draw();
    }
    public void mainGame(){
        handleInput();

        //Update camera
        viewport.getCamera().update();
        batch.setProjectionMatrix(viewport.getCamera().combined);

        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Draw the background image at (0, 0)
        batch.begin();
        batch.draw(backgroundImage, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());
        batch.end();

        //Draw unlockable tiles
        tileManager.RenderTiles(batch);

        //Draw timer
        timerValue += Gdx.graphics.getDeltaTime();

        batch.begin();
        BitmapFont font = new BitmapFont();
        font.getData().setScale(5, 5);
        font.draw(batch, String.valueOf(Math.round(timerValue)), 250, 250);
        batch.end();
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
        // Destroy application's resources here.
    }

    private void handleInput(){
        if (Gdx.input.isTouched()) {
            clickPos = new Vector2(Gdx.input.getX(), Gdx.input.getY());
            //Gdx.app.log("debug", clickPos.x + " " + clickPos.y + " : " + viewport.getScreenWidth() + " " + viewport.getScreenHeight());
            viewport.unproject(clickPos);
        }
    }
}
