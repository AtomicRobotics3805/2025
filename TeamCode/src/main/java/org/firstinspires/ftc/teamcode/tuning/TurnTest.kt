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
import com.rowanmcalpin.nextftc.command.groups.ParallelCommandGroup
import com.rowanmcalpin.nextftc.command.groups.SequentialCommandGroup
import com.rowanmcalpin.nextftc.command.utility.Delay
import com.rowanmcalpin.nextftc.command.utility.TelemetryCommand
import com.rowanmcalpin.nextftc.driving.Turn
import com.rowanmcalpin.nextftc.driving.drivers.MecanumDrive
import com.rowanmcalpin.nextftc.driving.localizers.TwoWheelOdometryLocalizer
import com.rowanmcalpin.nextftc.trajectories.ParallelTrajectory
import com.rowanmcalpin.nextftc.trajectories.toRadians
import org.firstinspires.ftc.teamcode.drive.DriveConstants
import org.firstinspires.ftc.teamcode.localization.OdometryConstants

@Config
@Autonomous(group = "tuning")
@Disabled
class TurnTest : LinearOpMode() {

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
        TelemetryController.dashboardAdapter.clear()
        CommandScheduler.scheduleCommand(Constants.drive.turn(ANGLE.toRadians, Turn.TurnType.RELATIVE))
        while (opModeIsActive()) {
            CommandScheduler.run()
        }
    }


    companion object {
        @JvmField
        var ANGLE = 90.0 // deg
    }
}