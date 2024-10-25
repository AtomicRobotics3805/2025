package org.firstinspires.ftc.teamcode.autonomous.routines

import com.acmerobotics.roadrunner.trajectory.Trajectory
import com.rowanmcalpin.nextftc.Constants
import com.rowanmcalpin.nextftc.command.Command
import com.rowanmcalpin.nextftc.command.groups.ParallelCommandGroup
import com.rowanmcalpin.nextftc.command.groups.SequentialCommandGroup
import com.rowanmcalpin.nextftc.command.utility.Delay
import com.rowanmcalpin.nextftc.command.utility.StopOpModeCommand
import org.firstinspires.ftc.teamcode.autonomous.trajectories.TrajectoryFactory
import org.firstinspires.ftc.teamcode.mechanisms.Arm
import org.firstinspires.ftc.teamcode.mechanisms.Claw
import org.firstinspires.ftc.teamcode.mechanisms.Intake
import org.firstinspires.ftc.teamcode.mechanisms.IntakeExtension
import org.firstinspires.ftc.teamcode.mechanisms.IntakePivot
import org.firstinspires.ftc.teamcode.mechanisms.IntakeSensor
import org.firstinspires.ftc.teamcode.mechanisms.Lift
import org.firstinspires.ftc.teamcode.mechanisms.Lights

object Routines {
    /**
     * Initialization for all autonomous OpModes
     */
    val autonomousWithSampleInitializationRoutine: Command
        get() = ParallelCommandGroup(
            Lift.LiftControl(),
            IntakeExtension.IntakeExtensionControl(),

            Arm.toIntakePos,
            SequentialCommandGroup(
                IntakePivot.intakePivotTransfer,
                Claw.close
            ),
//            Lift.Zero(),
            IntakeExtension.zero,
            Lights.DisplayColor()
        )

    val autonomousWithSpecimenInitializationRoutine: Command
        get() = ParallelCommandGroup(
            SequentialCommandGroup(
                IntakePivot.intakePivotTransfer,
                Claw.close
            ),
            Lift.toIntake,
            IntakeExtension.extensionIn
        )

    /**
     * Left-side start to high basket score
     */
    val leftStartToHighBasketScore: Command
        get() = ParallelCommandGroup(
            Constants.drive.followTrajectory(TrajectoryFactory.leftStartToHighBasket),
            Lift.toHigh,
            SequentialCommandGroup(
                Delay(1.0),
                Arm.toScorePos
            )
        )

    /**
     * Score to ascent pos
     */
    val highBasketScoreToAscent: Command
        get() = ParallelCommandGroup(
            Constants.drive.followTrajectory(TrajectoryFactory.highBasketToAscentPark),
            SequentialCommandGroup(
                Lift.toIntake,
                Lift.Zero()
            ),
            Arm.toAscentOnePos
        )

    val highBasketScoreToIntakeRight: Command
        get() = ParallelCommandGroup(
            SequentialCommandGroup(
                IntakePivot.intakePivotUp,
                IntakeExtension.extensionSlightlyOut,
                IntakePivot.intakePivotDownMore
            ),
            Intake.start,

            Constants.drive.followTrajectory(TrajectoryFactory.highBasketToRightSample)
        )

    val highBasketScoreToIntakeCenter: Command
        get() = ParallelCommandGroup(
            SequentialCommandGroup(
                IntakePivot.intakePivotUp,
                IntakeExtension.extensionSlightlyOut,
                IntakePivot.intakePivotDownMore
            ),
            Intake.start,

            Constants.drive.followTrajectory(TrajectoryFactory.highBasketToCenterSample)
        )

    val inToTransfer: Command
        get() = SequentialCommandGroup(
            ParallelCommandGroup(
                Intake.stop,
                IntakePivot.intakePivotUp,
                Lift.aLittleHigh,
                Claw.open,
                SequentialCommandGroup(
                    Delay(0.5),
                    Arm.toIntakePos
                )
            ),
            IntakeExtension.extensionIn,
            IntakePivot.intakePivotTransfer,
            Lift.toIntake,
            Claw.close
        )

    val scoreToRepeat: Command
        get() = SequentialCommandGroup(
            Claw.open,
            ParallelCommandGroup(
                Arm.toIntakePos,
                Lift.toIntake
            )
        )

    val rightSampleToHighScore: Command
        get() = SequentialCommandGroup(
            ParallelCommandGroup(
                SequentialCommandGroup(
                    inToTransfer,
                    ParallelCommandGroup(
                        Lift.toHigh,
                        SequentialCommandGroup(
                            Delay(0.5),
                            Intake.reverse,
                            Arm.toScorePos
                        )
                    )
                ),
                Constants.drive.followTrajectory(TrajectoryFactory.rightSampleToHighBasket)
            ),
            Intake.stop
        )

    val centerSampleToHighScore: Command
        get() = SequentialCommandGroup(
            ParallelCommandGroup(
                SequentialCommandGroup(
                    inToTransfer,
                    ParallelCommandGroup(
                        Lift.toHigh,
                        SequentialCommandGroup(
                            Delay(0.5),
                            Arm.toScorePos,
                            Intake.reverse
                        )
                    )
                ),
                Constants.drive.followTrajectory(TrajectoryFactory.centerSampleToHighBasket)
            ),
            Intake.stop
        )

    /**
     * Full routine for single sample w/ ascent park
     */
    val singleSampleAscentPark: Command
        get() = SequentialCommandGroup(
            leftStartToHighBasketScore,
            Claw.open,
            highBasketScoreToAscent,
            Delay(1.0),
            StopOpModeCommand()
        )

    val twoSampleAscentPark: Command
        get() = SequentialCommandGroup(
            leftStartToHighBasketScore,
            scoreToRepeat,
            highBasketScoreToIntakeRight,
            ParallelCommandGroup(
                IntakeSensor.BlockUntilDetected(),
                Constants.drive.followTrajectory(TrajectoryFactory.pickupRightSample)
            ),
            rightSampleToHighScore,
            Claw.open,
            highBasketScoreToAscent,
            Delay(1.0),
            StopOpModeCommand()
        )

    val threeSampleAscentPark: Command
        get() = SequentialCommandGroup(
            leftStartToHighBasketScore,
            scoreToRepeat,
            highBasketScoreToIntakeRight,
            ParallelCommandGroup(
                IntakeSensor.BlockUntilDetected(),
                Constants.drive.followTrajectory(TrajectoryFactory.pickupRightSample)
            ),
            rightSampleToHighScore,
            scoreToRepeat,
            highBasketScoreToIntakeCenter,
            ParallelCommandGroup(
                IntakeSensor.BlockUntilDetected(),
                Constants.drive.followTrajectory(TrajectoryFactory.pickupCenterSample)
            ),
            centerSampleToHighScore,
            Claw.open,
            highBasketScoreToAscent,
            Delay(1.0),
            StopOpModeCommand()
        )
}