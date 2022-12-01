package com.LemonSpirit.BloEdit.Interface;
import com.LemonSpirit.BloEdit.GetData;
import com.LemonSpirit.BloEdit.NoteActor;
import com.LemonSpirit.BloEdit.WorldBuild;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.LemonSpirit.BloEdit.DataHand.noteData;

public class BuildSetNote {
    WorldBuild build;
    public BuildSetNote(final WorldBuild build,final NoteActor note){
        this.build=build;
        final Group group=new Group();

        Image BlackBack=new Image(GetData.GetBlackTexture());
        BlackBack.setSize(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        BlackBack.setPosition(0,0);
        BlackBack.setColor(0,0,0,0);

        group.addActor(BlackBack);
        Image back=new Image(GetData.GetBlackTexture());
        back.setColor(0,0,0,0.5f);
        back.setSize(Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()/5*4);
        back.setPosition((Gdx.graphics.getWidth()-back.getWidth())/2,(Gdx.graphics.getHeight()-back.getHeight())/2);
        group.addActor(back);

        Label Title=new Label("设定音符数据",new Label.LabelStyle(build.Bit,build.Bit.getColor()));
        
        Title.setPosition(back.getX()+Title.getHeight(),back.getY()+back.getHeight()-Title.getHeight()*2);

        Table edit=new Table();
        edit.left();

        edit.setSize(back.getWidth(),back.getHeight()-Title.getHeight()*2);
        edit.setPosition(back.getX(),back.getY());

        BitmapFont Bit=build.Bit;

        TextField.TextFieldStyle style=new TextField.TextFieldStyle();
        style.background = new TextureRegionDrawable(createBackgroundTexture(back));

        // 设置光标纹理区域
        style.cursor = new TextureRegionDrawable(createCursorTexture(back));

        // 设置文本框显示文本的字体来源
        style.font = Bit;

        // 设置文本框字体颜色为白色
        style.fontColor = new Color(1, 1, 1, 1);
        final Label PositionLabel=new Label("位置(取0到1):",new Label.LabelStyle(Bit,Bit.getColor()));
        edit.add(PositionLabel).left().padLeft(20).padTop(20);
        edit.row();

        final TextField PositionField=new TextField(Float.toString(note.GetPosition()),style);
        edit.add(PositionField).width(back.getWidth()-40).height(back.getHeight()/8).padLeft(20);
        edit.row();

        final Label ScaleLable=new Label("缩放比例:",new Label.LabelStyle(Bit,Bit.getColor()));
        edit.add(ScaleLable).left().padLeft(20).padTop(20);
        edit.row();

        final TextField ScaleField=new TextField(Float.toString(note.GetScale()),style);
        edit.add(ScaleField).width(back.getWidth()-40).height(back.getHeight()/8).padLeft(20);
        edit.row();
        
        final Label SpeedLable=new Label("音符流速:",new Label.LabelStyle(Bit,Bit.getColor()));
        edit.add(SpeedLable).left().padLeft(20).padTop(20);
        edit.row();

        final TextField SpeedField=new TextField(Float.toString(note.speed),style);
        edit.add(SpeedField).width(back.getWidth()-40).height(back.getHeight()/8).padLeft(20);
        edit.row();
        Texture checkboxOnTexture = new Texture(Gdx.files.internal("check.png"));
        Texture checkboxOffTexture = new Texture(Gdx.files.internal("ischeck.png"));

        CheckBox.CheckBoxStyle CheckBoxStyle = new CheckBox.CheckBoxStyle();
        // 设置 style 的 选中 和 未选中 状态的纹理区域
        CheckBoxStyle.checkboxOn = new TextureRegionDrawable(new TextureRegion(checkboxOnTexture));
        CheckBoxStyle.checkboxOff = new TextureRegionDrawable(new TextureRegion(checkboxOffTexture));
        // 设置复选框文本的位图字体
        CheckBoxStyle.checkboxOn.setMinHeight(back.getHeight()/12);
        CheckBoxStyle.checkboxOn.setMinWidth(back.getHeight()/12);

        CheckBoxStyle.checkboxOff.setMinHeight(back.getHeight()/12);
        CheckBoxStyle.checkboxOff.setMinWidth(back.getHeight()/12);

        CheckBoxStyle.font = build.Bit;




        /* * 第 3 步: 创建 CheckBox */
        final CheckBox checkBox = new CheckBox("产生波纹", CheckBoxStyle);
        // 设置复选框的位置
     
        if(note.HaveWave){
            checkBox.setChecked(false);
        }else{
            checkBox.setChecked(true);
        }
        checkBox.addListener(new ChangeListener() { 
                @Override
                public void changed(ChangeEvent event, Actor actor) { 
                    note.HaveWave=!checkBox.isChecked();
                }
            });
        edit.add(checkBox).padTop(5);
        edit.row();
        Label.LabelStyle a=new Label.LabelStyle(Bit,Bit.getColor());
        a.background=new TextureRegionDrawable(GetData.GetWhiteTexture());
        a.fontColor=Color.BLACK;
        Label Create=new Label("确定",a);
        Create.setAlignment(Align.center);
        edit.add(Create).size(back.getWidth()/3,back.getHeight()/12).padTop(5);
        Create.addListener(new InputListener(){
                @Override

                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    try{
                    note.speed=Float.valueOf(SpeedField.getText());
                    if(Float.valueOf(PositionField.getText())>=0&&Float.valueOf(PositionField.getText())<=1){
                    note.setX(Float.valueOf(PositionField.getText())*(GetData.LineWeight-GetData.NoteWeight));
                    }
                    note.setWidth(Float.valueOf(ScaleField.getText())*GetData.NoteWeight);
                        GetData.SaveData.RemoveData(note,GetData.NowPlane,GetData.NowBlock);
                        GetData.SaveData.SaveNoteData(note,GetData.NowPlane,GetData.NowBlock);
                        
                    }catch(Exception e){
                        new Toast("内容有误，确认失败",build.Bit,build.WorldStage);
                        
                    }
                    group.remove();
                    return true;
                }
            });

        group.addActor(Title);
        group.addActor(edit);
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
    
    
    
    
}
