package com.LemonSpirit.BloEdit;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.audio.Music;
import com.LemonSpirit.BloEdit.Interface.Toast;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.files.FileHandle;

public class Welcome implements Screen {
    public MyGdxGame game;
    public Stage stage;
    public static float MusicTime=-1;
    Label.LabelStyle LabelStyle;
    TextField BackgroundField;
    TextField MusicField;
    TextField BPMField;
    public BitmapFont Bit=new BitmapFont(Gdx.files.internal("font.fnt"),Gdx.files.internal("font.png"),false);
    public Welcome(MyGdxGame game){
        this.game=game;
    }
    @Override
    public void show() {
        Bit.getData().setScale(30/Bit.getData().lineHeight);
        stage=new Stage();
        Image background=new Image(new Texture(Gdx.files.internal("background.jpg")));
        background.setSize(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        stage.addActor(background);
        Pixmap map=new Pixmap(1,1,Pixmap.Format.RGBA8888);
        map.setColor(Color.BLACK);
        map.fill();
        
        Image back=new Image(new Texture(map));
        
        back.setColor(0,0,0,0.5f);
        back.setSize(Gdx.graphics.getWidth()*2/3,Gdx.graphics.getHeight()*2/3);
        back.setPosition((Gdx.graphics.getWidth()-back.getWidth())/2,(Gdx.graphics.getHeight()-back.getHeight())/2);
        
        stage.addActor(back);
        
        Label Title=new Label("欢迎来到BloEdit  -  创建一个新的谱面",new Label.LabelStyle(Bit,Bit.getColor()));
        Title.setHeight(back.getHeight()/16);
        Title.setPosition(back.getX()+Title.getHeight(),back.getY()+back.getHeight()-Title.getHeight()*2);

        stage.addActor(Title);
        
        Table edit=new Table();
        edit.left();
        
        edit.setSize(back.getWidth(),back.getHeight()*7/8-Title.getHeight()*2);
        edit.setPosition(back.getX(),back.getY()+back.getHeight()/8);
        
        TextField.TextFieldStyle style=new TextField.TextFieldStyle();
        style.background = new TextureRegionDrawable(createBackgroundTexture(back));

        // 设置光标纹理区域
        style.cursor = new TextureRegionDrawable(createCursorTexture(back));

        // 设置文本框显示文本的字体来源
        style.font = Bit;

        // 设置文本框字体颜色为白色
        style.fontColor = new Color(1, 1, 1, 1);
        
        LabelStyle=new Label.LabelStyle(Bit,Bit.getColor());
        LabelStyle.background=new TextureRegionDrawable(GetData.GetWhiteTexture());
        LabelStyle.fontColor=Color.BLACK;
        
        Label MusicPath=new Label("音乐路径，请尽量使用ogg格式，bug最少:",new Label.LabelStyle(Bit,Bit.getColor()));
       
        
        
        edit.add(MusicPath).left().padLeft(20).padTop(20);
        edit.row();
        Label File1=new Label("选择",LabelStyle);
        File1.setAlignment(Align.center);
        
        MusicField=new TextField("",style);
        File1.addListener(new InputListener(){
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    new ChooseFile(Welcome.this,MusicField);
                    return true;
            }
        });
        
        edit.add(MusicField).width(back.getWidth()*4/5).height(back.getHeight()/10).padLeft(20);
        edit.add(File1).size(back.getWidth()/7,back.getHeight()/10).left().padLeft(10);
        edit.row();
        
        Label BackgrounfPath=new Label("背景路径:",new Label.LabelStyle(Bit,Bit.getColor()));
        edit.add(BackgrounfPath).left().padLeft(20).padTop(20);
        edit.row();
        
        Label File2=new Label("选择",LabelStyle);
        File2.setAlignment(Align.center);
        BackgroundField=new TextField("",style);
        File2.addListener(new InputListener(){
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    new ChooseFile(Welcome.this,BackgroundField);
                    return true;
                }
            });
        
        edit.add(BackgroundField).width(back.getWidth()*4/5).height(back.getHeight()/10).padLeft(20);
        edit.add(File2).size(back.getWidth()/7,back.getHeight()/10).left().padLeft(10);
        
        edit.row();
        
        Label BPMPath=new Label("BPM值:",new Label.LabelStyle(Bit,Bit.getColor()));
        edit.add(BPMPath).left().padLeft(20).padTop(20);
        edit.row();

        BPMField=new TextField("",style);
        edit.add(BPMField).width(back.getWidth()*4/5).height(back.getHeight()/10).padLeft(20).left();
        edit.row();
        map.setColor(Color.WHITE);
        map.fill();
        
        Table DownButton=new Table();
        DownButton.setSize(back.getWidth(),back.getHeight()/8);
        DownButton.setPosition(back.getX(),back.getY());
        DownButton.top();
        
