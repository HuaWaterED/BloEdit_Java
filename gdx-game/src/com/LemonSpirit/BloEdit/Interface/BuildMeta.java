package com.LemonSpirit.BloEdit.Interface;
import com.LemonSpirit.BloEdit.WorldBuild;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import java.util.ArrayList;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.LemonSpirit.BloEdit.GetData;

public class BuildMeta {
    WorldBuild build;
    Table ListButton;
    Image back;
    TextField.TextFieldStyle style;
    ArrayList<Actor> Field=new ArrayList<Actor>();
    public BuildMeta(final WorldBuild build){
        this.build=build;
        final Group group=new Group();
        
        Image BlackBack=new Image(GetData.GetBlackTexture());
        BlackBack.setSize(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        BlackBack.setPosition(0,0);
        BlackBack.setColor(0,0,0,0);
        BlackBack.addListener(new InputListener(){
                @Override
                
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    group.remove();
                    return true;
                }
            });
        group.addActor(BlackBack);



        back=new Image(GetData.GetBlackTexture());
        back.setColor(0,0,0,0.5f);
        back.setSize(Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()*7/10);
        back.setPosition((Gdx.graphics.getWidth()-back.getWidth())/2,(Gdx.graphics.getHeight()-back.getHeight())/2);
        group.addActor(back);

        
        
        ListButton=new Table();
        ListButton.top().left();
        ScrollPane pane=new ScrollPane(ListButton,new ScrollPane.ScrollPaneStyle());
        pane.setSize(back.getWidth(),back.getHeight()-back.getHeight()/8);
        pane.setPosition(back.getX(),back.getY()+back.getHeight()/8);
        pane.setScrollingDisabled(true,false);//设置是否可上下、左右移动..这里设置了横向可移动、纵向不可移动.
        
        style=new TextField.TextFieldStyle();
        style.background = new TextureRegionDrawable(new TextureRegion(createBackgroundTexture(back)));

        // 设置光标纹理区域
        style.cursor = new TextureRegionDrawable(new TextureRegion(createCursorTexture(back)));

        // 设置文本框显示文本的字体来源
        style.font = build.Bit;

        // 设置文本框字体颜色为白色
        style.fontColor = new Color(1, 1, 1, 1);

     
        
        BuildLine("标题");
        BuildLine("音乐路径");
        BuildLine("背景路径");
        BuildLine("谱面偏移 必须为正数");
        BuildLine("预览时间");
        BuildLine("预览持续时间");
        BuildLine("谱面作者");
        BuildLine("音乐作者");
        BuildLine("bpm值");
        BuildLine("版本");
        BuildLine("note数量");
        BuildLine("标签");
        BuildLine("音乐时长");
        
        Table Down=new Table();
        Down.setSize(back.getWidth(),back.getHeight()/8);
        Down.setPosition(back.getX(),back.getY());
        
        Label.LabelStyle a=new Label.LabelStyle(build.Bit,build.Bit.getColor());
        a.background=new TextureRegionDrawable(GetData.GetWhiteTexture());
        a.fontColor=Color.BLACK;
        Label Cancel=new Label("取消",a);
        Cancel.setAlignment(Align.center);
        Cancel.addListener(new InputListener(){
                @Override

                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    group.remove();
                    return true;
                }
            });
        Label Save=new Label("保存",a);
        Save.setAlignment(Align.center);
        Save.addListener(new InputListener(){
                @Override

                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                     Save();
                    return true;
                }
            });
        
        Down.add(Cancel).size(back.getWidth()/3,back.getHeight()/8*2/3).padLeft(back.getWidth()/9);
        
        Down.add(Save).size(back.getWidth()/3,back.getHeight()/8*2/3).padLeft(back.getWidth()/9);
        
        
        Down.left();
        group.addActor(Down);
        
        group.addActor(pane);
        
        
        Read();
        build.WorldStage.addActor(group);
        
    }
    private Texture createBackgroundTexture(Actor back) {
        Pixmap pixmap = new Pixmap((int)(back.getWidth()*2/3),(int)(back.getHeight()/8/6),Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.drawLine(1,(int)(back.getHeight()/8/6)-1,(int)(back.getWidth()*2/3),(int)(back.getHeight()/8/6)-1);
        Texture texture = new Texture(pixmap);
        pixmap.dispose();
        return texture;
    }
    private Texture createCursorTexture(Actor back) {
        Pixmap pixmap = new Pixmap(1, (int)(back.getHeight()/8/6)*2/3, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        Texture texture = new Texture(pixmap);
        pixmap.dispose();
        return texture;
    }
    public void BuildLine(String string){
        Label title=new Label(string+":",new Label.LabelStyle(build.Bit,build.Bit.getColor()));
        ListButton.add(title).left().padLeft(20).padTop(20);
        ListButton.row();

        final TextField Field=new TextField("",style);
        Field.setName(string);
        ListButton.add(Field).width(back.getWidth()-40).height(back.getHeight()/10).padLeft(20);
        this.Field.add(Field);
        ListButton.row();
    }
    public TextField GetField(String string){
        for(int i=0;i<Field.size();i++){
            if(Field.get(i).getName().equals(string)){
                return (TextField)Field.get(i);
            }
        }
        return null;
    }
    public void Save(){
        boolean sucsess=true;
        try{
        GetData.SaveData.meta.title=GetField("标题").getText();
        GetData.SaveData.meta.musicPath=GetField("音乐路径").getText();
        GetData.SaveData.meta.backgroundPath=GetField("背景路径").getText();
        GetData.SaveData.meta.artist=GetField("音乐作者").getText();
        GetData.SaveData.meta.creator=GetField("谱面作者").getText();
        GetData.SaveData.meta.version=GetField("版本").getText();
        GetData.SaveData.meta.bpm=Float.valueOf( GetField("bpm值").getText());
        GetData.SaveData.meta.notesCount=Integer.valueOf( GetField("note数量").getText());
        GetData.SaveData.meta.previewDuration=Float.valueOf( GetField("预览持续时间").getText());
        GetData.SaveData.meta.previewTime=Float.valueOf( GetField("预览时间").getText());
        GetData.SaveData.meta.delayTime=Float.valueOf(GetField("谱面偏移 必须为正数").getText());
        GetData.SaveData.meta.tag=GetField("标签").getText();
        GetData.SaveData.meta.MusicLength=Float.valueOf( GetField("音乐时长").getText());
        }catch(Exception e){
        sucsess=false;
        }
        if(sucsess){
            new Toast("已保存(部分选项重启生效)",build.Bit,build.WorldStage);
            
        }else{
            new Toast("内容有误",build.Bit,build.WorldStage);
            
        }
    }
    public void Read(){
        GetField("标题").setText(GetData.SaveData.meta.title);
        GetField("音乐路径").setText(GetData.SaveData.meta.musicPath);
        GetField("背景路径").setText(GetData.SaveData.meta.backgroundPath);
        GetField("谱面偏移 必须为正数").setText(Float.toString( GetData.SaveData.meta.delayTime));
        GetField("音乐作者").setText(GetData.SaveData.meta.artist);
        GetField("谱面作者").setText(GetData.SaveData.meta.creator);
        GetField("版本").setText(GetData.SaveData.meta.version);
        GetField("bpm值").setText(Float.toString( GetData.SaveData.meta.bpm));
        GetField("note数量").setText(Integer.toString( GetData.SaveData.meta.notesCount));
        GetField("预览持续时间").setText(Float.toString(GetData.SaveData.meta.previewDuration));
        GetField("预览时间").setText(Float.toString(GetData.SaveData.meta.previewTime));
        GetField("标签").setText(GetData.SaveData.meta.tag);
        GetField("音乐时长").setText(Float.toString(GetData.SaveData.meta.MusicLength));
    }
}
