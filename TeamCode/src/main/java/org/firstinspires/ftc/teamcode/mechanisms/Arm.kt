package org.firstinspires.ftc.teamcode.mechanisms

import com.acmerobotics.dashboard.config.Config
import com.rowanmcalpin.nextftc.command.Command
import com.rowanmcalpin.nextftc.hardware.ServoEx
import com.rowanmcalpin.nextftc.subsystems.MoveServo
import com.rowanmcalpin.nextftc.subsystems.Subsystem

@Config
object Arm: Subsystem {
    @JvmField
    var name = "arm"
    @JvmField
//    var name2 = "arm2"
    val armServo = ServoEx(name)

    @JvmField
    var intakePos = 0.027
    @JvmField
    var scorePos = 0.647
    @JvmField
    var ascentOnePos = 0.6
    @JvmField
    var specimenPickupPos = 0.908


    val toIntakePos: Command
        get() = MoveServo(armServo, intakePos, 1.0, listOf(this@Arm))
    val toAscentOnePos: Command
        get() = MoveServo(armServo, ascentOnePos, 1.0, listOf(this@Arm))
    val toScorePos: Command
        get() = MoveServo(armServo, scorePos, 1.0, listOf(this@Arm))
    val toSpecimenPickupPos: Command
        get() = MoveServo(armServo, specimenPickupPos, 1.0, listOf(this@Arm))

    override fun initialize() {
        armServo.initialize()
    }
}