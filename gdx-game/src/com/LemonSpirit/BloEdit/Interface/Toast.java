package com.LemonSpirit.BloEdit.Interface;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.LemonSpirit.BloEdit.WorldBuild;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap;

public class Toast extends Actor{
    float time;
    Label text;
    public Toast(String string,BitmapFont Bit,Stage stage){
        Label.LabelStyle a=new Label.LabelStyle(Bit,Bit.getColor());
        a.background=new TextureRegionDrawable((GetBlackTexture()));
        a.fontColor=Color.WHITE;
        text=new Label(string,a);
        //text.setFontScale(50/text.getHeight());
        text.setSize(text.getWidth()*text.getScaleX()+50,50);
        text.setPosition((Gdx.graphics.getWidth()-text.getWidth())/2,Gdx.graphics.getHeight()-text.getHeight()*3/2);
        text.setAlignment(Align.center);
        text.addAction(Actions.sequence(
        Actions.alpha(0),Actions.alpha(0.8f,0.5f),Actions.delay(2),Actions.alpha(0,0.5f)));
        stage.addActor(text);
    }

    @Override
    public void act(float delta) {
        time+=Gdx.graphics.getDeltaTime();
        if(time>=3.2f){
            text.remove();
            this.remove();
        }
        super.act(delta);
    }

    public Texture GetBlackTexture(){
        Pixmap map=new Pixmap(1,1,Pixmap.Format.RGBA8888);
        map.setColor(Color.BLACK);
        map.fill();
        return new Texture(map);
    }
    
}
