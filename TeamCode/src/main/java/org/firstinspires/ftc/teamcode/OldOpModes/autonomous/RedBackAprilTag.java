/* Copyright (c) 2019 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode.OldOpModes.autonomous;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.hardware.camera.BuiltinCameraDirection;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.teamcode.OldOpModes.drive.SampleMecanumDrive;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
import org.firstinspires.ftc.vision.tfod.TfodProcessor;

import java.util.List;

/*
 * This OpMode illustrates the basics of TensorFlow Object Detection,
 * including Java Builder structures for specifying Vision parameters.
 *
 * Use Android Studio to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this OpMode to the Driver Station OpMode list.
 */
@Autonomous(name = "RedBackAprilTag")

public class RedBackAprilTag extends LinearOpMode {


    int auto =3;
    private static final boolean USE_WEBCAM = true;  // true for webcam, false for phone camera

    /**
     * The variable to store our instance of the AprilTag processor.
     */
    private AprilTagProcessor aprilTag;

    /**
     * The variable to store our instance of the TensorFlow Object Detection processor.
     */
    private TfodProcessor tfod;

    /**
     * The variable to store our instance of the vision portal.
     */
    private VisionPortal myVisionPortal;
    private static final String TFOD_MODEL_ASSET = "Meet1Red.tflite";
    // TFOD_MODEL_FILE points to a model file stored onboard the Robot Controller's storage,
    // this is used when uploading models directly to the RC using the model upload interface.
    private static final String TFOD_MODEL_FILE = "/sdcard/FIRST/tflitemodels/myCustomModel.tflite";
    // Define the labels recognized in the model for TFOD (must be in training order!)
    private static final String[] LABELS = {
       "red",
    };

    /**
     * The variable to store our instance of the TensorFlow Object Detection processor.
     */


    /**
     * The variable to store our instance of the vision portal.
     */


    @Override
    public void runOpMode() {
        initDoubleVision();
        CRServo intakeMove = hardwareMap.crservo.get("intakeMove");
        CRServo intakeRotate = hardwareMap.crservo.get("intakeRotate");
        Servo pixelOut = hardwareMap.servo.get("pixelOut");
        CRServo pixelIn = hardwareMap.crservo.get("pixelIn");
        DcMotor frontLeftMotor = hardwareMap.dcMotor.get("frontLeftMotor");
        DcMotor backLeftMotor = hardwareMap.dcMotor.get("backLeftMotor");
        DcMotor frontRightMotor = hardwareMap.dcMotor.get("frontRightMotor");
        DcMotor backRightMotor = hardwareMap.dcMotor.get("backRightMotor");
        intakeMove.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backRightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontRightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontLeftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        double xforlast = 0;
        double yforlast = 0;
        int idforapril = 0;
        boolean hello = false;


        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);

        drive.setPoseEstimate(new Pose2d(0, 0, Math.toRadians(0)));

        Trajectory traj1a = drive.trajectoryBuilder(new Pose2d())
                .lineToConstantHeading(new Vector2d(-22,-6.6))

                .build();
        Trajectory traj1b = drive.trajectoryBuilder(new Pose2d())
                .lineToSplineHeading (new Pose2d(-1,0,Math.toRadians(-92)),
                        SampleMecanumDrive.getVelocityConstraint(20,6.7464,10.69),
                        SampleMecanumDrive.getAccelerationConstraint(50)

                        )
                .build();
        Trajectory trajz = drive.trajectoryBuilder(new Pose2d())
                .lineToConstantHeading (new Vector2d(0,2.5),
                        SampleMecanumDrive.getVelocityConstraint(13,2.5,10.69),
                        SampleMecanumDrive.getAccelerationConstraint(30)
                )
                .build();
        Trajectory traj1c = drive.trajectoryBuilder(new Pose2d())

                .lineToConstantHeading(new Vector2d(-75,0))

                .build();
        Trajectory trajcc = drive.trajectoryBuilder(new Pose2d())

                .lineToConstantHeading(new Vector2d(-78.5,0))

