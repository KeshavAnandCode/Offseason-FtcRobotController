package org.firstinspires.ftc.teamcode.Autonomous;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Autonomous.RR.MecanumDrive;

@Disabled
@Autonomous
public class CallOpenCv extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {

        while(opModeInInit()){
           int x =  OpenCvTest.getObjectPosition();
           telemetry.addData("position",x);
           telemetry.update();
        }

        waitForStart();

        if (isStopRequested()) return;


    }
}
