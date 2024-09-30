package org.firstinspires.ftc.teamcode.mechanisms

import com.acmerobotics.dashboard.config.Config
import com.rowanmcalpin.nextftc.command.Command
import com.rowanmcalpin.nextftc.command.groups.ParallelCommandGroup
import com.rowanmcalpin.nextftc.hardware.CRServoEx
import com.rowanmcalpin.nextftc.hardware.ServoEx
import com.rowanmcalpin.nextftc.subsystems.MoveServo
import com.rowanmcalpin.nextftc.subsystems.PowerServo
import com.rowanmcalpin.nextftc.subsystems.Subsystem
import javax.sql.StatementEvent

@Config
object Intake: Subsystem { // NOT DONE
    @JvmField
    var name = "intake"
    @JvmField
    var name2 = "intake2"
    @JvmField
    var speed = -1.0

    val servo: CRServoEx = CRServoEx(name)
    val servo2: CRServoEx = CRServoEx(name2, CRServoEx.Direction.REVERSE)

    val start: Command
        get() = ParallelCommandGroup(
            PowerServo(servo, speed, listOf(this@Intake)),
            PowerServo(servo2, speed)
        )
    val stop: Command
        get() = ParallelCommandGroup(
            PowerServo(servo, 0.0, listOf(this@Intake)),
            PowerServo(servo2, 0.0)
        )
    val reverse: Command
        get() = ParallelCommandGroup(
            PowerServo(servo, -speed, listOf(this@Intake)),
            PowerServo(servo2, -speed)
        )

    override fun initialize() {
        servo.initialize()
        servo2.initialize()
    }
}