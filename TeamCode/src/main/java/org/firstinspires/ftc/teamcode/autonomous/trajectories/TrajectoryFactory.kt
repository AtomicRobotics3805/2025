package org.firstinspires.ftc.teamcode.autonomous.trajectories

import com.acmerobotics.roadrunner.geometry.Pose2d
import com.rowanmcalpin.nextftc.Constants
import com.rowanmcalpin.nextftc.trajectories.ParallelTrajectory
import com.rowanmcalpin.nextftc.trajectories.TrajectoryFactory
import com.rowanmcalpin.nextftc.trajectories.rad

object TrajectoryFactory: TrajectoryFactory() {
    // Starting positions
    val startPosY = 63.0
    val startPosLeft: Pose2d = Pose2d(36.0, startPosY, 270.rad)
    val startPosRight: Pose2d = Pose2d(-24.0, startPosY, 270.rad)

    // Score positions
    val basketHigh: Pose2d = Pose2d(52.0, 49.0, 225.rad)
    val basketLow: Pose2d = Pose2d(55.0, 55.0, 225.rad)

    val highChamber: Pose2d = Pose2d(-6.0, 36.0,  90.rad)

    // Park positions
    val ascentPark: Pose2d = Pose2d(22.0, 8.0, 0.rad)
    val observationZonePark: Pose2d = Pose2d(-50.0, 60.0, 270.rad)

    // Trajectories
    // Left side
    lateinit var leftStartToHighBasket: ParallelTrajectory
    lateinit var highBasketToAscentPark: ParallelTrajectory
    lateinit var highBasketToZonePark: ParallelTrajectory

    // Right side
    lateinit var rightStartToHighChamber: ParallelTrajectory
    lateinit var highChamberToZonePark: ParallelTrajectory


    override fun initialize() {
        super.initialize()

        // Left side
        leftStartToHighBasket = Constants.drive.trajectoryBuilder(startPosLeft, 270.rad)
            .splineToLinearHeading(basketHigh, 45.rad).build()
        highBasketToAscentPark = Constants.drive.trajectoryBuilder(basketHigh)
            .splineToSplineHeading(ascentPark, 180.rad).build()
        highBasketToZonePark = Constants.drive.trajectoryBuilder(basketHigh)
            .splineToSplineHeading(observationZonePark, 180.rad).build()

        // Right side
        rightStartToHighChamber = Constants.drive.trajectoryBuilder(startPosRight, 270.rad)
            .splineToLinearHeading(highChamber, 270.rad).build()
        highChamberToZonePark = Constants.drive.trajectoryBuilder(highChamber, 90.rad)
            .splineToSplineHeading(observationZonePark, 135.rad).build()
    }
}