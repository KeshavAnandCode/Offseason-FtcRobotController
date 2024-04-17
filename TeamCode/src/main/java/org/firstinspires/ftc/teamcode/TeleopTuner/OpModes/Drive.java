package org.firstinspires.ftc.teamcode.TeleopTuner.OpModes;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import java.util.Objects;
@Config
@TeleOp
public class Drive extends LinearOpMode {

    public static String CURVE = "LINEAR";
    public static double DEGREE = 2;
    @Override

    public void runOpMode() throws InterruptedException {
        //Initialization Code Goes Here
        DcMotor frontLeftMotor = hardwareMap.dcMotor.get("frontLeftMotor");
        DcMotor backLeftMotor = hardwareMap.dcMotor.get("backLeftMotor");
        DcMotor frontRightMotor = hardwareMap.dcMotor.get("frontRightMotor");
        DcMotor backRightMotor = hardwareMap.dcMotor.get("backRightMotor");

        backLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backRightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontRightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontLeftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {

            double rx = JoystickCurve(gamepad1.left_stick_x);
            double x = JoystickCurve(gamepad1.right_stick_x);
            double y = JoystickCurve(-gamepad1.right_stick_y);

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

            frontLeftMotor.setPower(frontLeftPower);
            backLeftMotor.setPower(backLeftPower);
            frontRightMotor.setPower(frontRightPower);
            backRightMotor.setPower(backRightPower);


            //Teleop Code Goes Here

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
              double returnner = Math.pow((2*input), DEGREE);
              returner = returnner/2;
              if (input<0&&returnner>0){
                returner *= -1;
              }
              return returnner;
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