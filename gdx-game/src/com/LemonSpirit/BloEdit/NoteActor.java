package com.LemonSpirit.BloEdit;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public class NoteActor extends Actor {
    public Texture NoteTexture;
    WorldBuild build;
    BitmapFont Bit;
    public float StartValue=0;
    public float EndValue=0;
    public float speed=1;
    public int flickDirection=-1;
    public boolean IsMoment=false;
    public boolean HaveWave=true;

    boolean Past=true;

    public int type;
    public int easingType=1;
    public NoteActor(WorldBuild build, int type , int flickDirection) {
        this.build = build;
        this.type = type;
        this.Bit = build.Bit;
        this.flickDirection = flickDirection;

        setSize(GetData.NoteWeight, GetData.NoteHeight);

        SetBackColor();
    }
    public float GetPosition() {
        return getX() / (GetData.LineWeight - GetData.NoteWeight);
    }
    public float GetScale() {
        return getWidth() / GetData.NoteWeight;
    }
    public float GetTime() {
        float number=getY() / GetData.LineHeight;
        float NowTime = build.setbpm.GetTime(number);
        return NowTime;

    }
    public float GetHoldTime() {
        if (IsMoment) {
            return 0;
        } else {
            return  build.setbpm.GetTime((getY() + getHeight()) / GetData.LineHeight) - GetTime();
        }
    }
    public void SetHoldTime(float time) {
        if (time == 0) {
            setSize(GetData.NoteWeight, GetData.NoteHeight);
            this.NoteTexture = GetData.NoteTexture;
            setColor(Color.CYAN);
            addAction(Actions.alpha(0.8f));
            IsMoment = true;
        } else if (time > 0) {
            setHeight(build.setbpm.GetLine(GetTime() + time) * GetData.LineHeight - getY());
            this.NoteTexture = GetData.LongTexture;
            setColor(Color.WHITE);
            addAction(Actions.alpha(1f));
            IsMoment=false;
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

        batch.setColor(getColor());
        float Down=-build.LineGroup.getY() - getHeight();
        float Up=-build.LineGroup.getY() + Gdx.graphics.getHeight();
        if (Down <= getY() && getY() <= Up) {
            batch.draw(NoteTexture, getX(), getY(),  getWidth(), getHeight());
            switch (type) {
                case 10:
                    Bit.draw(batch, "MoveX", getX(), getY());
                    break;
                case 11:
                    Bit.draw(batch, "MoveY", getX(), getY());
                    break;
                case 12:
                    Bit.draw(batch, "rotate", getX(), getY());
                    break;
                case 13:
                    Bit.draw(batch, "scale", getX(), getY());
                    break;
                case 14:
                    Bit.draw(batch, "alpha", getX(), getY());
                    break;

            }
        }
        super.draw(batch, parentAlpha);
    }
    public void SetBackColor() {
        switch (type) {
            case 0:
                this.NoteTexture = GetData.NoteTexture;
                setColor(Color.CYAN);
                addAction(Actions.alpha(0.8f));
                break;
            case 1:
                this.NoteTexture = GetData.NoteTexture;
                setColor(Color.YELLOW);
                addAction(Actions.alpha(0.8f));
                break;
            case 2:
                this.NoteTexture = GetData.LongTexture;
                setColor(Color.WHITE);
                break;
            case 3:
                this.NoteTexture = GetData.MiddleTexture;
                setSize(GetData.NoteWeight, GetData.NoteWeight);
                setColor(Color.WHITE);
                break;
            case 4:
                if (flickDirection == 1) {
                    this.NoteTexture = GetData.DraggedLTexture;
                } else {
                    this.NoteTexture = GetData.DraggedRTexture;
                }
                setSize(GetData.LineWeight, GetData.NoteHeight * 2);
                setColor(Color.WHITE);
                break;

        }
        if (type >= 10) {

            if (IsMoment) {
                this.NoteTexture = GetData.NoteTexture;
                setColor(Color.CYAN);
                addAction(Actions.alpha(0.8f));
            } else {
                this.NoteTexture = GetData.LongTexture;
                setColor(Color.WHITE);
            }
        }




    }

    @Override
    public void act(float delta) {
        PlaySound();
        super.act(delta);
    }

    public void PlaySound() {

        boolean Now=-build.LineGroup.getY() + GetData.DownLineHeight > getY();
        if (Now && !Past && build.IsPlay) {

            GetData.NoteSound.play(1.0f);

        }
        Past = Now;


    }

}
