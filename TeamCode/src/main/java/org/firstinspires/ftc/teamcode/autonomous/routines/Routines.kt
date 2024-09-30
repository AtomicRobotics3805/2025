package org.firstinspires.ftc.teamcode.autonomous.routines

import com.rowanmcalpin.nextftc.command.Command
import com.rowanmcalpin.nextftc.command.groups.ParallelCommandGroup
import com.rowanmcalpin.nextftc.command.groups.SequentialCommandGroup
import com.rowanmcalpin.nextftc.command.utility.CustomCommand
import com.rowanmcalpin.nextftc.command.utility.Delay
import org.firstinspires.ftc.teamcode.mechanisms.Arm
import org.firstinspires.ftc.teamcode.mechanisms.Claw
import org.firstinspires.ftc.teamcode.mechanisms.Intake
import org.firstinspires.ftc.teamcode.mechanisms.IntakeExtension
import org.firstinspires.ftc.teamcode.mechanisms.Lift

object Routines {
    val autonomousInitializationRoutine: Command
        get() = SequentialCommandGroup(

        )

    val neutralToIntake: Command
        get() = SequentialCommandGroup(
            // If active intake tilting, delay 0.5 seconds then tilt down
            IntakeExtension.extensionOut
        )

    val intake: Command
        get() = SequentialCommandGroup(
            Intake.start,
            // Start "intake sensor watch sequence" -- don't set isDone to true until item is intook
            // Or delay for 1.5 seconds
            Delay(1.5),
            Intake.stop
        )

    val intakeToTransfer: Command
        get() = SequentialCommandGroup(
            ParallelCommandGroup(
                // If active intake tilting, tilt up
                IntakeExtension.extensionIn,
            ),
            Intake.reverse,
            Delay(1.0),
            Intake.stop
        )

    val transferToScoreHigh: Command
        get() = SequentialCommandGroup(
            Claw.clawClosed,
            ParallelCommandGroup(
                Arm.toScorePos,
                Lift.toHigh
            ),
            Claw.clawOpen
        )

    val scoreHighToNeutral: Command
        get() = SequentialCommandGroup(
            Claw.clawClosed,
            ParallelCommandGroup(
                Arm.toIntakePos,
                Lift.toLow
            ),
            Claw.clawOpen
        )
}