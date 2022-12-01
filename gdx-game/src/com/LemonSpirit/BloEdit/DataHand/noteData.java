package com.LemonSpirit.BloEdit.DataHand;
import java.util.ArrayList;

public class noteData{//note的数据
    public noteMovementData noteMovementData=new noteMovementData();
    public noteJudgementData noteJudgementData=new noteJudgementData();
    public ArrayList<noteEventData>noteEventData=new ArrayList<noteEventData>();
    
}

 class noteEventData{
    ArrayList<noteEvent>positionXOffset=new ArrayList<noteEvent>();
    ArrayList<noteEvent>positionYOffset=new ArrayList<noteEvent>();
}
class noteEvent{

}
