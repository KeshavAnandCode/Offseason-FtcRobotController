package org.firstinspires.ftc.teamcode.Teleop.BehindTheScenes.Singletons;

import com.acmerobotics.dashboard.config.Config;

@Config
public class IntakeServo {
    private static final IntakeServo instance = new IntakeServo();

    private IntakeServo(){}

    public static IntakeServo getInstance(){
        return instance;
    }
    public static double TOP_POSITION = 0.8;
    public static double PIXELS_4_5 = 0.6;
    public static double PIXELS_3_4 = 0.4;
    public static double PIXELS_2_3 = 0.2;
    public static double BOTTOM_POSITION = 0;

    public void SetPosition (Robot robot, String position){
        if (position == "TOP"){
            robot.stack.setPosition(TOP_POSITION);
        }
        if (position == "4/5"){
            robot.stack.setPosition(PIXELS_4_5);
        }
        if (position == "3/4"){
            robot.stack.setPosition(PIXELS_3_4);
        }
        if (position == "2/3"){
            robot.stack.setPosition(PIXELS_2_3);
        }
        if (position == "BOTOM"){
            robot.stack.setPosition(BOTTOM_POSITION);
        }

    }





}
