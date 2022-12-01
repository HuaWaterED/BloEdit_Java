package com.LemonSpirit.BloEdit.Interface;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.graphics.Texture;
import com.LemonSpirit.BloEdit.DataHand.SaveData;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.files.FileHandle;
import com.LemonSpirit.BloEdit.WorldBuild;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.JsonWriter;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.LemonSpirit.BloEdit.GetData;

public class BuildRightTool {
    
    public BuildRightTool(final WorldBuild build,final Actor RightBlack){
        
        
        final Group RightTool=new Group();
        
        Table table=new Table();
        table.setSize(RightBlack.getWidth(),RightBlack.getHeight());
        table.setPosition(RightBlack.getX(),0);
        table.top();
        ScrollPane pane=new ScrollPane(table,new ScrollPane.ScrollPaneStyle());
        pane.setSize(RightBlack.getWidth(),RightBlack.getHeight());
        pane.setPosition(RightBlack.getX(),0);
        pane.setScrollingDisabled(true,false);
        
        
        
        

        Image Pen=new Image(new Texture(Gdx.files.internal("pen.png")));
        Pen.addListener(new InputListener(){
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    
                    RightTool.remove();
                    if(GetData.NowPlane!=5){
                    new BuildNotePen(build,RightBlack);
                    }else{
                    new BuildActionsPen(build,RightBlack);
                    }
                    return true;
                }
            });
        table.add(Pen).size(RightBlack.getWidth(),RightBlack.getWidth()).row();
        Image Save=new Image(new Texture(Gdx.files.internal("save.png")));
        Save.addListener(new InputListener(){                                                                              
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    if(Gdx.app.getType()==Gdx.app.getType().Android){
                    GetData.SaveData.meta.thisTime=0;
                    }else{
                    GetData.SaveData.meta.thisTime=build.NowTime;
                    }
                    GetData.SaveData.Save(build);
                    build.WorldStage.addActor(new Toast("谱面保存成功",build.Bit,build.WorldStage));
                    return true;
                }
            });
        table.add(Save).size(RightBlack.getWidth(),RightBlack.getWidth()).row();
        if(Gdx.app.getType()==Gdx.app.getType().Android){
        Image Action=new Image(new Texture(Gdx.files.internal("action.png")));
        Action.addListener(new InputListener(){
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    //build.BsuEvent.notify(build,"");
                    new BuildStartView(build);
                    return true;
                }
            });
        table.add(Action).size(RightBlack.getWidth(),RightBlack.getWidth()).row();
        }
        Image Ruler=new Image(new Texture(Gdx.files.internal("ruler.png")));
        Ruler.addListener(new InputListener(){
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                    RightTool.remove();
                    new BuildRuler(build,RightBlack);
                    return true;
                }
            });
        table.add(Ruler).size(RightBlack.getWidth(),RightBlack.getWidth()).row();

        Image List=new Image(new Texture(Gdx.files.internal("list.png")));
        List.addListener(new InputListener(){
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    RightTool.remove();
                    new BuildInformation(build,RightBlack);
                    return true;
                }
            });
        table.add(List).size(RightBlack.getWidth(),RightBlack.getWidth()).row();

        Image Block=new Image(new Texture(Gdx.files.internal("block.png")));
        Block.addListener(new InputListener(){
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    new BuildAddBlock(build);
                    return true;
                }
            });
        table.add(Block).size(RightBlack.getWidth(),RightBlack.getWidth()).row();
        
        Image BPM=new Image(new Texture(Gdx.files.internal("BPM.png")));
        BPM.addListener(new InputListener(){
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    build.pause();
                    build.SetLine();
                    new BuildBpmList(build);
                    return true;
                }
            });
        table.add(BPM).size(RightBlack.getWidth(),RightBlack.getWidth()).row();
        
        final TextureRegion EyeTexture=new TextureRegion(new Texture(Gdx.files.internal("eye_off.png")));
        Image Eye=new Image(EyeTexture);
        Eye.addListener(new InputListener(){
                boolean IsEye=true;
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    if(IsEye){
                        build.ViewGroup.setVisible(false);
                        EyeTexture.setRegion(new Texture(Gdx.files.internal("eye_out.png")));
                        IsEye=false;
                    }else{
                        build.ViewGroup.setVisible(true);
                        EyeTexture.setRegion(new Texture(Gdx.files.internal("eye_off.png")));
                        IsEye=true;
                    }
                    return true;
                }
            });
        table.add(Eye).size(RightBlack.getWidth(),RightBlack.getWidth()).row();
        
        
        
        RightTool.addActor(pane);
        build.WorldStage.addActor(RightTool);
    
    }
    
    
}
