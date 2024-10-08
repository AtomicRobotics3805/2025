package org.firstinspires.ftc.teamcode.teleop

import com.rowanmcalpin.nextftc.controls.Controls
import org.firstinspires.ftc.teamcode.mechanisms.Arm
import org.firstinspires.ftc.teamcode.mechanisms.Intake
import org.firstinspires.ftc.teamcode.mechanisms.IntakeExtension
import org.firstinspires.ftc.teamcode.mechanisms.IntakePivot
import org.firstinspires.ftc.teamcode.mechanisms.Lift

class Controls: Controls() {
    override fun registerCommands() {
        gamepad1.b.pressedCommand = { Intake.start }
        gamepad1.x.pressedCommand = { Intake.stop }
        gamepad1.y.pressedCommand = { IntakeExtension.extensionIn }
        gamepad1.leftBumper.pressedCommand = { IntakeExtension.extensionOut }
        gamepad1.a.pressedCommand = { IntakePivot.intakePivotDown }
        gamepad1.rightBumper.pressedCommand = { IntakePivot.intakePivotTransfer }
    }
}