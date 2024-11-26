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
import org.firstinspires.ftc.teamcode.mechanisms.IntakeSensor

object TeleOpRoutines {
    val outToIntake: Command
        get() = SequentialCommandGroup(
            IntakePivot.intakePivotTransfer,
            IntakeExtension.extensionOut,
            SequentialCommandGroup(
                IntakePivot.intakePivotUp,
                IntakePivot.intakePivotDownMore
            ),
            Intake.start,
            IntakeSensor.BlockUntilDetectedNoWatchdog(),
            Intake.stop
        )

    val slightlyOutToIntake: Command
        get() = SequentialCommandGroup(
            IntakePivot.intakePivotTransfer,
            IntakeExtension.extensionSlightlyOut,

            SequentialCommandGroup(
                IntakePivot.intakePivotUp,
                IntakePivot.intakePivotDownMore
            ),
            Intake.start,
            IntakeSensor.BlockUntilDetectedNoWatchdog(),
            Intake.stop
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

    val liftUp: Command
        get() = ParallelCommandGroup(
            SequentialCommandGroup(
                IntakeExtension.extensionSlightlyOut,
                IntakeExtension.extensionIn
            ),
            SequentialCommandGroup(
                Delay(1.5),
                Arm.toScorePos
            ),
            Lift.toHigh,
            IntakePivot.intakePivotUp
        )

    val scoreToRepeat: Command
        get() = SequentialCommandGroup(
            Claw.open,
            ParallelCommandGroup(
                Arm.toIntakePos,
                Lift.toIntake
            )
        )

    val specimenPickupToScore: Command
        get() = SequentialCommandGroup(
            Claw.close,
            Lift.toSpecimenScoreHigh,
            Arm.toSpecimenPreScorePos
        )

    val toHang: Command
        get() = SequentialCommandGroup(
            Lift.toHangHigh,
            Arm.toSpecimenPreScorePos
        )

    val toSpecimenPickup: Command
        get() = SequentialCommandGroup(
            ParallelCommandGroup(
                SequentialCommandGroup(
                    Delay(0.8),
                    Claw.specimenOpen,
                ),
                SequentialCommandGroup(
                    Delay(0.8),
                    Lift.toSpecimenPickup
                ),
                SequentialCommandGroup(
                    Arm.toSpecimenScorePos,
                    Delay(0.2),
                    Arm.toSpecimenPickupPos
                )
            )
        )

    val reset: Command
        get() = SequentialCommandGroup(
            ParallelCommandGroup(
                IntakePivot.intakePivotUp,
                Arm.toIntakePos,
                Claw.open
            ),
            IntakeExtension.extensionIn,
            Lift.toIntake
        )
}