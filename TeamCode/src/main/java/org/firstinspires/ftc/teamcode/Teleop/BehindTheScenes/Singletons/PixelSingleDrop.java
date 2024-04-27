package org.firstinspires.ftc.teamcode.Teleop.BehindTheScenes.Singletons;

public class PixelSingleDrop {
    private static final PixelSingleDrop instance = new PixelSingleDrop();

    private PixelSingleDrop(){}

    public static PixelSingleDrop getInstance(){
        return instance;
    }

    public boolean drop(Robot robot, double timeStamp, double runtime, boolean override){

        double elapsedTime = timeStamp - runtime;

        if (elapsedTime<0.2){
            robot.pixelOut.setPosition(0);
        } else if (elapsedTime < 0.4) {
            robot.pixelOut.setPosition(0.8);
        } else if (elapsedTime < 0.7) {
            robot.pixelIn.setPower(0.77);
        } else {
            if (override){
                robot.pixelOut.setPosition(0);
            }
            return false;
        }

        return true;


    }
}
