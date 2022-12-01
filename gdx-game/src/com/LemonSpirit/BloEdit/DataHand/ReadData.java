package com.LemonSpirit.BloEdit.DataHand;
import com.LemonSpirit.BloEdit.BpmControl.BpmData;
import com.LemonSpirit.BloEdit.GetData;
import com.LemonSpirit.BloEdit.NoteActor;
import com.LemonSpirit.BloEdit.WorldBuild;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import com.LemonSpirit.BloEdit.ChooseFile;

public class ReadData {
    WorldBuild build;
    public ReadData(WorldBuild build) {
        this.build = build;
        FileHandle file=Gdx.files.absolute(GetData.GetChartPath());
        if (file.exists()) {
            Json json=new Json();
            GetData.SaveData=json.fromJson(SaveData.class,readFile(GetData.GetChartPath()));
            GetData.SaveData.meta.bpmGroup = GetData.SaveData.meta.bpmGroup;
            if(ChooseFile.IsImportant==true){//如果是导入的文件
            File Music=new File(GetData.SaveData.meta.musicPath);
            File Background=new File(GetData.SaveData.meta.backgroundPath);
            GetData.SaveData.meta.musicPath=GetData.GetFilePath()+Music.getName();
            GetData.SaveData.meta.backgroundPath=GetData.GetFilePath()+Background.getName();
            GetData.SaveData.Save(build);
            }
        } else {
            GetData.SaveData = new SaveData();
            BpmData StartBpm=new BpmData();
            StartBpm.startTime = 0;
            StartBpm.LinePosition = 0;
            StartBpm.bpm = GetData.BPM;
            GetData.SaveData.meta.bpmGroup.add(StartBpm);
		}
        
    }

	public void ReadLine(int plane,int Block) {
        ArrayList<noteData> list=GetData.SaveData.squareData.get(Block-1).judgeLineData[plane - 1].noteData;
        for (int i=0;i < list.size();i++) {
            noteMovementData NoteData=list.get(i).noteMovementData;
            NoteActor note=new NoteActor(build, NoteData.noteType,NoteData.flickDirection);
            note = NoteData.SetNote(note, build);
            note.setUserObject(list.get(i));
            float a=list.get(i).noteJudgementData.squareJudgementFX;
            if(a==1){
                note.HaveWave=false;
            }else{
                note.HaveWave=true;
            }
            build.input.CheckNote(note);
            build.LineGroup.addActor(note);
            build.NoteList.add(note);
        }
	}
    public void ReadActions(int Block){
        squareEventData EventData=GetData.SaveData.squareData.get(Block-1).squareEventData;
        ReadActionsFor(EventData.moveX,10);
        ReadActionsFor(EventData.moveY,11);
        ReadActionsFor(EventData.rotate,12);
        ReadActionsFor(EventData.scale,13);
        ReadActionsFor(EventData.alpha,14);
        
    }
    public void ReadActionsFor(ArrayList<squareEvent> list,int Type){
        for (int i=0;i < list.size();i++) {
            NoteActor note=new NoteActor(build,Type,-1);
            note = list.get(i).SetNote(note, build);
            note.easingType=list.get(i).easingType;
            build.input.CheckNote(note);
            build.LineGroup.addActor(note);
            build.NoteList.add(note);
        }
    }
    
	public void AddNote() {
        ArrayList<noteData> list=GetData.SaveData.squareData.get(0).judgeLineData[0].noteData;
        for (int i=0;i < list.size();i++) {
            noteMovementData NoteData=list.get(i).noteMovementData;
            NoteActor note=new NoteActor(build, NoteData.noteType,NoteData.flickDirection);
            note = NoteData.SetNote(note, build);
            note.setUserObject(list.get(i));
            float a=list.get(i).noteJudgementData.squareJudgementFX;
            if(a==1){
                note.HaveWave=false;
            }else{
                note.HaveWave=true;
            }
            build.input.CheckNote(note);
            build.LineGroup.addActor(note);
            build.NoteList.add(note);
            

        }
        
    }
    public static String readFile(String filePathAndName) {
        String fileContent = "";
        try {  
            File f = new File(filePathAndName);
            if(f.isFile()&&f.exists()){
                InputStreamReader read = new InputStreamReader(new FileInputStream(f),"UTF-8");
                BufferedReader reader=new BufferedReader(read);
                String line;
                while ((line = reader.readLine()) != null) {
                    fileContent += line;
                }   
                read.close();
            }
        } catch (Exception e) {
            System.out.println("读取文件内容操作出错");
            e.printStackTrace();
        }
        return fileContent;
    }

}
