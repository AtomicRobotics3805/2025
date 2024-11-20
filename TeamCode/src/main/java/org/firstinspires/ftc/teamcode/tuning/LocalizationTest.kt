package org.firstinspires.ftc.teamcode.tuning

import com.acmerobotics.dashboard.config.Config
import com.acmerobotics.roadrunner.geometry.Pose2d
import com.acmerobotics.roadrunner.geometry.Vector2d
import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.Disabled
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
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
import com.rowanmcalpin.nextftc.subsystems.DisplayRobot
import com.rowanmcalpin.nextftc.trajectories.ParallelTrajectory
import com.rowanmcalpin.nextftc.trajectories.toRadians
import org.firstinspires.ftc.teamcode.drive.DriveConstants
import org.firstinspires.ftc.teamcode.localization.OdometryConstants

@Config
@TeleOp(group = "tuning")
@Disabled
class LocalizationTest : LinearOpMode() {

    @Throws(InterruptedException::class)
    override fun runOpMode() {
        Constants.opMode = this
        Constants.color = Constants.Color.BLUE
        Constants.drive = MecanumDrive(
            DriveConstants,
            TwoWheelOdometryLocalizer(OdometryConstants),
        ) { Pose2d() }
        CommandScheduler.registerSubsystems(TelemetryController, Constants.drive)
        waitForStart()
        CommandScheduler.scheduleCommand(drive.driverControlled(gamepad1))
        CommandScheduler.scheduleCommand(DisplayRobot())
        CommandScheduler.scheduleCommand(TelemetryCommand(1000.0, "Position") { drive.poseEstimate.toString() })
        CommandScheduler.scheduleCommand(TelemetryCommand(1000.0, "Velocity") { drive.poseVelocity.toString() })
        while (!isStopRequested) {
            CommandScheduler.run()
        }
    }
}