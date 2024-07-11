package org.firstinspires.ftc.teamcode.Autonomous;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.TimeTrajectory;
import com.acmerobotics.roadrunner.TranslationalVelConstraint;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Autonomous.RR.MecanumDrive;
import org.firstinspires.ftc.teamcode.Teleop.BehindTheScenes.Singletons.Robot;

@Autonomous
@Config
public class RRTest extends LinearOpMode {

    public class FailoverAction implements Action {
        private final Action mainAction;
        private final Action failoverAction;
        private boolean failedOver = false;

        public FailoverAction(Action mainAction, Action failoverAction) {
            this.mainAction = mainAction;
            this.failoverAction = failoverAction;
        }

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            if (failedOver) {
                return failoverAction.run(telemetryPacket);
            }

            return mainAction.run(telemetryPacket);
        }

        public void failover() {
            failedOver = true;
        }


    }

    private Robot robot;

    @Override
    public void runOpMode() throws InterruptedException {

        MecanumDrive drive = new MecanumDrive(hardwareMap, new Pose2d(0, 0, 0));
        robot = new Robot(hardwareMap);

        Action traj = drive.actionBuilder(drive.pose)
                .strafeTo(new Vector2d(80, 0),
                        new TranslationalVelConstraint(10.0))
                .build();
        Action traj1 = drive.actionBuilder(drive.pose)
                .strafeTo(new Vector2d(0, 0),
                        new TranslationalVelConstraint(10.0))
                .build();
        FailoverAction a = new FailoverAction(traj, traj1);

        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {

            TelemetryPacket packet = new TelemetryPacket();


            if (!a.run(packet)) {
                break;
            }

            if (gamepad1.a) {
                a.failover();
            }

            FtcDashboard.getInstance().sendTelemetryPacket(packet);
        }
    }
}