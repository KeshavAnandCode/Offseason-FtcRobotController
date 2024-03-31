package org.firstinspires.ftc.teamcode.TeleopTuner.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.TeleopTuner.BehindTheScenes.MotorConfig;


@TeleOp
public class ManualMotorDriveTesting extends LinearOpMode {

    public static boolean AUTO_TEST = false;
    //TODO: ONLY SET THIS TO TRUE IF YOU WANT TO TEST ALL YOUR MOTORS (AS LISTED IN DRIVE CONFIG).

    @Override
    public void runOpMode() throws InterruptedException {

        telemetry.addLine("Each Motor Will Move \"forward\" for 3 Seconds");
        telemetry.addLine();
        telemetry.update();
        waitForStart();
        if (isStopRequested()) return;

        if (opModeIsActive()) {
            if (!AUTO_TEST) {
                for (int i = 1; i <= MotorConfig.MOTOR_IDS.size(); i++) {
                    telemetry.addLine("Test Motor" + MotorConfig.MOTOR_NAMES.get(i) + "?");
                    telemetry.addLine();
                    telemetry.addLine("Using Gamepad 1...");
                    telemetry.addLine("A/Cross = Yes, B/Circle = No");
                    telemetry.update();
                    boolean pressedA = false;
                    boolean pressedB = false;
                    while (!pressedA && !pressedB) {
                        if (gamepad1.a) {
                            pressedA = true;
                        }
                        if (gamepad1.b) {
                            pressedB = true;
                        }
                    }
                    while (gamepad1.a && pressedA) {
                        idle();
                    }
                    while (gamepad1.b && pressedB) {
                        idle();
                    }
                    if (pressedA) {
                        testMotor(MotorConfig.MOTOR_IDS.get(i));
                    }
                }
            }
            if (AUTO_TEST){
                for (int i = 1; i <= MotorConfig.MOTOR_IDS.size(); i++) {
                    telemetry.addLine("Testing Motor" + MotorConfig.MOTOR_NAMES.get(i));
                    telemetry.update();
                    testMotor(MotorConfig.MOTOR_IDS.get(i));
                }
            }
        }



    }
    private void testMotor(DcMotorEx motor){
        motor.setPower(0);
        resetRuntime();
        while (getRuntime()<3){
            motor.setPower(getRuntime()*1.6);
        }
        sleep(2000);
    }
}

