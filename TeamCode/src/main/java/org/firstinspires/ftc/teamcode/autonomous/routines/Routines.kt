package org.firstinspires.ftc.teamcode.autonomous.routines

import com.rowanmcalpin.nextftc.command.Command
import com.rowanmcalpin.nextftc.command.groups.ParallelCommandGroup
import com.rowanmcalpin.nextftc.command.groups.SequentialCommandGroup
import com.rowanmcalpin.nextftc.subsystems.DisplayRobot
import org.firstinspires.ftc.teamcode.mechanisms.Arm
import org.firstinspires.ftc.teamcode.mechanisms.Claw
import org.firstinspires.ftc.teamcode.mechanisms.Intake
import org.firstinspires.ftc.teamcode.mechanisms.IntakeExtension
import org.firstinspires.ftc.teamcode.mechanisms.IntakePivot
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

            IntakeExtension.Zero(),
            Lights.DisplayColor(),

            DisplayRobot()
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

            IntakeExtension.Zero(),
            Lights.DisplayColor(),

            DisplayRobot()
        )

    val scoreToIntake: Command
        get() = SequentialCommandGroup(
            Claw.open,
            ParallelCommandGroup(
                Arm.toIntakePos,
                Lift.aLittleHigh
            ),
            Intake.start
        )

    val scoreToBottom: Command
        get() = SequentialCommandGroup(
            Claw.open,
            ParallelCommandGroup(
                Arm.toIntakePos,
                Lift.toIntake
            )
        )

    val scoreToSpecPickup: Command
        get() = SequentialCommandGroup(
            Claw.open,
            ParallelCommandGroup(
                Arm.toSpecimenPickupPos,
                Lift.toSpecimenPickup
            )
        )
}