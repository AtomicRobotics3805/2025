package org.firstinspires.ftc.teamcode.mechanisms

import com.acmerobotics.dashboard.config.Config
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple
import com.qualcomm.robotcore.util.Range
import com.qualcomm.robotcore.util.RobotLog
import com.rowanmcalpin.nextftc.command.Command
import com.rowanmcalpin.nextftc.command.utility.CustomCommand
import com.rowanmcalpin.nextftc.controls.GamepadEx
import com.rowanmcalpin.nextftc.hardware.MotorEx
import com.rowanmcalpin.nextftc.hardware.MotorExGroup
import com.rowanmcalpin.nextftc.subsystems.MotorToPosition
import com.rowanmcalpin.nextftc.subsystems.MotorToPositionDepowerOnEnd
import com.rowanmcalpin.nextftc.subsystems.PowerMotor
import com.rowanmcalpin.nextftc.subsystems.Subsystem
import kotlin.math.abs
import kotlin.math.min
import kotlin.math.sign

@Config
object Lift: Subsystem {
    @JvmField
    var name = "lift"
    @JvmField
    var name2 = "lift2"
    @JvmField
    var motorRatio = 19.2
    @JvmField
    var motorDirection = DcMotorSimple.Direction.FORWARD
    @JvmField
    var motor2Direction = DcMotorSimple.Direction.FORWARD

    @JvmField
    var intakePos = -5
    @JvmField
    var specimenPickup = 300 // TODO
    @JvmField
    var highPos = 2850
    @JvmField
    var aLittleHighPos = 300
    @JvmField
    var specimenScoreHigh = 1800 // TODO

    @JvmField
    var maxSpeed = 1.0

    @JvmField
    var motor1 = MotorEx(name, MotorEx.MotorType.GOBILDA_YELLOWJACKET, motorRatio, motorDirection)
    var motor2 = MotorEx(name2, MotorEx.MotorType.GOBILDA_YELLOWJACKET, motorRatio, motor2Direction)

    var motorGroup = MotorExGroup(motor1, motor2)


    val pulleyRadius = 0.5 // Inches
    val gearReduction = 1.0
    val countsPerInch = motor1.ticksPerRev * gearReduction / (2 * pulleyRadius * Math.PI)

    val aLittleHigh: Command
        get() = CustomCommand(getDone = { LiftControl.withinDistanceOfTarget() }, _start = {
            LiftControl.targetPosition = aLittleHighPos
            LiftControl.commandRunning = true
        }, _done = {
            LiftControl.commandRunning = false
        })

    val toIntake: Command
        get() = CustomCommand(getDone = { LiftControl.withinDistanceOfTarget() }, _start = {
            LiftControl.targetPosition = intakePos
            LiftControl.commandRunning = true
        }, _done = {
            LiftControl.commandRunning = false
        })

    val toSpecimenPickup: Command
        get() = CustomCommand(getDone = { LiftControl.withinDistanceOfTarget() }, _start = {
            LiftControl.targetPosition = specimenPickup
            LiftControl.commandRunning = true
        }, _done = {
            LiftControl.commandRunning = false
        })

    val toSpecimenScoreHigh: Command
        get() = CustomCommand(getDone = { LiftControl.withinDistanceOfTarget() }, _start = {
            LiftControl.targetPosition = specimenScoreHigh
            LiftControl.commandRunning = true
        }, _done = {
            LiftControl.commandRunning = false
        })

    val toHigh: Command
        get() = CustomCommand(getDone = { LiftControl.withinDistanceOfTarget() }, _start = {
            LiftControl.targetPosition = highPos
            LiftControl.commandRunning = true
        }, _done = {
            LiftControl.commandRunning = false
        })


    val up: Command
        get() = PowerMotor(motorGroup, 0.8, DcMotor.RunMode.RUN_WITHOUT_ENCODER, listOf(this@Lift))

    val down: Command
        get() = PowerMotor(motorGroup, -0.8, DcMotor.RunMode.RUN_WITHOUT_ENCODER, listOf(this@Lift))
    val stop: Command
        get() = PowerMotor(motorGroup, 0.0, DcMotor.RunMode.RUN_WITHOUT_ENCODER, listOf(this@Lift))

    val resetEncoder: Command
        get() = CustomCommand(getDone = { true }, _start = {
            setRunMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER)
        })

    fun manual(speed: Double) {
        motorGroup.power = speed
    }

    fun setRunMode(mode: DcMotor.RunMode) {
        motorGroup.mode = mode
    }

    public class LiftControl: Command() {
        override val _isDone = false

        companion object {
            var targetPosition = 0;
            var commandRunning = false;

            fun withinDistanceOfTarget(): Boolean {
                return abs(targetPosition - motorGroup.currentPosition) <= 20
            }
        }

        override fun onStart() {
            targetPosition = 0;
        }

        override fun onExecute() {
            val error = targetPosition - motorGroup.currentPosition
            val direction = sign(error.toDouble())
            var power = 0.008 * abs(error) * maxSpeed * direction - 0.03
            // Depower if the lift is within 20 ticks of 0
            if (abs(0 - motorGroup.currentPosition) <= 20 && abs(0 - targetPosition) <= 20 && !commandRunning) {
                power = 0.0
            }

            motorGroup.power = Range.clip(power, -min(maxSpeed, 1.0), min(maxSpeed, 1.0))
        }
    }

    override fun initialize() {
        motor1.initialize()
        motor1.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
        motor1.mode = DcMotor.RunMode.RUN_WITHOUT_ENCODER
        motor2.initialize()
        motor2.mode = DcMotor.RunMode.RUN_WITHOUT_ENCODER
    }
}