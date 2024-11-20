package org.firstinspires.ftc.teamcode.tuning

import com.acmerobotics.dashboard.config.Config
import com.acmerobotics.roadrunner.geometry.Pose2d
import com.acmerobotics.roadrunner.geometry.Vector2d
import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.Disabled
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.rowanmcalpin.nextftc.Constants
import com.rowanmcalpin.nextftc.Constants.drive
import com.rowanmcalpin.nextftc.TelemetryController
import com.rowanmcalpin.nextftc.command.Command
import com.rowanmcalpin.nextftc.command.CommandScheduler
import com.rowanmcalpin.nextftc.command.groups.SequentialCommandGroup
import com.rowanmcalpin.nextftc.command.utility.Delay
import com.rowanmcalpin.nextftc.driving.drivers.MecanumDrive
import com.rowanmcalpin.nextftc.driving.localizers.TwoWheelOdometryLocalizer
import com.rowanmcalpin.nextftc.trajectories.ParallelTrajectory
import com.rowanmcalpin.nextftc.trajectories.toRadians
import org.firstinspires.ftc.teamcode.drive.DriveConstants
import org.firstinspires.ftc.teamcode.localization.OdometryConstants

@Config
@Autonomous(group = "tuning")
@Disabled
class SplineTest : LinearOpMode() {
    @Throws(InterruptedException::class)
    override fun runOpMode() {
        Constants.opMode = this
        Constants.color = Constants.Color.BLUE
        Constants.drive = MecanumDrive(
            DriveConstants,
            TwoWheelOdometryLocalizer(OdometryConstants),
        ) { Pose2d() }
        CommandScheduler.registerSubsystems(TelemetryController, Constants.drive)
        val forwardTrajectory: ParallelTrajectory = drive.trajectoryBuilder(Pose2d())
            .splineTo(Vector2d(DISTANCE, DISTANCE), 0.0)
            .build()
        val reverseTrajectory: ParallelTrajectory = drive.trajectoryBuilder(forwardTrajectory.end(), true)
            .splineTo(Vector2d(0.0, 0.0), 180.0.toRadians)
            .build()
        waitForStart()
        CommandScheduler.scheduleCommand(SequentialCommandGroup(
            Constants.drive.followTrajectory(forwardTrajectory),
            Delay(2.0),
            Constants.drive.followTrajectory(reverseTrajectory)
        ))
        while (opModeIsActive()) {
            CommandScheduler.run()
        }
    }

    companion object {
        @JvmField
        var DISTANCE = 30.0 // in
    }
}