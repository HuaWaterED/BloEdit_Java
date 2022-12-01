package com.LemonSpirit.BloEdit.BpmControl;
import java.util.ArrayList;
import com.LemonSpirit.BloEdit.WorldBuild;
import com.LemonSpirit.BloEdit.GetData;

public class SetBpm {
    public float NowBpm;
    public float LinePosition;
    public float StartTime;
    ArrayList<BpmData> list;
    WorldBuild build;
    public SetBpm(WorldBuild build){
        this.build=build;
        list=GetData.SaveData.meta.bpmGroup;
    }
    public void Updata(float NowPosition){
        for(int i=0;i<list.size();i++){
            float One=0;
            float Next=999999999;
            One=list.get(i).LinePosition;
            if(i+1<list.size()){
                Next=list.get(i+1).LinePosition;
            }
            if(One<=NowPosition&&NowPosition<Next){
                BpmData bpmdata=list.get(i);
                NowBpm=bpmdata.bpm;
                LinePosition=bpmdata.LinePosition;
                StartTime=bpmdata.startTime;

            }


        }
    }
    public float GetTime(float NowPosition){
        for(int i=0;i<list.size();i++){
            float One=0;
            float Next=999999999;
            One=list.get(i).LinePosition;
            if(i+1<list.size()){
                Next=list.get(i+1).LinePosition;
            }
            if(One<=NowPosition&&NowPosition<Next){
                BpmData bpmdata=list.get(i);
                float NowTime = bpmdata.startTime + ((NowPosition - bpmdata.LinePosition )* GetData.LineHeight) / (GetData.LineHeight * bpmdata.bpm) * 60;
                return NowTime;

            }


        }
        
        return 0;
      
    }
    public void UpdataTime(){
        for(int i=0;i<list.size();i++){
            
            if(i-1>=0){
                float time=list.get(i-1).startTime;
                float position=-list.get(i-1).LinePosition+list.get(i).LinePosition;
                float bpm=list.get(i-1).bpm;
                list.get(i).startTime=time+(position/bpm)*60f;
            }else{
                list.get(i).startTime=0;
            }
        }
    }
    public float GetLine(float time){
        for(int i=0;i<list.size();i++){
            float One=0;
            float Next=999999999;
            One=list.get(i).startTime;
            if(i+1<list.size()){
                Next=list.get(i+1).startTime;
            }
            if(One<=time&&time<Next){
                float atime=time-list.get(i).startTime;
                float line=list.get(i).LinePosition+list.get(i).bpm/60f*atime;
                return line;
            }


        }
        
        return 0;
    }
    
}
