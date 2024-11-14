package org.firstinspires.ftc.teamcode.autonomous.routines

import com.acmerobotics.roadrunner.trajectory.Trajectory
import com.rowanmcalpin.nextftc.Constants
import com.rowanmcalpin.nextftc.command.Command
import com.rowanmcalpin.nextftc.command.groups.ParallelCommandGroup
import com.rowanmcalpin.nextftc.command.groups.SequentialCommandGroup
import com.rowanmcalpin.nextftc.command.utility.Delay
import com.rowanmcalpin.nextftc.command.utility.TelemetryCommand
import org.firstinspires.ftc.teamcode.autonomous.routines.Routines.scoreToRepeat
import org.firstinspires.ftc.teamcode.autonomous.trajectories.TrajectoryFactory
import org.firstinspires.ftc.teamcode.mechanisms.Arm
import org.firstinspires.ftc.teamcode.mechanisms.Claw
import org.firstinspires.ftc.teamcode.mechanisms.Lift

object SpecimenRoutines {

    object SpecimenStateMachine {
        // This class lets me repeat some code to clean up the appearance. It keeps track of which
        // "slot" we've last put specimens onto (so we don't try to score into the same "slot" twice)

        var latestSlot = 0

        fun specimenScoreDrive(): Command {
            latestSlot++

            when (latestSlot) {
                1 -> {
                    return Constants.drive.followTrajectory(TrajectoryFactory.highChamber1Score)
                }

                2 -> {
                    return Constants.drive.followTrajectory(TrajectoryFactory.highChamber2Score)
                }
            }

            return TelemetryCommand(1.0, "There was an error")
        }
    }

    val rightStartToSpecimenScore: Command
        get() = SequentialCommandGroup(
            ParallelCommandGroup(
                Lift.toSpecimenScoreHigh,
                Constants.drive.followTrajectory(TrajectoryFactory.rightStartToHighChamber1),
                SequentialCommandGroup(
                    Delay(0.5),
                    Arm.toSpecimenPreScorePos
                )
            ),
            ParallelCommandGroup(
                SpecimenStateMachine.specimenScoreDrive(),
                Arm.toSpecimenScorePos,
                SequentialCommandGroup(
                    Delay(0.5),
                    Claw.open
                )
            )
        )

    val specimenScore1ToPark: Command
        get() = ParallelCommandGroup(
            scoreToRepeat,
            Constants.drive.followTrajectory(TrajectoryFactory.highChamber1ToPark)
        )

    val singleSpecimenWithObservationZonePark: Command
        get() = SequentialCommandGroup(

        )

}