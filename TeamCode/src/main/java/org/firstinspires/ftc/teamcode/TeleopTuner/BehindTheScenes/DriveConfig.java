package org.firstinspires.ftc.teamcode.TeleopTuner.BehindTheScenes;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class DriveConfig {

    //TODO: Change the motor declaration ID's

    public static final List<DcMotorEx> MOTOR_IDS =new ArrayList<DcMotorEx>(){{
        add(hardwareMap.get(DcMotorEx.class,""));
        //TODO: DO NOT CHANGE/DELETE THE ABOVE LINE. IT IS USED FOR INDEX CONVENIENCE
        //Also, do not change the order of the drivetrain motors. This ia very important.

        add(hardwareMap.get(DcMotorEx.class, "replaceThis1"));
        //front left motor ID
        add(hardwareMap.get(DcMotorEx.class, "replaceThis2"));
        //front right motor ID
        add(hardwareMap.get(DcMotorEx.class, "replaceThis3"));
        //back left motor ID
        add(hardwareMap.get(DcMotorEx.class, "replaceThis4"));
        //back right motor ID

        //TODO: Add any more motors to this list if you have them
        //Example: add(hardwareMap.get(DcMotorEx.class, "servoArm"));
    }};

    public static final List<String> MOTOR_NAMES = new ArrayList<String>(){{
        add("");
        //TODO: DO NOT DELETE, CHANGE, OR MODIFY THE ABOVE LINE. IT IS USED FOR INDEXING
        add("frontLeftMotor");
        add("frontRightMotor");
        add("backLeftMotor");
        add("backRightMotor");
        //FEEL FREE TO CHANGE THESE MOTOR NAMES
        //TODO: Add any more motors to this list if you have them.
        // This list must be the same size as the list above and the order must correspond

        //Example: add("servoArm");

    }};


    //TODO: Add more motor variables if needed
    // You MUST name them MOTOR_N, where N is the motor number

    public static final DcMotorEx MOTOR_1 = MOTOR_IDS.get(1);
    public static final DcMotorEx MOTOR_2 = MOTOR_IDS.get(2);
    public static final DcMotorEx MOTOR_3 = MOTOR_IDS.get(3);
    public static final DcMotorEx MOTOR_4 = MOTOR_IDS.get(4);

    //Example: public static final DcMotorEx MOTOR_5 = MOTOR_IDS.get(5);

    //TODO: If you are using encoders for your drivetrain motors or any other motor, declare them here
    // You MUST name them ENCODER_N, where N corresponds with the motor number above
    //These encoders do not need to be plugged into the same slot as the corresponding motor

    //Example 1: public static final DcMotorEx ENCODER_1 = MOTOR_IDS.get(1);
    //Example 2: puplic static final DcMotorEx ENCODER_2 = MOTOR_IDS.get(7);

    //TODO: If you have odometry pods, declare the below. 
    //YOU CANNOT CHANGE THE NAME OF THE ODO PODS
    //The 2/3 names for your odo pods are:
    //ODO_LEFT, ODO_RIGHT, ODO_SIDE

    //Example: public static final DcMotorEx ODO_LEFT = MOTOR_IDS.get(6);
    

}
