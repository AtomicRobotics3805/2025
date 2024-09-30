package org.firstinspires.ftc.teamcode

import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.util.Range
import com.rowanmcalpin.nextftc.Constants
import com.rowanmcalpin.nextftc.command.Command
import com.rowanmcalpin.nextftc.hardware.MotorEx
import com.rowanmcalpin.nextftc.subsystems.Subsystem
import org.firstinspires.ftc.teamcode.teleop.opmodes.TestingTeleOp.Companion.telemetryData
import kotlin.math.abs
import kotlin.math.sign

class ToPositionCommand(val motor: DcMotor, val target: Int, val ff: Double = 0.0, val tolerance: Int = 100, val requirementList: List<Subsystem>): Command() {
    override val requirements: List<Subsystem>
        get() = requirementList

    override val _isDone: Boolean
        get() = abs(error)<tolerance

    var power: Double = 0.0
    var error: Int = 0
    var direction: Double = 0.0

    override fun onStart() {
        motor.mode = DcMotor.RunMode.RUN_WITHOUT_ENCODER
        error = target - motor.currentPosition
        motor.targetPosition = target
    }

    override fun onExecute() {
        error = target - motor.currentPosition
        direction = sign(error.toDouble())
        power = 0.003 * abs(error) * direction
        telemetryData.add(Pair("Error", error));
        motor.power = Range.clip(power, -1.0, 1.0)
    }
}