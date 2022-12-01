package com.LemonSpirit.BloEdit.Interface;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.LemonSpirit.BloEdit.WorldBuild;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.LemonSpirit.BloEdit.GetData;

public class BuildNotePen {
    
    public BuildNotePen(final WorldBuild build,final Actor RightBlack){
        
        final Group RightPen=new Group();
        GetData.NowPlaneActor=RightPen;
        Table table=new Table();
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
                    RightPen.remove();
                    new BuildRightTool(build,RightBlack);
                    GetData.CheckedTool=null;
                    GetData.NowPlaneActor=null;
                    return true;
                }
            });
        table.add(Back).size(RightBlack.getWidth(),RightBlack.getWidth()).row();
        

        final Image Click=new Image(new Texture(Gdx.files.internal("click.png")));
        Click.addListener(new InputListener(){
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    Click.setColor(Color.CYAN);
                    if(GetData.CheckedTool!=null&&GetData.CheckedTool!=Click){
                        GetData.CheckedTool.setColor(Color.WHITE);
                        GetData.CheckedTool=null;
                    }
                    GetData.CheckedTool=Click;
                    GetData.CheckedTool.setName("Click");
                    return true;
                }
            });
        table.add(Click).size(RightBlack.getWidth(),RightBlack.getWidth()).row();
        

        final Image Yellow=new Image(new Texture(Gdx.files.internal("yellow.png")));
        Yellow.addListener(new InputListener(){
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    Yellow.setColor(Color.CYAN);
                    if(GetData.CheckedTool!=null&&GetData.CheckedTool!=Yellow){
                        GetData.CheckedTool.setColor(Color.WHITE);
                        GetData.CheckedTool=null;
                    }
                    GetData.CheckedTool=Yellow;
                    GetData.CheckedTool.setName("Yellow");
                    return true;
                }
            });
        table.add(Yellow).size(RightBlack.getWidth(),RightBlack.getWidth()).row();
        

        final Image Long=new Image(new Texture(Gdx.files.internal("Long.png")));
        Long.addListener(new InputListener(){
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    Long.setColor(Color.CYAN);
                    if(GetData.CheckedTool!=null&&GetData.CheckedTool!=Long){
                        GetData.CheckedTool.setColor(Color.WHITE);
                        GetData.CheckedTool=null;
                    }
                    GetData.CheckedTool=Long;
                    GetData.CheckedTool.setName("Long");
                    return true;
                }
            });
        table.add(Long).size(RightBlack.getWidth(),RightBlack.getWidth()).row();
        

        final Image Dragged=new Image(new Texture(Gdx.files.internal("dragged.png")));
        Dragged.addListener(new InputListener(){
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    Dragged.setColor(Color.CYAN);
                    if(GetData.CheckedTool!=null&&GetData.CheckedTool!=Dragged){
                        GetData.CheckedTool.setColor(Color.WHITE);
                        GetData.CheckedTool=null;
                    }
                    GetData.CheckedTool=Dragged;
                    GetData.CheckedTool.setName("Dragged");
                    return true;
                }
            });
        table.add(Dragged).size(RightBlack.getWidth(),RightBlack.getWidth()).row();
        
        final Image Middle=new Image(new Texture(Gdx.files.internal("Middle.png")));
        Middle.addListener(new InputListener(){
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    Middle.setColor(Color.CYAN);
                    if(GetData.CheckedTool!=null&&GetData.CheckedTool!=Middle){
                        GetData.CheckedTool.setColor(Color.WHITE);
                        GetData.CheckedTool=null;
                    }
                    GetData.CheckedTool=Middle;
                    GetData.CheckedTool.setName("Middle");
                    return true;
                }
            });
        table.add(Middle).size(RightBlack.getWidth(),RightBlack.getWidth()).row();
        
        final Image Remove=new Image(new Texture(Gdx.files.internal("remove.png")));
        Remove.addListener(new InputListener(){
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    if(build.input.CheckedNote!=null){
                        GetData.SaveData.RemoveData(build.input.CheckedNote,GetData.NowPlane,GetData.NowBlock);
                        build.NoteList.remove(build.input.CheckedNote);
                        
                        build.input.CheckedNote.remove();
                          build.input.CheckedNote=null;
                        }
                    return true;
                }
            });
        table.add(Remove).size(RightBlack.getWidth(),RightBlack.getWidth()).row();
        
        final Image Dot=new Image(new Texture(Gdx.files.internal("dot.png")));
        Dot.addListener(new InputListener(){
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    if(build.input.CheckedNote!=null){
                        new BuildSetNote(build,build.input.CheckedNote);
                    }
                    return true;
                }
            });
        table.add(Dot).size(RightBlack.getWidth(),RightBlack.getWidth()).row();
        
        RightPen.addActor(pane);
        build.WorldStage.addActor(RightPen);
    }
    
    
}
