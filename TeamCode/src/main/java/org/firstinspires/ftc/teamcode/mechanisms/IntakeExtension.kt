package org.firstinspires.ftc.teamcode.mechanisms

import com.acmerobotics.dashboard.config.Config
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple
import com.qualcomm.robotcore.util.ElapsedTime
import com.qualcomm.robotcore.util.Range
import com.rowanmcalpin.nextftc.command.Command
import com.rowanmcalpin.nextftc.command.groups.SequentialCommandGroup
import com.rowanmcalpin.nextftc.command.utility.CustomCommand
import com.rowanmcalpin.nextftc.controls.GamepadEx
import com.rowanmcalpin.nextftc.hardware.MotorEx
import com.rowanmcalpin.nextftc.subsystems.MotorToPosition
import com.rowanmcalpin.nextftc.subsystems.Subsystem
import org.firstinspires.ftc.teamcode.ToPositionCommand
import kotlin.math.abs
import kotlin.math.sign

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
    var outPos = 1000 // TODO
    @JvmField
    var slightlyOutPos = 300 // TODO
    @JvmField
    var middlePos = 600

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
    val extensionMiddle: Command
        get() = CustomCommand(getDone = { IntakeExtensionControl.withinDistanceOfTarget() }, _start = {
            IntakeExtensionControl.targetPosition = middlePos
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

    class Zero: Command() {
        private var timer = ElapsedTime();

        override val _isDone: Boolean
            get() = timer.seconds() > 1.0 || Lift.LiftControl.withinDistanceOfTarget()

        override fun onStart() {
            IntakeExtensionControl.targetPosition = -60
            IntakeExtensionControl.commandRunning = true
            timer.reset()
        }

        override fun onEnd(interrupted: Boolean) {
            IntakeExtensionControl.commandRunning = false
            IntakeExtensionControl.useManualControl = true
            motor.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
            IntakeExtensionControl.targetPosition = 0
            IntakeExtensionControl.useManualControl = false
        }
    }


    public class ManualExtensionControl(val stepDistance: Int, val outTrigger: GamepadEx.Trigger, val inTrigger: GamepadEx.Trigger, val period: Double = 0.2): Command() {
        val timer = ElapsedTime()
        var lastTime: Double = -period

        override val _isDone: Boolean
            get() = false

        override fun onStart() {
            timer.reset()
        }

        override fun onExecute() {
            if (timer.seconds() - lastTime >= period) {
                if (abs(outTrigger.amount) > 0.25f || abs(inTrigger.amount) > 0.25f) {
                    IntakeExtensionControl.useManualControl = true
                    motor.mode = DcMotor.RunMode.RUN_USING_ENCODER

                    motor.power = Range.clip((outTrigger.amount + -inTrigger.amount).toDouble(), -1.0, 1.0)
                } else {
                    IntakeExtensionControl.targetPosition = motor.currentPosition
                    IntakeExtensionControl.useManualControl = false
                }
            }
        }
    }


    public class IntakeExtensionControl: Command() {
        override val _isDone = false
        private var cachedPosition = 0

        companion object {
            var targetPosition = 0
            var commandRunning = false

            fun withinDistanceOfTarget(): Boolean {
                return abs(targetPosition - motor.currentPosition) <= 20
            }

            var useManualControl = false
        }

        override fun onStart() {
            targetPosition = 0;
        }

        override fun onExecute() {
            if (!useManualControl) {
                if (abs(targetPosition - cachedPosition) > 10) {
                    motor.targetPosition = targetPosition
                    cachedPosition = targetPosition

                    motor.power = maxSpeed
                    motor.mode = DcMotor.RunMode.RUN_TO_POSITION
                }
            }
        }
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