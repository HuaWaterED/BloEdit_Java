package com.LemonSpirit.BloEdit;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import java.util.ArrayList;
import com.LemonSpirit.BloEdit.BpmControl.BpmData;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.LemonSpirit.BloEdit.DataHand.noteMovementData;
import com.LemonSpirit.BloEdit.DataHand.SaveData;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.audio.Music;

public class GetData {
    public static float LineWeight;
    public static int LineWeightPix;
    public static float LineHeight;
    public static int LineHeightPix;
    public static float DownLineHeight;
    public static float LeftRightWeight;
    public static int BPM;
    public static float NoteHeight;
    public static float FontSize;
    public static float B;
    public static int LineNumber=4;
    public static WorldBuild build=null;
    public static Pixmap  map=new Pixmap(1,1,Pixmap.Format.RGBA8888);
    public static Texture NoteTexture;
    public static Texture LongTexture;
    public static Texture DraggedRTexture;
    public static Texture DraggedLTexture;
    public static Texture MiddleTexture;
    public static Actor CheckedRuler=null;
    public static Actor CheckedTool=null;
    public static float NoteWeight;
    public static int NowPlane=1;
    public static int NowBlock=1;
    public static Texture Block;
    public static Sound NoteSound;
    public static float DelayTime=5;
    public static boolean EnablePreview=true;
    public static int LineWeightUnit=4;
    public static Actor NowPlaneActor;
    public static SaveData SaveData=null;
    
    public GetData(WorldBuild build){
        this.build=build;
        LineWeight=Gdx.graphics.getWidth()*3/4;
        LineWeightPix=(int)(LineWeight/2);
        LineHeight=LineWeight/8;
        LineHeightPix=(int)(LineHeight/2);
        DownLineHeight=Gdx.graphics.getHeight()/10;
        LeftRightWeight=(Gdx.graphics.getWidth()-LineWeight)/2;
        BPM=120;
        NoteHeight=LineWeight/8/4;
        NoteWeight=LineWeight/5;
        FontSize=Gdx.graphics.getHeight()/30;
        
        
        Pixmap note=new Pixmap(1,3,Pixmap.Format.RGBA8888);
        note.setColor(Color.WHITE);
        note.drawLine(0,3,0,1);
        NoteTexture=new Texture(note);
        MiddleTexture=new Texture(Gdx.files.internal("Middle.png"));
        DraggedRTexture=new Texture(Gdx.files.internal("DraggedR.png"));
        DraggedLTexture=new Texture(Gdx.files.internal("DraggedL.png"));
        LongTexture=new Texture(Gdx.files.internal("NoteLong.png"));

       
        map.setColor(Color.BLACK);
        map.fill();
        
        Pixmap BlockMap=new Pixmap(120,120,Pixmap.Format.RGBA8888);//这里实例化一个画布，大小为120✘120
        BlockMap.setColor(Color.WHITE);//设置颜色为白色
		BlockMap.drawRectangle(0,0,120,120);//在画布里绘制一个方块，这里就是游戏的方块判定线
        Block=new Texture(BlockMap);
        
        build.Bit.getData().setScale(Gdx.graphics.getHeight()/24/build.Bit.getData().lineHeight);
        
        NoteSound=Gdx.audio.newSound(Gdx.files.internal("NOTE.wav"));
        
		//SaveData=new SaveData();
        
        
    }
    public static void SetLineHeight(float b){
        LineHeight=LineHeight*b;
        B=LineHeight/(LineWeight/8);
          }
    
    public static Texture GetBlackTexture(){
        map.setColor(Color.BLACK);
        map.fill();
        return new Texture(map);
    }
    public static Texture GetWhiteTexture(){
        
        map.setColor(Color.WHITE);
        map.fill();
        return new Texture(map);
    }
    public static String GetChartPath(){
        switch(Gdx.app.getType()){
            case Android:
                return "/storage/emulated/0/BloEdit/Chart.lshw";
            case Desktop:
                return "C:/BloEdit/Chart.lshw";
            default:
                return "/BloEdit/Chart.lshw";
        }
       
    }
    public static String GetFilePath(){
        switch(Gdx.app.getType()){
            case Android:
                return "/storage/emulated/0/BloEdit/";
            case Desktop:
                return "C:/BloEdit/";
            default:
                return "/BloEdit/";
        }

    }
    
}
