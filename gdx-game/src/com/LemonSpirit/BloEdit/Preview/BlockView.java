package com.LemonSpirit.BloEdit.Preview;
import java.util.ArrayList;
import com.LemonSpirit.BloEdit.DataHand.squareEvent;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.LemonSpirit.BloEdit.WorldBuild;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.LemonSpirit.BloEdit.GetData;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.graphics.Color;

public class BlockView {
    WorldBuild build;
    public Group[] Line=new Group[4];
    public ArrayList<squareEvent> Event=new ArrayList<squareEvent>();
    public Group mainGroup;
    public int number;
    Label[] PlaneNumber=new Label[4];
    Label BlockNumber;
    public BlockView(ViewBuild view, int number) {
        this.build = view.build;
        this.number = number;


        mainGroup = new Group();

        Image BlockImage=new Image(GetData.Block);
        BlockImage.setSize(Gdx.graphics.getHeight() * 0.5f, Gdx.graphics.getHeight() * 0.5f);
        
        BlockImage.setPosition(0,0);
        mainGroup.addActor(BlockImage);

        for (int i=0;i < 4;i++) {
            Line[i] = new Group();
            PlaneNumber[i]=new Label(Integer.toString(i+1), new Label.LabelStyle(build.Bit, build.Bit.getColor()));
            PlaneNumber[i].setPosition(BlockImage.getX()+(BlockImage.getWidth()-PlaneNumber[i].getWidth())/2,BlockImage.getY());
            
            mainGroup.addActor(Line[i]);
        }
        Line[1].setOrigin(BlockImage.getWidth()/ 2, BlockImage.getWidth()/ 2);
        Line[2].setOrigin(BlockImage.getWidth() / 2, BlockImage.getWidth() / 2);
        Line[3].setOrigin(BlockImage.getWidth()/ 2, BlockImage.getWidth() / 2);
        mainGroup.setOrigin(BlockImage.getWidth()/ 2, BlockImage.getWidth() / 2);
        
        
        Line[0].addActor(PlaneNumber[0]);
        Line[2].addActor(PlaneNumber[1]);
        Line[3].addActor(PlaneNumber[2]);
        Line[1].addActor(PlaneNumber[3]);
        BlockNumber=new Label(Integer.toString(number+1), new Label.LabelStyle(build.Bit, build.Bit.getColor()));
        BlockNumber.setPosition(BlockImage.getX()+(BlockImage.getWidth()-BlockNumber.getWidth())/2,BlockImage.getY()+(BlockImage.getHeight()-BlockNumber.getHeight())/2);
        Line[0].addActor(BlockNumber);
        
        Line[1].setRotation(90);//对他们进行旋转
        Line[2].setRotation(180);
		Line[3].setRotation(270);
        
        mainGroup.setPosition((Gdx.graphics.getWidth()-BlockImage.getWidth())/2,(Gdx.graphics.getHeight()-BlockImage.getWidth())/2);
        

        view.group.addActor(mainGroup);



    }
    
    public void act(float NowTime) {
        setAction(GetData.SaveData.squareData.get(number).squareEventData.moveX,NowTime,1);
        setAction(GetData.SaveData.squareData.get(number).squareEventData.moveY,NowTime,2);
        setAction(GetData.SaveData.squareData.get(number).squareEventData.rotate,NowTime,3);
        setAction(GetData.SaveData.squareData.get(number).squareEventData.scale,NowTime,4);
        setAction(GetData.SaveData.squareData.get(number).squareEventData.alpha,NowTime,5);
        if(GetData.NowBlock==number+1){
            BlockNumber.setColor(Color.YELLOW);
            for(Label a:PlaneNumber){
                a.setColor(Color.WHITE);
            }
            if(GetData.NowPlane!=5){
            PlaneNumber[GetData.NowPlane-1].setColor(Color.CYAN);
            }
            
        }else{
            BlockNumber.setColor(Color.WHITE);
            for(Label a:PlaneNumber){
                a.setColor(Color.WHITE);
            }
        }
        
    }
    public void setAction(ArrayList<squareEvent> list, float NowTime, int type) {
        for (int i=0;i < list.size();i++) {
            float Past=0;
            float PastValue=0;
            float This=list.get(i).startTime;
            float Next=999999999;
            
            if (i - 1 >= 0) {//获取上一个动作
                Past = list.get(i - 1).endTime;
                PastValue=list.get(i-1).endValue;
            }else{
                switch (type) {
                    case 1:
                    PastValue=0.5f;
                        break;
                    case 2:
                        PastValue=0.5f;
                        break;
                    case 3:
                        PastValue=0;
                        break;
                    case 4:
                        PastValue=1;
                        break;
                    case 5:
                        PastValue=1;
                        break;
                }
                
            }
            
            
            if (i + 1 < list.size()) {//获取下一个动作
                Next = list.get(i + 1).startTime;
            }
            if (list.get(i).startTime <= NowTime && NowTime < list.get(i).endTime) {//在动作时间内
                float a=list.get(i).endValue - list.get(i).startValue;
                float b=list.get(i).startValue +  (NowTime - list.get(i).startTime) / (list.get(i).endTime - list.get(i).startTime) * a;
                switch (type) {
                    case 1:
                        mainGroup.setX(b * Gdx.graphics.getWidth() - Gdx.graphics.getHeight() * 0.25f);
                        break;
                    case 2:
                        mainGroup.setY((1-b) * Gdx.graphics.getHeight() -Gdx.graphics.getHeight()*0.25f);
                        break;
                    case 3:
                        mainGroup.setRotation(b);
                        break;
                    case 4:
                        mainGroup.setScale(b);
                        break;
                    case 5:
                        mainGroup.addAction(Actions.alpha(b));
                        break;
                }
                break;

            } else if (This <= NowTime && NowTime < Next) {//在动作时间后
                switch (type) {
                    case 1:
                        mainGroup.setX(list.get(i).endValue * Gdx.graphics.getWidth() - Gdx.graphics.getHeight() * 0.25f);
                        break;
                    case 2:
                        mainGroup.setY((1-list.get(i).endValue)* Gdx.graphics.getHeight() -Gdx.graphics.getHeight()*0.25f);
                        break;
                    case 3:
                        mainGroup.setRotation(list.get(i).endValue);
                        break;
                    case 4:
                        mainGroup.setScale(list.get(i).endValue);
                        break;
                    case 5:
                        mainGroup.addAction(Actions.alpha(list.get(i).endValue));
                        break;
                  }
                  break;
            
                
            } else if (Past <= NowTime && NowTime <= This) {//在动作时间前
                switch (type) {
                    case 1:
                        mainGroup.setX(PastValue* Gdx.graphics.getWidth() - Gdx.graphics.getHeight() * 0.25f);
                        break;
                    case 2:
                        mainGroup.setY((1-PastValue)* Gdx.graphics.getHeight() -Gdx.graphics.getHeight()*0.25f);
                        break;
                    case 3:
                        mainGroup.setRotation(PastValue);
                        break;
                    case 4:
                        mainGroup.setScale(PastValue);
                        break;
                    case 5:
                        mainGroup.addAction(Actions.alpha(PastValue));
                        break;
                }
                break;
            }

        }
    }

}
