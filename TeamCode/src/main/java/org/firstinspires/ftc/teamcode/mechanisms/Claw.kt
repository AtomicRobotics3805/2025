package org.firstinspires.ftc.teamcode.mechanisms

import com.acmerobotics.dashboard.config.Config
import com.rowanmcalpin.nextftc.command.Command
import com.rowanmcalpin.nextftc.hardware.ServoEx
import com.rowanmcalpin.nextftc.subsystems.MoveServo
import com.rowanmcalpin.nextftc.subsystems.Subsystem

@Config
object Claw: Subsystem {
    @JvmField
    var name = "claw"

    val clawServo = ServoEx(name)

    @JvmField
    var openPos = 0.2
    @JvmField
    var closedPos = 0.35

    val clawOpen: Command
        get() = MoveServo(clawServo, openPos, 1.0, listOf(this@Claw))
    val clawClosed: Command
        get() = MoveServo(clawServo, closedPos, 1.0, listOf(this@Claw))

    override fun initialize() {
        clawServo.initialize()
    }
}
