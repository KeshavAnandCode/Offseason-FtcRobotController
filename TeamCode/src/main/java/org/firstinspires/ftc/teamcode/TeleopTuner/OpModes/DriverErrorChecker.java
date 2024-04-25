package org.firstinspires.ftc.teamcode.TeleopTuner.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp
@Disabled
public class DriverErrorChecker extends LinearOpMode {
    @Override

    public void runOpMode() throws InterruptedException {
        //Initialization Code Goes Here
        waitForStart();

        double xMax = 0;
        double xMin = 0;
        double yMax = 0;
        double yMin = 0;

        double x2Max = 0;
        double y2Max =0;
        double x2Min = 0;
        double y2Min =0;

        if (isStopRequested()) return;



        while (opModeIsActive()) {

            //Teleop Code Goes Here

            if (gamepad1.right_stick_x>x2Max){
                x2Max = gamepad1.right_stick_x;
            }
            if (gamepad1.right_stick_x<x2Min){
                x2Min = gamepad1.right_stick_x;
            }
            if (-gamepad1.right_stick_y<y2Min){
                y2Min = -gamepad1.right_stick_y;
            }
            if (-gamepad1.right_stick_y>y2Max){
                y2Max = -gamepad1.right_stick_y;
            }
            if (gamepad1.left_stick_x>xMax){
                xMax = gamepad1.left_stick_x;
            }
            if (gamepad1.left_stick_x<xMin){
                xMin = gamepad1.left_stick_x;
            }
            if (-gamepad1.left_stick_y<yMin){
                yMin = -gamepad1.left_stick_y;
            }
            if (-gamepad1.left_stick_y>yMax){
                yMax = -gamepad1.left_stick_y;
            }

            telemetry.addData("Right Stick X Max: ",x2Max);
            telemetry.addData("Right Stick X Min: ",x2Min);
            telemetry.addData("Right Stick Y Max: ",y2Max);
            telemetry.addData("Right Stick Y Min: ",y2Min);
            telemetry.addData("Left Stick X Max: ",xMax);
            telemetry.addData("Left Stick X Min: ",xMin);
            telemetry.addData("Left Stick Y Max: ",yMax);
            telemetry.addData("Left Stick Y Min: ",yMin);
            telemetry.update();


        }
    }
}