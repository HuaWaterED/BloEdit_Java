package com.LemonSpirit.BloEdit.Interface;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.LemonSpirit.BloEdit.WorldBuild;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.graphics.Pixmap;
import com.LemonSpirit.BloEdit.NoteActor;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.LemonSpirit.BloEdit.DataHand.squareEvent;
import com.LemonSpirit.BloEdit.GetData;

public class BuildSetActions {
    WorldBuild build;
    BuildSpeed BuildSpeed;
    float StartTime=-1;


    public BuildSetActions(final WorldBuild build, final NoteActor note) {
        this.build = build;
        final Group group=new Group();

        Image BlackBack=new Image(GetData.GetBlackTexture());
        BlackBack.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        BlackBack.setPosition(0, 0);
        BlackBack.setColor(0, 0, 0, 0);

        group.addActor(BlackBack);
        Image back=new Image(GetData.GetBlackTexture());
        back.setColor(0, 0, 0, 0.5f);
        back.setSize(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() * 7 / 10);
        back.setPosition((Gdx.graphics.getWidth() - back.getWidth()) / 2, (Gdx.graphics.getHeight() - back.getHeight()) / 2);
        group.addActor(back);

        Table edit=new Table();
        edit.left();
        ScrollPane pane=new ScrollPane(edit, new ScrollPane.ScrollPaneStyle());

        pane.setSize(back.getWidth(), back.getHeight() - back.getHeight() / 6 * 2 / 3);
        pane.setPosition(back.getX(), back.getY() + back.getHeight() / 6 * 2 / 3);

        BitmapFont Bit=build.Bit;

        TextField.TextFieldStyle style=new TextField.TextFieldStyle();
        style.background = new TextureRegionDrawable(createBackgroundTexture(back));

        // 设置光标纹理区域
        style.cursor = new TextureRegionDrawable(createCursorTexture(back));

        // 设置文本框显示文本的字体来源
        style.font = Bit;

        // 设置文本框字体颜色为白色
        style.fontColor = new Color(1, 1, 1, 1);
        final Label StartLabel=new Label("开始值:", new Label.LabelStyle(Bit, Bit.getColor()));
        edit.add(StartLabel).left().padLeft(20).padTop(20);
        edit.row();

        final TextField StartField=new TextField("", style);
        edit.add(StartField).width(back.getWidth() - 40).height(back.getHeight() / 8).padLeft(20);
        edit.row();

        final Label EndLable=new Label("结束值:", new Label.LabelStyle(Bit, Bit.getColor()));
        edit.add(EndLable).left().padLeft(20).padTop(20);
        edit.row();

        final TextField EndField=new TextField("", style);
        edit.add(EndField).width(back.getWidth() - 40).height(back.getHeight() / 8).padLeft(20);
        edit.row();

        final Label TimeLable=new Label("持续时间:", new Label.LabelStyle(Bit, Bit.getColor()));
        edit.add(TimeLable).left().padLeft(20).padTop(20);
        edit.row();

        final TextField TimeField=new TextField("", style);
        edit.add(TimeField).width(back.getWidth() - 40).height(back.getHeight() / 8).padLeft(20);
        edit.row();

        final Label TypeLable=new Label("非线性种类:", new Label.LabelStyle(Bit, Bit.getColor()));
        edit.add(TypeLable).left().padLeft(20).padTop(20);
        edit.row();

        final TextField TypeField=new TextField("", style);
        edit.add(TypeField).width(back.getWidth() - 40).height(back.getHeight() / 8).padLeft(20);
        edit.row();

        Label.LabelStyle a=new Label.LabelStyle(build.Bit, build.Bit.getColor());
        a.background = new TextureRegionDrawable(GetData.GetWhiteTexture());
        a.fontColor = Color.BLACK;


        Label AButton;
        Label BButton = null;
        if (note != null) {
            StartField.setText(Float.toString(note.StartValue));
            EndField.setText(Float.toString(note.EndValue));
            TimeField.setText(Float.toString(note.GetHoldTime()));
            TypeField.setText(Integer.toString(note.easingType));
            AButton = new Label("持续动作", a);
            AButton.setAlignment(Align.center);

            BButton = new Label("瞬时动作", a);
            BButton.setAlignment(Align.center);

            BButton.addListener(new InputListener(){
                    @Override

                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        try {
                            GetData.SaveData.RemoveData(note,GetData.NowPlane,GetData.NowBlock);
                            note.StartValue = Float.valueOf(StartField.getText());
                            note.EndValue = Float.valueOf(EndField.getText());
                            note.easingType = Integer.valueOf(TypeField.getText());
                            note.SetHoldTime(0);
                            GetData.SaveData.SaveNoteData(note,GetData.NowPlane,GetData.NowBlock);
                            
                        } catch (Exception e) {
                            new Toast("内容有误", build.Bit, build.WorldStage);

                        }
                        group.remove();
                        return true;
                    }
                });

        } else {
            AButton = new Label("确定", a);
            AButton.setAlignment(Align.center);
        }

