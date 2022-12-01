package com.LemonSpirit.BloEdit.Interface;
import com.LemonSpirit.BloEdit.WorldBuild;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.LemonSpirit.BloEdit.GetData;

public class BuildPlaneChange {
    Image back;
    Actor checked=null;
    WorldBuild build;
    public BuildPlaneChange(WorldBuild build,Actor RightBack){
        this.build=build;
        final Group group=new Group();
        Label.LabelStyle a=new Label.LabelStyle(build.Bit,build.Bit.getColor());
        a.background=new TextureRegionDrawable((GetData.GetWhiteTexture()));
        a.fontColor=Color.BLACK;
        
        Label Action=new Label("A",a);
        Action.setSize(RightBack.getWidth()*2/3,Action.getHeight()*3/2);
        Action.setPosition(RightBack.getX()-Action.getWidth(),Gdx.graphics.getHeight()/2/5);
        Action.setAlignment(Align.center);
        AddListen(Action,5);
        group.addActor(Action);
        
        Label Plane4=new Label("4",a);
        Plane4.setSize(RightBack.getWidth()*2/3,Plane4.getHeight()*3/2);
        Plane4.setPosition(RightBack.getX()-Action.getWidth(),Gdx.graphics.getHeight()/2/5*2);
        Plane4.setAlignment(Align.center);
        AddListen(Plane4,4);
        group.addActor(Plane4);
        
        Label Plane3=new Label("3",a);
        Plane3.setSize(RightBack.getWidth()*2/3,Plane3.getHeight()*3/2);
        Plane3.setPosition(RightBack.getX()-Action.getWidth(),Gdx.graphics.getHeight()/2/5*3);
        Plane3.setAlignment(Align.center);
        AddListen(Plane3,3);
        group.addActor(Plane3);
        
        Label Plane2=new Label("2",a);
        Plane2.setSize(RightBack.getWidth()*2/3,Plane2.getHeight()*3/2);
        Plane2.setPosition(RightBack.getX()-Action.getWidth(),Gdx.graphics.getHeight()/2/5*4);
        Plane2.setAlignment(Align.center);
        AddListen(Plane2,2);
        group.addActor(Plane2);
        
        Label Plane1=new Label("1",a);
        Plane1.setSize(RightBack.getWidth()*2/3,Plane1.getHeight()*3/2);
        Plane1.setPosition(RightBack.getX()-Action.getWidth(),Gdx.graphics.getHeight()/2/5*5);
        Plane1.setAlignment(Align.center);
        AddListen(Plane1,1);
        group.addActor(Plane1);
        
		checked=Plane1;
		Plane1.setColor(Color.CYAN);
        
        build.WorldStage.addActor(group);
    }
    public void AddListen(final Actor actor,final int plane){
        actor.addListener(new InputListener(){
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    actor.setColor(Color.CYAN);
                    if(checked!=null&&checked!=actor){
                        checked.setColor(Color.WHITE);
                        build.SetPlane(plane);
                    }
                    
                    checked=actor;
                    return true;
                }
            });
    }
    
    
    
}
