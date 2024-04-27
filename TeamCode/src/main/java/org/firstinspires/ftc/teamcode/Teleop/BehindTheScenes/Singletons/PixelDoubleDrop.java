package org.firstinspires.ftc.teamcode.Teleop.BehindTheScenes.Singletons;

public class PixelDoubleDrop {
    private static final PixelDoubleDrop instance = new PixelDoubleDrop();

    private PixelDoubleDrop(){}

    public static PixelDoubleDrop getInstance(){
        return instance;
    }

    public boolean drop(Robot robot, double timeStamp, double runtime, boolean override){

        double elapsedTime = timeStamp - runtime;

        if (elapsedTime<0.5){
            robot.pixelOut.setPosition(0);
        } else if (elapsedTime < 1) {
            robot.pixelIn.setPower(0.77);
        }  else {
            robot.pixelIn.setPower(0);
            return false;
        }
        return !override;


    }

}
