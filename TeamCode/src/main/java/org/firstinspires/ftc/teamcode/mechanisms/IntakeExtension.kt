package org.firstinspires.ftc.teamcode.mechanisms

import com.acmerobotics.dashboard.config.Config
import com.qualcomm.robotcore.hardware.DcMotorSimple
import com.rowanmcalpin.nextftc.command.Command
import com.rowanmcalpin.nextftc.command.groups.SequentialCommandGroup
import com.rowanmcalpin.nextftc.hardware.MotorEx
import com.rowanmcalpin.nextftc.subsystems.MotorToPosition
import com.rowanmcalpin.nextftc.subsystems.Subsystem

@Config
object IntakeExtension: Subsystem {
    @JvmField
    var name = "intakeSlide"
    @JvmField
    var motorRatio = 19.2
    @JvmField
    var motorDirection = DcMotorSimple.Direction.FORWARD

    @JvmField
    var maxSpeed = 1.0

    @JvmField
    var inPos = 0.0 // Inches // NOT DONE
    @JvmField
    var outPos = 8.0 // Inches // NOT DONE

    val motor = MotorEx(name, MotorEx.MotorType.GOBILDA_YELLOWJACKET, motorRatio, motorDirection)

    val pulleyRadius = 0.5 // Inches
    val gearReduction = 1.0
    val countsPerInch = motor.ticksPerRev * gearReduction / (2 * pulleyRadius * Math.PI)

    val extensionIn: Command
        get() = MotorToPosition(motor, (inPos * countsPerInch).toInt(), maxSpeed, listOf(this@IntakeExtension), kP = 0.003)
    val extensionOut: Command
        get() = MotorToPosition(motor, (outPos * countsPerInch).toInt(), maxSpeed, listOf(this@IntakeExtension), kP = 0.003)
}