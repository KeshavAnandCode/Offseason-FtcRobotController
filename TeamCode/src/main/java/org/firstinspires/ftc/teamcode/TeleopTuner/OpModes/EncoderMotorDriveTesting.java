package org.firstinspires.ftc.teamcode.TeleopTuner.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.TeleopTuner.BehindTheScenes.MotorConfig;


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
            for (int i = 1; i<= MotorConfig.ENCODER_IDS.size(); i++){
                if (MotorConfig.ENCODER_IDS.get(i)==hardwareMap.get(DcMotorEx.class,"")){
                    idle();
                } else {
                    telemetry.addData(MotorConfig.MOTOR_NAMES.get(i)+" Position ", MotorConfig.ENCODER_IDS.get(i).getCurrentPosition());
                    telemetry.addData(
                     MotorConfig.MOTOR_NAMES.get(i)+
                        MotorConfig.ENCODER_IDS.get(i).getController()+
                        "Port ",
                        MotorConfig.MOTOR_IDS.get(i).getPortNumber()
                    );
                }
            }
            telemetry.addLine("Press B on Gamepad 1 to end testing");
            telemetry.update();
            if (gamepad1.b){
                motorTelemetry();
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
    private void motorTelemetry(){
        for (int i = 1; i<= MotorConfig.ENCODER_IDS.size(); i++){
            if (MotorConfig.ENCODER_IDS.get(i)==hardwareMap.get(DcMotorEx.class,"")){
                idle();
            } else {
                telemetry.addData(MotorConfig.MOTOR_NAMES.get(i),calculateEncoder(MotorConfig.ENCODER_IDS.get(i)));
                telemetry.addData(
                        MotorConfig.MOTOR_NAMES.get(i)+
                        MotorConfig.ENCODER_IDS.get(i).getController()+
                        "Port ",
                        MotorConfig.MOTOR_IDS.get(i).getPortNumber()
                );
            }
        }
    }
}

