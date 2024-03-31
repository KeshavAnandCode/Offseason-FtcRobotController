package org.firstinspires.ftc.teamcode.TeleopTuner.BehindTheScenes;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotorEx;

/*
 * 3 Wheel Odometry Assuming the following Configuration
 *
 *    /--------------\
 *    |     ____     |
 *    |     ----     |
 *    | ||        || |
 *    | ||        || |
 *    |              |
 *    |              |
 *    \--------------/
 *
 * The two parallel odo pods are called ODO_LEFT and ODO_RIGHT, where the left one is ODO_LEFT
 * The odo pod facing sideways is called ODO_SIDE
 *
 */
@Disabled
public class Odometry_3Wheel {

    //TODO: Replace the following constants
    public static final double LATERAL_DISTANCE = 13; //Distance between ODO_1 and ODO_2 in inches
    public static final double FORWARD_OFFSET = -3.56; //Distance between the (midpoint of ODO_1 and ODO_2) and ODO_SIDE
    public static final double TICKS = 8192; //Encoder Ticks per Revolution
    public static final double WHEEL_RADIUS = 2.3; //Radius of the dead wheel
    public static final double IN_PER_TICK = 2.0 * Math.PI * WHEEL_RADIUS / TICKS;

    public static final DcMotorEx ODO_LEFT = hardwareMap.get(DcMotorEx.class, "replaceThis1");
    public static final DcMotorEx ODO_RIGHT = hardwareMap.get(DcMotorEx.class, "replaceThis2");
    public static final DcMotorEx ODO_SIDE = hardwareMap.get(DcMotorEx.class, "replaceThis1");





}
