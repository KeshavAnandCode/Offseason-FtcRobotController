package org.firstinspires.ftc.teamcode.Autonomous;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TranslationalVelConstraint;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Autonomous.RR.MecanumDrive;
import org.firstinspires.ftc.teamcode.Teleop.BehindTheScenes.Singletons.ColorSensors;
import org.firstinspires.ftc.teamcode.Teleop.BehindTheScenes.Singletons.IntakeServo;
import org.firstinspires.ftc.teamcode.Teleop.BehindTheScenes.Singletons.Intaking;
import org.firstinspires.ftc.teamcode.Teleop.BehindTheScenes.Singletons.Lights;
import org.firstinspires.ftc.teamcode.Teleop.BehindTheScenes.Singletons.Robot;

import java.util.List;
import java.util.Objects;

@Autonomous
@Config
public class RRTest extends LinearOpMode {

    public static class FailoverAction implements Action {
        private final Action mainAction;
        private final Action failoverAction;
        private static boolean failedOver = false;

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

        public static void failover() {
            failedOver = true;
        }


    }
    Action updateAction = new Action() {
        @Override

        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            List<String> colorOutput = ColorSensors.getInstance().colors(robot);
            Lights.getInstance().lightsFunction(robot, gamepad2, false, colorOutput);

            if (Objects.equals(colorOutput.get(2), "green")) {
                FailoverAction.failover();
                return false;
            }

            if (getRuntime()>20){
                FailoverAction.failover();
                return false;
            }


            return true;
        }
    };

    Action traj1 = new Action() {
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            List<String> colorOutput = ColorSensors.getInstance().colors(robot);
            Lights.getInstance().lightsFunction(robot, gamepad2, false, colorOutput);



            return false;
        }
    };


    Robot robot;

    @Override
    public void runOpMode() throws InterruptedException {

        FailoverAction.failedOver = false;


        robot = new Robot(hardwareMap);

        robot.drive = new MecanumDrive(hardwareMap, new Pose2d(0,0,0));



        Action helllo = robot.drive.actionBuilder(new Pose2d(0,0,0))
                .strafeTo(new Vector2d(40,0))
                .build();




        Action traj = robot.drive.actionBuilder(new Pose2d(40,0,0))
                .strafeTo(new Vector2d(45, 0), new TranslationalVelConstraint(10.0))
                .strafeToLinearHeading(new Vector2d(45.01,0),Math.toRadians(90))
                .strafeToLinearHeading(new Vector2d(44.99,0),Math.toRadians(-90))





                .strafeToLinearHeading(new Vector2d(40, 0),0, new TranslationalVelConstraint(10.0))

                .strafeTo(new Vector2d(45, 5), new TranslationalVelConstraint(10.0))
                .strafeTo(new Vector2d(40, 0), new TranslationalVelConstraint(10.0))
                .strafeTo(new Vector2d(45, -5), new TranslationalVelConstraint(10.0))
                .strafeTo(new Vector2d(40, 0), new TranslationalVelConstraint(10.0))
                .strafeTo(new Vector2d(45, 7), new TranslationalVelConstraint(10.0))
                .strafeTo(new Vector2d(40, 0), new TranslationalVelConstraint(10.0))
                .strafeTo(new Vector2d(45, -7), new TranslationalVelConstraint(10.0))



                .build();




        FailoverAction failoverAction = new FailoverAction(traj, traj1);

        robot.stack.setPosition(0.08);


        waitForStart();

        if (isStopRequested()) return;

        if (opModeIsActive()) {


            resetRuntime();

            robot.intakeMotor.setPower(0.9);
            robot.pixelIn.setPower(1);
            robot.pixelOut.setPosition(.8);
            robot.intakeRotate.setPower(-0.1);
            robot.intakeMove.setPower(0);


            Actions.runBlocking(helllo);





            Actions.runBlocking(
                    new ParallelAction(
                            failoverAction, updateAction
                            // basic action to check if gamepad a has been oressed, then set failover to true and stop the
                    )
            );

                List<String> colorOutput = ColorSensors.getInstance().colors(robot);
                Lights.getInstance().lightsFunction(robot, gamepad2, true, colorOutput);

            robot.intakeMotor.setPower(0);
            robot.pixelIn.setPower(0);

            robot.intakeRotate.setPower(0);
            robot.intakeMove.setPower(0);

            IntakeServo.getInstance().SetPosition(robot,"TOP");


            Actions.runBlocking(
                    new SequentialAction(
                            robot.drive.actionBuilder(robot.drive.pose)
                                    .strafeToLinearHeading(new Vector2d(40, 0),0)

                                    .strafeToLinearHeading(new Vector2d(0, 0),0)
                                    .build()
                    )
            );


            Lights.getInstance().lightsFunction(robot, gamepad2, false, colorOutput);


        }
    }
}