package org.firstinspires.ftc.teamcode.TeleopTuner.BehindTheScenes;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import java.util.ArrayList;
import java.util.List;
@Disabled
public class MotorConfig {

    //TODO: Update the below string based on your odometry pods
    // Your options are "none", "two", or "three", depending on your odo pod setup
    public static final String ODO_TYPE = "none";

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


    public static final List<DcMotorEx> ENCODER_IDS =new ArrayList<DcMotorEx>(){{
        add(hardwareMap.get(DcMotorEx.class,""));
        //TODO: DO NOT CHANGE/DELETE THE ABOVE LINE. IT IS USED FOR INDEX CONVENIENCE
        //TODO: If you are using encoders for your drivetrain motors or any other motor, declare them here
        //These 8 lines of code correspond with the MOTOR_NAMES list
        //Let us say you plugged in the frontRightMotor encoder into the encoder slot corresponding with the                   backRightMotor
        //As the frontRightMotor is the 2th line in the MOTOR_NAMES list, you will declare the encoder in the 2nd line         of the ENCODER_IDS list
        //Replace the 2nd line with:
        //add(MOTOR_4);
        //you are doing MOTOR_4 because the encoder is plugged into the backRightMotor encoder slot, which is the 4th on the MOTOR_IDS list (and MOTOR_NAMES list)

        add(hardwareMap.get(DcMotorEx.class,"")); //Corresponds with MOTOR_NAMES.get(1)
        add(hardwareMap.get(DcMotorEx.class,"")); //The line for the example, Corresponds with MOTOR_NAMES.get(2)
        add(hardwareMap.get(DcMotorEx.class,"")); //Corresponds with MOTOR_NAMES.get(3)
        add(hardwareMap.get(DcMotorEx.class,"")); //Corresponds with MOTOR_NAMES.get(4)

        //TODO:Add the below lines if you have more than 4 motors
        //The size of this list should equal the size of the MOTOR_NAMES list and the MOTOR_IDS list

//        add(hardwareMap.get(DcMotorEx.class,"")); //Corresponds with MOTOR_NAMES.get(5)
//        add(hardwareMap.get(DcMotorEx.class,"")); //Corresponds with MOTOR_NAMES.get(6)
//        add(hardwareMap.get(DcMotorEx.class,"")); //Corresponds with MOTOR_NAMES.get(7)
//        add(hardwareMap.get(DcMotorEx.class,"")); //Corresponds with MOTOR_NAMES.get(8)

    }};

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
    


    //TODO: If you have odometry pods, declare the below.
    //YOU CANNOT CHANGE THE NAME OF THE ODO PODS
    //The 2/3 names for your odo pods are:
    //ODO_LEFT, ODO_RIGHT, ODO_SIDE

    //Example: public static final DcMotorEx ODO_LEFT = MOTOR_IDS.get(6);


}