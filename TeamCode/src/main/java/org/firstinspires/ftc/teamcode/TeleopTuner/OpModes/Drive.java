package org.firstinspires.ftc.teamcode.TeleopTuner.OpModes;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.TeleopTuner.BehindTheScenes.ColorSensorConstants;
import org.firstinspires.ftc.teamcode.TeleopTuner.BehindTheScenes.Robot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
@Config
@TeleOp
public class Drive extends LinearOpMode {

    public static String CURVE = "LINEAR";
    public static double DEGREE = 1;
    public static String TELEMETRY = "COLOR_SENSORS";

    Robot robot;



    @Override

    public void runOpMode() throws InterruptedException {
        //Initialization Code Goes Here

        robot = new Robot(hardwareMap);
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());



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


            if (TELEMETRY=="MOTOR_POWER") {
                telemetry.addLine("FrontLeftPower: " + frontLeftPower);
                telemetry.addLine("FrontRightPower: " + frontRightPower);
                telemetry.addLine("BackLeftPower: " + backLeftPower);
                telemetry.addLine("BackLeftPower: " + backRightPower);


                telemetry.update();
            }

            robot.frontLeftMotor.setPower(frontLeftPower);
            robot.backLeftMotor.setPower(backLeftPower);
            robot.frontRightMotor.setPower(frontRightPower);
            robot.backRightMotor.setPower(backRightPower);


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
    
