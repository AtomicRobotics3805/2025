package org.firstinspires.ftc.teamcode.tuning

import com.acmerobotics.dashboard.config.Config
import com.acmerobotics.roadrunner.geometry.Pose2d
import com.acmerobotics.roadrunner.geometry.Vector2d
import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.rowanmcalpin.nextftc.Constants
import com.rowanmcalpin.nextftc.Constants.drive
import com.rowanmcalpin.nextftc.TelemetryController
import com.rowanmcalpin.nextftc.command.Command
import com.rowanmcalpin.nextftc.command.CommandScheduler
import com.rowanmcalpin.nextftc.command.groups.ParallelCommandGroup
import com.rowanmcalpin.nextftc.command.groups.SequentialCommandGroup
import com.rowanmcalpin.nextftc.command.utility.Delay
import com.rowanmcalpin.nextftc.command.utility.TelemetryCommand
import com.rowanmcalpin.nextftc.driving.drivers.MecanumDrive
import com.rowanmcalpin.nextftc.driving.localizers.TwoWheelOdometryLocalizer
import com.rowanmcalpin.nextftc.trajectories.ParallelTrajectory
import com.rowanmcalpin.nextftc.trajectories.toRadians
import org.firstinspires.ftc.teamcode.drive.DriveConstants
import org.firstinspires.ftc.teamcode.localization.OdometryConstants

@Config
@Autonomous(group = "tuning")
class StrafeTest : LinearOpMode() {

    @Throws(InterruptedException::class)
    override fun runOpMode() {
        Constants.opMode = this
        Constants.color = Constants.Color.BLUE
        Constants.drive = MecanumDrive(
            DriveConstants,
            TwoWheelOdometryLocalizer(OdometryConstants),
        ) { Pose2d() }
        CommandScheduler.registerSubsystems(TelemetryController, Constants.drive)
        val trajectory: ParallelTrajectory = Constants.drive.trajectoryBuilder(Pose2d())
            .strafeRight(DISTANCE)
            .build()
        waitForStart()
        CommandScheduler.scheduleCommand(SequentialCommandGroup(
            Constants.drive.followTrajectory(trajectory),
            ParallelCommandGroup(
                TelemetryCommand(1000.0, "finalX") { drive.poseEstimate.x.toString() },
                TelemetryCommand(1000.0, "finalY") { drive.poseEstimate.y.toString() },
                TelemetryCommand(1000.0, "finalHeading") { drive.poseEstimate.heading.toString() }
            )
        ))
        while (opModeIsActive()) {
            CommandScheduler.run()
        }
    }

    companion object {
        @JvmField
        var DISTANCE = 60.0 // in
    }
}