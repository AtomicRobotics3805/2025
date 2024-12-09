package org.firstinspires.ftc.teamcode.autonomous.trajectories

import com.acmerobotics.roadrunner.geometry.Pose2d
import com.acmerobotics.roadrunner.geometry.Vector2d
import com.acmerobotics.roadrunner.trajectory.constraints.TrajectoryVelocityConstraint
import com.acmerobotics.roadrunner.trajectory.constraints.TranslationalVelocityConstraint
import com.rowanmcalpin.nextftc.Constants
import com.rowanmcalpin.nextftc.trajectories.ParallelTrajectory
import com.rowanmcalpin.nextftc.trajectories.TrajectoryFactory
import com.rowanmcalpin.nextftc.trajectories.rad
import org.firstinspires.ftc.robotcore.external.Const

object TrajectoryFactory: TrajectoryFactory() {
    // Starting positions
    val startPosY = 63.0
    val startPosLeft: Pose2d = Pose2d(36.0, startPosY, 270.rad)
    val startPosRight: Pose2d = Pose2d(-12.0, startPosY, 90.rad)

    //region SAMPLES
    // Score positions
    val basketHigh: Pose2d = Pose2d(53.5, 50.5, 225.rad)
    val basketLow: Pose2d = Pose2d(55.0, 55.0, 225.rad)


    // Intake positions
    val rightSample: Pose2d = Pose2d(49.0, 43.0, 270.rad)
    val centerSample: Pose2d = Pose2d(58.5, 50.5, 270.rad)
    val leftSample: Pose2d = Pose2d(60.25, 36.0, 320.rad)
    val leftSamplePickup: Pose2d = Pose2d(60.25, 34.0, 315.rad)

    // Trajectories
    // Left side
    lateinit var leftStartToHighBasket: ParallelTrajectory

    lateinit var highBasketToAscentPark: ParallelTrajectory
    lateinit var highBasketToZonePark: ParallelTrajectory
    lateinit var highBasketToRightSample: ParallelTrajectory

    lateinit var pickupRightSample: ParallelTrajectory
    lateinit var rightSampleToHighBasket: ParallelTrajectory
    lateinit var highBasketToCenterSample: ParallelTrajectory


    lateinit var pickupCenterSample: ParallelTrajectory
    lateinit var centerSampleToHighBasket: ParallelTrajectory
    lateinit var highBasketToLeftSample: ParallelTrajectory

    lateinit var leftSampleToHighBasket: ParallelTrajectory
    lateinit var leftSampleToLeftSamplePickup: ParallelTrajectory
    //endregion

    //region SPECIMENS
    val submersibleYPos = 22.0
    val submersibleYApproach = 35.0
    val highChamber1 = Pose2d(-10.0, submersibleYPos,  90.rad)
    val highChamber2 = Pose2d(-7.0, submersibleYPos, 90.rad)
    val highChamber3 = Pose2d(0.0, submersibleYPos, 90.rad)
    val highChamber4 = Pose2d(2.0, submersibleYPos, 90.rad)

    val observationZoneYPos = 50.0
    val transitPos1 = Pose2d(-36.0, 36.0, 270.rad)
    val transitPos2 = Pose2d(-36.0, 16.0, 270.rad)
    val leftColoredSample = Pose2d(-52.0, 12.0, 270.rad)
    val observationZoneLeft = Pose2d(-52.0, observationZoneYPos, 270.rad)

    val centerColoredSample = Pose2d(-61.0, 8.0, 270.rad)
    val observationZoneMiddle = Pose2d(-61.0, observationZoneYPos, 270.rad)

    val prepatorySpecimenPickupPos = Pose2d(-36.0, 50.0, 270.rad)
    val specimenPickupPosition = Pose2d(-36.0, 60.0, 270.rad)

    lateinit var rightStartToHighChamber1: ParallelTrajectory
    lateinit var highChamber1ToBringLeftSampleToObservationZone: ParallelTrajectory
    lateinit var observationZoneLeftToBringCenterSampleToObservationZone: ParallelTrajectory

    lateinit var observationZoneMiddleToSpecimenPickupPosition: ParallelTrajectory
    lateinit var specimenPickupPositionToHighChamber2: ParallelTrajectory

    lateinit var highChamber2ToSpecimenPickupPosition: ParallelTrajectory
    lateinit var specimenPickupPositionToHighChamber3: ParallelTrajectory
    lateinit var highChamber3ToSpecimenPickupPosition: ParallelTrajectory
    lateinit var specimenPickupPositionToHighChamber4: ParallelTrajectory

    lateinit var highChamber3ToPark: ParallelTrajectory
    lateinit var highChamber4ToPark: ParallelTrajectory

    lateinit var highChamber1ToPark: ParallelTrajectory

    //endregion

    // Park positions
    val ascentPark: Pose2d = Pose2d(22.0, 8.0, 0.rad)
    val observationZonePark: Pose2d = Pose2d(-50.0, 60.0, 0.rad)

