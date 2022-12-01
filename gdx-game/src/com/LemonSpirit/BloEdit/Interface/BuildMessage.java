package com.LemonSpirit.BloEdit.Interface;

import com.LemonSpirit.BloEdit.GetData;
import com.LemonSpirit.BloEdit.Interface.BuildRightTool;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.LemonSpirit.BloEdit.WorldBuild;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.files.FileHandle;

public class BuildMessage {
    public BuildMessage(WorldBuild build,String Titile,String Context,String Button1,String Button2,final int type){
        final Group group=new Group();

        Image BlackBack=new Image(GetData.GetBlackTexture());
        BlackBack.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        BlackBack.setPosition(0, 0);
        BlackBack.setColor(0, 0, 0, 0.3f);

        group.addActor(BlackBack);
        Image back=new Image(GetData.GetBlackTexture());
        back.setColor(0, 0, 0, 0.5f);
        back.setSize(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() *0.65f);
        back.setPosition((Gdx.graphics.getWidth() - back.getWidth()) / 2, (Gdx.graphics.getHeight() - back.getHeight()) / 2);
        group.addActor(back);
        
        
        Label Title=new Label(Titile,new Label.LabelStyle(build.Bit,build.Bit.getColor()));
        Title.setHeight(back.getHeight()/16);
        Title.setPosition(back.getX()+Title.getHeight(),back.getY()+back.getHeight()-Title.getHeight()*2);
        group.addActor(Title);
        Table edit=new Table();
        edit.center();
        ScrollPane pane=new ScrollPane(edit, new ScrollPane.ScrollPaneStyle());
        pane.setSize(back.getWidth(),back.getHeight()*7/8-Title.getHeight()*2);
        pane.setPosition(back.getX(),back.getY()+back.getHeight()/8);
        
        pane.setScrollingDisabled(true,false);
        
        
        final Label label=new Label(Context, new Label.LabelStyle(build.Bit,build.Bit.getColor()));
        
        label.setWrap(true);//开启换行
        label.setAlignment(Align.center);
        edit.add(label).size(back.getWidth(),back.getHeight()*7/8-Title.getHeight()*2).center();
        edit.row();
        
        Label.LabelStyle a=new Label.LabelStyle(build.Bit,build.Bit.getColor());
        a.background=new TextureRegionDrawable(GetData.GetWhiteTexture());
        a.fontColor=Color.BLACK;
        
        
        Table DownButton=new Table();
        DownButton.setSize(back.getWidth(),back.getHeight()/8);
        DownButton.setPosition(back.getX(),back.getY());
        DownButton.top();

        Label Button_1=new Label(Button1,a);
        Button_1.setAlignment(Align.center);
        DownButton.add(Button_1).size(back.getWidth()/3,back.getHeight()/8*2/3).padRight(20);
        Button_1.addListener(new InputListener(){
                @Override

                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    if(type==1){
                        Preferences pre=Gdx.app.getPreferences("PreferenceTest");
                        pre.putBoolean("ed",true);
                        pre.flush();
                    }else if(type==2){
                        FileHandle chart=Gdx.files.absolute(GetData.GetChartPath());
                        chart.delete();
                        Gdx.app.exit();
                    }
                    
                    group.remove();
                    return true;
                }
            });

        Label Button_2=new Label(Button2,a);
        Button_2.setAlignment(Align.center);
        DownButton.add(Button_2).size(back.getWidth()/3,back.getHeight()/8*2/3).padLeft(20);
        Button_2.addListener(new InputListener(){
                @Override

                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    if(type==1){
                        Gdx.app.exit();
                    }
                    group.remove();
                    return true;
                }
            });
        
        group.addActor(DownButton);
        group.addActor(pane);
        build.WorldStage.addActor(group);
    }
    
    
}
