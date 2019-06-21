package com.example.Exercise4;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;


public class JoystickView extends SurfaceView implements SurfaceHolder.Callback, View.OnTouchListener {

    // (centerX, centerY) is the center of the inner circle of the joystick
    private float centerX;
    private float centerY;
    // the radius of the inner circle of the joystick
    private float smallRadius;
    // the center of the outer circle of the joystick
    private float bigRadius;
    // set commands to send to the simulator
    private String aileronSetCommand = "set controls/flight/aileron ";
    private String elavatorSetCommand = "set controls/flight/elevator ";

    // Constructors
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

    // initialize centerX, centerY, bigRadius and smallRadius values
    private void initializeViewSettings() {
        centerX = getWidth() / 2;
        centerY = getHeight() / 2;
        bigRadius  = Math.min(getWidth(), getHeight()) / 3;
        smallRadius = Math.min(getWidth(), getHeight()) / 12;
    }

    // draw the joystick
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

    /*
     * when a touch event is dispatched to the view, if the touch is within the
     * boundaries of the joystick - redraw the joystick and send set commands to the simulator
     */
    @Override
    public boolean onTouch(View v, MotionEvent motionEvent) {
        float newX = motionEvent.getX();
        float newY = motionEvent.getY();
        if(motionEvent.getAction() != MotionEvent.ACTION_UP) {
            // calculate the distance between the center of the inner circle of the
            // joystick and the new point
            float distance = (float)Math.sqrt((Math.pow(newX-centerX,2)) +
                    Math.pow(newY-centerY,2));
            // if the touch is within the boundaries of the joystick
            if(distance <= bigRadius) {
                // redraw the joystick
                DrawJoystick(newX, newY);
                float aileronValue = (newX - centerX) / bigRadius;
                float elevatorValue = (newY - centerY) / bigRadius;
                // send set commands to the new position to the simulator
                ClientSide.getInstance().SendCommandsToSimulator(aileronSetCommand + aileronValue + "\r\n");
                ClientSide.getInstance().SendCommandsToSimulator(elavatorSetCommand + elevatorValue + "\r\n");
            }
        }
        // return the inner circle of the joystick to the original center
        else {
            // redraw the joystick
            DrawJoystick(centerX, centerY);
            // send set commands to the original center (0,0) to the simulator
            ClientSide.getInstance().SendCommandsToSimulator(aileronSetCommand + 0 + "\r\n");
            ClientSide.getInstance().SendCommandsToSimulator(elavatorSetCommand + 0 + "\r\n");        }
        return true;
    }

    // when the surface is created, initialize the view settings and redraw the joystick
    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        initializeViewSettings();
        DrawJoystick(centerX, centerY);
    }

    // when the surface changes, initialize the view settings and redraw the joystick
    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int format, int width, int high) {
        initializeViewSettings();
        DrawJoystick(centerX, centerY);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
    }

}