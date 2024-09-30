package org.firstinspires.ftc.teamcode.mechanisms

import com.acmerobotics.dashboard.config.Config
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple
import com.rowanmcalpin.nextftc.command.Command
import com.rowanmcalpin.nextftc.command.groups.ParallelCommandGroup
import com.rowanmcalpin.nextftc.hardware.MotorEx
import com.rowanmcalpin.nextftc.hardware.MotorExGroup
import com.rowanmcalpin.nextftc.subsystems.MotorToPosition
import com.rowanmcalpin.nextftc.subsystems.PowerMotor
import com.rowanmcalpin.nextftc.subsystems.Subsystem
import org.firstinspires.ftc.teamcode.ToPositionCommand

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
    var lowPos = 0.0 // Inches // NOT DONE
    @JvmField
    var middlePos = 5.0 // Inches // NOT DONE
    @JvmField
    var highPos = 10.0 // Inches // NOT DONE

    @JvmField
    var maxSpeed = 1.0

    @JvmField
    var motor1 = MotorEx(name, MotorEx.MotorType.GOBILDA_YELLOWJACKET, motorRatio, motorDirection)
    var motor2 = MotorEx(name2, MotorEx.MotorType.GOBILDA_YELLOWJACKET, motorRatio, motor2Direction)


    val pulleyRadius = 0.5 // Inches
    val gearReduction = 1.0
    val countsPerInch = motor1.ticksPerRev * gearReduction / (2 * pulleyRadius * Math.PI)

    val toLow: Command
        get() = ParallelCommandGroup(
            ToPositionCommand(motor1.motor, (lowPos * countsPerInch).toInt(), requirementList = listOf(this@Lift)),
            PowerMotor(motor2, 0.0, DcMotor.RunMode.RUN_WITHOUT_ENCODER)
        )
    val toMiddle: Command
        get() = ParallelCommandGroup(
            ToPositionCommand(motor1.motor, (middlePos * countsPerInch).toInt(), requirementList = listOf(this@Lift)),
            PowerMotor(motor2, 0.0, DcMotor.RunMode.RUN_WITHOUT_ENCODER)
        )
    val toHigh: Command
        get() = ParallelCommandGroup(
            ToPositionCommand(motor1.motor, (highPos * countsPerInch).toInt(), requirementList = listOf(this@Lift)),
            PowerMotor(motor2, 0.0, DcMotor.RunMode.RUN_WITHOUT_ENCODER)
        )
    val up: Command
        get() = ParallelCommandGroup(
            PowerMotor(motor1, 0.8, DcMotor.RunMode.RUN_WITHOUT_ENCODER, listOf(this@Lift)),
            PowerMotor(motor2, 0.8, DcMotor.RunMode.RUN_WITHOUT_ENCODER)
        )
    val stop: Command
        get() = ParallelCommandGroup(
            PowerMotor(motor1, 0.0, DcMotor.RunMode.RUN_WITHOUT_ENCODER, listOf(this@Lift)),
            PowerMotor(motor2, 0.0, DcMotor.RunMode.RUN_WITHOUT_ENCODER)
        )

    override fun initialize() {
        motor1.initialize()
        motor1.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
        motor1.mode = DcMotor.RunMode.RUN_WITHOUT_ENCODER
        motor2.initialize()
        motor2.mode = DcMotor.RunMode.RUN_WITHOUT_ENCODER
    }
}