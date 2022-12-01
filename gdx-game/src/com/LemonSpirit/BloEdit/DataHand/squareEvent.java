package com.LemonSpirit.BloEdit.DataHand;
import com.LemonSpirit.BloEdit.GetData;
import com.LemonSpirit.BloEdit.NoteActor;
import com.LemonSpirit.BloEdit.WorldBuild;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public class squareEvent implements Comparable<squareEvent>{//动作的量
    public float startTime;
    public float endTime;
    public float startValue;
    public float endValue;
    public int easingType;
    public float positionX;
    public NoteActor SetNote(NoteActor note,WorldBuild build){
        note.setY(build.setbpm.GetLine(startTime)*GetData.LineHeight);
        note.setX(positionX*(GetData.LineWeight-GetData.NoteWeight));
        if(startTime==endTime){
        note.IsMoment=true;
        note.setHeight(GetData.NoteHeight);
        note.NoteTexture = GetData.NoteTexture;
            note.setColor(Color.CYAN);
            note.addAction(Actions.alpha(0.8f));
        }else{
        note.setHeight(build.setbpm.GetLine(endTime)*GetData.LineHeight-note.getY());
        }
        note.StartValue=startValue;
        note.EndValue=endValue;
        note.setUserObject(this);
        return note;
    }
    @Override
    public int compareTo(squareEvent p1) {
        return (int)(startTime*1000f)-(int)(p1.startTime*1000f);
    }
}
