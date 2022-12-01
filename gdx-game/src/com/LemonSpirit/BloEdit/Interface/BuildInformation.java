package com.LemonSpirit.BloEdit.Interface;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.LemonSpirit.BloEdit.WorldBuild;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.LemonSpirit.BloEdit.GetData;
import com.LemonSpirit.BloEdit.DataHand.SaveData;
import com.badlogic.gdx.files.FileHandle;
import java.io.File;
import com.LemonSpirit.BloEdit.DataHand.TestZIP;

public class BuildInformation {
    
    public BuildInformation(final WorldBuild build,final Actor RightBlack){
        final Group group=new Group();
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
                    return true;
                }
            });
        table.add(Back).size(RightBlack.getWidth(),RightBlack.getWidth()).row();
        Image Record=new Image(new Texture(Gdx.files.internal("record.png")));
        Record.addListener(new InputListener(){
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    new BuildMeta(build);
                    return true;
                }
            });
        table.add(Record).size(RightBlack.getWidth(),RightBlack.getWidth()).row();
        
        Image Speed=new Image(new Texture(Gdx.files.internal("speed.png")));
        Speed.addListener(new InputListener(){
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    build.pause();
                    build.SetLine();
                    if(GetData.NowPlane!=5){
                    new BuildSpeed(build);
                    }else{
                    new Toast("无法在Action内添加变速",build.Bit,build.WorldStage);
                    }
                    return true;
                }
            });
        table.add(Speed).size(RightBlack.getWidth(),RightBlack.getWidth()).row();
        Image Cog=new Image(new Texture(Gdx.files.internal("cog.png")));
        Cog.addListener(new InputListener(){
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    new Toast("暂时没有开放设置功能",build.Bit,build.WorldStage);
                    return true;
                }
            });
        table.add(Cog).size(RightBlack.getWidth(),RightBlack.getWidth()).row();
        
        Image Remove=new Image(new Texture(Gdx.files.internal("remove.png")));
        Remove.addListener(new InputListener(){
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    new BuildMessage(build,"提示","本操作将删除您的谱面\n并且不可复原，是否继续\n点击确定后删除谱面并退出制谱器"
                                       , "确定","取消",2);
                   return true;
                }
            });
        table.add(Remove).size(RightBlack.getWidth(),RightBlack.getWidth()).row();
        
        Image variant=new Image(new Texture(Gdx.files.internal("variant.png")));
        variant.addListener(new InputListener(){
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    GetData.SaveData.Save(build);
                    File f1=new File(GetData.SaveData.meta.musicPath);
                    File f2=new File(GetData.SaveData.meta.backgroundPath);
                    File f3=new File(GetData.GetChartPath());
                    File[] srcfile={f1,f2,f3};
                    TestZIP.zipFiles(srcfile,new File(GetData.GetFilePath()+GetData.SaveData.meta.title+".blo"));
                    new Toast("已保存并导出到BloEdit文件夹内",build.Bit,build.WorldStage);
                    
                    return true;
                }
            });
        table.add(variant).size(RightBlack.getWidth(),RightBlack.getWidth()).row();
        
        
        group.addActor(pane);
        build.WorldStage.addActor(group);
        
        
    }
    
    
}
