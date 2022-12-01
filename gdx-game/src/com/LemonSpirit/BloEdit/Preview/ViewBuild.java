package com.LemonSpirit.BloEdit.Preview;
import com.LemonSpirit.BloEdit.WorldBuild;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.LemonSpirit.BloEdit.GetData;
import java.util.ArrayList;
import com.badlogic.gdx.scenes.scene2d.Group;

public class ViewBuild {
    WorldBuild build;
    public Group group=new Group();
    public ArrayList<BlockView> Blocks=new ArrayList<BlockView>();
    public ViewBuild(WorldBuild build){
        this.build=build;
        //stage=build.WorldStage;
        
    }
    public void Build(){
        int BlockSize=GetData.SaveData.squareData.size();
        for(int i = 0;i<BlockSize;i++){
        Blocks.add(new BlockView(this,i));
        }
        build.WorldStage.addActor(group);
    }
    public void act(float NowTime){
        for(int i=0;i<Blocks.size();i++){
            Blocks.get(i).act(NowTime);
        }
    }
    public void UpDataBlock(){
        for(BlockView block:Blocks){
            block.mainGroup.remove();
        }
        Blocks.clear();
        
        int BlockSize=GetData.SaveData.squareData.size();
        for(int i = 0;i<BlockSize;i++){
            Blocks.add(new BlockView(this,i));
        }
    }
    
    public float ToNumber(float max,float one,float cha){
        return ((max-cha)/100)*one;
	}
    
}
