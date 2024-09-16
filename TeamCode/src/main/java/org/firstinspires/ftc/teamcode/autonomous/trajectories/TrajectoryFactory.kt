package org.firstinspires.ftc.teamcode.autonomous.trajectories

import com.acmerobotics.roadrunner.geometry.Pose2d
import com.rowanmcalpin.nextftc.Constants
import com.rowanmcalpin.nextftc.trajectories.ParallelTrajectory
import com.rowanmcalpin.nextftc.trajectories.TrajectoryFactory
import com.rowanmcalpin.nextftc.trajectories.rad
import com.rowanmcalpin.nextftc.Constants.drive as d

object TrajectoryFactory: TrajectoryFactory() {
    //region POSITIONS
    // Starting positions
    val startPosY = 60.0
    val startPosLeft: Pose2d = Pose2d(40.0, startPosY, 270.0.rad)

    val startPosRight: Pose2d = Pose2d(-24.0, startPosY, 270.0.rad)

    // Intake positions
    val leftYellow: Pose2d = Pose2d(55.0, 45.0, 315.0.rad)
    val centerYellow: Pose2d = Pose2d(58.0, 45.0, 270.0.rad)
    val rightYellow: Pose2d = Pose2d(48.0, 45.0, 270.0.rad)

    // Scoring positions
    val basketHigh: Pose2d = Pose2d(55.0, 55.0, 225.0.rad)
    val basketLow: Pose2d = Pose2d(55.0, 55.0, 225.0.rad)

    // Parking positions
    val submersiblePark: Pose2d = Pose2d(24.0, 12.0, 180.0.rad)
    val observationPark: Pose2d = Pose2d(-35.0, 60.0, 270.0.rad)

    //endregion
    //region TRAJECTORIES
    lateinit var leftStartToRightYellow: ParallelTrajectory
    lateinit var leftStartToBasketHighScore: ParallelTrajectory

    // Yellow zig-zag
    lateinit var basketHighScoreToRightYellow: ParallelTrajectory
    lateinit var rightYellowToBasketHighScore: ParallelTrajectory
    lateinit var basketHighScoreToCenterYellow: ParallelTrajectory
    lateinit var centerYellowToBasketHighScore: ParallelTrajectory
    lateinit var basketHighScoreToLeftYellow: ParallelTrajectory
    lateinit var leftYellowToBasketHighScore: ParallelTrajectory

    // Parking
    lateinit var basketHighScoreToSubmersiblePark: ParallelTrajectory
    lateinit var basketHighScoreToObservationPark: ParallelTrajectory
    //endregion

    override fun initialize() {
        super.initialize()

        // Probably not needed
        leftStartToRightYellow = d.trajectoryBuilder(startPosLeft)
            .splineToSplineHeading(rightYellow, 315.0.rad).build()

        leftStartToBasketHighScore = d.trajectoryBuilder(startPosLeft, 270.0.rad)
            .splineToLinearHeading(basketHigh, 45.0.rad).build()

        basketHighScoreToRightYellow = d.trajectoryBuilder(basketHigh)
            .splineToSplineHeading(rightYellow, 270.0.rad).build()

        rightYellowToBasketHighScore = d.trajectoryBuilder(rightYellow, 90.0.rad)
            .splineToSplineHeading(basketHigh, 45.0.rad).build()

        basketHighScoreToCenterYellow = d.trajectoryBuilder(basketHigh)
            .splineToSplineHeading(centerYellow, 315.0.rad).build()

        centerYellowToBasketHighScore = d.trajectoryBuilder(centerYellow, 135.0.rad)
            .splineToSplineHeading(basketHigh, 45.0.rad).build()

        basketHighScoreToLeftYellow = d.trajectoryBuilder(basketHigh)
            .splineToSplineHeading(leftYellow, 315.0.rad).build()

        leftYellowToBasketHighScore = d.trajectoryBuilder(leftYellow, 135.0.rad)
            .splineToSplineHeading(basketHigh, 45.0.rad).build()

        basketHighScoreToSubmersiblePark = d.trajectoryBuilder(basketHigh)
            .splineToSplineHeading(submersiblePark, 180.0.rad).build()
    }
}