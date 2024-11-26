package org.firstinspires.ftc.teamcode.autonomous.routines

import com.rowanmcalpin.nextftc.Constants
import com.rowanmcalpin.nextftc.command.Command
import com.rowanmcalpin.nextftc.command.groups.ParallelCommandGroup
import com.rowanmcalpin.nextftc.command.groups.SequentialCommandGroup
import com.rowanmcalpin.nextftc.command.utility.Delay
import com.rowanmcalpin.nextftc.command.utility.StopOpModeCommand
import org.firstinspires.ftc.teamcode.autonomous.routines.Routines.scoreToIntake
import org.firstinspires.ftc.teamcode.autonomous.trajectories.TrajectoryFactory
import org.firstinspires.ftc.teamcode.mechanisms.Arm
import org.firstinspires.ftc.teamcode.mechanisms.Claw
import org.firstinspires.ftc.teamcode.mechanisms.Intake
import org.firstinspires.ftc.teamcode.mechanisms.IntakeExtension
import org.firstinspires.ftc.teamcode.mechanisms.IntakePivot
import org.firstinspires.ftc.teamcode.mechanisms.IntakeSensor
import org.firstinspires.ftc.teamcode.mechanisms.Lift

object SampleRoutines {
    /**
     * Left-side start to high basket score
     */
    val leftStartToHighBasketScore: Command
        get() = ParallelCommandGroup(
            Constants.drive.followTrajectory(TrajectoryFactory.leftStartToHighBasket),
            SequentialCommandGroup(
                IntakeExtension.extensionSlightlyOut,
                IntakeExtension.extensionIn
            ),
            Lift.toHigh,
            SequentialCommandGroup(
                Delay(1.5),
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

    val highBasketScoreToIntakeLeft: Command
        get() = ParallelCommandGroup(
            SequentialCommandGroup(
                IntakePivot.intakePivotUp,
                IntakeExtension.extensionMiddle,
                IntakePivot.intakePivotDown
            ),
            Intake.start,

            Constants.drive.followTrajectory(TrajectoryFactory.highBasketToLeftSample)
        )

    val inToTransfer: Command
        get() = SequentialCommandGroup(
            ParallelCommandGroup(
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

    val rightSampleToHighScore: Command
        get() = SequentialCommandGroup(
            ParallelCommandGroup(
                SequentialCommandGroup(
                    inToTransfer,
                    ParallelCommandGroup(
                        SequentialCommandGroup(
                            IntakeExtension.extensionSlightlyOut,
                            IntakeExtension.extensionIn
                        ),
                        Lift.toHigh,
                        SequentialCommandGroup(
                            Intake.reverse,
                            Delay(1.5),
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
                        SequentialCommandGroup(
                            IntakeExtension.extensionSlightlyOut,
                            IntakeExtension.extensionIn
                        ),
                        Lift.toHigh,
                        SequentialCommandGroup(
                            Intake.reverse,
                            Delay(1.5),
                            Arm.toScorePos
                        )
                    )
                ),
                Constants.drive.followTrajectory(TrajectoryFactory.centerSampleToHighBasket)
            ),
            Intake.stop
        )

    val leftSampleToHighScore: Command
        get() = SequentialCommandGroup(
            ParallelCommandGroup(
                SequentialCommandGroup(
                    inToTransfer,
                    ParallelCommandGroup(
                        SequentialCommandGroup(
                            IntakeExtension.extensionSlightlyOut,
                            IntakeExtension.extensionIn
                        ),
                        Lift.toHigh,
                        SequentialCommandGroup(
                            Intake.reverse,
                            Delay(2.0),
                            Arm.toScorePos
                        )
                    )
                ),
                Constants.drive.followTrajectory(TrajectoryFactory.leftSampleToHighBasket)
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
            scoreToIntake,
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
            scoreToIntake,
            highBasketScoreToIntakeRight,
            ParallelCommandGroup(
                IntakeSensor.BlockUntilDetected(),
                Constants.drive.followTrajectory(TrajectoryFactory.pickupRightSample)
            ),
            rightSampleToHighScore,
            scoreToIntake,
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

    val fourSampleAscentPark: Command
        get() = SequentialCommandGroup(
            leftStartToHighBasketScore,
            Claw.open,
            ParallelCommandGroup(
                scoreToIntake,
                Intake.start,
                highBasketScoreToIntakeRight,
                SequentialCommandGroup(
                    Delay(1.25),
                    Constants.drive.followTrajectory(TrajectoryFactory.pickupRightSample),
                    IntakeSensor.BlockUntilDetected(),
                    Intake.stop
                )
            ),
            rightSampleToHighScore,
            Claw.open,
            ParallelCommandGroup(
                scoreToIntake,
                Intake.start,
                highBasketScoreToIntakeRight,
                SequentialCommandGroup(
                    Delay(1.25),
                    Constants.drive.followTrajectory(TrajectoryFactory.pickupCenterSample),
                    IntakeSensor.BlockUntilDetected(),
                    Intake.stop
                )
            ),
            centerSampleToHighScore,
            Claw.open,
            ParallelCommandGroup(
                scoreToIntake,
                Intake.start,
                highBasketScoreToIntakeLeft,
                SequentialCommandGroup(
                    Delay(1.25),
                    Constants.drive.followTrajectory(TrajectoryFactory.leftSampleToLeftSamplePickup),
                    IntakeSensor.BlockUntilDetected(),
                    Intake.stop
                )
            ),
            leftSampleToHighScore,
            // Final score and park
            Claw.open,
            highBasketScoreToAscent,
            Delay(1.0),
            StopOpModeCommand()
        )

}