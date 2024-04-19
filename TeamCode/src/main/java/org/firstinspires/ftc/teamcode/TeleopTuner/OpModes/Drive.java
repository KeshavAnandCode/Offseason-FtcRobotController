package org.firstinspires.ftc.teamcode.TeleopTuner.OpModes;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.TeleopTuner.BehindTheScenes.Robot;

import java.util.Objects;
@Config
@TeleOp
public class Drive extends LinearOpMode {

    public static String CURVE = "LINEAR";
    public static double DEGREE = 2;

    Robot robot;


    GamepadEx g1 = new GamepadEx(gamepad1);
    GamepadEx g2 = new GamepadEx(gamepad2);


    @Override

    public void runOpMode() throws InterruptedException {
        //Initialization Code Goes Here

        robot = new Robot(hardwareMap);


        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {
            //Teleop Code Goes Here



            double rx = JoystickCurve(g1.getLeftX());
            double x = JoystickCurve(g1.getRightX());
            double y = JoystickCurve(g1.getRightY());

            double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
            double frontLeftPower = (y + x + rx) / denominator;
            double backLeftPower = (y - x + rx) / denominator;
            double frontRightPower = (y - x - rx) / denominator;
            double backRightPower = (y + x - rx) / denominator;



            telemetry.addLine("FrontLeftPower: "+frontLeftPower);
            telemetry.addLine("FrontRightPower: "+frontRightPower);
            telemetry.addLine("BackLeftPower: "+backLeftPower);
            telemetry.addLine("BackLeftPower: "+backRightPower);


            telemetry.update();

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
}