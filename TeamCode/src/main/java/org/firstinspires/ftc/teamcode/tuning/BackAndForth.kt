package org.firstinspires.ftc.teamcode.tuning

import com.acmerobotics.dashboard.config.Config
import com.acmerobotics.roadrunner.geometry.Pose2d
import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.rowanmcalpin.nextftc.Constants
import com.rowanmcalpin.nextftc.TelemetryController
import com.rowanmcalpin.nextftc.command.Command
import com.rowanmcalpin.nextftc.command.CommandScheduler
import com.rowanmcalpin.nextftc.command.groups.SequentialCommandGroup
import com.rowanmcalpin.nextftc.command.utility.CustomCommand
import com.rowanmcalpin.nextftc.driving.drivers.MecanumDrive
import com.rowanmcalpin.nextftc.driving.localizers.TwoWheelOdometryLocalizer
import com.rowanmcalpin.nextftc.subsystems.DisplayRobot
import com.rowanmcalpin.nextftc.trajectories.ParallelTrajectory
import org.firstinspires.ftc.teamcode.drive.DriveConstants
import org.firstinspires.ftc.teamcode.localization.OdometryConstants

@Config
@Autonomous(group = "drive")
class BackAndForth : LinearOpMode() {

    private lateinit var trajectoryForward: ParallelTrajectory
    private lateinit var trajectoryBackward: ParallelTrajectory
    private val followTrajectories: Command
        get() = SequentialCommandGroup(
            Constants.drive.followTrajectory(trajectoryForward),
            Constants.drive.followTrajectory(trajectoryBackward),
            CustomCommand(getDone = { true }, _start = { isDone = true })
        )

    override fun runOpMode() {
        Constants.opMode = this
        Constants.color = Constants.Color.BLUE
        Constants.drive = MecanumDrive(
            DriveConstants,
            TwoWheelOdometryLocalizer(OdometryConstants),
        ) { Pose2d() }
        CommandScheduler.registerSubsystems(TelemetryController, Constants.drive)
        trajectoryForward = Constants.drive.trajectoryBuilder(Pose2d())
            .forward(DISTANCE)
            .build()
        trajectoryBackward = Constants.drive.trajectoryBuilder(trajectoryForward.end())
            .back(DISTANCE)
            .build()
        waitForStart()
        TelemetryController.telemetry.clearAll()
        DisplayRobot()()
        CommandScheduler.scheduleCommand(followTrajectories)
        while (opModeIsActive()) {
            if (isDone) {
                isDone = false
                CommandScheduler.scheduleCommand(followTrajectories)
            }
            CommandScheduler.run()
        }
    }

    companion object {
        @JvmField
        var DISTANCE = 50.0

        var isDone = false
    }
}