    public List<String> ColorSensors() {
        NormalizedRGBA colors = robot.color1.getNormalizedColors();
        NormalizedRGBA colors2 = robot.color2.getNormalizedColors();
        String colorIn1 = "Undefined";
        String colorIn2 = "Undefined";
        int numberInOne = 0;
        int numberInTwo = 0;
        String intendedColor = "";
        double colorSum = colors.red + colors.green + colors.blue;
        double colorSum2 = colors2.red + colors2.green + colors2.blue;
        boolean isPixelIn1 = false;
        boolean isPixelIn2 = false;
        double redPercentage = (100 * colors.red) / colorSum;
        double greenPercentage = (100 * colors.green) / colorSum;
        double bluePercentage = (100 * colors.blue) / colorSum;
        double redPercentage2 = (100 * colors2.red) / colorSum2;
        double greenPercentage2 = (100 * colors2.green) / colorSum2;
        double bluePercentage2 = (100 * colors2.blue) / colorSum2;
        if (((DistanceSensor) robot.color1).getDistance(DistanceUnit.INCH) < 0.7) {
            isPixelIn1 = true;
        }
        if (
                (isPixelIn1) &&
                        (greenPercentage > ColorSensorConstants.WHITE_G - 3) && (greenPercentage < ColorSensorConstants.WHITE_G + 3) &&
                        (redPercentage > ColorSensorConstants.WHITE_R - 3) && (redPercentage < ColorSensorConstants.WHITE_R + 3) &&
                        (bluePercentage > ColorSensorConstants.WHITE_B - 3) && (bluePercentage > ColorSensorConstants.WHITE_B + 3)) {
            colorIn1 = "white!";
        }
        if (
                (isPixelIn1) &&
                        (greenPercentage > ColorSensorConstants.YELLOW_G - 3) && (greenPercentage < ColorSensorConstants.YELLOW_G + 3) &&
                        (redPercentage > ColorSensorConstants.YELLOW_R - 3) && (redPercentage < ColorSensorConstants.YELLOW_R + 3) &&
                        (bluePercentage > ColorSensorConstants.YELLOW_B - 3) && (bluePercentage > ColorSensorConstants.YELLOW_B + 3)) {
            colorIn1 = "yellow!";
        }
        if (
                (isPixelIn1) &&
                        (greenPercentage > ColorSensorConstants.PURPLE_G - 3) && (greenPercentage < ColorSensorConstants.PURPLE_G + 3) &&
                        (redPercentage > ColorSensorConstants.PURPLE_R - 3) && (redPercentage < ColorSensorConstants.PURPLE_R + 3) &&
                        (bluePercentage > ColorSensorConstants.PURPLE_B - 3) && (bluePercentage > ColorSensorConstants.PURPLE_B + 3)) {
            colorIn1 = "purple!";
        }
        if (
                (isPixelIn1) &&
                        (greenPercentage > ColorSensorConstants.GREEN_G - 3) && (greenPercentage < ColorSensorConstants.GREEN_G + 3) &&
                        (redPercentage > ColorSensorConstants.GREEN_R - 3) && (redPercentage < ColorSensorConstants.GREEN_R + 3) &&
                        (bluePercentage > ColorSensorConstants.GREEN_B - 3) && (bluePercentage > ColorSensorConstants.GREEN_B + 3)) {
            colorIn1 = "green!";
        }

        //Color sensor 2
        if (
                (isPixelIn2) &&
                        (greenPercentage2 > ColorSensorConstants.WHITE_G - 3) && (greenPercentage2 < ColorSensorConstants.WHITE_G + 3) &&
                        (redPercentage2 > ColorSensorConstants.WHITE_R - 3) && (redPercentage2 < ColorSensorConstants.WHITE_R + 3) &&
                        (bluePercentage2 > ColorSensorConstants.WHITE_B - 3) && (bluePercentage2 > ColorSensorConstants.WHITE_B + 3)) {
            colorIn2 = "white!";
        }
        if (
                (isPixelIn2) &&
                        (greenPercentage2 > ColorSensorConstants.YELLOW_G - 3) && (greenPercentage2 < ColorSensorConstants.YELLOW_G + 3) &&
                        (redPercentage2 > ColorSensorConstants.YELLOW_R - 3) && (redPercentage2 < ColorSensorConstants.YELLOW_R + 3) &&
                        (bluePercentage2 > ColorSensorConstants.YELLOW_B - 3) && (bluePercentage2 > ColorSensorConstants.YELLOW_B + 3)) {
            colorIn2 = "yellow!";
        }
        if (
                (isPixelIn2) &&
                        (greenPercentage2 > ColorSensorConstants.PURPLE_G - 3) && (greenPercentage2 < ColorSensorConstants.PURPLE_G + 3) &&
                        (redPercentage2 > ColorSensorConstants.PURPLE_R - 3) && (redPercentage2 < ColorSensorConstants.PURPLE_R + 3) &&
                        (bluePercentage2 > ColorSensorConstants.PURPLE_B - 3) && (bluePercentage2 > ColorSensorConstants.PURPLE_B + 3)) {
            colorIn2 = "purple!";
        }
        if (
                (isPixelIn2) &&
                        (greenPercentage2 > ColorSensorConstants.GREEN_G - 3) && (greenPercentage2 < ColorSensorConstants.GREEN_G + 3) &&
                        (redPercentage2 > ColorSensorConstants.GREEN_R - 3) && (redPercentage2 < ColorSensorConstants.GREEN_R + 3) &&
                        (bluePercentage2 > ColorSensorConstants.GREEN_B - 3) && (bluePercentage2 > ColorSensorConstants.GREEN_B + 3)) {
            colorIn2 = "green!";
        }


        if (isPixelIn1 == true) {
            numberInOne = 1;
        } else {
            numberInOne = 0;
        }

        if (isPixelIn2 == true) {
            numberInTwo = 1;
        } else {
            numberInTwo = 0;
        }
        if (numberInTwo + numberInOne == 0) {
            intendedColor = "red";
        }
        if (numberInTwo + numberInOne == 1) {
            intendedColor = "yellow";
        }
        if (numberInTwo + numberInOne == 2) {
            intendedColor = "green";
        }


        if (TELEMETRY == "COLOR_SENSORS") {
            telemetry.addData("Pixel in Slot 1: ", isPixelIn1);
            telemetry.addData("Pixel Color in Slot 1: ", colorIn1);
            telemetry.addData("distance", ((DistanceSensor) robot.color1).getDistance(DistanceUnit.INCH));
            telemetry.addLine()
                    .addData("Red", "%.3f", redPercentage)
                    .addData("Green", "%.3f", greenPercentage)
                    .addData("Blue", "%.3f", bluePercentage);
            //COlor senosr 2
            telemetry.addData("Pixel in Slot 2: ", isPixelIn2);
            telemetry.addData("Pixel Color in Slot 2: ", colorIn2);
            telemetry.addData("distance", ((DistanceSensor) robot.color2).getDistance(DistanceUnit.INCH));
            telemetry.addLine()
                    .addData("Red", "%.3f", redPercentage2)
                    .addData("Green", "%.3f", greenPercentage2)
                    .addData("Blue", "%.3f", bluePercentage2);
            telemetry.update();
        }
        List<String> returner = new ArrayList<>();
        returner.add(colorIn1);
        returner.add(colorIn2);
        returner.add(intendedColor);
        return returner;
    }
}