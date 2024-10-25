package org.firstinspires.ftc.teamcode.visualization

import org.rowlandhall.meepmeep.MeepMeep
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
            14.0,
            18.0,
            {
                SequentialCommandGroup(
                    Constants.drive.followTrajectory(tf.leftStartToHighBasket),
                    Constants.drive.followTrajectory(tf.highBasketToRightSample),
                    Constants.drive.followTrajectory(tf.rightSampleToHighBasket),
                    Constants.drive.followTrajectory(tf.highBasketToCenterSample),
                    Constants.drive.followTrajectory(tf.centerSampleToHighBasket),
                    Constants.drive.followTrajectory(tf.highBasketToAscentPark)
                )
            },
            Constants.Color.BLUE
        )
    )

    MeepMeepVisualizer.setMouseCoordinateDisplayPosition(520, 592)

    MeepMeepVisualizer.run(tf, background = MeepMeep.Background.FIELD_INTOTHEDEEP_JUICE_DARK)
}
