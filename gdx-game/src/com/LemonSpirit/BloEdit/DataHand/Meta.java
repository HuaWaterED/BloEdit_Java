package com.LemonSpirit.BloEdit.DataHand;
import java.util.ArrayList;
import com.LemonSpirit.BloEdit.BpmControl.BpmData;

public class Meta {
    public String title;
    public String musicPath;
    public String backgroundPath;
    public float delayTime;
    public float previewTime;
    public float previewDuration;
    public String creator;
    public String artist;
    public float bpm;
    public String version;
    public int notesCount;
    public ArrayList<BpmData> bpmGroup=new ArrayList<BpmData>();
    public String tag;
    public float thisTime;
    public float MusicLength;
    public boolean isAutoplay;
   
    public Meta(){
    title="未知";
    musicPath="不应该未知";
    backgroundPath="不应该未知";
    previewTime=0;
    previewDuration=0;
    creator="未知";
    artist="未知";
    bpm=120;
    version="未知";
    notesCount=0;
    tag="未知";
    thisTime=0;
    delayTime=0;
    isAutoplay=true;
    MusicLength=-1;
    }
}
