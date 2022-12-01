package com.LemonSpirit.BloEdit;
import com.LemonSpirit.BloEdit.BpmControl.SetBpm;
import com.LemonSpirit.BloEdit.DataHand.ReadData;
import com.LemonSpirit.BloEdit.Interface.BuildPlaneChange;
import com.LemonSpirit.BloEdit.Interface.BuildRightTool;
import com.LemonSpirit.BloEdit.Preview.ViewBuild;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import java.util.ArrayList;
import com.LemonSpirit.BloEdit.Interface.BuildMessage;


public class WorldBuild {
    public Music music;
    public EditScreen screen;
    public Stage WorldStage;
    
    
    public Group LineGroup;
    public Group LeftLineGroup;
    public Group ViewGroup;
    
    public Button LineButton;
    public boolean IsPlay=false;
    public BitmapFont Bit;
    public Image PositionLine;
    public Image Progress;
    public Label NowTimeLabel;
    public LineInput input;
    public int NowLine=0;
    public float NowTime;
    public float number;
    public ArrayList <NoteActor> NoteList;
    public Label PlaneInform;
    public SetBpm setbpm;
    public TextureRegion background;
    public ReadData Read;
    public BsuEvent BsuEvent;
    public ViewBuild view;
    boolean IsFinishDelay=true;
    float DeltaRunTime=0;
    Image RightBlack;
    Texture C_Play=new Texture(Gdx.files.internal("play.png"));
    Texture C_pause=new Texture(Gdx.files.internal("pause.png"));
    TextureRegion C_stop=new TextureRegion(C_Play);
    Label EndTime;
    
    public WorldBuild(EditScreen screen) {
        this.screen = screen;
        this.BsuEvent=screen.BsuEvent;
        
    }
    
    public void Building(String backgroundPath, String musicPath, float StartBPM) {
        Bit=new BitmapFont(Gdx.files.internal("font.fnt"), Gdx.files.internal("font.png"), false);
        new GetData(this);
        if(StartBPM!=-1){
        GetData.BPM = (int)StartBPM;
        }
        Read=new ReadData(this);
        if(backgroundPath!=null){
        GetData.SaveData.meta.backgroundPath = backgroundPath;
        GetData.SaveData.meta.musicPath = musicPath;
        GetData.SaveData.meta.bpm = StartBPM;
        }
        
        
        WorldStage = new Stage();
        NoteList = new ArrayList <NoteActor>();
        input = new LineInput(this);
        
        music = Gdx.audio.newMusic(Gdx.files.absolute(GetData.SaveData.meta.musicPath));
        if(Welcome.MusicTime!=-1){
            GetData.SaveData.meta.MusicLength=Welcome.MusicTime;
        }
        if(GetData.SaveData.meta.MusicLength<=0){
        music.setVolume(0.6f);
        music.setPosition(99999);
        GetData.SaveData.meta.MusicLength=music.getPosition();
        }
        music.setOnCompletionListener(new Music.OnCompletionListener(){

            @Override
        public void onCompletion(Music p1)
        {
            pause();
        }
        
            
        });
        music.setPosition(0);
        background = new TextureRegion(new Texture(Gdx.files.absolute(GetData.SaveData.meta.backgroundPath)));
        Image background=new Image(this.background);
        background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        background.setPosition(0, 0);
        Pixmap map=new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        map.setColor(Color.BLACK);
        map.fill();
        Image black=new Image(new Texture(map));
        black.setColor(0, 0, 0, 0.3f);
        black.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        black.setPosition(0, 0);

        WorldStage.addActor(background);
        WorldStage.addActor(black);
        
        
        if(GetData.EnablePreview){
            view=new ViewBuild(this);
            view.Build();
        }
        
        LineGroup = new Group();
        
        ViewGroup=new Group();
        
        Group LineEditGroup=new Group();
        Image LineBlack=new Image(new Texture(map));
        LineBlack.setSize(GetData.LineWeight, Gdx.graphics.getHeight());
        LineBlack.setPosition(0, 0);
        LineBlack.setColor(0, 0, 0, 0.5f);
        LineEditGroup.addActor(LineBlack);

        map.setColor(Color.WHITE);
        map.fill();
        LineActor lineactor=new LineActor(new Texture(map),  this, true, Bit);
        LineGroup.addActor(lineactor);

        LineButton = new Button(new Button.ButtonStyle());
        LineButton.setSize(GetData.LineWeight, 999999999);
        LineButton.setPosition(0, 0);

        LineButton.addListener(input);
        LineGroup.addActor(LineButton);
        LineGroup.setPosition(0, GetData.DownLineHeight);
        LineEditGroup.addActor(LineGroup);


        map.setColor(Color.WHITE);
        map.fill();
        Image DownLine=new Image(new Texture(map));
        DownLine.setSize(GetData.LineWeight, 5);
        DownLine.setPosition(0, GetData.DownLineHeight);
        LineEditGroup.addActor(DownLine);


        LineEditGroup.setPosition(GetData.LeftRightWeight, 0);
        map.setColor(Color.BLACK);
        map.fill();
        Image ProgressBlack=new Image(new Texture(map));
        ProgressBlack.setSize(GetData.LineWeight, GetData.DownLineHeight);
        ProgressBlack.setPosition(0, 0);
        ProgressBlack.setColor(0, 0, 0, 0.5f);


        LineEditGroup.addActor(ProgressBlack);
        map.setColor(Color.WHITE);
        map.fill();

        
        final Image Stop=new Image(C_stop);
        Stop.setSize(GetData.DownLineHeight, GetData.DownLineHeight);
        Stop.setPosition(GetData.LineWeight - Stop.getWidth() - 10, 0);
        Stop.addListener(new InputListener(){
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    if (IsPlay) {
                        pause();
                        SetLine();
                    } else {
                        IsPlay = true;
                        if(NowTime-GetData.SaveData.meta.delayTime>=0){
                        music.setPosition(NowTime-GetData.SaveData.meta.delayTime);
                        music.play();
                        }
                        
                        
                        
                        C_stop.setRegion(C_pause);
                    }
                    return true;
                }
            });

