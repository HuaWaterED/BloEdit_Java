package com.LemonSpirit.BloEdit.Interface;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.LemonSpirit.BloEdit.WorldBuild;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import java.util.ArrayList;
import com.LemonSpirit.BloEdit.BpmControl.BpmData;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import java.util.Collections;
import com.LemonSpirit.BloEdit.GetData;

public class BuildBpmList {
    ArrayList<BpmData> list;
    Actor checked=null;
    WorldBuild build;
    Image back;
    Table BpmListButton;
    public BuildBpmList(final WorldBuild build){
        list=GetData.SaveData.meta.bpmGroup;
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
        
        Label Title=new Label("BPM LIST",new Label.LabelStyle(build.Bit,build.Bit.getColor()));
        //Title.setFontScale(back.getHeight()/16/build.BpmNumber.getHeight());
        Title.setHeight(back.getHeight()/16);
        Title.setPosition(back.getX()+Title.getHeight(),back.getY()+back.getHeight()-Title.getHeight()*2);
        
        group.addActor(Title);
        
        BpmListButton=new Table();
        BpmListButton.top();
        ScrollPane pane=new ScrollPane(BpmListButton,new ScrollPane.ScrollPaneStyle());
        pane.setSize(back.getWidth(),back.getHeight()/4*3-Title.getHeight()*2);
        pane.setPosition(back.getX(),back.getY()+back.getHeight()/4);
        pane.setScrollingDisabled(true,false);//设置是否可上下、左右移动..这里设置了横向可移动、纵向不可移动.
        
        
        group.addActor(pane);
        Table edit=new Table();
        edit.setSize(back.getWidth(),back.getHeight()/8);
        edit.setPosition(back.getX(),back.getY()+back.getHeight()/8);
        
        Label NowBPM=new Label("  当前Beat:  "+Float.toString(build.number),new Label.LabelStyle(build.Bit,build.Bit.getColor()));
        //NowBPM.setFontScale(back.getHeight()/16/build.BpmNumber.getHeight());
        Label SetBPM=new Label("  设定BPM:  ",new Label.LabelStyle(build.Bit,build.Bit.getColor()));
        //SetBPM.setFontScale(back.getHeight()/16/build.BpmNumber.getHeight());
        
        edit.row();
        //.height(back.getHeight()/10);
        edit.add(NowBPM);
        edit.add(SetBPM);
        
        TextField.TextFieldStyle style=new TextField.TextFieldStyle();
        style.background = new TextureRegionDrawable(new TextureRegion(createBackgroundTexture(back)));

        // 设置光标纹理区域
        style.cursor = new TextureRegionDrawable(new TextureRegion(createCursorTexture(back)));

        // 设置文本框显示文本的字体来源
        style.font = build.Bit;

        // 设置文本框字体颜色为白色
        style.fontColor = new Color(1, 1, 1, 1);
        
        
        final TextField SetBPMField=new TextField("",style);
        SetBPMField.setSize(back.getHeight()/8,back.getHeight()/8/6);
       
        edit.add(SetBPMField);
        
        BuildList();
		
        group.addActor(edit);
        
        Table Button=new Table();
        Button.setSize(back.getWidth(),back.getHeight()/8);
        Button.setPosition(back.getX(),back.getY());
        Label.LabelStyle a=new Label.LabelStyle(build.Bit,build.Bit.getColor());
        a.background=new TextureRegionDrawable(GetData.GetWhiteTexture());
        a.fontColor=Color.BLACK;
        Label Add=new Label("添加",a);
        //Add.setFontScale(back.getHeight()/16/build.BpmNumber.getHeight());
        Add.setAlignment(Align.center);
        Label Remove=new Label("移除",a);
        //Remove.setFontScale(back.getHeight()/16/build.BpmNumber.getHeight());
        Remove.setAlignment(Align.center);
        //edit.row();
        Button.left();
        Button.add(Add).size(back.getWidth()/3,back.getHeight()/8*2/3).padLeft(back.getWidth()/9).padBottom(20);

        Button.add(Remove).size(back.getWidth()/3,back.getHeight()/8*2/3).padLeft(back.getWidth()/9).padBottom(20);
        
        group.addActor(Button);
        Add.addListener(new InputListener(){
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    BpmListButton.clearChildren();
                    try{
                    
                    float bpm=Float.valueOf(SetBPMField.getText());
                    BpmData data=new BpmData();
                    data.bpm = bpm;
                    data.LinePosition = build.number;
                    for(int i=0;i<list.size();i++){
                        if(list.get(i).LinePosition==build.number){
                            list.remove(i);
                        }
                    }
                    list.add(data);

                    Collections.sort(list);
                    build.setbpm.UpdataTime();
                    
                    }catch(Exception e){
                        new Toast("操作有误",build.Bit,build.WorldStage);
                    }
                    BuildList();
                    return true;
                }
            });
        Remove.addListener(new InputListener(){
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    BpmListButton.clearChildren();
                    try{
                    float line=Float.valueOf(checked.getName());
                    for (int i=0;i < list.size();i++) {
                        if (list.get(i).LinePosition == line) {
                            list.remove(i);
                            
                            Collections.sort(list);
                            build.setbpm.UpdataTime();
                            
                            break;
                        }
                    }

                    }catch(Exception e){
                        new Toast("操作有误",build.Bit,build.WorldStage);
                    }
                    BuildList();
                    return true;
                }
            });


        
        
        build.WorldStage.addActor(group);
        
    }
    private Texture createBackgroundTexture(Actor back) {
        Pixmap pixmap = new Pixmap((int)(back.getHeight()/8),(int)(back.getHeight()/8/6),Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.drawLine(1,(int)(back.getHeight()/8/6)-1,(int)(back.getHeight()/8),(int)(back.getHeight()/8/6)-1);
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
    public void BuildList(){
        for(int i=0;i<list.size();i++){
            Label bpm=new Label("Beat:  "+Float.toString(list.get(i).LinePosition)+"  BPM:  "+Float.toString(list.get(i).bpm),new Label.LabelStyle(build.Bit,build.Bit.getColor()));
            //bpm.setFontScale(back.getHeight()/16/build.BpmNumber.getHeight());
            bpm.setWidth(back.getWidth());
            if(i==0){
                bpm.setColor(Color.CYAN);
            }
            bpm.setName(Float.toString(list.get(i).LinePosition));
            if(i>=1){
            AddListen(bpm);
            }
            BpmListButton.add(bpm).padTop(20);
            BpmListButton.row();
            


        }
    }
    public void AddListen(final Actor actor){
        actor.addListener(new InputListener(){
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    actor.setColor(Color.YELLOW);
                if(checked!=null&&checked!=actor){
                    checked.setColor(Color.WHITE);
                    checked=null;
                }
                    checked=actor;
                    return true;
                }
            });
    }
    
    
}
