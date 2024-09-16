package org.firstinspires.ftc.teamcode.mechanisms

import com.acmerobotics.dashboard.config.Config
import com.qualcomm.robotcore.hardware.DcMotorSimple
import com.rowanmcalpin.nextftc.command.Command
import com.rowanmcalpin.nextftc.hardware.MotorEx
import com.rowanmcalpin.nextftc.subsystems.MotorToPosition
import com.rowanmcalpin.nextftc.subsystems.Subsystem

@Config
object Lift: Subsystem {
    @JvmField
    var name = "lift"
    @JvmField
    var motorRatio = 19.2
    @JvmField
    var motorDirection = DcMotorSimple.Direction.FORWARD

    @JvmField
    var lowPos = 0.0 // Inches // NOT DONE
    @JvmField
    var middlePos = 5.0 // Inches // NOT DONE
    @JvmField
    var highPos = 10.0 // Inches // NOT DONE

    @JvmField
    var maxSpeed = 1.0

    @JvmField
    var motor = MotorEx(name, MotorEx.MotorType.GOBILDA_YELLOWJACKET, motorRatio, motorDirection)

    val pulleyRadius = 0.5 // Inches
    val gearReduction = 1.0
    val countsPerInch = motor.ticksPerRev * gearReduction / (2 * pulleyRadius * Math.PI)

    val toLow: Command
        get() = MotorToPosition(motor, (lowPos * countsPerInch).toInt(), maxSpeed, listOf(this@Lift), kP = 0.003)
    val toMiddle: Command
        get() = MotorToPosition(motor, (middlePos * countsPerInch).toInt(), maxSpeed, listOf(this@Lift), kP = 0.003)
    val toHigh: Command
        get() = MotorToPosition(motor, (highPos * countsPerInch).toInt(), maxSpeed, listOf(this@Lift), kP = 0.003)
}