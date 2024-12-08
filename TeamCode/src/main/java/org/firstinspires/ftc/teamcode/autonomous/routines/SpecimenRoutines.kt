package org.firstinspires.ftc.teamcode.autonomous.routines

import com.acmerobotics.roadrunner.trajectory.Trajectory
import com.rowanmcalpin.nextftc.Constants
import com.rowanmcalpin.nextftc.command.Command
import com.rowanmcalpin.nextftc.command.groups.ParallelCommandGroup
import com.rowanmcalpin.nextftc.command.groups.SequentialCommandGroup
import com.rowanmcalpin.nextftc.command.utility.Delay
import com.rowanmcalpin.nextftc.command.utility.StopOpModeCommand
import org.firstinspires.ftc.teamcode.autonomous.routines.Routines.scoreToBottom
import org.firstinspires.ftc.teamcode.autonomous.routines.Routines.scoreToIntake
import org.firstinspires.ftc.teamcode.autonomous.routines.Routines.scoreToSpecPickup
import org.firstinspires.ftc.teamcode.autonomous.trajectories.TrajectoryFactory
import org.firstinspires.ftc.teamcode.mechanisms.Arm
import org.firstinspires.ftc.teamcode.mechanisms.Claw
import org.firstinspires.ftc.teamcode.mechanisms.IntakeExtension
import org.firstinspires.ftc.teamcode.mechanisms.Lift

object SpecimenRoutines {

    val rightStartToFirstSpec: Command
        get() = ParallelCommandGroup(
            SequentialCommandGroup(
                Delay(0.2),
                Constants.drive.followTrajectory(TrajectoryFactory.rightStartToHighChamber1),
            ),
            SequentialCommandGroup(
                Delay(1.25),
                Claw.open
            ),
            Lift.toFirstSpecimenAuton,
            Arm.toSpecimenScorePos,
            SequentialCommandGroup(
                IntakeExtension.extensionMiddle,
                IntakeExtension.extensionIn
            )
        )

    val firstSpecToPushFirstSample: Command
        get() = ParallelCommandGroup(
            Constants.drive.followTrajectory(TrajectoryFactory.highChamber1ToBringLeftSampleToObservationZone),
            SequentialCommandGroup(
                Delay(1.0),
                Arm.toSpecimenPickupPos,
            ),
            Lift.toSpecimenPickup,
            Claw.specimenOpen
        )

    val pushFirstSampleToPushSecondSample: Command
        get() = ParallelCommandGroup(
            Constants.drive.followTrajectory(TrajectoryFactory.observationZoneLeftToBringCenterSampleToObservationZone)
        )

    val pushSecondSampleToGrabSecondSpec: Command
        get() = SequentialCommandGroup(
            Constants.drive.followTrajectory(TrajectoryFactory.observationZoneMiddleToSpecimenPickupPosition),
            Claw.close
        )

    val secondSpec: Command
        get() = ParallelCommandGroup(
            Constants.drive.followTrajectory(TrajectoryFactory.specimenPickupPositionToHighChamber2),
            SequentialCommandGroup(
                Delay(2.9),
                ParallelCommandGroup(
                    Claw.open,
                    Arm.toSpecimenPickupPos
                )
            ),
            Lift.toAutonSpecimenScoreHigh,
            Arm.toSpecimenScorePos,
            IntakeExtension.extensionIn
        )

    val secondSpecToGrabThirdSpec: Command
        get() = ParallelCommandGroup(
            SequentialCommandGroup(
                Constants.drive.followTrajectory(TrajectoryFactory.highChamber2ToSpecimenPickupPosition),
                Claw.close
            ),
            SequentialCommandGroup(
                Delay(1.0),
                Lift.toSpecimenPickup,
                Claw.specimenOpen
            )
        )

    val thirdSpec: Command
        get() = ParallelCommandGroup(
            Constants.drive.followTrajectory(TrajectoryFactory.specimenPickupPositionToHighChamber3),
            SequentialCommandGroup(
                Delay(2.9),
                Claw.open
            ),
            Lift.toAutonSpecimenScoreHigh,
            Arm.toSpecimenScorePos
        )

    val thirdSpecToGrabFourthSpec: Command
        get() = ParallelCommandGroup(
            SequentialCommandGroup(
                Constants.drive.followTrajectory(TrajectoryFactory.highChamber3ToSpecimenPickupPosition),
                Claw.close
            ),
            SequentialCommandGroup(
                Delay(1.0),
                Lift.toSpecimenPickup,
                Claw.specimenOpen
            ),
            Arm.toSpecimenPickupPos
        )
    val fourthSpec: Command
        get() = ParallelCommandGroup(
            Constants.drive.followTrajectory(TrajectoryFactory.specimenPickupPositionToHighChamber4),
            SequentialCommandGroup(
                Delay(2.9),
                Claw.open
            ),
            Lift.toAutonSpecimenScoreHigh,
            Arm.toSpecimenScorePos
        )

