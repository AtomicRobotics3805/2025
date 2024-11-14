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

/**
 * Routines that both specimen and sample opmodes are likely to use
 */
object Routines {
    val autonomousWithSampleInitializationRoutine: Command
        get() = ParallelCommandGroup(
            Lift.LiftControl(),
            IntakeExtension.IntakeExtensionControl(),

            Arm.toIntakePos,
            SequentialCommandGroup(
                IntakePivot.intakePivotTransfer,
                Claw.close
            ),

            IntakeExtension.zero,
            Lights.DisplayColor()
        )

    val autonomousWithSpecimenInitializationRoutine: Command
        get() = ParallelCommandGroup(
            Lift.LiftControl(),
            IntakeExtension.IntakeExtensionControl(),

            // DO NOT POWER ARM
            SequentialCommandGroup(
                IntakePivot.intakePivotUp,
                Claw.close
            ),

            IntakeExtension.zero,
            Lights.DisplayColor()
        )

    val scoreToRepeat: Command
        get() = SequentialCommandGroup(
            Claw.open,
            ParallelCommandGroup(
                Arm.toIntakePos,
                Lift.aLittleHigh
            )
        )
}