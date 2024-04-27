package org.firstinspires.ftc.teamcode.Teleop.BehindTheScenes.Singletons;

public class Hang {

    private static final Hang instance = new Hang();

    private Hang(){}
    
    public static double INITIAL_LOOSEN_TIME = 2;
    public static double UP_TIME = 1;
    public static double TIGHTEN_TIME = 4;
    public static double SECOND_LOOSEN_TIME = 1;

    public static Hang getInstance(){
        return instance;
    }
    
    public boolean hang (Robot robot, double timestamp, double runtime) {
        double time = runtime-timestamp;
        if (time < INITIAL_LOOSEN_TIME) {
            robot.linActServo.setPower(1);
        } else if (time < INITIAL_LOOSEN_TIME+UP_TIME) {
            robot.linActServo.setPower(0);
            robot.linearActuator.setPower(1);
        } else if (time < TIGHTEN_TIME+INITIAL_LOOSEN_TIME+UP_TIME) {
            robot.linearActuator.setPower(0);
            robot.linActServo.setPower(-1);
        } else if (time < SECOND_LOOSEN_TIME+TIGHTEN_TIME+INITIAL_LOOSEN_TIME+UP_TIME) {
            robot.linActServo.setPower(1);
        } else {
            robot.linActServo.setPower(0);
            robot.linearActuator.setPower(0);
            return false;
        }
        return true;
    }
}