    val thirdSpecToPark: Command
        get() = ParallelCommandGroup(
            Constants.drive.followTrajectory(TrajectoryFactory.highChamber3ToPark),
            IntakeExtension.extensionIn,
            SequentialCommandGroup(
                Claw.open,
                ParallelCommandGroup(
                    Arm.toIntakePos,
                    Lift.toIntake
                ),
                IntakeExtension.Zero()
            )
        )

    val fourthSpecToPark: Command
        get() = ParallelCommandGroup(
            Constants.drive.followTrajectory(TrajectoryFactory.highChamber4ToPark),
            IntakeExtension.extensionIn,
            SequentialCommandGroup(
                Claw.open,
                ParallelCommandGroup(
                    Arm.toIntakePos,
                    Lift.toIntake
                ),
                IntakeExtension.Zero()
            )
        )


    val fourSpecimenPlusPark: Command
        get() = SequentialCommandGroup(
            rightStartToFirstSpec,
            firstSpecToPushFirstSample,
            pushFirstSampleToPushSecondSample,
            pushSecondSampleToGrabSecondSpec,
            secondSpec,
            secondSpecToGrabThirdSpec,
            thirdSpec,
            thirdSpecToGrabFourthSpec,
            fourthSpec,
            fourthSpecToPark,
            StopOpModeCommand()
        )

    val threeSpecimenPlusPark: Command
        get() = SequentialCommandGroup(
            rightStartToFirstSpec,
            firstSpecToPushFirstSample,
            pushFirstSampleToPushSecondSample,
            pushSecondSampleToGrabSecondSpec,
            secondSpec,
            secondSpecToGrabThirdSpec,
            thirdSpec,
            thirdSpecToPark,
            StopOpModeCommand()
        )

//    val rightStartToSpecimenScore: Command
//        get() = ParallelCommandGroup(
//                SequentialCommandGroup(
//                    Delay(0.25),
//                    IntakeExtension.extensionMiddle,
//                    IntakeExtension.extensionIn
//                ),
//                SequentialCommandGroup(
////                    Delay(2.0),
//                    Lift.toAutonSpecimenScoreHigh
//                ),
//                Constants.drive.followTrajectory(TrajectoryFactory.rightStartToHighChamber1),
//                Arm.toScorePos
//            )
//
//    val specimenScore1ToBringSamples: Command
//        get() = SequentialCommandGroup(
//            ParallelCommandGroup(
//                Constants.drive.followTrajectory(TrajectoryFactory.highChamber1Score),
//                Claw.open
//            ),
//            ParallelCommandGroup(
//                scoreToBottom,
//                Constants.drive.followTrajectory(TrajectoryFactory.highChamber1ToBringLeftSampleToObservationZone),
//            ),
//            Constants.drive.followTrajectory(TrajectoryFactory.firstSampleBringingSecondPart),
//            Constants.drive.followTrajectory(TrajectoryFactory.observationZoneLeftToBringCenterSampleToObservationZone),
//            Constants.drive.followTrajectory(TrajectoryFactory.secondSampleBringingSecondPart)
//        )
//
//    val secondSpecimenPickupToScore: Command
//        get() = SequentialCommandGroup(
//            ParallelCommandGroup(
//                SequentialCommandGroup(
//                    Delay(1.0),
//                    Constants.drive.followTrajectory(TrajectoryFactory.observationZoneMiddleToSpecimenPickupPosition),
//                ),
//                Lift.toSpecimenPickup,
//                Arm.toSpecimenPickupPos,
//                SequentialCommandGroup(
//                    Delay(1.0),
//                    Claw.specimenOpen
//                )
//            ),
//            Claw.close,
//            ParallelCommandGroup(
//                Lift.toAutonSpecimenScoreHigh,
//                SequentialCommandGroup(
//                    Delay(0.5),
//                    Constants.drive.followTrajectory(TrajectoryFactory.specimenPickupPositionToHighChamber2),
//                ),
//                SequentialCommandGroup(
//                    Delay(2.0),
//                    Arm.toScorePos
//                )
//            ),
//            ParallelCommandGroup(
//                Constants.drive.followTrajectory(TrajectoryFactory.highChamber2Score),
//                Claw.open
//            )
//        )
//
//    val secondSpecimenScoreToThirdSpecimenScore: Command
//        get() = SequentialCommandGroup(
//            ParallelCommandGroup(
//                Constants.drive.followTrajectory(TrajectoryFactory.highChamber2Score),
//                Claw.open
//            ),
//        )
//
//    val specimenScore1ToPark: Command
//        get() = ParallelCommandGroup(
//            SequentialCommandGroup(
//                Constants.drive.followTrajectory(TrajectoryFactory.highChamber1ToPark)
//            ),
//            SequentialCommandGroup(
//                Delay(0.5),
//                Claw.open,
//                scoreToBottom,
//            )
//        )
//
//    val singleSpecimenWithObservationZonePark: Command
//        get() = SequentialCommandGroup(
//            rightStartToSpecimenScore,
//            specimenScore1ToPark,
//            Delay(1.0),
//            StopOpModeCommand()
//        )
//
//    val biggerSpecimenAuton: Command
//        get() = SequentialCommandGroup(
//            rightStartToSpecimenScore,
//            specimenScore1ToBringSamples,
//            secondSpecimenPickupToScore
//        )

}