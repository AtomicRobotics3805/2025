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
    var openPos = 0.15
    @JvmField
    var closedPos = 0.35
    @JvmField
    var specimenOpenPos = 0.08

    val open: Command
        get() = MoveServo(clawServo, openPos, 1.0, listOf(this@Claw))
    val close: Command
        get() = MoveServo(clawServo, closedPos, 0.7, listOf(this@Claw))
    val specimenOpen: Command
        get() = MoveServo(clawServo, specimenOpenPos, 1.0, listOf(this@Claw))

    override fun initialize() {
        clawServo.initialize()
    }
}
