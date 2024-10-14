package org.firstinspires.ftc.teamcode.teleop

import com.rowanmcalpin.nextftc.command.Command
import com.rowanmcalpin.nextftc.command.groups.ParallelCommandGroup
import com.rowanmcalpin.nextftc.command.groups.SequentialCommandGroup
import com.rowanmcalpin.nextftc.command.utility.Delay
import org.firstinspires.ftc.teamcode.mechanisms.Arm
import org.firstinspires.ftc.teamcode.mechanisms.Claw
import org.firstinspires.ftc.teamcode.mechanisms.Intake
import org.firstinspires.ftc.teamcode.mechanisms.IntakeExtension
import org.firstinspires.ftc.teamcode.mechanisms.IntakePivot
import org.firstinspires.ftc.teamcode.mechanisms.Lift

object TeleOpRoutines {
    val outToIntake: Command
        get() = SequentialCommandGroup(
            IntakeExtension.extensionOut,
            IntakePivot.intakePivotDown,
            Intake.start
        )

    val inToTransfer: Command
        get() = SequentialCommandGroup(
            ParallelCommandGroup(
                Intake.stop,
                IntakePivot.intakePivotUp,
                Lift.aLittleHigh,
                Claw.clawOpen,
                Arm.toIntakePos
            ),
            IntakeExtension.extensionIn,
            IntakePivot.intakePivotTransfer,
            Lift.toLow,
            Claw.clawClosed
        )

    val liftUp: Command
        get() = ParallelCommandGroup(
            Arm.toScorePos,
            Lift.toHigh,
            IntakePivot.intakePivotUp
        )

    val scoreToRepeat: Command
        get() = SequentialCommandGroup(
            Claw.clawOpen,
            ParallelCommandGroup(
                Arm.toIntakePos,
                Lift.toLow
            )
        )

    val specimenPickupToScore: Command
        get() = SequentialCommandGroup(
            Claw.clawClosed,
            Lift.toSampleScoreHigh
        )

    val toSpecimenPickup: Command
        get() = SequentialCommandGroup(
            ParallelCommandGroup(
                SequentialCommandGroup(
                    Delay(0.5),
                    Claw.clawOpen,
                ),
                Lift.toSamplePickup
            ),
            Arm.toSamplePickupPos
        )

    val reset: Command
        get() = SequentialCommandGroup(
            ParallelCommandGroup(
                IntakePivot.intakePivotUp,
                Arm.toIntakePos,
                Claw.clawOpen
            ),
            IntakeExtension.extensionIn,
            Lift.toLow
        )
}