package org.firstinspires.ftc.teamcode.Teleop.BehindTheScenes.Singletons;

import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.hardware.Gamepad;

import java.util.List;

public class Lights {

    private static final Lights instance = new Lights();

    private Lights(){}

    public static Lights getInstance(){
        return instance;
    }

    public void lightsFunction (Robot robot, Gamepad gamepad, boolean intaking, List<String> colorSensorList){

        if (gamepad.right_stick_x >= 0.5) {
            robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.YELLOW);
        } else if (gamepad.right_stick_x <= -0.5) {
            robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.VIOLET);
        } else if (gamepad.right_stick_y >= 0.5) {
            robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.GREEN);
        } else if (gamepad.right_stick_y <= -0.5) {
            robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.WHITE);
        } else if (intaking) {

            if (colorSensorList.get(2) == "red") {
                robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.DARK_RED);
            } else if (colorSensorList.get(2) == "yellow") {
                robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.YELLOW);
            } else if (colorSensorList.get(2) == "green") {
                robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.SKY_BLUE);
            }
        } else if (colorSensorList.get(0) == "white!") {
            robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.WHITE);
        } else if (colorSensorList.get(0) == "yellow!") {
            robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.ORANGE);
        } else if (colorSensorList.get(0) == "purple!") {
            robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.VIOLET);
        } else if (colorSensorList.get(0) == "green!") {
            robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.GREEN);
        } else {
            robot.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.COLOR_WAVES_PARTY_PALETTE);
        }
    }

}
