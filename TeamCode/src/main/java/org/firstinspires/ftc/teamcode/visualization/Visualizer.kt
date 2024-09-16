package org.firstinspires.ftc.teamcode.visualization

import com.noahbres.meepmeep.MeepMeep
import com.rowanmcalpin.nextftc.Constants
import com.rowanmcalpin.nextftc.command.groups.SequentialCommandGroup
import com.rowanmcalpin.nextftc.driving.drivers.MecanumDrive
import com.rowanmcalpin.nextftc.driving.localizers.TwoWheelOdometryLocalizer
import com.rowanmcalpin.nextftc.visualization.MeepMeepRobot
import com.rowanmcalpin.nextftc.visualization.MeepMeepVisualizer
import org.firstinspires.ftc.teamcode.autonomous.trajectories.TrajectoryFactory as tf
import org.firstinspires.ftc.teamcode.drive.DriveConstants
import org.firstinspires.ftc.teamcode.localization.OdometryConstants
fun main() {
    MeepMeepVisualizer.addRobot(
        MeepMeepRobot(
            MecanumDrive(
                DriveConstants,
                TwoWheelOdometryLocalizer(OdometryConstants)
            ) { tf.startPosLeft },
            18.0,
            18.0,
            {
                SequentialCommandGroup(
                    Constants.drive.followTrajectory(tf.leftStartToBasketHighScore),
                    Constants.drive.followTrajectory(tf.basketHighScoreToRightYellow),
                    Constants.drive.followTrajectory(tf.rightYellowToBasketHighScore),
                    Constants.drive.followTrajectory(tf.basketHighScoreToCenterYellow),
                    Constants.drive.followTrajectory(tf.centerYellowToBasketHighScore),
                    Constants.drive.followTrajectory(tf.basketHighScoreToLeftYellow),
                    Constants.drive.followTrajectory(tf.leftYellowToBasketHighScore),
                    Constants.drive.followTrajectory(tf.basketHighScoreToSubmersiblePark)
                )
            },
            Constants.Color.BLUE
        )
    )

    MeepMeepVisualizer.setMouseCoordinateDisplayPosition(520, 592)

    MeepMeepVisualizer.run(tf, background = MeepMeep.Background.FIELD_INTOTHEDEEP_JUICE_DARK)
}
