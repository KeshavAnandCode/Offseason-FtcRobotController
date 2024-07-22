package org.firstinspires.ftc.teamcode.Teleop.OpModes;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.gamepad.ButtonReader;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.arcrobotics.ftclib.gamepad.ToggleButtonReader;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;
import org.firstinspires.ftc.teamcode.Teleop.BehindTheScenes.Singletons.ColorSensors;
import org.firstinspires.ftc.teamcode.Teleop.BehindTheScenes.Singletons.GamepadJoystickCurve;
import org.firstinspires.ftc.teamcode.Teleop.BehindTheScenes.Singletons.Hang;
import org.firstinspires.ftc.teamcode.Teleop.BehindTheScenes.Singletons.IntakeServo;
import org.firstinspires.ftc.teamcode.Teleop.BehindTheScenes.Singletons.Intaking;
import org.firstinspires.ftc.teamcode.Teleop.BehindTheScenes.Singletons.Lights;
import org.firstinspires.ftc.teamcode.Teleop.BehindTheScenes.Singletons.Robot;

import java.util.List;

@Config
@TeleOp
public class Drive_Automatic extends LinearOpMode {

    public static String CURVE = "LINEAR";
    public static double DEGREE = 1;
    public static int TELEMETRY = 0;
    int LINEARSLIDE_LOCK_POS =100;
    private static MultipleTelemetry tele;

    double singleDropTimestamp = 0;
    boolean singleDrop = false;
    double doubleDropTimestamp = 0;
    boolean doubleDrop = false;
    boolean linearSlideMode = false;

    boolean Up = false;
    boolean Down = false;
    boolean linearDown = false;

    double upTimestamp = 0.0;
    double downTimestamp = 0.0;
    boolean intakeLights = false;
    double intakeTimestamp = -90.0;
    boolean hang = false;
    double hangTimestamp = 0.0;
    double oldMosaicTimestamp = 0.0;

    double slides = 800;


    int ticker = 0;

    Robot robot;





    @Override

    public void runOpMode() throws InterruptedException {

         tele = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());


        //Initialization Code Goes Here
        GamepadEx g2 = new GamepadEx(gamepad2);

        GamepadEx g1 = new GamepadEx(gamepad1);

        robot = new Robot(hardwareMap);

        robot.linActServo.setPower(0);


        ButtonReader g2X = new ButtonReader(
            g2, GamepadKeys.Button.X
        );

        ButtonReader g2Y = new ButtonReader(
                g2, GamepadKeys.Button.Y
        );

        ButtonReader g1Back = new ButtonReader(
                g1, GamepadKeys.Button.BACK
        );

        ToggleButtonReader drone = new ToggleButtonReader(
                g1, GamepadKeys.Button.START
        );

        ButtonReader g2RB = new ToggleButtonReader(
                g2, GamepadKeys.Button.RIGHT_BUMPER
        );
        ButtonReader g2LB = new ToggleButtonReader(
                g2, GamepadKeys.Button.LEFT_BUMPER
        );

        ButtonReader up = new ButtonReader(
                g2, GamepadKeys.Button.DPAD_UP
        );


        ButtonReader down = new ButtonReader(
                g2, GamepadKeys.Button.DPAD_DOWN
        );

        ToggleButtonReader oldMosaic = new ToggleButtonReader(
                g2, GamepadKeys.Button.START
        );


