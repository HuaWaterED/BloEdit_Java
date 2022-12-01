package com.LemonSpirit.BloEdit;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.Actor;
import java.util.ArrayList;
import com.LemonSpirit.BloEdit.Interface.BuildSetActions;

public class LineInput extends InputListener {
    WorldBuild build;

    public NoteActor CheckedNote;
    NoteActor CheckedLong=null;
    public boolean IsFirstTouch=false;
    ArrayList <NoteActor> NoteList;

    public LineInput(WorldBuild build) {
        this.build = build;
        this.NoteList = build.NoteList;
    }

    @Override
    public boolean scrolled(InputEvent event, float x, float y, float amountX, float amountY) {
        //build.LineGroup.setY(build.LineGroup.getY()+amountX);
        build.LineGroup.setY(build.LineGroup.getY()+amountY*10);
        return super.scrolled(event, x, y, amountX, amountY);
    }

    
    
    
    
    

    @Override
    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        if (CheckedNote == null && GetData.CheckedTool != null && CheckedLong == null) {
            if (GetData.CheckedTool.getName().equals("Click")) {
                AddClickOrYellow(x, y, true);
            } else if (GetData.CheckedTool.getName().equals("Yellow")) {
                AddClickOrYellow(x, y, false);
            } else if (GetData.CheckedTool.getName().equals("Long")) {
                AddLongType(x, y, 2);
            } else if (GetData.CheckedTool.getName().equals("MoveX")) {
                AddLongType(x, y, 10);
            } else if (GetData.CheckedTool.getName().equals("MoveY")) {
                AddLongType(x, y, 11);
            } else if (GetData.CheckedTool.getName().equals("rotate")) {
                AddLongType(x, y, 12);
            } else if (GetData.CheckedTool.getName().equals("scale")) {
                AddLongType(x, y, 13);
            } else if (GetData.CheckedTool.getName().equals("alpha")) {
                AddLongType(x, y, 14);
            } else if (GetData.CheckedTool.getName().equals("Dragged")) {
                AddDragged(x, y);
            } else if (GetData.CheckedTool.getName().equals("Middle")) {
                AddMiddle(x, y);
            }

        } else if (CheckedLong != null) {

            float a=GetData.LineHeight / GetData.LineNumber;
            int b=(int)(y / a);

            if (CheckedLong.getY() + CheckedLong.getHeight() < y) {
                CheckedLong.setHeight((a * b - CheckedLong.getY()));
                CheckedLong.setColor(Color.WHITE);
                CheckNote(CheckedLong);
                
                if (CheckedLong.type >= 10) {
                    new BuildSetActions(build, CheckedLong);
                }
                   GetData.SaveData.SaveNoteData(CheckedLong,GetData.NowPlane,GetData.NowBlock);
                    
                
                CheckedLong = null;

            }
        }

        return true;
    }

    @Override
    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
        if (CheckedNote != null) {
            CheckedNote.SetBackColor();
            
            GetData.SaveData.RemoveData(CheckedNote,GetData.NowPlane,GetData.NowBlock);
            GetData.SaveData.SaveNoteData(CheckedNote,GetData.NowPlane,GetData.NowBlock);
            
            CheckedNote = null;
            IsFirstTouch = false;
            
            
                
        }
        super.touchUp(event, x, y, pointer, button);
    }

    @Override
    public void touchDragged(InputEvent event, float x, float y, int pointer) {
        if (CheckedNote != null && IsFirstTouch) {
            float a=GetData.LineHeight / GetData.LineNumber;
            int b=(int)(y / a);
            if (CheckedNote.type == 3 || CheckedNote.type == 4) {
                CheckedNote.setPosition(CheckedNote.getX(), a * b);
            } else {//普通note
                float X=(x - CheckedNote.getWidth() / 2);
                float c=GetData.LineWeight / (GetData.LineWeightUnit*5);
                int d=Math.round(X / c);
                if(X<0){
                    CheckedNote.setPosition(0, a * b);
                }else if(X>GetData.LineWeight-GetData.NoteWeight){
                    CheckedNote.setPosition(GetData.LineWeight-GetData.NoteWeight, a * b);
                }else{
                    CheckedNote.setPosition(d*c, a * b);
                }
            }
        }
        super.touchDragged(event, x, y, pointer);
    }

    public void CheckNote(final NoteActor note) {
        note.addListener(new InputListener(){
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    note.setColor(Color.ORANGE);

                    if (CheckedNote != null && CheckedNote != note) {
                        CheckedNote.SetBackColor();
                        CheckedNote = null;          
                    }
                    if (CheckedNote == note) {
                        build.LineButton.fire(event);
                        IsFirstTouch = true;
                    } else {
                        CheckedNote = note;
                    }

                    return true;
                }

            });

    }
    public void AddClickOrYellow(float x, float y, boolean IsClick) {
        NoteActor note;
        if (IsClick) {
            note = new NoteActor(build, 0, -1);
        } else {
            note = new NoteActor(build, 1, -1);
        }
        SetPosition(note,x,y);
        CheckNote(note);
        build.LineGroup.addActor(note);
        NoteList.add(note);
        GetData.SaveData.SaveNoteData(note,GetData.NowPlane,GetData.NowBlock);
    }

    public void AddLongType(float x, float y, int type) {
        NoteActor note=new NoteActor(build, type, -1);

        SetPosition(note,x,y);
        note.setColor(Color.ORANGE);
        build.LineGroup.addActor(note);
        NoteList.add(note);
        CheckedLong = note;
        
    }
    public void AddDragged(float x, float y) {
        NoteActor note;

        float a=GetData.LineHeight / GetData.LineNumber;
        int b=(int)(y / a);
        if (x < GetData.LineWeight / 2) {
            note = new NoteActor(build, 4, 1);      
        } else {
            note = new NoteActor(build, 4, -1);
        }
        note.setPosition(0, a * b);
        CheckNote(note);
        build.LineGroup.addActor(note);
        NoteList.add(note);
        GetData.SaveData.SaveNoteData(note,GetData.NowPlane,GetData.NowBlock);

    }
    public void AddMiddle(float x, float y) {
        NoteActor note=new NoteActor(build, 3, -1);

        float a=GetData.LineHeight / GetData.LineNumber;
        int b=(int)(y / a);
        note.setPosition((GetData.LineWeight - note.getWidth()) / 2, a * b);
        CheckNote(note);
        build.LineGroup.addActor(note);
        NoteList.add(note);
        GetData.SaveData.SaveNoteData(note,GetData.NowPlane,GetData.NowBlock);
    }
    public void SetPosition(NoteActor note,float x,float y){
        float a=GetData.LineHeight / GetData.LineNumber;
        int b=(int)(y / a);
        float X=x - note.getWidth() / 2;
        float c=GetData.LineWeight / (GetData.LineWeightUnit*5);
        int d=Math.round(X / c);
        if(X<0){
            note.setPosition(0, a * b);
        }else if(X>GetData.LineWeight-GetData.NoteWeight){
            note.setPosition(GetData.LineWeight-GetData.NoteWeight, a * b);
        }else{
            note.setPosition(d*c, a * b);
        }
    }
    

}
    
