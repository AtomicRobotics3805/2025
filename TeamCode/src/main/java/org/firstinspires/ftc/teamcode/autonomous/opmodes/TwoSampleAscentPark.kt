package org.firstinspires.ftc.teamcode.autonomous.opmodes

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.rowanmcalpin.nextftc.Constants
import com.rowanmcalpin.nextftc.driving.drivers.MecanumDrive
import com.rowanmcalpin.nextftc.driving.localizers.TwoWheelOdometryLocalizer
import com.rowanmcalpin.nextftc.opmodes.NextFTCOpMode
import org.firstinspires.ftc.teamcode.autonomous.routines.Routines
import org.firstinspires.ftc.teamcode.autonomous.trajectories.TrajectoryFactory
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

@Autonomous(name = "Two Sample Ascent Park")
class TwoSampleAscentPark: NextFTCOpMode(Arm, Claw, Intake, IntakeExtension, IntakePivot, IntakeSensor, Lift, Lights) {
    override val color = Constants.Color.BLUE // Doesn't actually matter, since the field is rotationally symmetrical
    override val trajectoryFactory = TrajectoryFactory
    override val drive = MecanumDrive(DriveConstants,
        TwoWheelOdometryLocalizer(OdometryConstants))
        { TrajectoryFactory.startPosLeft }

    override fun onInit() {
        Routines.autonomousWithSampleInitializationRoutine()
        telemetryData.add(Pair("Lift position", Lift.motor1.currentPosition))
        telemetryData.add(Pair("Lift target", Lift.LiftControl.targetPosition))
        telemetryData.add(Pair("Lift power", Lift.motor1.power))
        telemetryData.add(Pair("IntakeExtension", IntakeExtension.motor.currentPosition))
        telemetryData.add(Pair("Intake Extension target", IntakeExtension.IntakeExtensionControl.targetPosition))
        telemetryData.add(Pair("Drive", drive.getWheelVelocities()))
        updateOurTelemetry()
    }

    override fun onUpdate() {
        telemetryData.add(Pair("Lift position", Lift.motor1.currentPosition))
        telemetryData.add(Pair("Lift target", Lift.LiftControl.targetPosition))
        telemetryData.add(Pair("Lift power", Lift.motor1.power))
        telemetryData.add(Pair("IntakeExtension", IntakeExtension.motor.currentPosition))
        telemetryData.add(Pair("Intake Extension target", IntakeExtension.IntakeExtensionControl.targetPosition))
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

    override fun onStartButtonPressed() {
        Routines.twoSampleAscentPark()
    }
}