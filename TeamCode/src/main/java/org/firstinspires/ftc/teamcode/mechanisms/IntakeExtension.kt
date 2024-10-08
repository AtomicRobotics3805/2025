package org.firstinspires.ftc.teamcode.mechanisms

import com.acmerobotics.dashboard.config.Config
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple
import com.rowanmcalpin.nextftc.command.Command
import com.rowanmcalpin.nextftc.command.groups.SequentialCommandGroup
import com.rowanmcalpin.nextftc.command.utility.CustomCommand
import com.rowanmcalpin.nextftc.hardware.MotorEx
import com.rowanmcalpin.nextftc.subsystems.MotorToPosition
import com.rowanmcalpin.nextftc.subsystems.Subsystem
import org.firstinspires.ftc.teamcode.ToPositionCommand

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
    var inPos = 0 // Inches // NOT DONE
    @JvmField
    var outPos = 1200 // Inches // NOT DONE

    val motor = MotorEx(name, MotorEx.MotorType.GOBILDA_YELLOWJACKET, motorRatio, motorDirection)

    val pulleyRadius = 0.675 // Inches
    val gearReduction = 1.0
    val countsPerInch = motor.ticksPerRev * gearReduction / (2 * pulleyRadius * Math.PI)

    val extensionIn: Command
        get() =
//            CustomCommand({ inPos - motor.currentPosition < 50 }, _start = {
//            motor.mode = DcMotor.RunMode.RUN_TO_POSITION
//            motor.targetPosition = inPos
//            motor.power = 0.2
//        })
            ToPositionCommand(motor.motor, (inPos * countsPerInch).toInt(), requirementList = listOf(this@IntakeExtension))
    val extensionOut: Command
        get() =
    //        CustomCommand({ outPos - motor.currentPosition < 50 }, _start = {
//            motor.mode = DcMotor.RunMode.RUN_TO_POSITION
//            motor.targetPosition = outPos
//            motor.power = 0.2
//        })
            ToPositionCommand(motor.motor, outPos, requirementList = listOf(this@IntakeExtension))

    override fun initialize() {
        motor.initialize()
        motor.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
        motor.targetPosition = 0
        motor.mode = DcMotor.RunMode.RUN_TO_POSITION
    }
}