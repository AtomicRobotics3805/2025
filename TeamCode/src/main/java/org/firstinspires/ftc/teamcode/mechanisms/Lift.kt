package org.firstinspires.ftc.teamcode.mechanisms

import com.acmerobotics.dashboard.config.Config
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple
import com.qualcomm.robotcore.util.ElapsedTime
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
    var intakePos = -40
    @JvmField
    var specimenPickup = 300 // TODO
    @JvmField
    var highPos = 2850
    @JvmField
    var aLittleHighPos = 300
    @JvmField
    var specimenScoreHigh = 820 // TODO

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
            LiftControl.zeroPower = false
            LiftControl.targetPosition = aLittleHighPos
            LiftControl.commandRunning = true
        }, _done = {
            LiftControl.commandRunning = false
        })

    val toIntake: Command
        get() = CustomCommand(getDone = { LiftControl.withinDistanceOfTarget() }, _start = {
            LiftControl.zeroPower = false
            LiftControl.targetPosition = intakePos
            LiftControl.commandRunning = true
        }, _done = {
            LiftControl.commandRunning = false
            LiftControl.zeroPower = true
        })

    class Zero: Command() {
        private var timer = ElapsedTime();

        override val _isDone: Boolean
            get() = timer.seconds() > 0.75 || LiftControl.withinDistanceOfTarget()

        override fun onStart() {
            LiftControl.zeroPower = false
            LiftControl.targetPosition = -60
            LiftControl.commandRunning = true
            timer.reset()
        }

        override fun onEnd(interrupted: Boolean) {
            LiftControl.commandRunning = false
            motorGroup.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
        }
    }

    val toSpecimenScoreHigh: Command
        get() = CustomCommand(getDone = { LiftControl.withinDistanceOfTarget() }, _start = {
            LiftControl.zeroPower = false
            LiftControl.targetPosition = specimenScoreHigh
            LiftControl.commandRunning = true
        }, _done = {
            LiftControl.commandRunning = false
        })

    val toHigh: Command
        get() = CustomCommand(getDone = { LiftControl.withinDistanceOfTarget() }, _start = {
            LiftControl.zeroPower = false
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

            var zeroPower = false;

            fun withinDistanceOfTarget(): Boolean {
                return abs(targetPosition - motorGroup.currentPosition) <= 60
            }
        }

        override fun onStart() {
            targetPosition = 0;
        }

        override fun onExecute() {
            motorGroup.targetPosition = targetPosition
            var power = maxSpeed

//            if (abs(motorGroup.currentPosition) <= 20 && abs(targetPosition) <= 20 && !commandRunning) {
//                power = 0.0
//            }
//            val error = targetPosition - motorGroup.currentPosition
//            val direction = sign(error.toDouble())
//            var power = 0.008 * abs(error) * maxSpeed * direction - 0.03
//            // Depower if the lift is within 20 ticks of 0
            if (zeroPower) {
                power = 0.0
            }

//            motorGroup.power = Range.clip(power, -min(maxSpeed, 1.0), min(maxSpeed, 1.0))
            motorGroup.power = power
            motorGroup.mode = DcMotor.RunMode.RUN_TO_POSITION
        }
    }

    override fun initialize() {
        motorGroup.initialize()
        motorGroup.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
        motorGroup.targetPosition = 0
        motorGroup.power = 1.0
        motorGroup.mode = DcMotor.RunMode.RUN_TO_POSITION
    }
}