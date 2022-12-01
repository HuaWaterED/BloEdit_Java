package com.LemonSpirit.BloEdit.DataHand;
import java.util.ArrayList;

public class Line {//一个框的多条线
    public ArrayList<noteData> noteData=new ArrayList<noteData>();
    public ArrayList<squareEvent> speed=new ArrayList <squareEvent>();
    
    public Line() {
        squareEvent speed=new squareEvent();
        speed.startTime = 0;
        speed.endTime = 9999;
        speed.startValue = 5;
        speed.endValue = 5;
        speed.easingType = 0;
        this.speed.add(speed);
    }

}



    

