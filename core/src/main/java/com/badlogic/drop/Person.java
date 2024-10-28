package com.badlogic.drop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;

public class Person {

    public Vector2 pos;

    public Person(Vector2 pos){
        this.pos = pos;
    }

    public void render(SpriteBatch spriteBatch){
        Move(1920/2, 1080/2);

        spriteBatch.begin();
        spriteBatch.draw(new Texture("person.png"), pos.x, pos.y, 20, 20);  // Draw with width and height
        spriteBatch.end();
    }

    public void Move(int x, int y){
        pos.interpolate(new Vector2(x, y), Gdx.graphics.getDeltaTime(), Interpolation.linear);
    }
}
