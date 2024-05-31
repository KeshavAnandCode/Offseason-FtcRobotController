package org.firstinspires.ftc.teamcode.Autonomous;

import android.graphics.Canvas;

import org.firstinspires.ftc.robotcore.internal.camera.calibration.CameraCalibration;
import org.firstinspires.ftc.vision.VisionProcessor;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

public class OpenCvTest implements VisionProcessor {

    Rect LEFT_RECTANGLE;
    Rect MIDDLE_RECTANGLE;
    Rect RIGHT_RECTANGLE;

    Mat hsvMat = new Mat();
    Mat lowMat = new Mat();
    Mat highMat = new Mat();
    Mat detectedMat = new Mat();

    Scalar lowerRedThresholdLow = new Scalar (0, 125, 125);
    Scalar lowerRedThresholdHigh = new Scalar (10, 255, 255);
    Scalar upperRedThresholdLow = new Scalar (165, 125, 125);
    Scalar upperRedThresholdHigh = new Scalar (180, 255, 255);
    double leftThreshold = 0.1;
    double middleThreshold = 0.1;
    double rightThreshold = 0.1;
    static int objectPosition = 0;


    @Override
    public void init(int width, int height, CameraCalibration cameraCalibration) {
        LEFT_RECTANGLE = new Rect(
               new Point(0,0),
               new Point(0.33 * width, height)
        );

        MIDDLE_RECTANGLE = new Rect(
                new Point(0.33*width,0),
                new Point(0.66 * width, height)
        );

        RIGHT_RECTANGLE = new Rect(
                new Point(0.66*width,0),
                new Point(1 * width, height)
        );


    }

    @Override
    public Object processFrame(Mat frame, long captureTimeNanos) {

        Imgproc.cvtColor(frame, hsvMat, Imgproc.COLOR_RGB2HSV);

        Core.inRange(hsvMat,lowerRedThresholdLow,lowerRedThresholdHigh,lowMat);
        Core.inRange(hsvMat,upperRedThresholdLow,upperRedThresholdHigh,highMat);

        Core.bitwise_or(lowMat,highMat,detectedMat);

        double leftPercent = (Core.sumElems(detectedMat.submat(LEFT_RECTANGLE)).val[0] / 255) / LEFT_RECTANGLE.area();
        double middlePercent = (Core.sumElems(detectedMat.submat(MIDDLE_RECTANGLE)).val[0] / 255) / MIDDLE_RECTANGLE.area();
        double rightPercent = (Core.sumElems(detectedMat.submat(RIGHT_RECTANGLE)).val[0] / 255) / RIGHT_RECTANGLE.area();

        if (leftPercent>middlePercent&&leftPercent>rightPercent&&leftPercent>leftThreshold){

            objectPosition = 1;

        } else if (middlePercent>leftPercent&&middlePercent>rightPercent&&middlePercent>middleThreshold){

            objectPosition = 2;

        } else if (rightPercent > leftPercent && rightPercent> middlePercent&&rightPercent>rightThreshold) {

            objectPosition = 3;

        } else {
            objectPosition = 0;
        }


        return null;
    }

    @Override
    public void onDrawFrame(Canvas canvas, int onscreenWidth, int onscreenHeight, float v, float v1, Object o) {

    }

    public static int getObjectPosition() {
        return objectPosition;
    }
}
