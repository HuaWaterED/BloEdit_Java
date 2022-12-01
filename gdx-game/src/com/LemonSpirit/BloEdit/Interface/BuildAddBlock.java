package com.LemonSpirit.BloEdit.Interface;
import com.LemonSpirit.BloEdit.WorldBuild;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Align;
import java.util.ArrayList;
import com.LemonSpirit.BloEdit.DataHand.Blocks;
import com.badlogic.gdx.scenes.scene2d.Actor;
import java.util.Collections;
import com.LemonSpirit.BloEdit.GetData;

public class BuildAddBlock {
    WorldBuild build;
    ArrayList<Blocks> list;
    Actor checked=null;
    public BuildAddBlock(final WorldBuild build) {
        this.list = GetData.SaveData.squareData;
        this.build = build;
        final Group group=new Group();


        Image BlackBack=new Image(GetData.GetBlackTexture());
        BlackBack.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        BlackBack.setPosition(0, 0);
        BlackBack.setColor(0, 0, 0, 0);
        BlackBack.addListener(new InputListener(){
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    group.remove();
                    return true;
                }
            });
        group.addActor(BlackBack);



        final Image back=new Image(GetData.GetBlackTexture());
        back.setColor(0, 0, 0, 0.5f);
        back.setSize(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() * 7 / 10);
        back.setPosition((Gdx.graphics.getWidth() - back.getWidth()) / 2, (Gdx.graphics.getHeight() - back.getHeight()) / 2);
        group.addActor(back);

        Label Title=new Label("BLOCK LIST", new Label.LabelStyle(build.Bit, build.Bit.getColor()));
        Title.setHeight(back.getHeight() / 16);
        Title.setPosition(back.getX() + Title.getHeight(), back.getY() + back.getHeight() - Title.getHeight() * 2);

        group.addActor(Title);

        final Table Blocks=new Table();
        Blocks.top();
        ScrollPane pane=new ScrollPane(Blocks, new ScrollPane.ScrollPaneStyle());
        pane.setSize(back.getWidth(), back.getHeight() / 8 * 7 - Title.getHeight() * 2);
        pane.setPosition(back.getX(), back.getY() + back.getHeight() / 8);
        pane.setScrollingDisabled(true, false);//设置是否可上下、左右移动..这里设置了横向可移动、纵向不可移动.

        group.addActor(pane);

        Table Button=new Table();
        Button.setSize(back.getWidth(), back.getHeight() / 8);
        Button.setPosition(back.getX(), back.getY());
        Label.LabelStyle a=new Label.LabelStyle(build.Bit, build.Bit.getColor());
        a.background = new TextureRegionDrawable(GetData.GetWhiteTexture());
        a.fontColor = Color.BLACK;
        Label Add=new Label("添加", a);
        Add.setAlignment(Align.center);
        Label Remove=new Label("移除", a);
        Remove.setAlignment(Align.center);
        Button.left();
        Button.add(Add).size(back.getWidth() / 3, back.getHeight() / 8 * 2 / 3).padLeft(back.getWidth() / 9).padBottom(20);
        Button.add(Remove).size(back.getWidth() / 3, back.getHeight() / 8 * 2 / 3).padLeft(back.getWidth() / 9).padBottom(20);
        Add.addListener(new InputListener(){
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                    Blocks.clearChildren();
                    Blocks block=new Blocks();
                    list.add(block);
                    // Collections.reverse(list);
                    build.view.UpDataBlock();
                    BuildList(back, Blocks);
                    return true;
                }
            });
        Remove.addListener(new InputListener(){
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    if (checked != null&&list.size()>1) {
                        Blocks.clearChildren();
                        
                        list.remove(checked.getUserObject());
                        build.view.UpDataBlock();
                       // build.SetBlock(list.indexOf(checked.getUserObject())+1);
                        build.SetBlock(1);
                        
                        BuildList(back, Blocks);
                    }
                    return true;
                }
            });





        group.addActor(Button);
        BuildList(back, Blocks);
        build.WorldStage.addActor(group);
    }
    private void BuildList(Image back, Table Blocks) {
        for (int i=0;i < list.size();i++) {

            Label block=new Label(Integer.toString(i + 1) + "：" + "  Block", new Label.LabelStyle(build.Bit, build.Bit.getColor()));
            //bpm.setFontScale(back.getHeight()/16/build.BpmNumber.getHeight());
            block.setWidth(back.getWidth());
            block.setUserObject(list.get(i));
            if(i+1==GetData.NowBlock){
                block.setColor(Color.CYAN);
                checked=block;
                }
                
            AddListen(block);

            Blocks.add(block).padTop(20);
            Blocks.row();



        }
    }
    public void AddListen(final Actor actor) {
        actor.addListener(new InputListener(){
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    actor.setColor(Color.CYAN);
                    if (checked != null && checked != actor) {
                        checked.setColor(Color.WHITE);
                        
                        checked = null;
                    }
                    checked = actor;
                    build.SetBlock(list.indexOf(checked.getUserObject())+1);
                    
                    return true;
                }
            });
    }

}
