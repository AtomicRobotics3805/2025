package org.firstinspires.ftc.teamcode.teleop.opmodes

import com.acmerobotics.roadrunner.geometry.Pose2d
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.rowanmcalpin.nextftc.Constants
import com.rowanmcalpin.nextftc.command.groups.SequentialCommandGroup
import com.rowanmcalpin.nextftc.command.utility.Delay
import com.rowanmcalpin.nextftc.controls.Controls
import com.rowanmcalpin.nextftc.driving.drivers.MecanumDrive
import com.rowanmcalpin.nextftc.driving.localizers.TwoWheelOdometryLocalizer
import com.rowanmcalpin.nextftc.opmodes.NextFTCOpMode
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

@TeleOp(name="Testing TeleOp")
class TestingTeleOp: NextFTCOpMode(Arm, Claw, Intake, IntakeExtension, IntakePivot, Lift, Lights, IntakeSensor) {
    override val controls: Controls = org.firstinspires.ftc.teamcode.teleop.Controls()
    override val drive = MecanumDrive(DriveConstants, TwoWheelOdometryLocalizer(OdometryConstants), { Pose2d(0.0, 0.0, 0.0) })
//
//
//    lateinit var lf: DcMotor;
//    lateinit var rf: DcMotor;
//    lateinit var lb: DcMotor;
//    lateinit var rb: DcMotor;


    override val color: Constants.Color
        get() = Constants.Color.RED

    override fun onInit() {
        Constants.opMode = this
        Lights.DisplayColor()()
        IntakeSensor.DetectColor()()
        IntakeExtension.extensionIn()
        Lift.LiftControl()()
        super.telemetry.addData("Lift position", Lift.motor1.currentPosition)
        super.telemetry.addData("Lift target", Lift.motor1.targetPosition)
        super.telemetry.addData("Lift power", Lift.motor1.power)
        super.telemetry.addData("Drive", drive.getWheelVelocities())
        super.telemetry.update()
    }

    override fun onUpdate() {
        telemetryData.add(Pair("Lift position", Lift.motor1.currentPosition))
        telemetryData.add(Pair("Lift target", Lift.LiftControl.targetPosition))
        telemetryData.add(Pair("Lift power", Lift.motor1.power))
        telemetryData.add(Pair("IntakeExtension", IntakeExtension.motor.currentPosition))
        telemetryData.add(Pair("Drive", drive.getWheelVelocities()))
        updateOurTelemetry()
//        var driveP = -gamepad1.left_stick_y
//        var strafe = gamepad1.left_stick_x
//        var turn = gamepad1.right_stick_x
//        var denominator = Math.max(Math.abs(driveP).toDouble() + Math.abs(strafe).toDouble() + Math.abs(turn).toDouble(), 1.0)
//        lf.power = (driveP + strafe + turn) / denominator
//        lb.power = (driveP - strafe + turn) / denominator
//        rf.power = (driveP - strafe - turn) / denominator
//        rb.power = (driveP + strafe - turn) / denominator
    }

    override fun onStop() {
        Lights.leds.clear()
        Lights.leds.update()
    }

    companion object {
        public var telemetryData: MutableList<Pair<String, Any>> = mutableListOf()
    }

    fun updateOurTelemetry() {
        telemetryData.forEach {
            super.telemetry.addData(it.first, it.second)
        }

        super.telemetry.update()

        telemetryData.clear()
    }
}