                .build();
        Trajectory traj3c = drive.trajectoryBuilder(new Pose2d())

                .lineToConstantHeading(new Vector2d(-82,0))

                .build();
        Trajectory traj1d = drive.trajectoryBuilder(new Pose2d())

                .lineToConstantHeading(new Vector2d(18,6.6))
                .build();

        Trajectory traj1e = drive.trajectoryBuilder(new Pose2d())

                .lineToSplineHeading (new Pose2d(16,0,Math.toRadians(1)))

                .build();
        Trajectory traj1x = drive.trajectoryBuilder(new Pose2d())

                .lineToSplineHeading(new Pose2d(0,-28,Math.toRadians(-5)))
                .build();
        Trajectory traj2x = drive.trajectoryBuilder(new Pose2d())

                .lineToSplineHeading(new Pose2d(0,-21,Math.toRadians(-5)))
                .build();
        Trajectory traj3x = drive.trajectoryBuilder(new Pose2d())

                .lineToSplineHeading(new Pose2d(0,-15,Math.toRadians(-5)))

                .build();
        Trajectory traj3e = drive.trajectoryBuilder(new Pose2d())

                .lineToSplineHeading (new Pose2d(11,0,Math.toRadians(1)))

                .build();
        Trajectory trajd = drive.trajectoryBuilder(new Pose2d())

                .lineToConstantHeading (new Vector2d(-1,0))

                .build();
        Trajectory traj1f = drive.trajectoryBuilder(new Pose2d())

                .lineToConstantHeading (new Vector2d(0,29.5))

                .build();
        Trajectory traj2f = drive.trajectoryBuilder(new Pose2d())

                .lineToConstantHeading (new Vector2d(0,-23))

                .build();
        Trajectory traj3f = drive.trajectoryBuilder(new Pose2d())

                .lineToConstantHeading (new Vector2d(0,-29))

                .build();
        Trajectory traj1g = drive.trajectoryBuilder(new Pose2d())

                .lineToConstantHeading (new Vector2d(-10,0))

                .build();
        Trajectory traj3g = drive.trajectoryBuilder(new Pose2d())

                .lineToConstantHeading (new Vector2d(-15,0))

                .build();
        Trajectory traj2a = drive.trajectoryBuilder(new Pose2d())
                .lineToConstantHeading(new Vector2d(-31,0))
                .build();
        Trajectory traj2d = drive.trajectoryBuilder(new Pose2d())
                .lineToConstantHeading(new Vector2d(27,0))
                .build();
        Trajectory traj3a = drive.trajectoryBuilder(new Pose2d())
                .lineToSplineHeading(new Pose2d(-27,7.3,Math.toRadians(-90)))

                .build();
        Trajectory traj3b = drive.trajectoryBuilder(new Pose2d())
                .lineToConstantHeading(new Vector2d(7.3,0),
                        SampleMecanumDrive.getVelocityConstraint(13,2.5,10.69),
                        SampleMecanumDrive.getAccelerationConstraint(30)
                )

                .build();
        Trajectory traj3r = drive.trajectoryBuilder(new Pose2d())
                .lineToSplineHeading(new Pose2d(0,23.5,Math.toRadians(90)))

                .build();

        Trajectory trajApril = drive.trajectoryBuilder(new Pose2d())
                .lineToConstantHeading(new Vector2d(1,0))

                .build();
        boolean d= true;
        while (!opModeIsActive()) {
            telemetry.addData("DS preview on/off","3 dots, Camera Stream");
            telemetry.addLine();
            telemetry.addLine("----------------------------------------");
            telemetry.addData("auto",auto);
            telemetry.update();
        }




        waitForStart();

        if (opModeIsActive()) {
            sleep(6000);
            if (auto == 2 || auto == 1) {
                telemetry.addData("auto", auto);
                telemetry.update();
            } else {
                resetRuntime();
                while (getRuntime() < 1 && auto == 3) {
                    telemetryTfod();

                    telemetry.update();
                }
            }
        }
        telemetry.addData("auto",auto);

        telemetry.update();
        resetRuntime();