        IntakeServo.getInstance().SetPosition(robot,"TOP");






        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {

            ticker ++;





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





        
            //Single Drop

            if (g2X.wasJustPressed()){
                slides = robot.linearSlideLeft.getCurrentPosition();
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
                slides = robot.linearSlideLeft.getCurrentPosition();
                doubleDropTimestamp = getRuntime();
                doubleDrop = true;
            }
            g2Y.readValue();

            if (doubleDrop) {
                double sDTime = getRuntime() - doubleDropTimestamp;
                if (sDTime < 0.5) {
                    robot.pixelOut.setPosition(0);
                } else if (sDTime < 1) {
                    robot.pixelIn.setPower(0.77);
                } else {
                    robot.pixelIn.setPower(0);
                    doubleDrop = false;
                }
                if (gamepad2.x){
                    doubleDrop = false;
                }
            }

           


            //Color Sensors

            List<String> colorOutput = ColorSensors.getInstance().colors(robot);

            //Intaking


            if (gamepad2.a){
                Intaking.getInstance().intake(robot,colorOutput.get(15),colorOutput.get(16),false);
                intakeTimestamp = getRuntime();
                if (robot.stack.getPosition()==0.8&&colorOutput.get(2)!="green"){
                    robot.stack.setPosition(0.08);
                }
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
            
            //Hang

            if (g1Back.wasJustPressed()){
                hangTimestamp = getRuntime();
                hang = true;
            }
            
            if (hang){
               hang = Hang.getInstance().hang(robot,hangTimestamp,getRuntime());
            }
            g1Back.readValue();

            if (!hang) {

                if (gamepad1.x) {
                    robot.linActServo.setPower(-1);
                } else if (gamepad1.b) {
                    robot.linActServo.setPower(1);
                } else {
                    robot.linActServo.setPower(0);

                }

                if (gamepad1.y) {
                    robot.linearActuator.setPower(1);
                } else if (gamepad1.a) {
                    robot.linearActuator.setPower(-1);
                } else {
                    robot.linearActuator.setPower(0);
                }
            }
            
            //Drone:

            if (drone.getState()) {
                robot.droneLauncher.setPower(1);
            } else {
                robot.droneLauncher.setPower(0);
            }
            drone.readValue();
            
            //Mosaic Mover

            if(oldMosaic.getState()){
                robot.mosaicMover.setPower(1);
            } else {
                if (oldMosaic.stateJustChanged()){
                    oldMosaicTimestamp = getRuntime();
                }
                if ((getRuntime()-oldMosaicTimestamp)<1.5){
                    robot.mosaicMover.setPower(-1);
                } else {
                    robot.mosaicMover.setPower(0);
                }
            }
            oldMosaic.readValue();

//            if(gamepad2.back){
//                robot.pixelOut.setPosition(0.23);
//            }

            
            //Arm + Slides

            if (up.wasJustPressed()){
                upTimestamp = getRuntime();
                Up = true;
            }

            up.readValue();

            if (down.wasJustPressed()){
                Down = true;
            }

            down.readValue();

            if (Up) {
                double upTime = getRuntime()- upTimestamp;

                if (upTime<1 || robot.linearSlideLeft.getCurrentPosition()<slides-100) {
                    if (upTime < 1) {
                        robot.intakeRotate.setPower(0.1);
                        robot.stack.setPosition(0.8);
                        robot.intakeMove.setPower(1);

                    }

                    if (robot.linearSlideLeft.getCurrentPosition() < slides - 100) {

                        robot.linearSlideLeft.setPower(1);
                        robot.linearSlideRight.setPower(1);

                    } else {
                        robot.linearSlideLeft.setPower(0);
                        robot.linearSlideRight.setPower(0);
                    }
                } else {

                     Up = false;
                 }
            }

            else if (Down) {

                if (robot.linearSlideLeft.getCurrentPosition()>150){

                    robot.linearSlideLeft.setPower(-1);
                    robot.linearSlideRight.setPower(-1);

                } else {
                    linearDown = true;
                    downTimestamp = getRuntime();
                    Down = false;
                }

            }

            else if (linearDown) {

                double downTime = getRuntime() - downTimestamp;

                if (downTime<0.7) {
                    robot.intakeRotate.setPower(-0.1);
                    robot.intakeMove.setPower(-1);
                } else {
                    linearDown = false;
                }
            }

            else {

                //Arm:

                if (-gamepad2.left_stick_y > 0) {
                    robot.intakeRotate.setPower(0.1);
                    robot.intakeMove.setPower(-gamepad2.left_stick_y * 0.5);
                    robot.stack.setPosition(0.8);
                } else if (-gamepad2.left_stick_y < 0){

                    if (robot.linearSlideLeft.getCurrentPosition()<50) {
                        robot.intakeRotate.setPower(-0.1);
                        robot.intakeMove.setPower(-gamepad2.left_stick_y * 0.5);
                    }

                } else {
                    robot.intakeRotate.setPower(0);
                    robot.intakeMove.setPower(0);
                }

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

            }

            //Intake Servo:


            if (gamepad2.dpad_up){
                robot.stack.setPosition(0.8);
            }
            if (colorOutput.get(2)=="green"){
                robot.stack.setPosition(0.8);
            }

            if (g2RB.wasJustPressed()){
                    robot.stack.setPosition(0.2);
            }

            if (g2LB.wasJustPressed()){
                if (robot.stack.getPosition()==0.8) {
                    robot.stack.setPosition(0.08);
                }  else {
                    robot.stack.setPosition(robot.stack.getPosition()-0.04);
                }
            }

            g2LB.readValue();
            g2RB.readValue();







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
                tele.addLine("Use FTC Dash to select telemetry");
                tele.addLine("1 = Motor Power");
                tele.addLine("2 = Color Sensors");
                tele.addLine("3 = Intake Servo Position");
                tele.addLine("4 = Ticker (Frame Count)");
                tele.addLine("5 = Current of Intake Motor");
                tele.update();
            }
            if(TELEMETRY==3){
                tele.addLine(String.valueOf(robot.stack.getPosition()));
                tele.update();
            }
            if (TELEMETRY==4){
                tele.addLine(String.valueOf(ticker));
                tele.update();
            }







        }
    }

    


}