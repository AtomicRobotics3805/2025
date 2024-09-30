package org.firstinspires.ftc.teamcode.teleop

import com.rowanmcalpin.nextftc.command.Command
import com.rowanmcalpin.nextftc.command.groups.ParallelCommandGroup
import com.rowanmcalpin.nextftc.command.groups.SequentialCommandGroup
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
                IntakePivot.intakePivotUp
            ),
            ParallelCommandGroup(
                IntakeExtension.extensionIn,
                IntakePivot.intakePivotTransfer
            ),
            Claw.clawClosed,
            ParallelCommandGroup(
                Arm.toScorePos,
                Lift.toHigh,
                IntakePivot.intakePivotUp
            )
        )

    val scoreToRepeat: Command
        get() = SequentialCommandGroup(
            Claw.clawOpen,
            ParallelCommandGroup(
                Arm.toIntakePos,
                Lift.toLow
            )
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