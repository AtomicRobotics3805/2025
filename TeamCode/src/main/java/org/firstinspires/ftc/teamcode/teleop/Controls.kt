package org.firstinspires.ftc.teamcode.teleop

import com.rowanmcalpin.nextftc.controls.Controls
import org.firstinspires.ftc.teamcode.mechanisms.Arm
import org.firstinspires.ftc.teamcode.mechanisms.Intake
import org.firstinspires.ftc.teamcode.mechanisms.IntakeExtension
import org.firstinspires.ftc.teamcode.mechanisms.Lift

class Controls: Controls() {
    override fun registerCommands() {
        gamepad1.b.pressedCommand = { Lift.toMiddle }
        gamepad1.x.pressedCommand = { Lift.toLow }
        gamepad1.y.pressedCommand = { IntakeExtension.extensionIn }
        gamepad1.leftBumper.pressedCommand = { IntakeExtension.extensionOut }
    }
}