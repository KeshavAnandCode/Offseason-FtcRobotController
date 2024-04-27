package org.firstinspires.ftc.teamcode.Teleop.BehindTheScenes.Singletons;


import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.Teleop.BehindTheScenes.Constants.ColorSensorConstants;

import java.util.ArrayList;
import java.util.List;

public class ColorSensors {


    private static final ColorSensors instance = new ColorSensors();

    private ColorSensors(){}

    public static ColorSensors getInstance(){
        return instance;
    }

    public List<String> colors(Robot robot) {

        NormalizedRGBA colors = robot.color1.getNormalizedColors();
        NormalizedRGBA colors2 = robot.color2.getNormalizedColors();
        String colorIn1 = "Undefined";
        String colorIn2 = "Undefined";
        int numberInOne = 0;
        int numberInTwo = 0;
        String intendedColor = "";
        double colorSum = colors.red + colors.green + colors.blue;
        double colorSum2 = colors2.red + colors2.green + colors2.blue;
        boolean isPixelIn1 = false;
        boolean isPixelIn2 = false;
        double redPercentage = (100 * colors.red) / colorSum;
        double greenPercentage = (100 * colors.green) / colorSum;
        double bluePercentage = (100 * colors.blue) / colorSum;
        double redPercentage2 = (100 * colors2.red) / colorSum2;
        double greenPercentage2 = (100 * colors2.green) / colorSum2;
        double bluePercentage2 = (100 * colors2.blue) / colorSum2;
        if (((DistanceSensor) robot.color1).getDistance(DistanceUnit.INCH) < 0.7) {
            isPixelIn1 = true;
        }
        if (
                (isPixelIn1) &&
                        (greenPercentage > (ColorSensorConstants.WHITE_G - 3)) && (greenPercentage < ColorSensorConstants.WHITE_G + 3) &&
                        (redPercentage > (ColorSensorConstants.WHITE_R - 3)) && (redPercentage < ColorSensorConstants.WHITE_R + 3) &&
                        (bluePercentage > (ColorSensorConstants.WHITE_B - 3)) && (bluePercentage < ColorSensorConstants.WHITE_B + 3)) {
            colorIn1 = "white!";
        }
        if (
                (isPixelIn1) &&
                        (greenPercentage > ColorSensorConstants.YELLOW_G - 3) && (greenPercentage < ColorSensorConstants.YELLOW_G + 3) &&
                        (redPercentage > ColorSensorConstants.YELLOW_R - 3) && (redPercentage < ColorSensorConstants.YELLOW_R + 3) &&
                        (bluePercentage > ColorSensorConstants.YELLOW_B - 3) && (bluePercentage < ColorSensorConstants.YELLOW_B + 3)) {
            colorIn1 = "yellow!";
        }
        if (
                (isPixelIn1) &&
                        (greenPercentage > ColorSensorConstants.PURPLE_G - 3) && (greenPercentage < ColorSensorConstants.PURPLE_G + 3) &&
                        (redPercentage > ColorSensorConstants.PURPLE_R - 3) && (redPercentage < ColorSensorConstants.PURPLE_R + 3) &&
                        (bluePercentage > ColorSensorConstants.PURPLE_B - 3) && (bluePercentage < ColorSensorConstants.PURPLE_B + 3)) {
            colorIn1 = "purple!";
        }
        if (
                (isPixelIn1) &&
                        (greenPercentage > ColorSensorConstants.GREEN_G - 3) && (greenPercentage < ColorSensorConstants.GREEN_G + 3) &&
                        (redPercentage > ColorSensorConstants.GREEN_R - 3) && (redPercentage < ColorSensorConstants.GREEN_R + 3) &&
                        (bluePercentage > ColorSensorConstants.GREEN_B - 3) && (bluePercentage < ColorSensorConstants.GREEN_B + 3)) {
            colorIn1 = "green!";
        }

        //Color sensor 2
        if (
                (isPixelIn2) &&
                        (greenPercentage2 > ColorSensorConstants.WHITE_G - 3) && (greenPercentage2 < ColorSensorConstants.WHITE_G + 3) &&
                        (redPercentage2 > ColorSensorConstants.WHITE_R - 3) && (redPercentage2 < ColorSensorConstants.WHITE_R + 3) &&
                        (bluePercentage2 > ColorSensorConstants.WHITE_B - 3) && (bluePercentage2 < ColorSensorConstants.WHITE_B + 3)) {
            colorIn2 = "white!";
        }
        if (
                (isPixelIn2) &&
                        (greenPercentage2 > ColorSensorConstants.YELLOW_G - 3) && (greenPercentage2 < ColorSensorConstants.YELLOW_G + 3) &&
                        (redPercentage2 > ColorSensorConstants.YELLOW_R - 3) && (redPercentage2 < ColorSensorConstants.YELLOW_R + 3) &&
                        (bluePercentage2 > ColorSensorConstants.YELLOW_B - 3) && (bluePercentage2 < ColorSensorConstants.YELLOW_B + 3)) {
            colorIn2 = "yellow!";
        }
        if (
                (isPixelIn2) &&
                        (greenPercentage2 > ColorSensorConstants.PURPLE_G - 3) && (greenPercentage2 < ColorSensorConstants.PURPLE_G + 3) &&
                        (redPercentage2 > ColorSensorConstants.PURPLE_R - 3) && (redPercentage2 < ColorSensorConstants.PURPLE_R + 3) &&
                        (bluePercentage2 > ColorSensorConstants.PURPLE_B - 3) && (bluePercentage2 < ColorSensorConstants.PURPLE_B + 3)) {
            colorIn2 = "purple!";
        }
        if (
                (isPixelIn2) &&
                        (greenPercentage2 > ColorSensorConstants.GREEN_G - 3) && (greenPercentage2 < ColorSensorConstants.GREEN_G + 3) &&
                        (redPercentage2 > ColorSensorConstants.GREEN_R - 3) && (redPercentage2 < ColorSensorConstants.GREEN_R + 3) &&
                        (bluePercentage2 > ColorSensorConstants.GREEN_B - 3) && (bluePercentage2 < ColorSensorConstants.GREEN_B + 3)) {
            colorIn2 = "green!";
        }


        if (isPixelIn1 == true) {
            numberInOne = 1;
        } else {
            numberInOne = 0;
        }

        if (isPixelIn2 == true) {
            numberInTwo = 1;
        } else {
            numberInTwo = 0;
        }
        if (numberInTwo + numberInOne == 0) {
            intendedColor = "red";
        }
        if (numberInTwo + numberInOne == 1) {
            intendedColor = "yellow";
        }
        if (numberInTwo + numberInOne == 2) {
            intendedColor = "green";
        }




        List<String> returner = new ArrayList<>();
        returner.add(colorIn1);
        returner.add(colorIn2);
        returner.add(intendedColor);


        returner.add( "Pixel in Slot 1: "+ isPixelIn1);
        returner.add("Pixel Color in Slot 1: "+ colorIn1);
        returner.add("distance: "+ ((DistanceSensor) robot.color1).getDistance(DistanceUnit.INCH));
        returner.add("R "+((double) (Math.round(redPercentage*100))/100));
        returner.add("G "+((double) (Math.round(greenPercentage*100))/100));
        returner.add("B "+((double) (Math.round(bluePercentage*100))/100));
        //COlor senosr 2
        returner.add("Pixel in Slot 2: "+ isPixelIn2);
        returner.add("Pixel Color in Slot 2: "+ colorIn2);
        returner.add("distance: "+ ((DistanceSensor) robot.color2).getDistance(DistanceUnit.INCH));
        returner.add("R "+((double) (Math.round(redPercentage2*100))/100));
        returner.add("G "+((double) (Math.round(greenPercentage2*100))/100));
        returner.add("B "+((double) (Math.round(bluePercentage2*100))/100));
        returner.add(String.valueOf(isPixelIn1));
        returner.add(String.valueOf(isPixelIn2));
        return returner;
    }
}
