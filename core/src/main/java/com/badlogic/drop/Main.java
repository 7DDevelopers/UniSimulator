package com.badlogic.drop;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import org.w3c.dom.Text;

import java.awt.*;
import java.awt.image.renderable.RenderContext;
import java.sql.Time;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main implements ApplicationListener {
    ShapeRenderer shape;

    Vector2 pos = new Vector2(100, 100);
    int yChange = 300;
    int xChange = 300;

    float timerValue = 0;

    @Override
    public void create() {
        shape = new ShapeRenderer();

        // Prepare your application here.
    }

    @Override
    public void resize(int width, int height) {
        // Resize your application here. The parameters represent the new window size.
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        shape.begin(ShapeRenderer.ShapeType.Filled);
        shape.rect(pos.x, pos.y, 50, 50);
        shape.setColor(Color.BLUE);
        shape.end();

        pos.x += xChange * Gdx.graphics.getDeltaTime();
        pos.y += yChange * Gdx.graphics.getDeltaTime();

        if(pos.x >=  1920 || pos.x < 0){
            xChange = -xChange;
        }
        if(pos.y >=  1080 || pos.y < 0) {
            yChange = -yChange;
        }

        timerValue += Gdx.graphics.getDeltaTime();

        SpriteBatch spriteBatch = new SpriteBatch();
        spriteBatch.begin();
        BitmapFont font = new BitmapFont();
        font.draw(spriteBatch, String.valueOf(Gdx.graphics.getFramesPerSecond()), 10, 10);
        spriteBatch.end();

        Gdx.app.log("debug", String.valueOf(pos.x) + "  " + String.valueOf(pos.y));
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
}
