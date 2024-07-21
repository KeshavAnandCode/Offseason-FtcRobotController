package com.example.meepmeep;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepMain {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 13)
                .build();

        RoadRunnerBotEntity myBot1 = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 13)
                .build();

        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(12, -62, Math.toRadians(90)))
                .splineToSplineHeading(new Pose2d(18, -22,0), Math.toRadians(0))
                .splineToSplineHeading(new Pose2d(50, -34,Math.toRadians(-179.99999)), Math.toRadians(0))
                        .waitSeconds(1)

                .splineTo(new Vector2d(22, -59), Math.toRadians(180))
                .splineTo(new Vector2d(-30, -59), Math.toRadians(180))
                .splineTo(new Vector2d(-58, -36), Math.toRadians(180))
                .waitSeconds(3)
                        .setReversed(true)
                .splineTo(new Vector2d(-30, -59), Math.toRadians(0))
                .splineTo(new Vector2d(22, -59), Math.toRadians(0))
                .splineTo(new Vector2d(50, -34), Math.toRadians(0))
                .waitSeconds(1)
                .splineTo(new Vector2d(22, -59), Math.toRadians(180))
                .splineTo(new Vector2d(-30, -59), Math.toRadians(180))
                .splineTo(new Vector2d(-58, -36), Math.toRadians(180))
                .waitSeconds(3)
                .setReversed(true)
                .splineTo(new Vector2d(-30, -59), Math.toRadians(0))
                .splineTo(new Vector2d(22, -59), Math.toRadians(0))
                .splineTo(new Vector2d(50, -34), Math.toRadians(0))
                .waitSeconds(1)










                .build());
        meepMeep.setBackground(MeepMeep.Background.FIELD_CENTERSTAGE_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}
