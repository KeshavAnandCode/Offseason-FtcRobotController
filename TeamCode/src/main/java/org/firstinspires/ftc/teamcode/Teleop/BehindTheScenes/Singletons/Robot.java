package org.firstinspires.ftc.teamcode.Teleop.BehindTheScenes.Singletons;


import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Robot {
    public DcMotorEx frontLeftMotor;
    public DcMotorEx frontRightMotor;
    public DcMotorEx backLeftMotor;
    public DcMotorEx backRightMotor;
    public CRServo linActServo;
    public CRServo mosaicMover;
    public CRServo pixelIn;
    public Servo pixelOut;
    public Servo stack;
    public CRServo droneLauncher;
    public CRServo intakeRotate;
    public CRServo intakeMove;
    public CRServo stackKnocker;
    public NormalizedColorSensor color1;
    public NormalizedColorSensor color2;
    public RevBlinkinLedDriver lights;
    public DistanceSensor dist;
    public DistanceSensor dist2;
    public DcMotorEx linearSlideLeft;
    public DcMotorEx linearSlideRight;
    public DcMotorEx intakeMotor;
    public DcMotorEx leftOdo;
    public DcMotorEx rightOdo;
    public DcMotorEx sideOdo;
    public DcMotorEx linearActuator;


    public Telemetry telemetry;


    public Robot(HardwareMap hardwareMap) {
        frontLeftMotor = hardwareMap.get(DcMotorEx.class,"frontLeftMotor");
        frontRightMotor = hardwareMap.get(DcMotorEx.class,"frontRightMotor");
        backLeftMotor = hardwareMap.get(DcMotorEx.class,"backLeftMotor");
        backRightMotor = hardwareMap.get(DcMotorEx.class,"backRightMotor");
        linActServo = hardwareMap.crservo.get("linActServo");
        mosaicMover = hardwareMap.crservo.get("mosaicMover");
        linearSlideLeft = hardwareMap.get(DcMotorEx.class,"linearSlideLeft");
        linearSlideRight = hardwareMap.get(DcMotorEx.class,"linearSlideRight");
        intakeMotor = hardwareMap.get(DcMotorEx.class,"intakeMotor");
        pixelIn = hardwareMap.crservo.get("pixelIn");
        pixelOut = hardwareMap.servo.get("pixelOut");
        droneLauncher = hardwareMap.crservo.get("dl");
        color1 = hardwareMap.get(NormalizedColorSensor.class, "color");
        color2 = hardwareMap.get(NormalizedColorSensor.class, "color2");
        intakeMove = hardwareMap.crservo.get("intakeMove");
        intakeRotate = hardwareMap.crservo.get("intakeRotate");
        linearActuator = hardwareMap.get(DcMotorEx.class,"linearActuator");
        dist = hardwareMap.get(DistanceSensor.class, "dist");
        dist2 = hardwareMap.get(DistanceSensor.class, "dist2");
        lights = hardwareMap.get(RevBlinkinLedDriver.class, "lights");
        stackKnocker = hardwareMap.crservo.get("stackKnocker");
        stack = hardwareMap.servo.get("Stack");

        leftOdo = hardwareMap.get(DcMotorEx.class, "frontLeftMotor");
        rightOdo = hardwareMap.get(DcMotorEx.class, "backRightMotor");
        sideOdo = hardwareMap.get(DcMotorEx.class, "backLeftMotor");


        linearSlideLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        linearSlideRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        intakeMove.setDirection(DcMotorSimple.Direction.FORWARD);
        intakeRotate.setDirection(DcMotorSimple.Direction.REVERSE);
        pixelIn.setDirection(DcMotorSimple.Direction.REVERSE);
        linearSlideLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        linearSlideRight.setDirection(DcMotorSimple.Direction.FORWARD);
        backLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backRightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontRightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontLeftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightOdo.setDirection(DcMotorSimple.Direction.REVERSE);
        sideOdo.setDirection(DcMotorSimple.Direction.REVERSE);




    }
}
