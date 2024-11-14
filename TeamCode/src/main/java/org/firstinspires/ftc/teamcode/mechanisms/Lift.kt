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
    var motorDirection = DcMotorSimple.Direction.REVERSE
    @JvmField
    var motor2Direction = DcMotorSimple.Direction.FORWARD

    @JvmField
    var intakePos = -1.0 // Inches // TODO
    @JvmField
    var specimenPickup = 3.0 // Inches // TODO
    @JvmField
    var highPos = 24.0 // Inches // TODO
    @JvmField
    var aLittleHighPos = 3.0 // Inches // TODO
    @JvmField
    var specimenScoreHigh = 12.0 // Inches // TODO

    @JvmField
    var maxSpeed = 1.0

    @JvmField
    var motor1 = MotorEx(name, MotorEx.MotorType.GOBILDA_YELLOWJACKET, motorRatio, motorDirection)
    var motor2 = MotorEx(name2, MotorEx.MotorType.GOBILDA_YELLOWJACKET, motorRatio, motor2Direction)

    @JvmField
    val pulleyRadius = 0.5 // Inches
    @JvmField
    val gearReduction = 1.0
    val countsPerInch = motor1.ticksPerRev * gearReduction / (2 * pulleyRadius * Math.PI)

    val aLittleHigh: Command
        get() = CustomCommand(getDone = { LiftControl.withinDistanceOfTarget() }, _start = {
            LiftControl.zeroPower = false
            LiftControl.targetPosition = (aLittleHighPos * countsPerInch).toInt()
            LiftControl.commandRunning = true
        }, _done = {
            LiftControl.commandRunning = false
        })

    val toIntake: Command
        get() = CustomCommand(getDone = { LiftControl.withinDistanceOfTarget() }, _start = {
            LiftControl.zeroPower = false
            LiftControl.targetPosition = (intakePos * countsPerInch).toInt()
            LiftControl.commandRunning = true
        }, _done = {
            LiftControl.commandRunning = false
            LiftControl.zeroPower = true
        })

    val toHang: Command
        get() = CustomCommand(getDone = { LiftControl.withinDistanceOfTarget() }, _start = {
            LiftControl.zeroPower = false
            LiftControl.targetPosition = (intakePos * countsPerInch).toInt()
            LiftControl.commandRunning = true
        }, _done = {
            LiftControl.commandRunning = false
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
            motor1.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
            motor2.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
        }
    }

    val toSpecimenScoreHigh: Command
        get() = CustomCommand(getDone = { LiftControl.withinDistanceOfTarget() }, _start = {
            LiftControl.zeroPower = false
            LiftControl.targetPosition = (specimenScoreHigh * countsPerInch).toInt()
            LiftControl.commandRunning = true
        }, _done = {
            LiftControl.commandRunning = false
        })

    val toSpecimenPickup: Command
        get() = CustomCommand(getDone = { LiftControl.withinDistanceOfTarget() }, _start = {
            LiftControl.zeroPower = false
            LiftControl.targetPosition = (specimenPickup * countsPerInch).toInt()
            LiftControl.commandRunning = true
        }, _done = {
            LiftControl.commandRunning = false
        })

    val toHigh: Command
        get() = CustomCommand(getDone = { LiftControl.withinDistanceOfTarget() }, _start = {
            LiftControl.zeroPower = false
            LiftControl.targetPosition = (highPos * countsPerInch).toInt()
            LiftControl.commandRunning = true
        }, _done = {
            LiftControl.commandRunning = false
        })

    val resetEncoder: Command
        get() = CustomCommand(getDone = { true }, _start = {
            setRunMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER)
        })

    fun manual(speed: Double) {
        motor1.power = speed
        motor2.power = speed
    }

    fun setRunMode(mode: DcMotor.RunMode) {
        motor1.mode = mode
        motor2.mode = mode
    }

    public class LiftControl: Command() {
        override val _isDone = false

        companion object {
            var targetPosition = 0;
            var commandRunning = false;

            var zeroPower = false;

            fun withinDistanceOfTarget(): Boolean {
                return abs(targetPosition - motor1.currentPosition) <= 100
            }
        }

        override fun onStart() {
            targetPosition = 0;
        }

        override fun onExecute() {
            motor1.targetPosition = targetPosition
            motor2.targetPosition = targetPosition
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
            motor1.power = power
            motor2.power = power
            motor1.mode = DcMotor.RunMode.RUN_TO_POSITION
            motor2.mode = DcMotor.RunMode.RUN_TO_POSITION
        }
    }

    override fun initialize() {
        motor1.initialize()
        motor2.initialize()
        motor1.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
        motor2.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
        motor1.targetPosition = 0
        motor2.targetPosition = 0
        motor1.power = 1.0
        motor2.power = 1.0
        motor1.mode = DcMotor.RunMode.RUN_TO_POSITION
        motor2.mode = DcMotor.RunMode.RUN_TO_POSITION
    }
}