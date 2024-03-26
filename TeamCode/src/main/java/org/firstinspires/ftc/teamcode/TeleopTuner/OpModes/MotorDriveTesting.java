package org.firstinspires.ftc.teamcode.TeleopTuner.OpModes;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.TeleopTuner.BehindTheScenes.DriveConfig;

import java.util.List;


// List of motor names
@TeleOp
public class MotorDriveTesting extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        List<DcMotorEx> motorIds = DriveConfig.MOTOR_IDS;
        int i = 1;
    }
}

