package com.LemonSpirit.BloEdit.Interface;
import com.LemonSpirit.BloEdit.WorldBuild;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.LemonSpirit.BloEdit.GetData;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class BuildStartView {
    WorldBuild build;
    CheckBox checkBox;
    public BuildStartView(final WorldBuild build){
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


        Image back=new Image(GetData.GetBlackTexture());
        back.setColor(0,0,0,0.5f);
        back.setSize(Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()/2);
        back.setPosition((Gdx.graphics.getWidth()-back.getWidth())/2,(Gdx.graphics.getHeight()-back.getHeight())/2);
        group.addActor(back);
        
        Label Title=new Label("使用BloPhy进行预览",new Label.LabelStyle(build.Bit,build.Bit.getColor()));
        //Title.setFontScale(back.getHeight()/16/build.BpmNumber.getHeight());
        Title.setHeight(back.getHeight()/16);
        Title.setPosition(back.getX()+Title.getHeight(),back.getY()+back.getHeight()-Title.getHeight()*2);

        group.addActor(Title);
        Table table=new Table();
        table.setSize(back.getWidth(),back.getHeight()-Title.getHeight()*2);
        table.setPosition(back.getX(),back.getY());
        
        Label.LabelStyle a=new Label.LabelStyle(build.Bit,build.Bit.getColor());
        a.background=new TextureRegionDrawable(GetData.GetWhiteTexture());
        a.fontColor=Color.BLACK;
        Label Now=new Label("当前位置进行预览",a);
        Now.setAlignment(Align.center);
        Label Start=new Label("从头开始进行预览",a);
        
       Texture checkboxOnTexture = new Texture(Gdx.files.internal("check.png"));
       Texture checkboxOffTexture = new Texture(Gdx.files.internal("ischeck.png"));
        
        CheckBox.CheckBoxStyle style = new CheckBox.CheckBoxStyle();
        // 设置 style 的 选中 和 未选中 状态的纹理区域
        style.checkboxOn = new TextureRegionDrawable(new TextureRegion(checkboxOnTexture));
        style.checkboxOff = new TextureRegionDrawable(new TextureRegion(checkboxOffTexture));
        // 设置复选框文本的位图字体
        style.checkboxOn.setMinHeight(back.getHeight()/6);
        style.checkboxOn.setMinWidth(back.getHeight()/6);
      
        style.checkboxOff.setMinHeight(back.getHeight()/6);
        style.checkboxOff.setMinWidth(back.getHeight()/6);
        
        style.font = build.Bit;
        
        
        
        
        /* * 第 3 步: 创建 CheckBox */
        checkBox = new CheckBox("Auto Play", style);
        // 设置复选框的位置
        
        if(GetData.SaveData.meta.isAutoplay){
            checkBox.setChecked(false);
        }else{
            checkBox.setChecked(true);
        }
        checkBox.addListener(new ChangeListener() { 
                @Override
                public void changed(ChangeEvent event, Actor actor) { 
                    GetData.SaveData.meta.isAutoplay=!checkBox.isChecked();
                }
            });
        Start.addListener(new InputListener(){                                                                              
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    GetData.SaveData.meta.thisTime=0;
                    GetData.SaveData.Save(build);
                    build.BsuEvent.notify(null,"Action");
                    return true;
                }
            });
        Now.addListener(new InputListener(){                                                                              
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    GetData.SaveData.meta.thisTime=build.NowTime;
                    GetData.SaveData.Save(build);
                    build.BsuEvent.notify(null,"Action");
                    return true;
                }
            });
        
        Start.setAlignment(Align.center);
        table.add(Now).size(back.getWidth()*3/4,back.getHeight()/6);
        table.row();
        table.add(Start).size(back.getWidth()*3/4,back.getHeight()/6).padTop(back.getHeight()/10);
        table.row();
        table.add(checkBox).padTop(back.getHeight()/10);
        
        group.addActor(table);
        
        build.WorldStage.addActor(group);
        
    }
    
    
}
