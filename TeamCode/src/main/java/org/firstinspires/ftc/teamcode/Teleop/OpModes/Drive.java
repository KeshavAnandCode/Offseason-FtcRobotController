package org.firstinspires.ftc.teamcode.Teleop.OpModes;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.gamepad.ButtonReader;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.Teleop.BehindTheScenes.ColorSensorConstants;
import org.firstinspires.ftc.teamcode.Teleop.BehindTheScenes.ColorSensors;
import org.firstinspires.ftc.teamcode.Teleop.BehindTheScenes.Robot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
@Config
@TeleOp
public class Drive extends LinearOpMode {

    public static String CURVE = "LINEAR";
    public static double DEGREE = 1;
    public static int TELEMETRY = 0;
    private static MultipleTelemetry tele;


    double g2XTimestamp = 0;

    Robot robot;









    @Override

    public void runOpMode() throws InterruptedException {

         tele = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        //Initialization Code Goes Here
        GamepadEx g2 = new GamepadEx(gamepad2);

        GamepadEx g1 = new GamepadEx(gamepad1);

        robot = new Robot(hardwareMap);

        ButtonReader g2X = new ButtonReader(
            g2, GamepadKeys.Button.X
        );




        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {
            //Teleop Code Goes Here



            double rx = JoystickCurve(gamepad1.left_stick_x);
            double x = JoystickCurve(gamepad1.right_stick_x);
            double y = JoystickCurve(-gamepad1.right_stick_y);

            double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
            double frontLeftPower = (y + x + rx) / denominator;
            double backLeftPower = (y - x + rx) / denominator;
            double frontRightPower = (y - x - rx) / denominator;
            double backRightPower = (y + x - rx) / denominator;


            if (TELEMETRY==1) {
                tele.addLine("FrontLeftPower: " + frontLeftPower);
                tele.addLine("FrontRightPower: " + frontRightPower);
                tele.addLine("BackLeftPower: " + backLeftPower);
                tele.addLine("BackLeftPower: " + backRightPower);
                tele.update();
            }

            if (TELEMETRY==0){
                tele.addLine("Use FTC Dash to select telmetry");
                tele.addLine("1 = Motor Power");
                tele.addLine("2 = Color Sensors");
                tele.update();
            }






            robot.frontLeftMotor.setPower(frontLeftPower);
            robot.backLeftMotor.setPower(backLeftPower);
            robot.frontRightMotor.setPower(frontRightPower);
            robot.backRightMotor.setPower(backRightPower);


            if (g2X.wasJustPressed()){
              g2XTimestamp = getRuntime();
            }
            g2X.readValue();
          
            double g2XReleaseTime = getRuntime() - g2XTimestamp;
 
            if (getRuntime()>=1&&g2XTimestamp>=0){
              if (g2XReleaseTime<0.2){
                robot.pixelOut.setPosition(0);
              } else if (g2XReleaseTime < 0.4) {
                robot.pixelOut.setPosition(0.8);
              } else if (g2XReleaseTime < 0.7) {
                robot.pixelIn.setPower(0.77);
              } else {
                robot.pixelIn.setPower(0);
                if (gamepad2.y){
                  robot.pixelOut.setPosition(0);
                }
                g2XTimestamp = -800000;
              }
            }
            List<String> colorOutput = ColorSensors.getInstance().colors(robot);
            lightsFunction(gamepad2,gamepad2.a, colorOutput);

            if (TELEMETRY==2){
                tele.addLine(colorOutput.get(3));
                tele.addLine(colorOutput.get(4));
                tele.addLine(colorOutput.get(5));
                tele.addLine(colorOutput.get(6)+", "+colorOutput.get(7)+ ", " + colorOutput.get(8));
                tele.addLine();
                tele.addLine(colorOutput.get(9));
                tele.addLine(colorOutput.get(10));
                tele.addLine(colorOutput.get(11));
                tele.addLine(colorOutput.get(12)+", "+colorOutput.get(13)+ ", " + colorOutput.get(14 ));

                tele.update();
            }

          

            

         
            


        }
    }
    public static double JoystickCurve (double input) {
        if (Objects.equals(CURVE, "LINEAR")){
            return input*DEGREE;
        } else if (Objects.equals(CURVE,"POWER")){
            double returner = Math.pow(input, DEGREE);
            if (input<0&&returner>0){
                returner*=-1;
            }
            return returner;
        } else if (Objects.equals(CURVE,"WIDE")){
            if (Math.abs(input)<0.5){
              double returner = Math.pow((2*input), DEGREE);
              returner = returner/2;
              if (input<0&&returner>0){
                returner *= -1;
              }
              return returner;
            } else {
              double returner = Math.pow(((2*Math.abs(input))-1),(1/DEGREE));
              returner = returner/2;
              returner +=0.5;
              if (input<0&&returner>0){
                returner*=-1;
              }
              return returner;
            }
        }
        return input;
    }
    

    public void lightsFunction (Gamepad gamepad, boolean intaking, List<String> colorSensorList){
        robot = new Robot(hardwareMap);
        
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