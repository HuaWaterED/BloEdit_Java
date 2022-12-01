package com.LemonSpirit.BloEdit.BpmControl;

public class BpmData implements Comparable<BpmData> {

    
    public float LinePosition;
    public float startTime;
    public float bpm;
    public BpmData(){
        
    }
    @Override
    public int compareTo(BpmData p1) {
        return (int)(LinePosition*1000f)-(int)(p1.LinePosition*1000f);
    }
    
    
}
