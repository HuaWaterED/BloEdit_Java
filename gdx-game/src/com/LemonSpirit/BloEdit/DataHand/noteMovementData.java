package com.LemonSpirit.BloEdit.DataHand;
import com.LemonSpirit.BloEdit.NoteActor;
import com.LemonSpirit.BloEdit.GetData;
import com.LemonSpirit.BloEdit.WorldBuild;

public class noteMovementData{
    public float hitTime;
    public float holdTime;
    public int line;  //该Note所在的判定线号(=方块号*4+判定线位置常数(0123下上左右))
    public float positionX;
    public float speed;
    public float scale;
    public int noteType;
    public int flickDirection;
    public noteMovementData(){
        
    }
    
    public void SetData(float hitTime,
                        float holdTime,
                        int line,  //该Note所在的判定线号(=方块号*4+判定线位置常数(0123下上左右))
                        float positionX,
                        float speed,
                        float scale,
                        int type,
                        int flickDirection){
        this.hitTime=hitTime;
        this.holdTime=holdTime;
        this.line=line;
        this.positionX=positionX;
        this.speed=speed;
        this.scale=scale;
        this.noteType=type;
        this.flickDirection=flickDirection;

    }
    public NoteActor SetNote(NoteActor note,WorldBuild build){
        note.setY(build.setbpm.GetLine(hitTime)*GetData.LineHeight);
        note.setX(positionX*(GetData.LineWeight-GetData.NoteWeight));
        if(holdTime!=0){
            note.setHeight(build.setbpm.GetLine(hitTime+holdTime)*GetData.LineHeight-note.getY());
        }
        note.speed=speed;
        note.setWidth(scale*GetData.NoteWeight);
        
        
        return note;
    }
}
