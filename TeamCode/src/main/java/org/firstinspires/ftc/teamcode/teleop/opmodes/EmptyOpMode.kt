package org.firstinspires.ftc.teamcode.teleop.opmodes

import com.acmerobotics.roadrunner.geometry.Pose2d
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.rowanmcalpin.nextftc.Constants
import com.rowanmcalpin.nextftc.TelemetryController
import com.rowanmcalpin.nextftc.controls.Controls
import com.rowanmcalpin.nextftc.driving.drivers.MecanumDrive
import com.rowanmcalpin.nextftc.driving.localizers.TwoWheelOdometryLocalizer
import com.rowanmcalpin.nextftc.opmodes.NextFTCOpMode
import com.rowanmcalpin.nextftc.trajectories.rad
import org.firstinspires.ftc.teamcode.drive.DriveConstants
import org.firstinspires.ftc.teamcode.localization.OdometryConstants
import org.firstinspires.ftc.teamcode.mechanisms.Arm
import org.firstinspires.ftc.teamcode.mechanisms.Claw
import org.firstinspires.ftc.teamcode.mechanisms.Intake
import org.firstinspires.ftc.teamcode.mechanisms.IntakeExtension
import org.firstinspires.ftc.teamcode.mechanisms.IntakePivot
import org.firstinspires.ftc.teamcode.mechanisms.IntakeSensor
import org.firstinspires.ftc.teamcode.mechanisms.Lift
import org.firstinspires.ftc.teamcode.mechanisms.Lights

@TeleOp(name = "Empty OpMode")
class EmptyOpMode: NextFTCOpMode(Arm, Claw, Intake, IntakeExtension, IntakePivot, Lift, Lights, IntakeSensor) {

    override val controls: Controls = org.firstinspires.ftc.teamcode.teleop.Controls()
    override val drive = MecanumDrive(DriveConstants, TwoWheelOdometryLocalizer(OdometryConstants), { Pose2d(0.0, 0.0, 90.rad) })

    override val color: Constants.Color
        get() = Constants.Color.RED

    override fun onInit() {
        Constants.opMode = this
        Lift.resetEncoder()
        Lights.DisplayColor()()
        IntakeSensor.DetectColor()()
        Lift.LiftControl()()
        IntakeExtension.IntakeExtensionControl()()
        IntakeExtension.zero()
        IntakeSensor.IntakeStopOnIntaken()
    }

    override fun onUpdate() {
        TelemetryController.telemetry.addData("Lift position 1", Lift.motor1.currentPosition)
    }
}