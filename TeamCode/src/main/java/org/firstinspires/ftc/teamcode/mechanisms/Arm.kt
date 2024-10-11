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
<<<<<<< HEAD
//    val armServoLeft = ServoEx(name)
=======
    val armServoLeft = ServoEx(name2)
>>>>>>> 8dc0203fcabee346d9760a07381f245e4bf86a0d

    @JvmField
    var intakePos = 0.9
    @JvmField
    var scorePos = 0.25
    @JvmField
    var samplePickupPos = 0.0


    val toIntakePos: Command
        get() = ParallelCommandGroup(
            MoveServo(armServo, intakePos, 1.0),
//            MoveServo(armServoLeft, 1.0 - intakePos, 1.0)
        )
    val toScorePos: Command
        get() = ParallelCommandGroup(
            MoveServo(armServo, scorePos, 1.0),
//            MoveServo(armServoLeft, 1.0 - scorePos, 1.0)
        )
    val toSamplePickupPos: Command
        get() = ParallelCommandGroup(
            MoveServo(armServo, samplePickupPos, 1.0),
//            MoveServo(armServoLeft, 1.0 - samplePickupPos, 1.0)
        )

    override fun initialize() {
        armServo.initialize()
//        armServoLeft.initialize()
    }
}