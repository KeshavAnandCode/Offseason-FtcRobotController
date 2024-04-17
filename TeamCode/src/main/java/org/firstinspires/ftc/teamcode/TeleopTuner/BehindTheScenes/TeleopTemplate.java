package org.firstinspires.ftc.teamcode.TeleopTuner.BehindTheScenes;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp
@Disabled
public class TeleopTemplate extends LinearOpMode {
    @Override

    public void runOpMode() throws InterruptedException {
        //Initialization Code Goes Here
        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {

            //Teleop Code Goes Here

        }
    }
}