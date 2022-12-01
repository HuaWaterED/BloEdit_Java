package com.LemonSpirit.BloEdit.Interface;
import com.LemonSpirit.BloEdit.WorldBuild;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.LemonSpirit.BloEdit.GetData;

public class BuildActionsPen {
  public BuildActionsPen(final WorldBuild build,final Actor RightBlack){
      final Group group=new Group();
      GetData.NowPlaneActor=group;
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
                  group.remove();
                  new BuildRightTool(build,RightBlack);
                  GetData.CheckedTool=null;
                  GetData.NowPlaneActor=null;
                  return true;
              }
          });
     table.add(Back).size(RightBlack.getWidth(),RightBlack.getWidth()).row();
      final Label B1=new Label("MoveX",new Label.LabelStyle(build.Bit,build.Bit.getColor()));
      B1.setAlignment(Align.center);
      B1.setFontScale(0.8f);
      B1.addListener(new InputListener(){
              @Override
              public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                  B1.setColor(Color.CYAN);
                  if(GetData.CheckedTool!=null&&GetData.CheckedTool!=B1){
                      GetData.CheckedTool.setColor(Color.WHITE);
                      GetData.CheckedTool=null;
                  }
                  GetData.CheckedTool=B1;
                  GetData.CheckedTool.setName("MoveX");
                  return true;
              }
          });
      table.add(B1).size(RightBlack.getWidth(),RightBlack.getWidth()).row();
      final Label B2=new Label("MoveY",new Label.LabelStyle(build.Bit,build.Bit.getColor()));
      B2.setAlignment(Align.center);
      B2.setFontScale(0.8f);
      B2.addListener(new InputListener(){
              @Override
              public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                  B2.setColor(Color.CYAN);
                  if(GetData.CheckedTool!=null&&GetData.CheckedTool!=B2){
                      GetData.CheckedTool.setColor(Color.WHITE);
                      GetData.CheckedTool=null;
                  }
                  GetData.CheckedTool=B2;
                  GetData.CheckedTool.setName("MoveY");
                  return true;
              }
          });
      table.add(B2).size(RightBlack.getWidth(),RightBlack.getWidth()).row();
      final Label B3=new Label("rotate",new Label.LabelStyle(build.Bit,build.Bit.getColor()));
      B3.setAlignment(Align.center);
      B3.setFontScale(0.8f);
      B3.addListener(new InputListener(){
              @Override
              public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                  B3.setColor(Color.CYAN);
                  if(GetData.CheckedTool!=null&&GetData.CheckedTool!=B3){
                      GetData.CheckedTool.setColor(Color.WHITE);
                      GetData.CheckedTool=null;
                  }
                  GetData.CheckedTool=B3;
                  GetData.CheckedTool.setName("rotate");
                  return true;
              }
          });
      table.add(B3).size(RightBlack.getWidth(),RightBlack.getWidth()).row();
      final Label B4=new Label("scale",new Label.LabelStyle(build.Bit,build.Bit.getColor()));
      B4.setAlignment(Align.center);
      B4.setFontScale(0.8f);
      B4.addListener(new InputListener(){
              @Override
              public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                  B4.setColor(Color.CYAN);
                  if(GetData.CheckedTool!=null&&GetData.CheckedTool!=B4){
                      GetData.CheckedTool.setColor(Color.WHITE);
                      GetData.CheckedTool=null;
                  }
                  GetData.CheckedTool=B4;
                  GetData.CheckedTool.setName("scale");
                  return true;
              }
          });
      table.add(B4).size(RightBlack.getWidth(),RightBlack.getWidth()).row();
      final Label B5=new Label("alpha",new Label.LabelStyle(build.Bit,build.Bit.getColor()));
      B5.setAlignment(Align.center);
      B5.setFontScale(0.8f);
      B5.addListener(new InputListener(){
              @Override
              public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                  B5.setColor(Color.CYAN);
                  if(GetData.CheckedTool!=null&&GetData.CheckedTool!=B5){
                      GetData.CheckedTool.setColor(Color.WHITE);
                      GetData.CheckedTool=null;
                  }
                  GetData.CheckedTool=B5;
                  GetData.CheckedTool.setName("alpha");
                  return true;
              }
          });
      table.add(B5).size(RightBlack.getWidth(),RightBlack.getWidth()).row();
      final Image Remove=new Image(new Texture(Gdx.files.internal("remove.png")));
      Remove.addListener(new InputListener(){
              @Override
              public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                  if(build.input.CheckedNote!=null){
                      build.NoteList.remove(build.input.CheckedNote);
                      GetData.SaveData.RemoveData(build.input.CheckedNote,GetData.NowPlane,GetData.NowBlock);
                      
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
                  new BuildSetActions(build,build.input.CheckedNote);
                  }
                  return true;
              }
          });
      table.add(Dot).size(RightBlack.getWidth(),RightBlack.getWidth()).row();
      
     group.addActor(pane);
     build.WorldStage.addActor(group);
     
  }
}
