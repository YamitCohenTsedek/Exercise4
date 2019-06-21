package com.example.Exercise4;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import androidx.core.view.MotionEventCompat;


public class JoystickView extends SurfaceView implements SurfaceHolder.Callback, View.OnTouchListener {

    private float centerX;
    private float centerY;
    private float bigRadius;
    private float smallRadius;
    private Boolean isMouseMoving = false;
    private String aileronSetCommand = "set controls/flight/aileron ";
    private String elavatorSetCommand = "set controls/flight/elevator ";

    public JoystickView(Context context) {
        super(context);
        getHolder().addCallback(this);
        setOnTouchListener(this);
    }

    public JoystickView(Context context, AttributeSet attributes) {
        super(context, attributes);
        getHolder().addCallback(this);
        setOnTouchListener(this);
    }

    private void DrawJoystick(float newX, float newY) {
        if (getHolder().getSurface().isValid()) {
            Canvas myCanvas = this.getHolder().lockCanvas();
            myCanvas.drawRGB(153, 255, 204);
            Paint colors = new Paint();
            colors.setARGB(255, 153, 221, 255);
            myCanvas.drawCircle(centerX, centerY, bigRadius + 80, colors);
            colors.setARGB(204, 230,255,242);
            myCanvas.drawCircle(centerX, centerY, bigRadius, colors);
            colors.setARGB(255, 153, 221, 255);
            myCanvas.drawCircle(newX, newY, smallRadius, colors);
            getHolder().unlockCanvasAndPost(myCanvas);
        }
    }


    @Override
    public boolean onTouch(View v, MotionEvent motionEvent) {
        float newX = motionEvent.getX();
        float newY = motionEvent.getY();
        if(motionEvent.getAction() != MotionEvent.ACTION_UP) {
            float distance = (float)Math.sqrt((Math.pow(newX-centerX,2)) +
                    Math.pow(newY-centerY,2));
            if(distance <= bigRadius) {
                DrawJoystick(newX, newY);
                float aileronValue = (newX - centerX) / bigRadius;
                float elevatorValue = (newY - centerY) / bigRadius;
                ClientSide.getInstance().SendCommandsToSimulator(aileronSetCommand + aileronValue + "\r\n");
                ClientSide.getInstance().SendCommandsToSimulator(elavatorSetCommand + elevatorValue + "\r\n");
            }
        }
        else {
            DrawJoystick(centerX, centerY);
            ClientSide.getInstance().SendCommandsToSimulator(aileronSetCommand + 0 + "\r\n");
            ClientSide.getInstance().SendCommandsToSimulator(elavatorSetCommand + 0 + "\r\n");        }
        return true;
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        centerX = getWidth() / 2;
        centerY = getHeight() / 2;
        bigRadius  = Math.min(getWidth(), getHeight()) / 3;
        smallRadius = Math.min(getWidth(), getHeight()) / 12;
        DrawJoystick(centerX, centerY);
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int format, int width, int high) {
        centerX = getWidth() / 2;
        centerY = getHeight() / 2;
        bigRadius  = Math.min(getWidth(), getHeight()) / 3;
        smallRadius = Math.min(getWidth(), getHeight()) / 12;
        DrawJoystick(centerX, centerY);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
    }

}