        Table Down=new Table();
        Down.setSize(back.getWidth(), back.getHeight() / 8);
        Down.setPosition(back.getX(), back.getY());




        AButton.addListener(new InputListener(){
                @Override

                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    if (note != null) {
                        try {
                            GetData.SaveData.RemoveData(note,GetData.NowPlane,GetData.NowBlock);
                            note.StartValue = Float.valueOf(StartField.getText());
                            note.EndValue = Float.valueOf(EndField.getText());
                            note.easingType = Integer.valueOf(TypeField.getText());
                            note.SetHoldTime(Float.valueOf(TimeField.getText()));
                            
                            GetData.SaveData.SaveNoteData(note,GetData.NowPlane,GetData.NowBlock);
                            
                        } catch (Exception e) {
                            new Toast("内容有误", build.Bit, build.WorldStage);

                        }

                    } else {
                        try {
                            squareEvent SE =new squareEvent();
                            SE.startValue = Float.valueOf(StartField.getText());
                            SE.endValue = Float.valueOf(EndField.getText());
                            SE.startTime = build.NowTime;
                            SE.endTime = build.NowTime + Float.valueOf(TimeField.getText());
                            SE.easingType = Integer.valueOf(TypeField.getText());
                            GetData.SaveData.squareData.get(GetData.NowBlock - 1).judgeLineData[GetData.NowPlane - 1].speed.add(SE);
                            new BuildSpeed(build);
                        } catch (Exception e) {
                            new Toast("内容有误", build.Bit, build.WorldStage);

                        }

                    }
                    group.remove();
                    return true;
                }
            });

        if (note != null) {
            Down.add(AButton).size(back.getWidth() / 3, back.getHeight() / 8 * 2 / 3).padRight(back.getWidth() / 18);
            Down.add(BButton).size(back.getWidth() / 3, back.getHeight() / 8 * 2 / 3).padLeft(back.getWidth() / 18);
        } else {
            Down.add(AButton).size(back.getWidth() / 3, back.getHeight() / 8 * 2 / 3);

        }
        group.addActor(Down);



        group.addActor(pane);
        build.WorldStage.addActor(group);
    }
    private Texture createBackgroundTexture(Actor back) {
        Pixmap pixmap = new Pixmap((int)(back.getWidth() * 2 / 3), (int)(back.getHeight() / 8 / 6), Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.drawLine(1, (int)(back.getHeight() / 8 / 6) - 1, (int)(back.getWidth() * 2 / 3), (int)(back.getHeight() / 8 / 6) - 1);
        Texture texture = new Texture(pixmap);
        pixmap.dispose();
        return texture;
    }
    private Texture createCursorTexture(Actor back) {
        Pixmap pixmap = new Pixmap(1, (int)(back.getHeight() / 8 / 6) * 2 / 3, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        Texture texture = new Texture(pixmap);
        pixmap.dispose();
        return texture;
    }

}
