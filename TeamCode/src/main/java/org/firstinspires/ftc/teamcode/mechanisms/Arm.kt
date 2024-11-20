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
    var intakePos = 0.05
    @JvmField
    var scorePos = 0.69
    @JvmField
    var ascentOnePos = 0.69
    @JvmField
    var specimenPickupPos = 0.97
    @JvmField
    var specimenPreScorePose = 0.69
    @JvmField
    var specimenScorePos = 0.69


    val toIntakePos: Command
        get() = MoveServo(armServo, intakePos, 1.0, listOf(this@Arm))
    val toAscentOnePos: Command
        get() = MoveServo(armServo, ascentOnePos, 1.0, listOf(this@Arm))
    val toScorePos: Command
        get() = MoveServo(armServo, scorePos, 1.0, listOf(this@Arm))
    val toSpecimenPickupPos: Command
        get() = MoveServo(armServo, specimenPickupPos, 1.0, listOf(this@Arm))
    val toSpecimenPreScorePos: Command
        get() = MoveServo(armServo, specimenPreScorePose, 1.0, listOf(this@Arm))
    val toSpecimenScorePos: Command
        get() = MoveServo(armServo, specimenScorePos, 1.0, listOf(this@Arm))

    override fun initialize() {
        armServo.initialize()
    }
}