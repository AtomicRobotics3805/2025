package org.firstinspires.ftc.teamcode.autonomous.routines

import com.rowanmcalpin.nextftc.Constants
import com.rowanmcalpin.nextftc.command.Command
import com.rowanmcalpin.nextftc.command.groups.ParallelCommandGroup
import com.rowanmcalpin.nextftc.command.groups.SequentialCommandGroup
import com.rowanmcalpin.nextftc.command.utility.Delay
import org.firstinspires.ftc.teamcode.autonomous.trajectories.TrajectoryFactory
import org.firstinspires.ftc.teamcode.mechanisms.Arm
import org.firstinspires.ftc.teamcode.mechanisms.Claw
import org.firstinspires.ftc.teamcode.mechanisms.IntakeExtension
import org.firstinspires.ftc.teamcode.mechanisms.IntakePivot
import org.firstinspires.ftc.teamcode.mechanisms.Lift

object Routines {
    /**
     * Initialization for all autonomous OpModes
     */
    val autonomousWithSampleInitializationRoutine: Command
        get() = ParallelCommandGroup(
            Arm.toIntakePos,
            SequentialCommandGroup(
                IntakePivot.intakePivotTransfer,
                Claw.close
            ),
            Lift.toIntake,
            IntakeExtension.extensionIn
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
            Lift.toIntake,
            Arm.toAscentOnePos
        )

    /**
     * Full routine for single sample w/ ascent park
     */
    val singleSampleAscentPark: Command
        get() = SequentialCommandGroup(
            leftStartToHighBasketScore,
            Claw.open,
            highBasketScoreToAscent
        )
}