        Image Plus=new Image(new Texture(Gdx.files.internal("plus.png")));
        Plus.setSize(GetData.DownLineHeight, GetData.DownLineHeight);
        Plus.setPosition(GetData.LineWeight - Stop.getWidth() * 2 - 10, 0);
        Plus.addListener(new InputListener(){
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    GetData.SetLineHeight(4 / 3f);
                    for (int i=0;i < NoteList.size();i++) {
                        NoteList.get(i).setY(NoteList.get(i).getY() * (4 / 3f));
                        if (NoteList.get(i).type == 2 || NoteList.get(i).type >= 10) {
                            if(!NoteList.get(i).IsMoment){
                            NoteList.get(i).setHeight(NoteList.get(i).getHeight() * (4 / 3f));
                            }
                        }
                    }
                    pause();
                    LineGroup.setY(-NowLine * GetData.LineHeight / GetData.LineNumber + GetData.DownLineHeight);
                    return true;
                }
            });



        Image Minus=new Image(new Texture(Gdx.files.internal("minus.png")));
        Minus.setSize(GetData.DownLineHeight, GetData.DownLineHeight);
        Minus.setPosition(GetData.LineWeight - Stop.getWidth() * 3 - 10, 0);
        Minus.addListener(new InputListener(){
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                    GetData.SetLineHeight(3 / 4f);
                    for (int i=0;i < NoteList.size();i++) {
                        NoteList.get(i).setY(NoteList.get(i).getY() * (3 / 4f));
                        if (NoteList.get(i).type == 2 || NoteList.get(i).type >= 10) {
                            if(!NoteList.get(i).IsMoment){
                            NoteList.get(i).setHeight(NoteList.get(i).getHeight() * (3 / 4f));
                            }
                        }
                    }
                    pause();
                    LineGroup.setY(-NowLine * GetData.LineHeight / GetData.LineNumber + GetData.DownLineHeight);

                    return true;
                }
            });

        NowTimeLabel = new Label("000.000", new Label.LabelStyle(Bit, Bit.getColor()));
        //NowTimeLabel.setFontScale(GetData.FontSize/NowTimeLabel.getHeight());
        NowTimeLabel.setSize(NowTimeLabel.getWidth() * NowTimeLabel.getFontScaleX(), GetData.FontSize);
        NowTimeLabel.setPosition(10, (GetData.DownLineHeight - NowTimeLabel.getHeight()) / 2);
        LineEditGroup.addActor(NowTimeLabel);



        EndTime=new Label(Float.toString(GetData.SaveData.meta.MusicLength), new Label.LabelStyle(Bit, Bit.getColor()));
        //EndTime.setFontScale(GetData.FontSize/EndTime.getHeight());
        EndTime.setSize(EndTime.getWidth() * EndTime.getFontScaleX(), GetData.FontSize);
        EndTime.setPosition(Minus.getX() - EndTime.getWidth(), (GetData.DownLineHeight - EndTime.getHeight()) / 2);
        LineEditGroup.addActor(EndTime);

        Progress = new Image(new Texture(map));
        Progress.setSize(EndTime.getX() - (NowTimeLabel.getX() + NowTimeLabel.getWidth() + 20), GetData.DownLineHeight / 15);
        Progress.setPosition(NowTimeLabel.getWidth() + NowTimeLabel.getX() + 10, GetData.DownLineHeight / 2);
        LineEditGroup.addActor(Progress);

        PositionLine = new Image(new Texture(map));
        PositionLine.setSize(Progress.getHeight(), GetData.DownLineHeight / 2);
        PositionLine.setPosition(Progress.getX(), Progress.getY() - (PositionLine.getHeight() - Progress.getHeight()) / 2);
        final Button PositionButton=new Button(new Button.ButtonStyle());
        PositionButton.setSize(Progress.getWidth(), GetData.DownLineHeight);
        PositionButton.setPosition(Progress.getX(), 0);
        PositionButton.addListener(new InputListener(){
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    if (0 <= x && x <= PositionButton.getWidth()) {
                        PositionLine.setX(x + Progress.getX());

                        LineGroup.setY(-setbpm.GetLine(x / PositionButton.getWidth() * GetData.SaveData.meta.MusicLength) * GetData.LineHeight);
                        pause();
                    }
                    return true;
                }
                public void touchDragged(InputEvent event, float x, float y, int pointer) {
                    if (x <= 0) {
                        PositionLine.setX(Progress.getX());
                        LineGroup.setY(GetData.DownLineHeight);
                    } else if (x >= PositionButton.getWidth()) {
                        PositionLine.setX(Progress.getX() + PositionButton.getWidth());
                        LineGroup.setY(-setbpm.GetLine(GetData.SaveData.meta.MusicLength) * GetData.LineHeight + GetData.DownLineHeight);
                    } else {
                        PositionLine.setX(x + Progress.getX());
                        LineGroup.setY(-setbpm.GetLine(x / PositionButton.getWidth() * GetData.SaveData.meta.MusicLength) * GetData.LineHeight);
                    }
                    pause();
                    super.touchDragged(event, x, y, pointer);
                }
                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    SetLine();
                    super.touchUp(event, x, y, pointer, button);
                }

            });
        LineEditGroup.addActor(PositionLine);
        LineEditGroup.addActor(PositionButton);
        LineEditGroup.addActor(Plus);
        LineEditGroup.addActor(Minus);
        LineEditGroup.addActor(Stop);
        ViewGroup.addActor(LineEditGroup);

        map.setColor(Color.BLACK);
        map.fill();
        Image LeftBlack=new Image(new Texture(map));
        LeftBlack.setSize(GetData.LeftRightWeight - GetData.LeftRightWeight / 10, Gdx.graphics.getHeight());
        LeftBlack.setPosition(0, 0);
        LeftBlack.setColor(0, 0, 0, 0.5f);
        ViewGroup.addActor(LeftBlack);


        LeftLineGroup = new Group();
        map.setColor(Color.WHITE);
        map.fill();
        LineActor RightLine=new LineActor(new Texture(map), this, false, Bit);
        LeftLineGroup.addActor(RightLine);
        ViewGroup.addActor(LeftLineGroup);
        map.setColor(Color.WHITE);
        map.fill();
        Image LeftDownLine=new Image(new Texture(map));
        LeftDownLine.setSize(LeftBlack.getWidth(), LeftBlack.getWidth() / 20);
        LeftDownLine.setPosition(0, Gdx.graphics.getHeight() / 2);
        ViewGroup.addActor(LeftDownLine);
        LeftLineGroup.setPosition(0, LeftDownLine.getY());
        Button LeftButton=new Button(new Button.ButtonStyle());
        LeftButton.setSize(LeftBlack.getWidth(), LeftBlack.getHeight());
        LeftButton.setPosition(0, 0);
        LeftButton.addListener(new InputListener(){
                float DownY;
                float GroupY;
                float LineY;
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    DownY = y;
                    GroupY = LeftLineGroup.getY();
                    LineY = LineGroup.getY();
                    IsPlay = false;
                    music.pause();
                    C_stop.setRegion(C_Play);
                    return true;
                }
                public void touchDragged(InputEvent event, float x, float y, int pointer) {

                    LeftLineGroup.setY(GroupY - (DownY - y));
                    LineGroup.setY(LineY - (DownY - y));
                    pause();
                    super.touchDragged(event, x, y, pointer);
                }
                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    SetLine();
                    super.touchUp(event, x, y, pointer, button);
                }
            });


        Group Inform=new Group();
        PlaneInform = new Label("  Line:1       Block:1", new Label.LabelStyle(Bit, Bit.getColor()));
        //PlaneInform.setFontScale((GetData.LeftRightWeight/4)/PlaneInform.getHeight());
        PlaneInform.setPosition(0, 0);
        PlaneInform.setHeight(GetData.LeftRightWeight / 4);
        Inform.addActor(PlaneInform);
        Inform.setPosition(Gdx.graphics.getWidth() - GetData.LeftRightWeight, Gdx.graphics.getHeight());
        Inform.setOrigin(0, 0);
        Inform.setRotation(-90);


        ViewGroup.addActor(Inform);

        ViewGroup.addActor(LeftButton);

        RightBlack=new Image(new Texture(map));
        RightBlack.setSize(GetData.LeftRightWeight / 2, Gdx.graphics.getHeight());
        RightBlack.setPosition(Gdx.graphics.getWidth() - RightBlack.getWidth(), 0);
        RightBlack.setColor(0, 0, 0, 0.5f);
        WorldStage.addActor(RightBlack);

        WorldStage.addActor(ViewGroup);

        new BuildRightTool(this, RightBlack);



        Gdx.input.setInputProcessor(WorldStage);

        setbpm = new SetBpm(this);
        Read.AddNote();
        new BuildPlaneChange(this, RightBlack);
        
        Preferences pre=Gdx.app.getPreferences("PreferenceTest");
        if(pre.getBoolean("ed")==false){//第一次使用
            new BuildMessage(this,"免责声明","\n在您使用BloEdit或者BloPhy预览器进行自制谱时请明确获得授权再进行使用\n由您自己产生的任何纠纷(包括但不限于曲绘版权纠纷、歌曲版权纠纷等) ，都与BloEdit和BloPhy预览器的作者以及制作团队无关，责任由使用者全部承担\n继续使用代表您同意上述规则，如不同意，请卸载BloEdit和BloPhy预览器，感谢您的配合"
                             , "同意","不同意",1);
        
        }
    }
    public void WorldDraw() {
        WorldStage.act();
        
        if(GetData.EnablePreview){
            view.act(NowTime);
        }
        
        
        WorldStage.draw();

        if (IsPlay) {
            float Delta=(GetData.LineHeight * setbpm.NowBpm) / 60 * Gdx.graphics.getDeltaTime();
            if(NowTime>=GetData.SaveData.meta.delayTime){
                LineGroup.setY(LineGroup.getY() - Delta - (music.getPosition() - NowTime+GetData.SaveData.meta.delayTime) * (GetData.LineHeight * setbpm.NowBpm)/ 60/20);//执行运动
            DeltaRunTime+=Gdx.graphics.getDeltaTime();
            }else{
            LineGroup.setY(LineGroup.getY() - Delta );
            }
            
            if(DeltaRunTime>=30){
                music.setPosition(music.getPosition());
                DeltaRunTime=0;
            }
        }
        
        boolean IsFinishDelay= NowTime>=GetData.SaveData.meta.delayTime;
        if(IsFinishDelay&&!this.IsFinishDelay&&IsPlay){
            music.setPosition(0);
            music.play();
        }
        this.IsFinishDelay=IsFinishDelay;
        
        //判定线所在位置
        float Position=-LineGroup.getY() + GetData.DownLineHeight;
        number = Position / (GetData.LineHeight);

        LeftLineGroup.setY(LineGroup.getY() + Gdx.graphics.getHeight() / 2 - GetData.DownLineHeight);
        setbpm.Updata(number);
        NowTime = setbpm.StartTime + (Position - setbpm.LinePosition * GetData.LineHeight) / (GetData.LineHeight * setbpm.NowBpm) * 60;
        PositionLine.setX(Progress.getX() + (NowTime / GetData.SaveData.meta.MusicLength) * Progress.getWidth());

        NowTimeLabel.setText(Float.toString((int)(NowTime * 1000) / 1000f));
        EndTime.setText(Float.toString(GetData.SaveData.meta.MusicLength));

        NowLine = Math.round(((-LineGroup.getY() + GetData.DownLineHeight) / (GetData.LineHeight / GetData.LineNumber)));




    }

    public void SetLine() {
        float Position=-LineGroup.getY() + GetData.DownLineHeight;
        int number=Math.round((Position / (GetData.LineHeight / GetData.LineNumber)));
        LineGroup.setY(-number * GetData.LineHeight / GetData.LineNumber + GetData.DownLineHeight);
        this.number = (float)number / GetData.LineNumber;
    }
    public void pause() {
        IsPlay = false;
        music.pause();
        C_stop.setRegion(C_Play);
        DeltaRunTime=0;
    }
    public void SetPlane(int plane) {
       
        for (int i=0;i < NoteList.size();i++) {
            NoteList.get(i).remove();
        }
        NoteList.clear();
        if(GetData.NowPlane==5&&plane<5){//动作切换到note
        if(GetData.NowPlaneActor!=null){
        GetData.NowPlaneActor.remove();
        new BuildRightTool(this,RightBlack);
        GetData.NowPlaneActor=null;
        GetData.CheckedTool=null;
        }
        }else if(GetData.NowPlane<5&&plane==5){//note切换到note
            if(GetData.NowPlaneActor!=null){
                GetData.NowPlaneActor.remove();
                new BuildRightTool(this,RightBlack);
                GetData.NowPlaneActor=null;
                GetData.CheckedTool=null;
            }
        }


        if (plane == 5) {
            PlaneInform.setText("  Actions" + "       Block:" + Integer.toString(GetData.NowBlock));
            Read.ReadActions(GetData.NowBlock);
        } else {
            PlaneInform.setText("  Line:" + Integer.toString(plane) + "       Block:" + Integer.toString(GetData.NowBlock));
            Read.ReadLine(plane, GetData.NowBlock);
        }
        GetData.NowPlane = plane;


    }
    public void SetBlock(int Block) {
       
        for (int i=0;i < NoteList.size();i++) {
            NoteList.get(i).remove();
        }
        NoteList.clear();
        
        
        GetData.NowBlock=Block;
        if (GetData.NowPlane == 5) {
            PlaneInform.setText("  Actions" + "       Block:" + Integer.toString(GetData.NowBlock));
            Read.ReadActions(GetData.NowBlock);
        } else {
            PlaneInform.setText("  Line:" + Integer.toString(GetData.NowPlane) + "       Block:" + Integer.toString(GetData.NowBlock));
            Read.ReadLine(GetData.NowPlane, GetData.NowBlock);
        }
        
    }
    

}
