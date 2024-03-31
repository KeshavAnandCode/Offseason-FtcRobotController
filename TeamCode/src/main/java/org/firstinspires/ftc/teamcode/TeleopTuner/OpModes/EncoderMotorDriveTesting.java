package org.firstinspires.ftc.teamcode.TeleopTuner.OpModes;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.TeleopTuner.BehindTheScenes.DriveConfig;


@TeleOp
public class EncoderMotorDriveTesting extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {


        telemetry.addLine("Move every motor that you want to test forward a significant amount");
        telemetry.addLine();
        telemetry.addLine("Check with your coder that he has correctly declared encoders for this");
        telemetry.update();
        waitForStart();
        if (isStopRequested()) return;

        while (opModeIsActive()) {
            for (int i = 1; i<=DriveConfig.ENCODER_IDS.size();i++){
                if (DriveConfig.ENCODER_IDS.get(i)==hardwareMap.get(DcMotorEx.class,"")){
                    idle();
                } else {
                    telemetry.addData(DriveConfig.MOTOR_NAMES.get(i)+" Position: ",DriveConfig.ENCODER_IDS.get(i).getCurrentPosition());
                    telemetry.addData(
                     DriveConfig.MOTOR_NAMES.get(i)+
                        DriveConfig.ENCODER_IDS.get(i).getController()+
                        "Port ",
                        DriveConfig.MOTOR_IDS.get(i).getPortNumber()
                    );
                }
            }

        }
    }
    private String calculateEncoder(DcMotorEx motor){
       double motorPos = motor.getCurrentPosition();
       if (motorPos>100){
           return "FORWARD";
       } else if  (motorPos<-100){
           return "BACKWARD";
       } else{
           return "NA";
       }

    }
}

