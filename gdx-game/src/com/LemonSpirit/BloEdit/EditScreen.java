package com.LemonSpirit.BloEdit;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.audio.Music;

public class EditScreen implements Screen {
    MyGdxGame game;
    WorldBuild world;
    String background;
    String music;
    float BPM;
    boolean IsFirst=false;
    public BsuEvent BsuEvent;
    public EditScreen(MyGdxGame game,BsuEvent BsuEvent){
        this.game=game;
        this.BsuEvent=BsuEvent;
    }
    
    @Override
    public void show() {
        world=new WorldBuild(this);
        if(IsFirst){
        world.Building(background,music,BPM);
        }else{
        world.Building(null,null,-1);
        }

    }
    public void set(String background,String music,float BPM){
        this.background=background;
        this.music=music;
        this.BPM=BPM;
        IsFirst=true;
    }
    @Override
    public void render(float p1) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
	    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        world.WorldDraw();
    }

    @Override
    public void resize(int p1, int p2) {
    }

  

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        
    }
    
    
    
    
}
