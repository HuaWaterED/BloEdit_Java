package com.LemonSpirit.BloEdit.Interface;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.LemonSpirit.BloEdit.WorldBuild;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.LemonSpirit.BloEdit.GetData;

public class BuildRuler {
    Actor RightBlack;
    WorldBuild build;
    Table table;
    Group RightRuler;
    public BuildRuler(final WorldBuild build,final Actor RightBlack){
        this.RightBlack=RightBlack;
        this.build=build;
        
        RightRuler=new Group();
        table=new Table();
        table.setSize(RightBlack.getWidth(),RightBlack.getHeight());
        table.setPosition(RightBlack.getX(),0);
        table.top();
        ScrollPane pane=new ScrollPane(table,new ScrollPane.ScrollPaneStyle());
        pane.setSize(RightBlack.getWidth(),RightBlack.getHeight());
        pane.setPosition(RightBlack.getX(),0);
        pane.setScrollingDisabled(true,false);
        
        
        
        final Image Back=new Image(new Texture(Gdx.files.internal("back.png")));
        Back.addListener(new InputListener(){
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    RightRuler.remove();
                    new BuildRightTool(build,RightBlack);
                    GetData.CheckedTool=null;
                    return true;
                }
            });
        table.add(Back).size(RightBlack.getWidth(),RightBlack.getWidth()).row();
        final Label B1=new Label("垂直",new Label.LabelStyle(build.Bit,build.Bit.getColor()));
        B1.setAlignment(Align.center);
        B1.addListener(new InputListener(){
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    table.clear();
                    Vertical();
                    return true;
                }
            });
        table.add(B1).size(RightBlack.getWidth(),RightBlack.getWidth()).row();

        final Label B2=new Label("水平",new Label.LabelStyle(build.Bit,build.Bit.getColor()));
        B2.setAlignment(Align.center);
        B2.addListener(new InputListener(){
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    table.clear();
                    Horizontal();
                    return true;
                }
            });
        table.add(B2).size(RightBlack.getWidth(),RightBlack.getWidth()).row();
        
        
        RightRuler.addActor(pane);
        build.WorldStage.addActor(RightRuler);
    }
    private void Horizontal(){
        final Image Back=new Image(new Texture(Gdx.files.internal("back.png")));
        Back.addListener(new InputListener(){
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    RightRuler.remove();
                    new BuildRightTool(build,RightBlack);
                    GetData.CheckedTool=null;
                    return true;
                }
            });
        table.add(Back).size(RightBlack.getWidth(),RightBlack.getWidth()).row();

        final Label B1=new Label("1/2",new Label.LabelStyle(build.Bit,build.Bit.getColor()));
        B1.setAlignment(Align.center);
        B1.addListener(new InputListener(){
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    B1.setColor(Color.CYAN);
                    if(GetData.CheckedRuler!=null&&GetData.CheckedRuler!=B1){
                        GetData.CheckedRuler.setColor(Color.WHITE);
                        GetData.CheckedRuler=null;
                    }
                    GetData.CheckedRuler=B1;
                    GetData.LineWeightUnit=2;
                    return true;
                }
            });
        table.add(B1).size(RightBlack.getWidth(),RightBlack.getWidth()).row();

        final Label B2=new Label("1/4",new Label.LabelStyle(build.Bit,build.Bit.getColor()));
        B2.setAlignment(Align.center);
        B2.addListener(new InputListener(){
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    B2.setColor(Color.CYAN);
                    if(GetData.CheckedRuler!=null&&GetData.CheckedRuler!=B2){
                        GetData.CheckedRuler.setColor(Color.WHITE);
                        GetData.CheckedRuler=null;
                    }
                    GetData.CheckedRuler=B2;
                    GetData.LineWeightUnit=4;
                    return true;
                }
            });
        table.add(B2).size(RightBlack.getWidth(),RightBlack.getWidth()).row();

        final Label B3=new Label("1/6",new Label.LabelStyle(build.Bit,build.Bit.getColor()));
        B3.setAlignment(Align.center);
        B3.addListener(new InputListener(){
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    B3.setColor(Color.CYAN);
                    if(GetData.CheckedRuler!=null&&GetData.CheckedRuler!=B3){
                        GetData.CheckedRuler.setColor(Color.WHITE);
                        GetData.CheckedRuler=null;
                    }
                    GetData.CheckedRuler=B3;
                    GetData.LineWeightUnit=6;
                    return true;
                }
            });
        table.add(B3).size(RightBlack.getWidth(),RightBlack.getWidth()).row();

        final Label B4=new Label("1/8",new Label.LabelStyle(build.Bit,build.Bit.getColor()));
        B4.setAlignment(Align.center);
        B4.addListener(new InputListener(){
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    B4.setColor(Color.CYAN);
                    if(GetData.CheckedRuler!=null&&GetData.CheckedRuler!=B4){
                        GetData.CheckedRuler.setColor(Color.WHITE);
                        GetData.CheckedRuler=null;
                    }
                    GetData.CheckedRuler=B4;
                    GetData.LineWeightUnit=8;
                    return true;
                }
            });
        table.add(B4).size(RightBlack.getWidth(),RightBlack.getWidth()).row();

        final Label B5=new Label("1/10",new Label.LabelStyle(build.Bit,build.Bit.getColor()));
        B5.setAlignment(Align.center);
        B5.addListener(new InputListener(){
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    B5.setColor(Color.CYAN);
                    if(GetData.CheckedRuler!=null&&GetData.CheckedRuler!=B5){
                        GetData.CheckedRuler.setColor(Color.WHITE);
                        GetData.CheckedRuler=null;
                    }
                    GetData.CheckedRuler=B5;
                    GetData.LineWeightUnit=10;
                    return true;
                }
            });
        table.add(B5).size(RightBlack.getWidth(),RightBlack.getWidth()).row();
        final Label B6=new Label("1/12",new Label.LabelStyle(build.Bit,build.Bit.getColor()));
        B6.setAlignment(Align.center);
        B6.addListener(new InputListener(){
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    B6.setColor(Color.CYAN);
                    if(GetData.CheckedRuler!=null&&GetData.CheckedRuler!=B6){
                        GetData.CheckedRuler.setColor(Color.WHITE);
                        GetData.CheckedRuler=null;
                    }
                    GetData.CheckedRuler=B6;
                    GetData.LineWeightUnit=12;
                    return true;
                }
            });
        table.add(B6).size(RightBlack.getWidth(),RightBlack.getWidth()).row();
        final Label B7=new Label("1/14",new Label.LabelStyle(build.Bit,build.Bit.getColor()));
        B7.setAlignment(Align.center);
        B7.addListener(new InputListener(){
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    B7.setColor(Color.CYAN);
                    if(GetData.CheckedRuler!=null&&GetData.CheckedRuler!=B7){
                        GetData.CheckedRuler.setColor(Color.WHITE);
                        GetData.CheckedRuler=null;
                    }
                    GetData.CheckedRuler=B7;
                    GetData.LineWeightUnit=14;
                    return true;
                }
            });
        table.add(B7).size(RightBlack.getWidth(),RightBlack.getWidth()).row();
        
    }
    private void Vertical(){
        final Image Back=new Image(new Texture(Gdx.files.internal("back.png")));
        Back.addListener(new InputListener(){
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    RightRuler.remove();
                    new BuildRightTool(build,RightBlack);
                    GetData.CheckedTool=null;
                    return true;
                }
            });
        table.add(Back).size(RightBlack.getWidth(),RightBlack.getWidth()).row();
        
        final Label B1=new Label("1/2",new Label.LabelStyle(build.Bit,build.Bit.getColor()));
        B1.setAlignment(Align.center);
        B1.addListener(new InputListener(){
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    B1.setColor(Color.CYAN);
                    if(GetData.CheckedRuler!=null&&GetData.CheckedRuler!=B1){
                        GetData.CheckedRuler.setColor(Color.WHITE);
                        GetData.CheckedRuler=null;
                    }
                    GetData.CheckedRuler=B1;
                    GetData.LineNumber=2;
                    return true;
                }
            });
        table.add(B1).size(RightBlack.getWidth(),RightBlack.getWidth()).row();

        final Label B2=new Label("1/3",new Label.LabelStyle(build.Bit,build.Bit.getColor()));
        B2.setAlignment(Align.center);
        B2.addListener(new InputListener(){
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    B2.setColor(Color.CYAN);
                    if(GetData.CheckedRuler!=null&&GetData.CheckedRuler!=B2){
                        GetData.CheckedRuler.setColor(Color.WHITE);
                        GetData.CheckedRuler=null;
                    }
                    GetData.CheckedRuler=B2;
                    GetData.LineNumber=3;
                    return true;
                }
            });
        table.add(B2).size(RightBlack.getWidth(),RightBlack.getWidth()).row();

        final Label B3=new Label("1/4",new Label.LabelStyle(build.Bit,build.Bit.getColor()));
        B3.setAlignment(Align.center);
        B3.addListener(new InputListener(){
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    B3.setColor(Color.CYAN);
                    if(GetData.CheckedRuler!=null&&GetData.CheckedRuler!=B3){
                        GetData.CheckedRuler.setColor(Color.WHITE);
                        GetData.CheckedRuler=null;
                    }
                    GetData.CheckedRuler=B3;
                    GetData.LineNumber=4;
                    return true;
                }
            });
        table.add(B3).size(RightBlack.getWidth(),RightBlack.getWidth()).row();

        final Label B4=new Label("1/6",new Label.LabelStyle(build.Bit,build.Bit.getColor()));
        B4.setAlignment(Align.center);
        B4.addListener(new InputListener(){
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    B4.setColor(Color.CYAN);
                    if(GetData.CheckedRuler!=null&&GetData.CheckedRuler!=B4){
                        GetData.CheckedRuler.setColor(Color.WHITE);
                        GetData.CheckedRuler=null;
                    }
                    GetData.CheckedRuler=B4;
                    GetData.LineNumber=6;
                    return true;
                }
            });
        table.add(B4).size(RightBlack.getWidth(),RightBlack.getWidth()).row();

        final Label B5=new Label("1/8",new Label.LabelStyle(build.Bit,build.Bit.getColor()));
        B5.setAlignment(Align.center);
        B5.addListener(new InputListener(){
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    B5.setColor(Color.CYAN);
                    if(GetData.CheckedRuler!=null&&GetData.CheckedRuler!=B5){
                        GetData.CheckedRuler.setColor(Color.WHITE);
                        GetData.CheckedRuler=null;
                    }
                    GetData.CheckedRuler=B5;
                    GetData.LineNumber=8;
                    return true;
                }
            });
        table.add(B5).size(RightBlack.getWidth(),RightBlack.getWidth()).row();
    }
}
