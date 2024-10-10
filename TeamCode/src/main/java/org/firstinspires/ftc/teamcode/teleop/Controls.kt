package org.firstinspires.ftc.teamcode.teleop

import com.rowanmcalpin.nextftc.Constants
import com.rowanmcalpin.nextftc.command.CommandScheduler
import com.rowanmcalpin.nextftc.controls.Controls
import com.rowanmcalpin.nextftc.driving.DriverControlled
import org.firstinspires.ftc.teamcode.drive.DriveConstants
import org.firstinspires.ftc.teamcode.mechanisms.Claw
import org.firstinspires.ftc.teamcode.mechanisms.Intake
import org.firstinspires.ftc.teamcode.mechanisms.Lift

class Controls: Controls() {
    override fun registerCommands() {
        val dc = DriverControlled(Constants.opMode.gamepad1, pov = DriveConstants.POV, reverseStrafe = DriveConstants.REVERSE_STRAFE, reverseStraight = DriveConstants.REVERSE_STRAIGHT, reverseTurn = DriveConstants.REVERSE_TURN)
        CommandScheduler.scheduleCommand(dc)

        gamepad2.a.pressedCommand = { TeleOpRoutines.outToIntake }
        gamepad2.b.pressedCommand = { TeleOpRoutines.inToTransfer }
        gamepad2.x.pressedCommand = { TeleOpRoutines.scoreToRepeat }

        gamepad2.y.toggleCommands = listOf({ TeleOpRoutines.scoreToSample }, { TeleOpRoutines.sampleToScore })

        gamepad2.rightBumper.toggleCommands = listOf({ Intake.start }, { Intake.start })
        gamepad2.leftBumper.toggleCommands = listOf({ Intake.reverse }, { Intake.stop })

        gamepad2.dpadUp.pressedCommand = { Lift.up }
        gamepad2.dpadDown.pressedCommand = { Lift.down }
        gamepad2.dpadUp.releasedCommand = { Lift.stop }
        gamepad2.dpadDown.releasedCommand = { Lift.stop }







    }
}