package org.firstinspires.ftc.teamcode.Autonomous;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Autonomous.RR.MecanumDrive;
import org.firstinspires.ftc.teamcode.Teleop.BehindTheScenes.Singletons.Robot;

@Autonomous
@Config
public class RRTest extends LinearOpMode {

    Robot robot;
    @Override
    public void runOpMode() throws InterruptedException {

        robot = new Robot (hardwareMap);

        MecanumDrive drive = new MecanumDrive(hardwareMap, new Pose2d(0,0,Math.toRadians(0)));

        //Trajectories go here

        Action trajectoryAction1 = drive.actionBuilder(drive.pose)
                .setTangent(0)
                .splineToSplineHeading(new Pose2d(20,20,Math.toRadians(0)),0)
                .setTangent(0)
                .splineToSplineHeading(new Pose2d(0,0,0),Math.toRadians(0))
                .strafeToLinearHeading(new Vector2d(10,10),0.8)
                .build();

        waitForStart();

        if (isStopRequested()) return;

        //Autonomous Code goes here

        Actions.runBlocking(trajectoryAction1);
    }
}
