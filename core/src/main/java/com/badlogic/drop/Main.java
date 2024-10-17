package com.badlogic.drop;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import org.w3c.dom.Text;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.awt.*;
import java.awt.image.renderable.RenderContext;
import java.sql.Time;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main implements ApplicationListener {
    FitViewport viewport;

    ShapeRenderer shape;

    Vector2 clickPos;

    Vector2 pos = new Vector2(100, 100);
    int yChange = 300;
    int xChange = 300;

    float timerValue = 0;
    public static Texture backgroundImage;
    private SpriteBatch batch;
    @Override
    public void create() {
        viewport = new FitViewport(800, 450);

        shape = new ShapeRenderer();
        batch = new SpriteBatch();
        // Load the background image
        backgroundImage = new Texture(Gdx.files.internal("map.png"));
        // Prepare your application here.
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void render() {
        handleInput();

        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        Gdx.gl.glClearColor(1, 1, 1, 1); // Set clear color (white)
        Gdx.gl.glClear(Gdx.gl.GL_COLOR_BUFFER_BIT);

        // Draw the background image at (0, 0)
        batch.begin();
        batch.draw(backgroundImage, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());
        batch.end();

        pos.x += xChange * Gdx.graphics.getDeltaTime();
        pos.y += yChange * Gdx.graphics.getDeltaTime();

        timerValue += Gdx.graphics.getDeltaTime();

        //Draw timer

        batch.begin();
        BitmapFont font = new BitmapFont();
        font.getData().setScale(5, 5);
        font.draw(batch, String.valueOf(Math.round(timerValue)), 250, 250);
        batch.end();

    }//

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

            pos = clickPos;
        }
    }
}
