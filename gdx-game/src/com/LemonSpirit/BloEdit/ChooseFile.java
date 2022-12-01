package com.LemonSpirit.BloEdit;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import java.io.File;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.LemonSpirit.BloEdit.DataHand.TestZIP;
import com.LemonSpirit.BloEdit.Interface.Toast;

public class ChooseFile {
    Table ListButton;
    Texture FileTexture;
    Texture MusicTexture;
    Texture ImageTexture;
    Texture BackTexture;
    Texture BloTexture;
    Welcome build;
    TextField field;
    Group group;
    public static boolean IsImportant=false;
    public ChooseFile(Welcome build,TextField field){
        this.build=build;
        this.field=field;
        group=new Group();
        
        FileTexture=new Texture(Gdx.files.internal("file.png"));
        MusicTexture=new Texture(Gdx.files.internal("music.png"));
        ImageTexture=new Texture(Gdx.files.internal("image.png"));
        BackTexture=new Texture(Gdx.files.internal("back.png"));
        BloTexture=new Texture(Gdx.files.internal("folder_music.png"));
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
        back.setColor(0,0,0,1);
        back.setSize(Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()*7/10);
        back.setPosition((Gdx.graphics.getWidth()-back.getWidth())/2,(Gdx.graphics.getHeight()-back.getHeight())/2);
        group.addActor(back);

        Label Title=new Label("选择文件",new Label.LabelStyle(build.Bit,build.Bit.getColor()));
        //Title.setFontScale(back.getHeight()/16/build.BpmNumber.getHeight());
        Title.setHeight(back.getHeight()/16);
        Title.setPosition(back.getX()+Title.getHeight(),back.getY()+back.getHeight()-Title.getHeight()*2);

        group.addActor(Title);

        

        ListButton=new Table();
        ListButton.top().left();
        ScrollPane pane=new ScrollPane(ListButton,new ScrollPane.ScrollPaneStyle());
        pane.setSize(back.getWidth(),back.getHeight()-Title.getHeight()*2);
        pane.setPosition(back.getX(),back.getY());
        pane.setScrollingDisabled(true,false);//设置是否可上下、左右移动..这里设置了横向可移动、纵向不可移动.
        group.addActor(pane);
        
        build.stage.addActor(group);
        
        
        switch(Gdx.app.getType()){//定义不同设备的预览根目录
            case Android:
                CreatList("/storage/emulated/0");
                break;
            default:
                CreatList("/");
        }
    }
    public void CreatList(final String path){
        Image BACK=new Image(BackTexture);
        BACK.addListener(new InputListener(){
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    try{
                    String p=new File(path).getParent();
                    ListButton.clearChildren();
                    CreatList(p);
                    }catch(Exception e){
                        
                    }
                    return true;
                }
                });
        Label BackText=new Label("返回上级",new Label.LabelStyle(build.Bit,build.Bit.getColor()));
        ListButton.add(BACK).size(Gdx.graphics.getHeight()/8,Gdx.graphics.getHeight()/8);
        ListButton.add(BackText).left();
        ListButton.row();
        
        File[] FileList=new File(path).listFiles();
        for(int i=0;i<FileList.length;i++){
            String suffix=getSuffix(FileList[i].getName()).toLowerCase();
            Image FileImage=null;
            if(suffix.equals("mp3")||suffix.equals("ogg")||suffix.equals("wav")){
                FileImage=new Image(MusicTexture);
            }else if(suffix.equals("png")||suffix.equals("jpg")){
                FileImage=new Image(ImageTexture);
            }else if(suffix.equals("blo")){
                FileImage=new Image(BloTexture);
            }else if(FileList[i].isFile()){
                continue;
            }else{
                FileImage=new Image(FileTexture);
            }
        
        Label FileName=new Label(FileList[i].getName(),new Label.LabelStyle(build.Bit,build.Bit.getColor()));
        FileImage.setUserObject(FileList[i]);
        AddListener(FileImage);
        ListButton.add(FileImage).size(Gdx.graphics.getHeight()/8,Gdx.graphics.getHeight()/8);
        ListButton.add(FileName).left();
        ListButton.row();
        }
    }
    private String getSuffix(String filename){
        int dix = filename.lastIndexOf('.');
        if(dix<0){
            return "";
        }
        else{
            return filename.substring(dix+1);
        }
    }
    private void AddListener(final Actor actor){
        actor.addListener(new InputListener(){
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                   File file=(File)actor.getUserObject();
                   if(file.isFile()){
                   if(field!=null){
                   field.setText(file.getPath());
                   }else{//为谱面导入
                   new Toast("正在导入中",build.Bit,build.stage);
                   TestZIP.unZipFiles(file,GetData.GetFilePath());
                   IsImportant=true;
                   build.game.setScreen(build.game.editscreen);
                   }
                   group.remove();
                   
                   
                   
                   }else if(file.isDirectory()){
                   ListButton.clearChildren();
                   CreatList(file.getPath());
                   }
                    return true;
                }
        
    });
    }
}
