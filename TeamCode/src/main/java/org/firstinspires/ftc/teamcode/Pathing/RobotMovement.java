package org.firstinspires.ftc.teamcode.Pathing;

import static org.firstinspires.ftc.teamcode.Pathing.Util.MathFunctions.AngleWrap;
import static org.firstinspires.ftc.teamcode.Pathing.Util.MathFunctions.lineCircleIntersection;
import static org.firstinspires.ftc.teamcode.Pathing.Util.MovementVars.movement_turn;
import static org.firstinspires.ftc.teamcode.Pathing.Util.MovementVars.movement_x;
import static org.firstinspires.ftc.teamcode.Pathing.Util.MovementVars.movement_y;
import static org.firstinspires.ftc.teamcode.Pathing.Util.RobotMovementUtil.worldAngle_rad;
import static org.firstinspires.ftc.teamcode.Pathing.Util.RobotMovementUtil.worldXPosition;
import static org.firstinspires.ftc.teamcode.Pathing.Util.RobotMovementUtil.worldYPosition;

import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.Pathing.Util.CurvePoint;
import org.firstinspires.ftc.teamcode.Pathing.Util.MathFunctions;
import org.opencv.core.Point;

import java.util.ArrayList;


public class RobotMovement {

    public static void followCurve(ArrayList<CurvePoint> allPoints, double followAngle){

        CurvePoint followMe = getFollowPointPath(allPoints,new Point(worldXPosition, worldYPosition),allPoints.get(0).followDistance);

        goToPosition(followMe.x,followMe.y, followMe.moveSpeed,followAngle,followMe.turnSpeed);
    }

    public static CurvePoint getFollowPointPath(ArrayList<CurvePoint> pathPoints, Point robotLocation, double followRadius){
        CurvePoint followMe = new CurvePoint(pathPoints.get(0));

        for (int i = 0; i< pathPoints.size() -1; i++){
            CurvePoint startLine = pathPoints.get(i);
            CurvePoint endLine = pathPoints.get(i+1);

            ArrayList<Point> intersections = lineCircleIntersection(robotLocation,followRadius, startLine.toPoint(),
                    endLine.toPoint());

            double closestAngle = 10000000;

            for (Point thisIntersecion : intersections){
                double angle = Math.atan2(thisIntersecion.y - worldYPosition, thisIntersecion.x - worldXPosition);
                double deltaAngle = Math.abs(MathFunctions.AngleWrap(angle - worldAngle_rad));

                if(deltaAngle<closestAngle){
                    closestAngle = deltaAngle;
                    followMe.setPoint(thisIntersecion);
                }
            }
        }
        return followMe;
    }



    /**
     *
     * @param x
     * @param y
     * @param movementSpeed
     * @param preferredAngle
     * @param turnSpeed
     */
    public static void goToPosition(double x, double y, double movementSpeed, double preferredAngle, double turnSpeed){

        double distanceToTarget = Math.hypot(x-worldXPosition, y-worldYPosition);
        double absoluteAngleToTarget = Math.atan2(y-worldYPosition, x-worldXPosition);
        double relativeAngleToPoint = AngleWrap(absoluteAngleToTarget - (worldAngle_rad - Math.toRadians(90)));

        double relativeXToPoint = Math.cos(relativeAngleToPoint) * distanceToTarget;
        double relativeYToPoint = Math.sin(relativeAngleToPoint) * distanceToTarget;

        double movementXPower = relativeXToPoint/ (Math.abs(relativeXToPoint) + Math.abs(relativeYToPoint));
        double movementYPower = relativeYToPoint/ (Math.abs(relativeXToPoint) + Math.abs(relativeYToPoint));

        movement_x = movementXPower * movementSpeed;
        movement_y = movementYPower * movementSpeed;

        double relativeTurnAngle = relativeAngleToPoint - Math.toRadians(180) + preferredAngle;

        movement_turn = Range.clip(relativeTurnAngle/Math.toRadians(30), -1,1) * turnSpeed;

        if (distanceToTarget < 10){
            movement_turn = 0;
        }

    }
}
