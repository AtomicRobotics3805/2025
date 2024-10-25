package org.firstinspires.ftc.teamcode.mechanisms

import com.acmerobotics.dashboard.config.Config
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple
import com.rowanmcalpin.nextftc.command.Command
import com.rowanmcalpin.nextftc.command.groups.SequentialCommandGroup
import com.rowanmcalpin.nextftc.command.utility.CustomCommand
import com.rowanmcalpin.nextftc.controls.GamepadEx
import com.rowanmcalpin.nextftc.hardware.MotorEx
import com.rowanmcalpin.nextftc.subsystems.MotorToPosition
import com.rowanmcalpin.nextftc.subsystems.Subsystem
import org.firstinspires.ftc.teamcode.ToPositionCommand
import kotlin.math.abs

@Config
object IntakeExtension: Subsystem {
    @JvmField
    var name = "intake_extension"
    @JvmField
    var motorRatio = 13.7
    @JvmField
    var motorDirection = DcMotorSimple.Direction.REVERSE

    @JvmField
    var maxSpeed = 1.0

    @JvmField
    var inPos = 69
    @JvmField
    var outPos = 1200 // TODO
    @JvmField
    var slightlyOutPos = 300 // TODO

    val motor = MotorEx(name, MotorEx.MotorType.GOBILDA_YELLOWJACKET, motorRatio, motorDirection)

    val pulleyRadius = 0.675 // Inches
    val gearReduction = 1.0
    val countsPerInch = motor.ticksPerRev * gearReduction / (2 * pulleyRadius * Math.PI)

    val zero: Command
        get() = CustomCommand(getDone = { IntakeExtensionControl.withinDistanceOfTarget() }, _start = {
            IntakeExtensionControl.targetPosition = 0
            IntakeExtensionControl.commandRunning = true
        }, _done = {
            IntakeExtensionControl.commandRunning = false
        })
    val extensionIn: Command
        get() = CustomCommand(getDone = { IntakeExtensionControl.withinDistanceOfTarget() }, _start = {
            IntakeExtensionControl.targetPosition = inPos
            IntakeExtensionControl.commandRunning = true
        }, _done = {
            IntakeExtensionControl.commandRunning = false
        })
    val extensionExtraIn: Command
        get() = CustomCommand(getDone = { IntakeExtensionControl.withinDistanceOfTarget() }, _start = {
            IntakeExtensionControl.targetPosition = -15
            IntakeExtensionControl.commandRunning = true
        }, _done = {
            IntakeExtensionControl.commandRunning = false
        })
    val extensionOut: Command
        get() = CustomCommand(getDone = { IntakeExtensionControl.withinDistanceOfTarget() }, _start = {
            IntakeExtensionControl.targetPosition = outPos
            IntakeExtensionControl.commandRunning = true
        }, _done = {
            IntakeExtensionControl.commandRunning = false
        })
    val extensionSlightlyOut: Command
        get() = CustomCommand(getDone = { IntakeExtensionControl.withinDistanceOfTarget() }, _start = {
            IntakeExtensionControl.targetPosition = slightlyOutPos
            IntakeExtensionControl.commandRunning = true
        }, _done = {
            IntakeExtensionControl.commandRunning = false
        })

    val resetEncoder: Command
        get() = CustomCommand(getDone = { true }, _start = {
            setRunMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER)
        })

    public class IntakeExtensionControl: Command() {
        override val _isDone = false

        companion object {
            var targetPosition = 0;
            var commandRunning = false;

            fun withinDistanceOfTarget(): Boolean {
                return abs(targetPosition - motor.currentPosition) <= 20
            }
        }

        override fun onStart() {
            targetPosition = 0;
        }

        override fun onExecute() {
            motor.targetPosition = targetPosition
            var power = maxSpeed

            if (abs(motor.currentPosition) <= 20 && abs(targetPosition) <= 20 && !commandRunning) {
                power = 0.0
            }
//            val error = targetPosition - motorGroup.currentPosition
//            val direction = sign(error.toDouble())
//            var power = 0.008 * abs(error) * maxSpeed * direction - 0.03
//            // Depower if the lift is within 20 ticks of 0
//            if (abs(0 - motorGroup.currentPosition) <= 20 && abs(0 - targetPosition) <= 20 && !commandRunning) {
//                power = 0.0
//            }

//            motorGroup.power = Range.clip(power, -min(maxSpeed, 1.0), min(maxSpeed, 1.0))
            motor.power = power
            motor.mode = DcMotor.RunMode.RUN_TO_POSITION
        }
    }

    fun manual(speed: Double) {
        motor.power = speed
    }

    fun setRunMode(mode: DcMotor.RunMode) {
        motor.mode = mode
    }

    override fun initialize() {
        motor.initialize()
        motor.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
        motor.targetPosition = 0
        motor.mode = DcMotor.RunMode.RUN_TO_POSITION
    }
}