        Label Create=new Label("创建",LabelStyle);
        Create.setAlignment(Align.center);
        DownButton.add(Create).size(back.getWidth()/3,back.getHeight()/8*2/3).padRight(20);
        Create.addListener(new InputListener(){
                @Override

                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                  
                    try{
                        if(Gdx.files.absolute(BackgroundField.getText()).exists()&&Gdx.files.absolute(MusicField.getText()).exists()){
                        switch(Gdx.app.getType()){
                            case Desktop://电脑平台
                                BuildGetMusicTime();
                            break;
                            default:
                                FileHandle Music=Gdx.files.absolute(MusicField.getText());
                                FileHandle Background=Gdx.files.absolute(BackgroundField.getText());
                                Music.copyTo(Gdx.files.absolute(GetData.GetFilePath()+Music.name()));
                                Background.copyTo(Gdx.files.absolute(GetData.GetFilePath()+Background.name()));
                                
                                game.editscreen.set(GetData.GetFilePath()+Background.name(),GetData.GetFilePath()+Music.name(),Float.valueOf( BPMField.getText()));
                                game.setScreen(game.editscreen);
                                break;
                        }
                        
                        }else{
                            new Toast("填写内容有误",Bit,stage);
                        }
                    }catch(Exception e){
                        new Toast("填写内容有误",Bit,stage);
                    }
                    
                    return true;
                }
            });
        
        Label Import=new Label("导入",LabelStyle);
        Import.setAlignment(Align.center);
        DownButton.add(Import).size(back.getWidth()/3,back.getHeight()/8*2/3).padLeft(20);
        Import.addListener(new InputListener(){
                @Override

                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    new ChooseFile(Welcome.this,null);
                    return true;
                }
            });
        stage.addActor(DownButton);
        stage.addActor(edit);
        Gdx.input.setInputProcessor(stage);
        
        
    }
    private void BuildGetMusicTime(){
        final Group group=new Group();

        Image BlackBack=new Image(GetData.GetBlackTexture());
        BlackBack.setSize(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        BlackBack.setPosition(0,0);
        BlackBack.setColor(0,0,0,0);

        group.addActor(BlackBack);
        Image back=new Image(GetData.GetBlackTexture());
        back.setColor(0,0,0,1f);
        back.setSize(Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()/3);
        back.setPosition((Gdx.graphics.getWidth()-back.getWidth())/2,(Gdx.graphics.getHeight()-back.getHeight())/2);
        group.addActor(back);

        Label Title=new Label("由于移植问题，需要手动输入音乐时长",new Label.LabelStyle(Bit,Bit.getColor()));

        Title.setPosition(back.getX()+Title.getHeight(),back.getY()+back.getHeight()-Title.getHeight()*2);
        
        group.addActor(Title);
        
        Table edit=new Table();
        edit.left();

        edit.setSize(back.getWidth(),back.getHeight()-Title.getHeight()*2);
        edit.setPosition(back.getX(),back.getY());

        
        TextField.TextFieldStyle style=new TextField.TextFieldStyle();
        style.background = new TextureRegionDrawable(createBackgroundTexture(back));

        // 设置光标纹理区域
        style.cursor = new TextureRegionDrawable(createCursorTexture(back));

        // 设置文本框显示文本的字体来源
        style.font = Bit;

        // 设置文本框字体颜色为白色
        style.fontColor = new Color(1, 1, 1, 1);
        final Label Label=new Label("音乐时长 单位为秒:",new Label.LabelStyle(Bit,Bit.getColor()));
        edit.add(Label).left().padLeft(20).padTop(20);
        edit.row();

        final TextField Field=new TextField("",style);
        edit.add(Field).width(back.getWidth()-40).height(back.getHeight()/6).padLeft(20);
        edit.row();
        Label Create=new Label("确定",LabelStyle);
        Create.setAlignment(Align.center);
        
        Create.addListener(new InputListener(){
                @Override

                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    FileHandle Music=Gdx.files.absolute(MusicField.getText());
                    FileHandle Background=Gdx.files.absolute(BackgroundField.getText());
                    Music.copyTo(Gdx.files.absolute(GetData.GetFilePath()+Music.name()));
                    Background.copyTo(Gdx.files.absolute(GetData.GetFilePath()+Background.name()));
                    game.editscreen.set(GetData.GetFilePath()+Background.name(),GetData.GetFilePath()+Music.name(),Float.valueOf( BPMField.getText()));
                    MusicTime=Float.valueOf(Field.getText());
                    game.setScreen(game.editscreen);
                    return true;
                }
                });
        
        edit.add(Create).size(back.getWidth()/3,back.getHeight()/6).padTop(10);
        group.addActor(edit);
        stage.addActor(group);
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
    @Override
    public void render(float p1) {
        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int p1, int p2) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
    }
    
    
    
    
}
