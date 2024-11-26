package org.firstinspires.ftc.teamcode.autonomous.opmodes

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.rowanmcalpin.nextftc.Constants
import com.rowanmcalpin.nextftc.TelemetryController
import com.rowanmcalpin.nextftc.driving.drivers.MecanumDrive
import com.rowanmcalpin.nextftc.driving.localizers.TwoWheelOdometryLocalizer
import com.rowanmcalpin.nextftc.opmodes.NextFTCOpMode
import com.rowanmcalpin.nextftc.subsystems.DisplayRobot
import org.firstinspires.ftc.teamcode.autonomous.routines.Routines
import org.firstinspires.ftc.teamcode.autonomous.routines.SampleRoutines
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

@Autonomous(name = "Four Sample Ascent Park")
class FourSampleAscentPark: NextFTCOpMode(Arm, Claw, Intake, IntakeExtension, IntakePivot, IntakeSensor, Lift, Lights) {
    override val color = Constants.Color.UNKNOWN

    override val trajectoryFactory = TrajectoryFactory

    override val drive = MecanumDrive(DriveConstants,
        TwoWheelOdometryLocalizer(OdometryConstants))
    { TrajectoryFactory.startPosLeft }

    override fun onInit() {
        Routines.autonomousWithSampleInitializationRoutine()
        DisplayRobot()()
        TelemetryController.telemetry.addData("Lift position 1", Lift.motor1.currentPosition)
        TelemetryController.telemetry.addData("Lift position 2", Lift.motor1.currentPosition)
        TelemetryController.telemetry.addData("Lift power 1", Lift.motor1.power)
        TelemetryController.telemetry.addData("Lift power 2", Lift.motor2.power)
        TelemetryController.telemetry.addData("Lift target", Lift.LiftControl.targetPosition)
        TelemetryController.telemetry.addData("Intake extension position", IntakeExtension.motor.currentPosition)
        TelemetryController.telemetry.addData("Intake extension target", IntakeExtension.IntakeExtensionControl.targetPosition)
    }

    override fun onUpdate() {
        TelemetryController.telemetry.addData("Lift position 1", Lift.motor1.currentPosition)
        TelemetryController.telemetry.addData("Lift position 2", Lift.motor1.currentPosition)
        TelemetryController.telemetry.addData("Lift power 1", Lift.motor1.power)
        TelemetryController.telemetry.addData("Lift power 2", Lift.motor2.power)
        TelemetryController.telemetry.addData("Lift target", Lift.LiftControl.targetPosition)
        TelemetryController.telemetry.addData("Intake extension position", IntakeExtension.motor.currentPosition)
        TelemetryController.telemetry.addData("Intake extension target", IntakeExtension.IntakeExtensionControl.targetPosition)
    }

    override fun onStartButtonPressed() {
        SampleRoutines.fourSampleAscentPark()
    }
}