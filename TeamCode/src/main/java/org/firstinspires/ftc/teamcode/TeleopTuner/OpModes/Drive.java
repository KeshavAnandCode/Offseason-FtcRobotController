package org.firstinspires.ftc.teamcode.TeleopTuner.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import java.util.Objects;

@TeleOp
@Disabled
public class Drive extends LinearOpMode {

    public static String CURVE = "LINEAR";
    @Override

    public void runOpMode() throws InterruptedException {
        //Initialization Code Goes Here
        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {

            //Teleop Code Goes Here

        }
    }
    public static double JoystickCurve (double input) {
        if (Objects.equals(CURVE, "LINEAR")){
            return input;
        } else if (Objects.equals(CURVE,"SQUARED")){
            return input*input;
        } else if (Objects.equals(CURVE,"CUBED")){
            return Math.pow(input,3);
        } else if (Objects.equals(CURVE,"LOG")){
            return (Math.log10(input+1)+1);
        } else if (Objects.equals(CURVE,"CUSTOM")){
            return input;
        }
        return input;
    }
}