    override fun initialize() {
        super.initialize()

        //region LEFT SIDE
        leftStartToHighBasket = Constants.drive.trajectoryBuilder(startPosLeft, 270.rad)
            .splineToLinearHeading(basketHigh, 45.rad).build()
        highBasketToAscentPark = Constants.drive.trajectoryBuilder(basketHigh)
            .splineToSplineHeading(ascentPark, 180.rad).build()
        highBasketToZonePark = Constants.drive.trajectoryBuilder(basketHigh)
            .splineToSplineHeading(observationZonePark, 180.rad).build()

        highBasketToRightSample = Constants.drive.trajectoryBuilder(basketHigh)
            .lineToSplineHeading(rightSample).build()
        pickupRightSample = Constants.drive.trajectoryBuilder(rightSample)
            .forward(8.0, TranslationalVelocityConstraint(40.0)).build()
        rightSampleToHighBasket = Constants.drive.trajectoryBuilder(pickupRightSample.end(), 90.rad)
            .splineToSplineHeading(basketHigh, 45.rad).build()

        highBasketToCenterSample = Constants.drive.trajectoryBuilder(basketHigh)
            .lineToLinearHeading(centerSample).build()
        pickupCenterSample = Constants.drive.trajectoryBuilder(centerSample)
            .forward(15.0, TranslationalVelocityConstraint(20.0)).build()
        centerSampleToHighBasket = Constants.drive.trajectoryBuilder(pickupCenterSample.end(), 90.rad)
            .lineToSplineHeading(basketHigh).build()

        highBasketToLeftSample = Constants.drive.trajectoryBuilder(basketHigh)
            .splineToSplineHeading(leftSample, 315.rad).build()
        leftSampleToLeftSamplePickup = Constants.drive.trajectoryBuilder(leftSample)
            .lineToLinearHeading(leftSamplePickup, TranslationalVelocityConstraint(40.0)).build()
        leftSampleToHighBasket = Constants.drive.trajectoryBuilder(leftSamplePickup, 135.rad)
            .splineToSplineHeading(basketHigh, 90.rad).build()
        //endregion

        //region RIGHT SIDE
        rightStartToHighChamber1 = Constants.drive.trajectoryBuilder(startPosRight, 270.rad)
            .splineToLinearHeading(Pose2d(highChamber1.x, submersibleYApproach, highChamber1.heading), 270.rad)
            .lineTo(highChamber1.vec()).build()

        highChamber1ToBringLeftSampleToObservationZone = Constants.drive.trajectoryBuilder(highChamber1, 90.rad)
            .lineTo(Vector2d(highChamber1.x, highChamber1.y + 4.0))
            .splineToSplineHeading(transitPos1, 270.rad)
            .splineToSplineHeading(transitPos2, 270.rad)
            .splineToConstantHeading(leftColoredSample.vec(), 90.rad, TranslationalVelocityConstraint(20.0))
            .lineTo(observationZoneLeft.vec())
            .build()

        observationZoneLeftToBringCenterSampleToObservationZone = Constants.drive.trajectoryBuilder(observationZoneLeft, 270.rad)
            .splineTo(leftColoredSample.vec(), 270.rad)
            .splineToConstantHeading(centerColoredSample.vec(), 90.rad, TranslationalVelocityConstraint(20.0))
            .lineTo(observationZoneMiddle.vec())
            .build()

        observationZoneMiddleToSpecimenPickupPosition = Constants.drive.trajectoryBuilder(observationZoneMiddle, 270.rad)
            .splineToConstantHeading(prepatorySpecimenPickupPos.vec(), 90.rad)
            .splineToConstantHeading(specimenPickupPosition.vec(), 90.rad)
            .build()
        specimenPickupPositionToHighChamber2 = Constants.drive.trajectoryBuilder(specimenPickupPosition)
            .splineToSplineHeading(Pose2d(highChamber2.x, submersibleYApproach, highChamber2.heading), 270.rad)
            .splineToLinearHeading(highChamber2, 270.rad).build()
        highChamber2ToSpecimenPickupPosition = Constants.drive.trajectoryBuilder(highChamber2, 90.rad)
            .splineToSplineHeading(prepatorySpecimenPickupPos, 90.rad)
            .splineToConstantHeading(specimenPickupPosition.vec(), 90.rad).build()
        specimenPickupPositionToHighChamber3 = Constants.drive.trajectoryBuilder(specimenPickupPosition)
            .splineToSplineHeading(Pose2d(highChamber3.x, submersibleYApproach, highChamber3.heading), 270.rad)
            .splineToLinearHeading(highChamber3, 270.rad).build()
        highChamber3ToSpecimenPickupPosition = Constants.drive.trajectoryBuilder(highChamber3, 90.rad)
            .splineToSplineHeading(prepatorySpecimenPickupPos, 90.rad)
            .splineToConstantHeading(specimenPickupPosition.vec(), 90.rad).build()
        specimenPickupPositionToHighChamber4 = Constants.drive.trajectoryBuilder(specimenPickupPosition)
            .splineToSplineHeading(Pose2d(highChamber4.x, submersibleYApproach, highChamber4.heading), 270.rad)
            .splineToLinearHeading(highChamber4, 270.rad).build()


        highChamber1ToPark = Constants.drive.trajectoryBuilder(highChamber1)
            .splineToSplineHeading(observationZonePark, 135.rad).build()

        highChamber3ToPark = Constants.drive.trajectoryBuilder(highChamber3)
            .splineToSplineHeading(observationZonePark, 135.rad).build()

        highChamber4ToPark = Constants.drive.trajectoryBuilder(highChamber4)
            .splineToSplineHeading(observationZonePark, 135.rad).build()


        //endregion

    }
}
