package org.firstinspires.ftc.teamcode.mechanisms

import com.acmerobotics.dashboard.config.Config
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple
import com.qualcomm.robotcore.util.ElapsedTime
import com.rowanmcalpin.nextftc.command.Command
import com.rowanmcalpin.nextftc.command.utility.CustomCommand
import com.rowanmcalpin.nextftc.controls.GamepadEx
import com.rowanmcalpin.nextftc.hardware.MotorEx
import com.rowanmcalpin.nextftc.subsystems.Subsystem
import kotlin.math.abs
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
    var specimenPickup = 1.78 // Inches // TODO
    @JvmField
    var highPos = 23.0 // Inches // TODO
    @JvmField
    var aLittleHighPos = 3.0 // Inches // TODO
    @JvmField
    var specimenScoreHigh = 4.6 // Inches // TODO
    @JvmField
    var specimenAutoScoreHigh = 4.6 // Inches // TODO
    @JvmField
    var firstAutonSpecimenScoreHigh = 4.2 // Inches
    @JvmField
    var hangPos = 12.0 // Inches

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

    val toHangHigh: Command
        get() = CustomCommand(getDone = { LiftControl.withinDistanceOfTarget() }, _start = {
            LiftControl.zeroPower = false
            LiftControl.targetPosition = (hangPos * countsPerInch).toInt()
            LiftControl.commandRunning = true
        }, _done = {
            LiftControl.commandRunning = false
        })

    val toHangDown: Command
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
            get() = timer.seconds() > 1.0 || LiftControl.withinDistanceOfTarget()

        override fun onStart() {
            LiftControl.zeroPower = false
            LiftControl.targetPosition = -60
            LiftControl.commandRunning = true
            timer.reset()
        }

        override fun onEnd(interrupted: Boolean) {
            LiftControl.commandRunning = false
            LiftControl.useManualControl = true
            motor1.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
            motor2.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
            LiftControl.targetPosition = 0
            LiftControl.useManualControl = false
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

    val toAutonSpecimenScoreHigh: Command
        get() = CustomCommand(getDone = { LiftControl.withinDistanceOfTarget() }, _start = {
            LiftControl.zeroPower = false
            LiftControl.targetPosition = (specimenAutoScoreHigh * countsPerInch).toInt()
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

    val toFirstSpecimenAuton: Command
        get() = CustomCommand(getDone = { LiftControl.withinDistanceOfTarget() }, _start = {
            LiftControl.zeroPower = false
            LiftControl.targetPosition = (firstAutonSpecimenScoreHigh * countsPerInch).toInt()
            LiftControl.commandRunning = true
        }, _done = {
            LiftControl.commandRunning = false
        })

    val resetEncoder: Command
        get() = CustomCommand(getDone = { true }, _start = {
            setRunMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER)
        })

    fun setRunMode(mode: DcMotor.RunMode) {
        motor1.mode = mode
        motor2.mode = mode
    }

    public class ManualLiftControl(val stepDistance: Int, val joyStick: GamepadEx.JoyStick, val period: Double = 0.2): Command() {
        val timer = ElapsedTime()
        var lastTime: Double = -period

        override val _isDone: Boolean
            get() = false

        override fun onStart() {
            timer.reset()
        }

        override fun onExecute() {
            if (timer.seconds() - lastTime >= period) {
                if (abs(joyStick.y) > 0.25f) {
                    LiftControl.useManualControl = true
                    motor1.mode = DcMotor.RunMode.RUN_USING_ENCODER
                    motor2.mode = DcMotor.RunMode.RUN_USING_ENCODER

                    motor1.power = -joyStick.y.toDouble()
                    motor2.power = -joyStick.y.toDouble()
                } else {
                    LiftControl.targetPosition = motor1.currentPosition
                    LiftControl.useManualControl = false
                }
            }
        }
    }

    public class LiftControl: Command() {
        override val _isDone = false

        private var cachedPosition = 0;

        companion object {
            var targetPosition = 0;
            var commandRunning = false;

            var zeroPower = false;

            fun withinDistanceOfTarget(): Boolean {
                return abs(targetPosition - motor1.currentPosition) <= 100
            }

            var useManualControl = false
        }

        override fun onStart() {
            targetPosition = 0;
        }

        override fun onExecute() {
            if(!useManualControl) {
                if (abs(targetPosition - cachedPosition) > 10) { // If we need to move the motor to achieve our target position, IE if our target position is different from our cached position
                    motor1.targetPosition = targetPosition
                    motor2.targetPosition = targetPosition


                    cachedPosition = targetPosition

                    motor1.power = maxSpeed
                    motor2.power = maxSpeed
                    motor1.mode = DcMotor.RunMode.RUN_TO_POSITION
                    motor2.mode = DcMotor.RunMode.RUN_TO_POSITION
                }

                // De-power if the lift is within 20 ticks of 0
                if (zeroPower) {
                    motor1.power = 0.0
                    motor2.power = 0.0
                }
            }
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