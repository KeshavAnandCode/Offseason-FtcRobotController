package org.firstinspires.ftc.teamcode.Teleop.OpModes;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.gamepad.ButtonReader;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Teleop.BehindTheScenes.Singletons.ColorSensors;
import org.firstinspires.ftc.teamcode.Teleop.BehindTheScenes.Singletons.GamepadJoystickCurve;
import org.firstinspires.ftc.teamcode.Teleop.BehindTheScenes.Singletons.Intaking;
import org.firstinspires.ftc.teamcode.Teleop.BehindTheScenes.Singletons.Lights;
import org.firstinspires.ftc.teamcode.Teleop.BehindTheScenes.Singletons.PixelDoubleDrop;
import org.firstinspires.ftc.teamcode.Teleop.BehindTheScenes.Singletons.PixelSingleDrop;
import org.firstinspires.ftc.teamcode.Teleop.BehindTheScenes.Singletons.Robot;

import java.util.List;
@Config
@TeleOp
public class Drive extends LinearOpMode {

    public static String CURVE = "POWER";
    public static double DEGREE = 2;
    public static int TELEMETRY = 0;
    public static int LINEARSLIDE_LOCK_POS =100;
    private static MultipleTelemetry tele;


    double singleDropTimestamp = 0;
    boolean singleDrop = false;
    double doubleDropTimestamp = 0;
    boolean doubleDrop = false;
    boolean linearSlideMode = false;
    boolean intakeLights = false;
    double intakeTimestamp = -90.0;
    public static double POSITION = 0.5;

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

        ButtonReader g2Y = new ButtonReader(
                g2, GamepadKeys.Button.Y
        );





        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {

            //Drivetrain:

            double turbo = 0.8 + 0.2 * GamepadJoystickCurve.JoystickCurve(gamepad1.right_trigger, "POWER", 2.0)
                    - 0.6 * GamepadJoystickCurve.JoystickCurve(gamepad1.left_trigger, "POWER", 2.0);


            double rx = GamepadJoystickCurve.JoystickCurve(gamepad1.left_stick_x,CURVE, DEGREE);
            double x = GamepadJoystickCurve.JoystickCurve(gamepad1.right_stick_x,CURVE, DEGREE);
            double y = GamepadJoystickCurve.JoystickCurve(-gamepad1.right_stick_y,CURVE, DEGREE);

            rx*=turbo;
            x*=turbo;
            y*=turbo;

            double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
            double frontLeftPower = (y + x + rx) / denominator;
            double backLeftPower = (y - x + rx) / denominator;
            double frontRightPower = (y - x - rx) / denominator;
            double backRightPower = (y + x - rx) / denominator;

            robot.frontLeftMotor.setPower(frontLeftPower);
            robot.backLeftMotor.setPower(backLeftPower);
            robot.frontRightMotor.setPower(frontRightPower);
            robot.backRightMotor.setPower(backRightPower);


            //Linear Slides:

            if (robot.linearSlideLeft.getCurrentPosition()>LINEARSLIDE_LOCK_POS) {
                linearSlideMode = true;
            } else {
                linearSlideMode = false;
            }

            if (gamepad2.right_trigger != 0) {
                robot.linearSlideLeft.setPower(gamepad2.right_trigger);
                robot.linearSlideRight.setPower(gamepad2.right_trigger);
            } else if (gamepad2.left_trigger != 0) {
                robot.linearSlideLeft.setPower(-gamepad2.left_trigger);
                robot.linearSlideRight.setPower(-gamepad2.left_trigger);
            } else if (linearSlideMode) {
                robot.linearSlideLeft.setPower(0.1);
                robot.linearSlideRight.setPower(0.1);
            } else {
                robot.linearSlideLeft.setPower(0);
                robot.linearSlideRight.setPower(0);
            }

            if (gamepad2.dpad_down){
                robot.stack.setPosition(POSITION);
            }

            //Single Drop

            if (g2X.wasJustPressed()){
              singleDropTimestamp = getRuntime();
              singleDrop = true;
            }
            g2X.readValue();

            if (singleDrop) {
                double sDTime = getRuntime() - singleDropTimestamp;
                if (sDTime < 0.2) {
                    robot.pixelOut.setPosition(0);
                } else if (sDTime < 0.4) {
                    robot.pixelOut.setPosition(0.8);
                } else if (sDTime < 0.7) {
                    robot.pixelIn.setPower(0.77);
                } else {
                    robot.pixelIn.setPower(0);
                    if (gamepad2.y){
                        robot.pixelOut.setPosition(0);
                    }
                    singleDrop = false;
                }
            }

            //Double Drop

            if (g2Y.wasJustPressed()){
                doubleDropTimestamp = getRuntime();
                doubleDrop = true;
            }
            g2Y.readValue();

            if (doubleDrop){
                doubleDrop = PixelDoubleDrop.getInstance().drop(robot,doubleDropTimestamp,getRuntime(),gamepad2.x);
            }


            //Color Sensors

            List<String> colorOutput = ColorSensors.getInstance().colors(robot);

            //Intaking


            if (gamepad2.a){
                Intaking.getInstance().intake(robot,colorOutput.get(15),colorOutput.get(16),false);
                intakeTimestamp = getRuntime();
            } else if (gamepad2.b){
                Intaking.getInstance().intake(robot,colorOutput.get(15),colorOutput.get(16),true);
                intakeTimestamp = getRuntime();
            } else {
                if (!(gamepad2.x&&gamepad2.y)){
                    robot.pixelIn.setPower(0);
                }
                robot.intakeMotor.setPower(0);
            }

            if (getRuntime()-intakeTimestamp<=0.5){
                intakeLights = true;
            } else {
                intakeLights = false;
            }

            //Lights

            Lights.getInstance().lightsFunction(robot, gamepad2, intakeLights, colorOutput);

            //Telemetry

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
            if(TELEMETRY==3){
                tele.addLine(String.valueOf(robot.stack.getPosition()));
                tele.update();
            }


        }
    }

    


}