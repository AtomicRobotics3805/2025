package org.firstinspires.ftc.teamcode.autonomous.routines

import com.rowanmcalpin.nextftc.Constants
import com.rowanmcalpin.nextftc.command.Command
import com.rowanmcalpin.nextftc.command.groups.ParallelCommandGroup
import com.rowanmcalpin.nextftc.command.groups.SequentialCommandGroup
import com.rowanmcalpin.nextftc.command.utility.Delay
import com.rowanmcalpin.nextftc.command.utility.StopOpModeCommand
import org.firstinspires.ftc.teamcode.autonomous.routines.Routines.scoreToBottom
import org.firstinspires.ftc.teamcode.autonomous.routines.Routines.scoreToIntake
import org.firstinspires.ftc.teamcode.autonomous.trajectories.TrajectoryFactory
import org.firstinspires.ftc.teamcode.mechanisms.Arm
import org.firstinspires.ftc.teamcode.mechanisms.Claw
import org.firstinspires.ftc.teamcode.mechanisms.IntakeExtension
import org.firstinspires.ftc.teamcode.mechanisms.Lift

object SpecimenRoutines {

    val rightStartToSpecimenScore: Command
        get() = ParallelCommandGroup(
                SequentialCommandGroup(
                    Delay(0.25),
                    IntakeExtension.extensionMiddle,
                    IntakeExtension.extensionIn
                ),
                SequentialCommandGroup(
                    Delay(2.0),
                    Lift.toAutonSpecimenScoreHigh,
                ),
                Constants.drive.followTrajectory(TrajectoryFactory.rightStartToHighChamber1),
                Arm.toScorePos
            )

    val specimenScore1ToBringSamples: Command
        get() = SequentialCommandGroup(

        )

    val specimenScore1ToPark: Command
        get() = ParallelCommandGroup(
            SequentialCommandGroup(
                Constants.drive.followTrajectory(TrajectoryFactory.highChamber1ToPark)
            ),
            SequentialCommandGroup(
                Delay(0.5),
                Claw.open,
                scoreToBottom,
            )
        )

    val singleSpecimenWithObservationZonePark: Command
        get() = SequentialCommandGroup(
            rightStartToSpecimenScore,
            specimenScore1ToPark,
            Delay(1.0),
            StopOpModeCommand()
        )

    val biggerSpecimenAuton: Command
        get() = SequentialCommandGroup(

        )

}