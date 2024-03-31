package org.firstinspires.ftc.teamcode.TeleopTuner.BehindTheScenes;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;

@Disabled
public class Localizer_3Wheel {
    public static int currentOdoLeftPosition = 0;
    public static int currentOdoRightPosition = 0;
    public static int currentOdoSidePosition = 0;

    public static int oldOdoLeftPosition = 0;
    public static int oldOdoRightPosition = 0;
    public static int oldOdoSidePosition = 0;
    public static double X = 0;
    public static double Y = 0;
    public static double HEADING = 0;

    public static Coordinate_3D START_POS = new Coordinate_3D(0,0,Math.toRadians(0));
    public static Coordinate_3D position = new Coordinate_3D(START_POS);


    public static void getPosition() {
       oldOdoSidePosition = currentOdoSidePosition;
       oldOdoLeftPosition = currentOdoLeftPosition;
       oldOdoRightPosition = currentOdoRightPosition;

       currentOdoLeftPosition  = Odometry_3Wheel.ODO_LEFT.getCurrentPosition();
       currentOdoRightPosition  = Odometry_3Wheel.ODO_RIGHT.getCurrentPosition();
       currentOdoSidePosition  = Odometry_3Wheel.ODO_SIDE.getCurrentPosition();

       int deltaLeft = currentOdoLeftPosition - oldOdoLeftPosition;
       int deltaRight = currentOdoRightPosition - oldOdoRightPosition;
       int deltaSide = currentOdoSidePosition - oldOdoSidePosition;

       double deltaTheta = Odometry_3Wheel.IN_PER_TICK * (deltaRight-deltaLeft) / Odometry_3Wheel.LATERAL_DISTANCE;
       double deltaX = Odometry_3Wheel.IN_PER_TICK*(deltaLeft+deltaRight) / 2.0;
       double deltaY = Odometry_3Wheel.IN_PER_TICK * (deltaSide - (deltaRight-deltaLeft) * Odometry_3Wheel.FORWARD_OFFSET / Odometry_3Wheel.LATERAL_DISTANCE );

       double theta = position.heading + (deltaTheta/ 2.0);
       position.x += deltaX * Math.cos(theta) - deltaY * Math.sin(theta);
       position.y += deltaX * Math.sin(theta) + deltaY * Math.cos(theta);
       position.heading += deltaTheta;

    }
    public static void setPosition(Coordinate_3D pos) {
        position.x = pos.x;
        position.y = pos.y;
        position.heading = pos.heading;
    }
}