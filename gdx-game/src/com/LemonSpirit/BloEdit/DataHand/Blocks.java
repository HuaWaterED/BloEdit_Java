package com.LemonSpirit.BloEdit.DataHand;

public class Blocks {//表示不同的框
    public Line[] judgeLineData=new Line[4];
    public squareEventData squareEventData=new squareEventData();
    public Blocks() {
        for (int i=0;i < 4;i++) {
            judgeLineData[i] = new Line();
        }

    }
}

