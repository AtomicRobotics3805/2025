package org.firstinspires.ftc.teamcode.mechanisms

import com.acmerobotics.dashboard.config.Config
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple
import com.rowanmcalpin.nextftc.command.Command
import com.rowanmcalpin.nextftc.command.groups.SequentialCommandGroup
import com.rowanmcalpin.nextftc.hardware.MotorEx
import com.rowanmcalpin.nextftc.subsystems.MotorToPosition
import com.rowanmcalpin.nextftc.subsystems.Subsystem

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
    var inPos = 2.0 // Inches // NOT DONE
    @JvmField
    var outPos = 8.0 // Inches // NOT DONE

    val motor = MotorEx(name, MotorEx.MotorType.GOBILDA_YELLOWJACKET, motorRatio, motorDirection)

    val pulleyRadius = 0.675 // Inches
    val gearReduction = 1.0
    val countsPerInch = motor.ticksPerRev * gearReduction / (2 * pulleyRadius * Math.PI)

    val extensionIn: Command
        get() = MotorToPosition(motor, (inPos * countsPerInch).toInt(), maxSpeed, listOf(this@IntakeExtension), kP = 0.003)
    val extensionOut: Command
        get() = MotorToPosition(motor, (outPos * countsPerInch).toInt(), maxSpeed, listOf(this@IntakeExtension), kP = 0.003)

    override fun initialize() {
        motor.initialize()
        motor.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
        motor.mode = DcMotor.RunMode.RUN_USING_ENCODER
    }
}