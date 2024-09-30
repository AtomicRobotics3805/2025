package org.firstinspires.ftc.teamcode.mechanisms

import com.acmerobotics.dashboard.config.Config
import com.rowanmcalpin.nextftc.command.Command
import com.rowanmcalpin.nextftc.command.groups.SequentialCommandGroup
import com.rowanmcalpin.nextftc.hardware.ServoEx
import com.rowanmcalpin.nextftc.subsystems.MoveServo
import com.rowanmcalpin.nextftc.subsystems.Subsystem

@Config
object Arm: Subsystem {
    @JvmField
    var name = "arm"
    val armServo = ServoEx(name)

    @JvmField
    var intakePos = 0.9
    @JvmField
    var scorePos = 0.25
    @JvmField
    var samplePickupPos = 0.0


    val toIntakePos: Command
        get() = SequentialCommandGroup(
            MoveServo(armServo, intakePos, 1.0)
        )
    val toScorePos: Command
        get() = SequentialCommandGroup(
            MoveServo(armServo, scorePos, 1.0)
        )
    val toSamplePickupPos: Command
        get() = SequentialCommandGroup(
            MoveServo(armServo, samplePickupPos, 1.0)
        )

    override fun initialize() {
        armServo.initialize()
    }
}