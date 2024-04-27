package org.firstinspires.ftc.teamcode.Teleop.BehindTheScenes.Singletons;

public class Intaking {
    private static final Intaking instance = new Intaking();

    private Intaking(){}

    public static Intaking getInstance(){
        return instance;
    }

    public void intake(Robot robot, String pixelIn1, String pixelIn2, boolean reverse){
        if(!reverse) {
            robot.pixelIn.setPower(1);
            robot.pixelOut.setPosition(.8);
            robot.intakeRotate.setPower(-0.1);
            robot.intakeMove.setPower(0);
            if(pixelIn1=="true"&&pixelIn2=="true"){
                robot.intakeMotor.setPower(-0.42);
            } else {
                robot.intakeMotor.setPower(0.9);
            }
        } else {
            robot.pixelOut.setPosition(.8);
            robot.intakeMotor.setPower(-0.8);
            robot.pixelIn.setPower(1);
            robot.intakeRotate.setPower(-0.1);
            robot.intakeMove.setPower(0);
        }


    }

}
