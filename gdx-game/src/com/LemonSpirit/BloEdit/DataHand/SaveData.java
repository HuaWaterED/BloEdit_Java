package com.LemonSpirit.BloEdit.DataHand;

import com.LemonSpirit.BloEdit.DataHand.Blocks;
import com.LemonSpirit.BloEdit.GetData;
import com.LemonSpirit.BloEdit.NoteActor;
import com.LemonSpirit.BloEdit.WorldBuild;
import com.badlogic.gdx.math.Plane;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;


public class SaveData {//Json Main
    public ArrayList<Blocks> squareData=new ArrayList<Blocks>();
    public Meta meta=new Meta();
    public SaveData() {
        Blocks one=new Blocks();
		squareData.add(one);

    }
    public void Save(WorldBuild build) {
  
        Json json=new Json();
        json.setTypeName(null);
        json.setUsePrototypes(false);
        json.setIgnoreUnknownFields(true);
        json.setOutputType(JsonWriter.OutputType.json);

        String string=json.toJson(GetData.SaveData);
        writeFile(GetData.GetChartPath(),string);
    }

    public void SaveNoteData(NoteActor Note, int Plane, int Block) {
        if(Note.type<10){
        
            float hitTime=0;
            float holdTime;
            int line;
            float positionX;
            float speed;
            float scale;
            int type;
            int flickDirection;

            hitTime = Note.GetTime();

            if (Note.type == 2) {
                holdTime = Note.GetHoldTime();
                
            } else {
                holdTime = 0;
            }

            line = Plane;

            positionX = Note.GetPosition();

            speed = Note.speed;

            scale = Note.GetScale();

            type = Note.type;

            flickDirection = Note.flickDirection;

            noteMovementData note=new noteMovementData();
            note.SetData(hitTime, holdTime, line, positionX, speed, scale, type, flickDirection);
            noteData noteData=new noteData();
            noteData.noteMovementData = note;
            
            if(Note.HaveWave){
                noteData.noteJudgementData.squareJudgementFX=0;
            }else{
                noteData.noteJudgementData.squareJudgementFX=1;
            }
            
            Note.setUserObject(noteData);
            
            
            squareData.get(Block - 1).judgeLineData[Plane - 1].noteData.add(noteData);
            }else{
            SaveActions(Note,Block);
            }
        }


    
    private void SaveActions(NoteActor Note, int Block) {

        float StartTime;
        float EndTime;
        float StartValue;
        float EndValue;
        int easingType;

        StartTime = Note.GetTime();
        EndTime = Note.GetHoldTime() + StartTime;
        StartValue = Note.StartValue;
        EndValue = Note.EndValue;
        easingType = Note.easingType;
        squareEvent squareEvent=new squareEvent();
        squareEvent.startTime = StartTime;
        squareEvent.endTime = EndTime;
        squareEvent.startValue = StartValue;
        squareEvent.endValue = EndValue;
        squareEvent.easingType = easingType;
        squareEvent.positionX = Note.GetPosition();
        Note.setUserObject(squareEvent);
        switch (Note.type) {
            case 10:
                squareData.get(Block - 1).squareEventData.moveX.add(squareEvent);
                Collections.sort(squareData.get(Block - 1).squareEventData.moveX);
                break;
            case 11:
                squareData.get(Block - 1).squareEventData.moveY.add(squareEvent);
                Collections.sort(squareData.get(Block - 1).squareEventData.moveY);
                break;
            case 12:
                squareData.get(Block - 1).squareEventData.rotate.add(squareEvent);
                Collections.sort(squareData.get(Block - 1).squareEventData.rotate);
                break;
            case 13:
                squareData.get(Block - 1).squareEventData.scale.add(squareEvent);
                Collections.sort(squareData.get(Block - 1).squareEventData.scale);
                break;
            case 14:
                squareData.get(Block - 1).squareEventData.alpha.add(squareEvent);
                Collections.sort(squareData.get(Block - 1).squareEventData.alpha);
                break;
        }

    }
    public void RemoveData(NoteActor Note,int Plane,int Block){
        if(Note.type<10){
            squareData.get(Block - 1).judgeLineData[Plane - 1].noteData.remove(Note.getUserObject());
        }else{
            switch (Note.type) {
                case 10:
                    squareData.get(Block - 1).squareEventData.moveX.remove(Note.getUserObject());
                    Collections.sort(squareData.get(Block - 1).squareEventData.moveX);
                    break;
                case 11:
                    squareData.get(Block - 1).squareEventData.moveY.remove(Note.getUserObject());
                    Collections.sort(squareData.get(Block - 1).squareEventData.moveY);
                    break;
                case 12:
                    squareData.get(Block - 1).squareEventData.rotate.remove(Note.getUserObject());
                    Collections.sort(squareData.get(Block - 1).squareEventData.rotate);
                    break;
                case 13:
                    squareData.get(Block - 1).squareEventData.scale.remove(Note.getUserObject());
                    Collections.sort(squareData.get(Block - 1).squareEventData.scale);
                    break;
                case 14:
                    squareData.get(Block - 1).squareEventData.alpha.remove(Note.getUserObject());
                    Collections.sort(squareData.get(Block - 1).squareEventData.alpha);
                    break;
            }
        }
    }
    public static void writeFile(String filePathAndName, String fileContent) {
        try {
            File f = new File(filePathAndName);
            if (!f.exists()) {
                f.createNewFile();
            }
            OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(f),"UTF-8");
            BufferedWriter writer=new BufferedWriter(write);  
            //PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(filePathAndName)));
            //PrintWriter writer = new PrintWriter(new FileWriter(filePathAndName));
            writer.write(fileContent);
            writer.close();
        } catch (Exception e) {
            System.out.println("写文件内容操作出错");
            e.printStackTrace();
        }
    }
    
    
}