        if (auto ==1){

            drive.followTrajectory(traj1a);
            sleep(500);
            drive.setPoseEstimate(new Pose2d(0, 0, Math.toRadians(0)));
            drive.followTrajectory(traj1d);
            sleep(300);
            drive.setPoseEstimate(new Pose2d(0, 0, Math.toRadians(0)));
            sleep(300);
            drive.followTrajectory(traj1b);
            drive.setPoseEstimate(new Pose2d(0, 0, Math.toRadians(0)));
            sleep(300);
            drive.setPoseEstimate(new Pose2d(0, 0, Math.toRadians(0)));
            drive.followTrajectory(trajz);
            sleep(300);
            drive.setPoseEstimate(new Pose2d(0, 0, Math.toRadians(0)));
            drive.followTrajectory(trajcc);
            drive.setPoseEstimate(new Pose2d(0, 0, Math.toRadians(0)));
            sleep(300);
            drive.followTrajectory(traj1x);
            drive.setPoseEstimate(new Pose2d(0, 0, Math.toRadians(0)));
            resetRuntime();
            while (getRuntime()<1) {
                if (aprilTag.getDetections().size() > 0) {
                    for (int i = 0; i < aprilTag.getDetections().size(); i++) {
                        AprilTagDetection tag = aprilTag.getDetections().get(i);
                        idforapril = tag.id;
                        xforlast = -1 * tag.ftcPose.y;
                        yforlast = tag.ftcPose.x;

                        if (idforapril == 4) {
                            trajApril = drive.trajectoryBuilder(new Pose2d())
                                    .lineToConstantHeading(new Vector2d(xforlast+3, yforlast+0.5))

                                    .build();
                            hello = true;
                        }
                    }


                    if (hello) {
                        drive.followTrajectory(trajApril);
                    }

                }
            }
            drive.setPoseEstimate(new Pose2d(0, 0, Math.toRadians(0)));
            sleep(200);
            frontLeftMotor.setPower(-0.2);
            frontRightMotor.setPower(-0.2);
            backLeftMotor.setPower(-0.2);
            backRightMotor.setPower(-0.2);
            sleep(3000);
            intakeMove.setPower(-0.5);
            intakeRotate.setPower(-0.1);
            sleep(2300);
            intakeRotate.setPower(-0.7);
            intakeMove.setPower(-0.5);
            sleep(300);
            pixelOut.setPosition(0);
            pixelIn.setPower(-1);
            sleep(800);
            pixelIn.setPower(-1);
            intakeMove.setPower(-0.5);
            intakeRotate.setPower(-0.1);
            sleep(1000);
            pixelIn.setPower(0);
            intakeMove.setPower(0);
            intakeRotate.setPower(0);
            sleep(200);
            pixelIn.setPower(-1);
            intakeMove.setPower(0.5);
            sleep(1000);
            pixelIn.setPower(0);
            intakeMove.setPower(0);
            intakeRotate.setPower(0);



        }
        if (auto ==2){
            drive.followTrajectory(traj2a);
            sleep(500);
            drive.setPoseEstimate(new Pose2d(0, 0, Math.toRadians(0)));
            drive.followTrajectory(traj2d);
            sleep(300);
            drive.setPoseEstimate(new Pose2d(0, 0, Math.toRadians(0)));
            sleep(300);
            drive.followTrajectory(traj1b);
            drive.setPoseEstimate(new Pose2d(0, 0, Math.toRadians(0)));
            sleep(300);
            drive.setPoseEstimate(new Pose2d(0, 0, Math.toRadians(0)));
            drive.followTrajectory(trajz);
            sleep(300);
            drive.setPoseEstimate(new Pose2d(0, 0, Math.toRadians(0)));
            drive.followTrajectory(traj1c);
            drive.setPoseEstimate(new Pose2d(0, 0, Math.toRadians(0)));
            sleep(300);
            drive.followTrajectory(traj2x);
            drive.setPoseEstimate(new Pose2d(0, 0, Math.toRadians(0)));
            resetRuntime();
            while (getRuntime()<1) {
                if (aprilTag.getDetections().size() > 0) {
                    for (int i = 0; i < aprilTag.getDetections().size(); i++) {
                        AprilTagDetection tag = aprilTag.getDetections().get(i);
                        idforapril = tag.id;
                        xforlast = -1 * tag.ftcPose.y;
                        yforlast = tag.ftcPose.x;

                        if (idforapril == 5) {
                            trajApril = drive.trajectoryBuilder(new Pose2d())
                                    .lineToConstantHeading(new Vector2d(xforlast+3, yforlast))

                                    .build();
                            hello = true;
                        }
                        if (hello) {
                            drive.followTrajectory(trajApril);
                        }
                    }




                }
            }
            drive.setPoseEstimate(new Pose2d(0, 0, Math.toRadians(0)));
            sleep(200);
            frontLeftMotor.setPower(-0.2);
            frontRightMotor.setPower(-0.2);
            backLeftMotor.setPower(-0.2);
            backRightMotor.setPower(-0.2);
            sleep(3000);
            intakeMove.setPower(-0.5);
            intakeRotate.setPower(-0.1);
            sleep(2300);
            intakeRotate.setPower(-0.7);
            intakeMove.setPower(-0.5);
            sleep(300);
            pixelOut.setPosition(0);
            pixelIn.setPower(-1);
            sleep(800);
            pixelIn.setPower(-1);
            intakeMove.setPower(-0.5);
            intakeRotate.setPower(-0.1);
            sleep(1000);
            pixelIn.setPower(0);
            intakeMove.setPower(0);
            intakeRotate.setPower(0);
            sleep(200);
            pixelIn.setPower(-1);
            intakeMove.setPower(0.5);
            sleep(1000);
            pixelIn.setPower(0);
            intakeMove.setPower(0);
            intakeRotate.setPower(0);


        } if (auto ==3){
            drive.followTrajectory(traj3a);
            sleep(500);
            drive.setPoseEstimate(new Pose2d(0, 0, Math.toRadians(0)));
            drive.followTrajectory(traj3b);
            sleep(500);
            drive.setPoseEstimate(new Pose2d(0, 0, Math.toRadians(0)));
            drive.followTrajectory(traj3r);
            sleep(300);
            drive.setPoseEstimate(new Pose2d(0, 0, Math.toRadians(0)));
            sleep(300);
            drive.followTrajectory(traj1b);
            drive.setPoseEstimate(new Pose2d(0, 0, Math.toRadians(0)));
            sleep(300);
            drive.setPoseEstimate(new Pose2d(0, 0, Math.toRadians(0)));
            drive.followTrajectory(trajz);
            drive.setPoseEstimate(new Pose2d(0, 0, Math.toRadians(0)));
            sleep(300);
            drive.followTrajectory(traj3c);
            drive.setPoseEstimate(new Pose2d(0, 0, Math.toRadians(0)));
            sleep(300);
            drive.followTrajectory(traj3x);
            drive.setPoseEstimate(new Pose2d(0, 0, Math.toRadians(0)));
            resetRuntime();
            while (getRuntime()<1) {
                if (aprilTag.getDetections().size() > 0) {
                    for (int i = 0; i < aprilTag.getDetections().size(); i++) {
                        AprilTagDetection tag = aprilTag.getDetections().get(i);
                        idforapril = tag.id;
                        xforlast = -1 * tag.ftcPose.y;
                        yforlast = tag.ftcPose.x;

                        if (idforapril == 6) {
                            // You have to fix this part of the code. It needs to go more left
                            trajApril = drive.trajectoryBuilder(new Pose2d())
                                    .lineToConstantHeading(new Vector2d(xforlast+3, yforlast+0))

                                    .build();
                            hello = true;
                        }
                    }
                    if (hello){
                        drive.followTrajectory(trajApril);
                    }







                }

            }

            drive.setPoseEstimate(new Pose2d(0, 0, Math.toRadians(0)));
            sleep(200);
            frontLeftMotor.setPower(-0.2);
            frontRightMotor.setPower(-0.2);
            backLeftMotor.setPower(-0.2);
            backRightMotor.setPower(-0.2);
            sleep(3000);
            intakeMove.setPower(-0.5);
            intakeRotate.setPower(-0.1);
            sleep(2300);
            intakeRotate.setPower(-0.7);
            intakeMove.setPower(-0.5);
            sleep(300);
            pixelOut.setPosition(0);
            pixelIn.setPower(-1);
            sleep(800);
            pixelIn.setPower(-1);
            intakeMove.setPower(-0.5);
            intakeRotate.setPower(-0.1);
            sleep(1000);
            pixelIn.setPower(0);
            intakeMove.setPower(0);
            intakeRotate.setPower(0);
            sleep(200);
            pixelIn.setPower(-1);
            intakeMove.setPower(0.5);
            sleep(1000);
            pixelIn.setPower(0);
            intakeMove.setPower(0);
            intakeRotate.setPower(0);

        }

