package org.firstinspires.ftc.teamcode.mechanisms

import com.acmerobotics.dashboard.config.Config
import com.rowanmcalpin.nextftc.command.Command
import com.rowanmcalpin.nextftc.command.groups.ParallelCommandGroup
import com.rowanmcalpin.nextftc.command.groups.SequentialCommandGroup
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
    var samplePickupPos = 0.908


    val toIntakePos: Command
        get() = MoveServo(armServo, intakePos, 0.5, listOf(this@Arm))
//            MoveServo(armServoLeft, 1.0 - intakePos, 1.0)

    val toScorePos: Command
        get() = MoveServo(armServo, scorePos, 0.5, listOf(this@Arm))
    val toSamplePickupPos: Command
        get() = MoveServo(armServo, samplePickupPos, 1.0, listOf(this@Arm))

    override fun initialize() {
        armServo.initialize()
//        armServoLeft.initialize()
    }
}