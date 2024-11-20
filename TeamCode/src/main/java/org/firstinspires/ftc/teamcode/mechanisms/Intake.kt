package org.firstinspires.ftc.teamcode.mechanisms

import com.acmerobotics.dashboard.config.Config
import com.qualcomm.robotcore.hardware.ServoImplEx
import com.rowanmcalpin.nextftc.command.Command
import com.rowanmcalpin.nextftc.command.groups.ParallelCommandGroup
import com.rowanmcalpin.nextftc.command.groups.SequentialCommandGroup
import com.rowanmcalpin.nextftc.command.utility.CustomCommand
import com.rowanmcalpin.nextftc.command.utility.Delay
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
    var speed = -0.9

    val servo: CRServoEx = CRServoEx(name)
    val servo2: CRServoEx = CRServoEx(name2, CRServoEx.Direction.REVERSE)

    val start: Command
        get() = ParallelCommandGroup(
            PowerServo(servo2, speed),
            PowerServo(servo, speed)
        )
    val stop: Command
        get() = ParallelCommandGroup(
            PowerServo(servo2, 0.0),
            PowerServo(servo, 0.0)
        )
    val reverse: Command
        get() = ParallelCommandGroup(
            PowerServo(servo, -speed),
            PowerServo(servo2, -speed)
        )

    val wakeUp: Command
        get() = SequentialCommandGroup(
            ParallelCommandGroup(
                CustomCommand(getDone = { true }, _start = { servo.position = -1.0 }),
                CustomCommand(getDone = { true }, _start = { servo2.position = -1.0 })
            ),
            Delay(1.0),
            ParallelCommandGroup(
                CustomCommand(getDone = { true }, _start = { servo.position = 1.0 }),
                CustomCommand(getDone = { true }, _start = { servo2.position = 1.0 })
            ),
            Delay(1.0),
            ParallelCommandGroup(
                CustomCommand(getDone = { true }, _start = { servo.position = -1.0 }),
                CustomCommand(getDone = { true }, _start = { servo2.position = -1.0 })
            ),
            Delay(1.0)
        )

    override fun initialize() {
        servo.initialize()
        servo2.initialize()
    }
}