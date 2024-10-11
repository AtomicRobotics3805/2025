package org.firstinspires.ftc.teamcode.teleop.opmodes

import com.acmerobotics.roadrunner.geometry.Pose2d
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple
import com.rowanmcalpin.nextftc.Constants
import com.rowanmcalpin.nextftc.Constants.drive
import com.rowanmcalpin.nextftc.command.CommandScheduler
import com.rowanmcalpin.nextftc.command.utility.TelemetryCommand
import com.rowanmcalpin.nextftc.controls.Controls
import com.rowanmcalpin.nextftc.driving.drivers.MecanumDrive
import com.rowanmcalpin.nextftc.driving.localizers.TwoWheelOdometryLocalizer
import com.rowanmcalpin.nextftc.opmodes.NextFTCOpMode
import org.firstinspires.ftc.teamcode.TestCommand
import org.firstinspires.ftc.teamcode.drive.DriveConstants
import org.firstinspires.ftc.teamcode.localization.OdometryConstants
import org.firstinspires.ftc.teamcode.mechanisms.Arm
import org.firstinspires.ftc.teamcode.mechanisms.Claw
import org.firstinspires.ftc.teamcode.mechanisms.Intake
import org.firstinspires.ftc.teamcode.mechanisms.IntakeExtension
import org.firstinspires.ftc.teamcode.mechanisms.IntakePivot
import org.firstinspires.ftc.teamcode.mechanisms.Lift
import org.firstinspires.ftc.teamcode.teleop.TeleOpRoutines

@TeleOp(name="Testing TeleOp")
class TestingTeleOp: NextFTCOpMode(Arm, Claw, Intake, IntakeExtension, IntakePivot, Lift) {
    override val controls: Controls = org.firstinspires.ftc.teamcode.teleop.Controls()
    override val drive = MecanumDrive(DriveConstants, TwoWheelOdometryLocalizer(OdometryConstants), { Pose2d(0.0, 0.0, 0.0) })


    lateinit var lf: DcMotor;
    lateinit var rf: DcMotor;
    lateinit var lb: DcMotor;
    lateinit var rb: DcMotor;


    override val color: Constants.Color
        get() = Constants.Color.RED
    override fun onInit() {
        lf = Constants.opMode.hardwareMap.get(DcMotor::class.java, "LF")
        lf.direction = DcMotorSimple.Direction.REVERSE
        lf.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE
        rf = Constants.opMode.hardwareMap.get(DcMotor::class.java, "RF")
        rf.direction = DcMotorSimple.Direction.FORWARD
        rf.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE
        lb = Constants.opMode.hardwareMap.get(DcMotor::class.java, "LB")
        lb.direction = DcMotorSimple.Direction.REVERSE
        lb.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE
        rb = Constants.opMode.hardwareMap.get(DcMotor::class.java, "RB")
        rb.direction = DcMotorSimple.Direction.FORWARD
        rb.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE
        Constants.opMode = this
        IntakePivot.intakePivotUp()
        Arm.toIntakePos()
        Claw.clawOpen()
        TestCommand()
        super.telemetry.addData("Lift position", Lift.motor1.currentPosition)
        super.telemetry.addData("Lift target", Lift.motor1.targetPosition)
        super.telemetry.addData("Lift power", Lift.motor1.power)
        super.telemetry.addData("Drive", drive.getWheelVelocities())
        super.telemetry.update()
        Intake.start()
    }

    override fun onUpdate() {
        telemetryData.add(Pair("Lift position", Lift.motor1.currentPosition))
        telemetryData.add(Pair("Lift target", Lift.motor1.targetPosition))
        telemetryData.add(Pair("Lift power", Lift.motor1.power))
        telemetryData.add(Pair("IntakeExtension", IntakeExtension.motor.currentPosition))
        telemetryData.add(Pair("Drive", drive.getWheelVelocities()))
        updateOurTelemetry()
        var driveP = -gamepad1.left_stick_y
        var strafe = gamepad1.left_stick_x
        var turn = gamepad1.right_stick_x
        var denominator = Math.max(Math.abs(driveP).toDouble() + Math.abs(strafe).toDouble() + Math.abs(turn).toDouble(), 1.0)
        lf.power = (driveP + strafe + turn) / denominator
        lb.power = (driveP - strafe + turn) / denominator
        rf.power = (driveP - strafe - turn) / denominator
        rb.power = (driveP + strafe - turn) / denominator
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