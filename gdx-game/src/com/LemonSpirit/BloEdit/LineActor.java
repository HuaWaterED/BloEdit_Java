package com.LemonSpirit.BloEdit;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;


public class LineActor extends Actor {
    Texture map;
    WorldBuild build;
    boolean IsMiddle;
    BitmapFont Bit;
    float LineHeight;

    public LineActor(Texture map, WorldBuild build, boolean IsMiddle, BitmapFont Bit) {
        this.map = map;
        this.build = build;
        this.IsMiddle = IsMiddle;
        this.Bit = Bit;
        this.LineHeight = Bit.getData().lineHeight;

    }

    @Override
    public void act(float delta) {

        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (IsMiddle) {
            AddMiddleLine(batch);
        } else {
            AddRightLine(batch);
        }
        super.draw(batch, parentAlpha);
    }
    public void AddMiddleLine(Batch batch) {
        batch.setColor(getColor());
        int DownNumber=(int)(-build.LineGroup.getY() / (GetData.LineHeight / GetData.LineNumber));
        int UpNumber=(int)((-build.LineGroup.getY() + Gdx.graphics.getHeight()) / (GetData.LineHeight / GetData.LineNumber));
        int Middle=GetData.LineNumber / 2;
        for (int i=0;i <= UpNumber - DownNumber;i++) {
            int number=(DownNumber + i) % GetData.LineNumber;
            if (number == 0) {

                batch.setColor(Color.WHITE.r, Color.WHITE.g, Color.WHITE.b, 1f);

            } else if (number == Middle) {
                batch.setColor(Color.CYAN.r, Color.CYAN.g, Color.CYAN.b, 0.5f);
            } else {
                batch.setColor(Color.GREEN.r, Color.GREEN.g, Color.GREEN.b, 0.5f);

            }

            setSize(GetData.LineWeight, 2);
            setPosition(0, GetData.LineHeight / GetData.LineNumber * (DownNumber + i));

            batch.draw(map, getX(), getY(),  getWidth(), getHeight());

        }

        for (int i=0;i <= GetData.LineWeightUnit * 5;i++) {
            if (i == GetData.LineWeightUnit * 5 / 2) {
                batch.setColor(Color.PINK.r, Color.PINK.g, Color.PINK.b, 0.5f);

            } else {
                batch.setColor(Color.WHITE.r, Color.WHITE.g, Color.WHITE.b, 0.5f);

            }

            setSize(2, Gdx.graphics.getHeight());

            setPosition(((GetData.LineWeight - 2) / (GetData.LineWeightUnit * 5)) * i, -build.LineGroup.getY());
            batch.draw(map, getX(), getY(),  getWidth(), getHeight());

        }

    }
    public void AddRightLine(Batch batch) {
        batch.setColor(getColor());

        int DownNumber=(int)(-build.LeftLineGroup.getY() / (GetData.LineHeight / GetData.LineNumber)) - GetData.LineNumber;
        int UpNumber=(int)((-build.LeftLineGroup.getY() + Gdx.graphics.getHeight()) / (GetData.LineHeight / GetData.LineNumber)) + GetData.LineNumber;
        int Middle=GetData.LineNumber / 2;
        for (int i=0;i <= UpNumber - DownNumber;i++) {
            int number=(DownNumber + i) % GetData.LineNumber;
            if (number == 0) {
                batch.setColor(Color.WHITE.r, Color.WHITE.g, Color.WHITE.b, 1f);

                setSize(GetData.LeftRightWeight - GetData.LeftRightWeight / 10, 2);
                float LineY=GetData.LineHeight / GetData.LineNumber * (DownNumber + i) + 15 - GetData.LineHeight / 2;
                if (getY() > 0) {
                    Bit.draw(batch, Integer.toString((int)(getY() / (GetData.LineHeight))), (GetData.LeftRightWeight - GetData.LeftRightWeight / 10) / 4 + 5, LineY);
                }
            } else if (number == Middle) {
                batch.setColor(Color.CYAN.r, Color.CYAN.g, Color.CYAN.b, 0.5f);

                setSize((GetData.LeftRightWeight - GetData.LeftRightWeight / 10) / 4, 2);
            } else {
                batch.setColor(Color.GREEN.r, Color.GREEN.g, Color.GREEN.b, 0.5f);
                setSize((GetData.LeftRightWeight - GetData.LeftRightWeight / 10) / 4, 2);
            }
            setPosition(0, GetData.LineHeight / GetData.LineNumber * (DownNumber + i));

            batch.draw(map, getX(), getY(),  getWidth(), getHeight());


        }


    }

}
