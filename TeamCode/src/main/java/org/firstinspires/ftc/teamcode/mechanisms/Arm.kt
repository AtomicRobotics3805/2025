package org.firstinspires.ftc.teamcode.mechanisms

import com.acmerobotics.dashboard.config.Config
import com.rowanmcalpin.nextftc.command.Command
import com.rowanmcalpin.nextftc.command.groups.SequentialCommandGroup
import com.rowanmcalpin.nextftc.hardware.ServoEx
import com.rowanmcalpin.nextftc.subsystems.MoveServo
import com.rowanmcalpin.nextftc.subsystems.Subsystem

// If a second servo is added facing the opposite direction as the main servo, uncomment all of the
//  commented lines.
@Config
object Arm: Subsystem {
    @JvmField
    var name = "arm"
//    @JvmField
//    var name2 = "arm2"
    val armServo = ServoEx(name)
//    val armServo2 = ServoEx(name2)

    @JvmField
    var intakePos = 1.0 // NOT DONE
    @JvmField
    var scorePos = 0.0 // NOT DONE
    @JvmField
    var samplePickupPos = 0.4 // NOT DONE

    val Double.servoInverted get() = -this + 1

    val toIntakePos: Command
        get() = SequentialCommandGroup(
            MoveServo(armServo, intakePos, 1.0)
//            , MoveServo(armServo2, intakePos.servoInverted, 1.0)
        )
    val toScorePos: Command
        get() = SequentialCommandGroup(
            MoveServo(armServo, scorePos, 1.0)
//            , MoveServo(armServo2, scorePos.servoInverted, 1.0)
        )
    val toSamplePickupPos: Command
        get() = SequentialCommandGroup(
            MoveServo(armServo, samplePickupPos, 1.0)
//            , MoveServo(armServo2, samplePickupPos.servoInverted, 1.0)
        )
}