        // Save more CPU resources when camera is no longer needed.



    }   // end runOpMode()

    /**
     * Initialize the TensorFlow Object Detection processor.
     */


    /**
     * Add telemetry about TensorFlow Object Detection (TFOD) recognitions.
     */
    private void telemetryTfod() {

        List<Recognition> currentRecognitions = tfod.getRecognitions();
        telemetry.addData("# Objects Detected", currentRecognitions.size());

        // Step through the list of recognitions and display info for each one.
        for (Recognition recognition : currentRecognitions) {
            double x = (recognition.getLeft() + recognition.getRight()) / 2 ;
            double y = (recognition.getTop()  + recognition.getBottom()) / 2 ;

            telemetry.addData(""," ");
            telemetry.addData("Image", "%s (%.0f %% Conf.)", recognition.getLabel(), recognition.getConfidence() * 100);
            telemetry.addData("- Position", "%.0f / %.0f", x, y);
            telemetry.addData("- Size", "%.0f x %.0f", recognition.getWidth(), recognition.getHeight());
        }   // end for() loop
        if (currentRecognitions.size()!=0){
            for (Recognition recognition : currentRecognitions) {
                if((recognition.getImageHeight()<=1.5* recognition.getImageWidth())&&(recognition.getImageWidth()<=1.5* recognition.getImageHeight())&&(recognition.getConfidence()>0.87)){
                    if (((recognition.getLeft()+ recognition.getRight())/2)<200){
                        auto=1;
                    } else {
                        auto = 2;
                    }

                } else {
                    idle();
                }

            telemetry.addData("auto",auto);
                telemetry.addData("confonf",recognition.getConfidence());
            }   // end for() loop
            telemetry.addData("auto",auto);
        }else {
            auto =3;
        }

        telemetry.addData("auto",auto);

    }   // end method telemetryTfod()
    private void initDoubleVision() {
        // -----------------------------------------------------------------------------------------
        // AprilTag Configuration
        // -----------------------------------------------------------------------------------------

        aprilTag = new AprilTagProcessor.Builder()
                .build();

        // -----------------------------------------------------------------------------------------
        // TFOD Configuration
        // -----------------------------------------------------------------------------------------

        tfod = new TfodProcessor.Builder()
                .setModelAssetName(TFOD_MODEL_ASSET)
                //.setModelFileName(TFOD_MODEL_FILE)

                // The following default settings are available to un-comment and edit as needed to
                // set parameters for custom models.
                .setModelLabels(LABELS)
                .build();

        // -----------------------------------------------------------------------------------------
        // Camera Configuration
        // -----------------------------------------------------------------------------------------

        if (USE_WEBCAM) {
            myVisionPortal = new VisionPortal.Builder()
                    .setCamera(hardwareMap.get(WebcamName.class, "Webcam 1"))
                    .addProcessors(tfod, aprilTag)
                    .build();
        } else {
            myVisionPortal = new VisionPortal.Builder()
                    .setCamera(BuiltinCameraDirection.BACK)
                    .addProcessors(tfod, aprilTag)
                    .build();
        }
    }   // end initDoubleVision()
}   // end class