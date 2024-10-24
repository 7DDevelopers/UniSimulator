package com.badlogic.drop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class InputManager implements InputProcessor {

    OrthographicCamera cam;
    FitViewport viewport;

    TileManager tileManager;

    public InputManager(FitViewport viewport, OrthographicCamera cam){
        this.cam = cam;
        this.viewport = viewport;
    }

    @Override
    public boolean keyDown(int i) {
        return false;
    }

    @Override
    public boolean keyUp(int i) {
        return false;
    }

    @Override
    public boolean keyTyped(char c) {
        return false;
    }

    @Override
    public boolean touchDown(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchUp(int i, int i1, int i2, int i3) {
        Vector3 clickPos = viewport.unproject(new Vector3(i,i1,0));
        clickPos = new Vector3((int)Math.floor(clickPos.x / 40), (int)Math.floor(clickPos.y / 40), 0);

        System.out.println((int)clickPos.x + "," + (int)clickPos.y);

        tileManager.LockTile((int)clickPos.x, (int)clickPos.y);
        switch (1){
            default -> {
                Building newBuilding = new Building((int)clickPos.x*40, (int)clickPos.y*40, "lectureHall.png");
                tileManager.buildings.add(newBuilding);
            }
        }

        return false;
    }

    @Override
    public boolean touchCancelled(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchDragged(int i, int i1, int i2) {
        //cam.position.set(cam.viewportWidth-i,i1,cam.position.z);
        //cam.update();
        return false;
    }

    @Override
    public boolean mouseMoved(int i, int i1) {
        return false;
    }

    @Override
    public boolean scrolled(float v, float v1) {
        cam.zoom = Math.clamp(cam.zoom + v1*0.1f, 0.3f,100);
        return false;
    }
}
