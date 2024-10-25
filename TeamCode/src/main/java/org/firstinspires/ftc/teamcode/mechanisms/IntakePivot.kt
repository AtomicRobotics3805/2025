package org.firstinspires.ftc.teamcode.mechanisms

import com.rowanmcalpin.nextftc.command.Command
import com.rowanmcalpin.nextftc.hardware.ServoEx
import com.rowanmcalpin.nextftc.subsystems.MoveServo
import com.rowanmcalpin.nextftc.subsystems.Subsystem

object IntakePivot: Subsystem {
    @JvmField
    var name = "intake_pivot"

    val intakePivotServo = ServoEx(name)

    @JvmField
    var downPos = 0.565
    @JvmField
    var downMorePos = 0.568
    @JvmField
    var upPos = 0.535
    @JvmField
    var transferPos = 0.527

    val intakePivotDown: Command
        get() = MoveServo(intakePivotServo, downPos, 0.5, listOf(this@IntakePivot))
    val intakePivotDownMore: Command
        get() = MoveServo(intakePivotServo, downMorePos, 0.5, listOf(this@IntakePivot))
    val intakePivotUp: Command
        get() = MoveServo(intakePivotServo, upPos, 0.5, listOf(this@IntakePivot))
    val intakePivotTransfer: Command
        get() = MoveServo(intakePivotServo, transferPos, 0.5, listOf(this@IntakePivot))


    override fun initialize() {
        intakePivotServo.initialize()
    }
}