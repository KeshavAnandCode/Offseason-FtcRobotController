package org.firstinspires.ftc.teamcode.Autonomous;

import org.firstinspires.ftc.teamcode.Teleop.BehindTheScenes.Singletons.Robot;

import java.util.ArrayList;
import java.util.List;

public class Localizer {

    private static final Localizer instance = new Localizer();

    private Localizer(){}

    public static Localizer getInstance(){
        return instance;
    }

    public List<Double> pos (Robot robot, double prevX, double prevY, double prevHeading, double prevLeft, double prevRight, double prevSide){

        List<Double> returner = new ArrayList<>();

        double deltaLeft = toInches(robot.leftOdo.getCurrentPosition())-prevLeft;
        double deltaRight = toInches(robot.rightOdo.getCurrentPosition())-prevRight;
        double deltaSide = toInches(robot.sideOdo.getCurrentPosition())-prevSide;

        returner.add(deltaLeft);
        returner.add(deltaRight);
        returner.add(deltaSide);

        double rawX = (deltaLeft+deltaRight)/2;
        double rawHeading = AngleWrap(((deltaRight-deltaLeft)/LocalizerConstants.LATERAL_DISTANCE));
        double rawY = deltaRight - (rawHeading * 2 * LocalizerConstants.FORWARD_OFFSET);

        double finalHeading = prevHeading+rawHeading;
        double finalX = prevX + rawX*Math.cos(finalHeading) - rawY*Math.sin(finalHeading);
        double finalY = prevY + rawX*Math.sin(finalHeading) +rawY*Math.cos(finalHeading);

        returner.add(finalX);
        returner.add(finalY);
        returner.add(finalHeading);

        return returner;







    }
    private double toInches (double input){
        return ((Math.PI*2*LocalizerConstants.WHEEL_RADIUS*input)/LocalizerConstants.TICKS);
    }

    private double AngleWrap (double input){
        return (input%(2*Math.PI));
    }
}
