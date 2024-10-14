package org.firstinspires.ftc.teamcode.teleop

import com.rowanmcalpin.nextftc.Constants
import com.rowanmcalpin.nextftc.controls.Controls
import com.rowanmcalpin.nextftc.driving.DriverControlled
import org.firstinspires.ftc.teamcode.drive.DriveConstants
import org.firstinspires.ftc.teamcode.mechanisms.Intake
import org.firstinspires.ftc.teamcode.mechanisms.Lift

class Controls: Controls() {
    override fun registerCommands() {
        val dc = DriverControlled(Constants.opMode.gamepad1, pov = DriverControlled.POV.FIELD_CENTRIC,
            reverseStrafe = DriveConstants.REVERSE_STRAFE,
            reverseStraight =  DriveConstants.REVERSE_STRAIGHT,
            reverseTurn =  DriveConstants.REVERSE_TURN)
        dc()

        gamepad1.a.pressedCommand = { TeleOpRoutines.outToIntake }
        gamepad1.rightTrigger.pressedCommand = { Intake.start }
        gamepad1.rightTrigger.releasedCommand = { Intake.stop }
        gamepad1.leftTrigger.pressedCommand = { Intake.reverse }
        gamepad1.leftTrigger.releasedCommand = { Intake.stop }

        gamepad2.a.pressedCommand = { TeleOpRoutines.outToIntake }
        gamepad2.b.pressedCommand = { TeleOpRoutines.inToTransfer }
        gamepad2.rightTrigger.pressedCommand = { TeleOpRoutines.scoreToRepeat }
        gamepad2.y.toggleCommands = listOf({ TeleOpRoutines.toSpecimenPickup }, { TeleOpRoutines.specimenPickupToScore })
        gamepad2.x.pressedCommand = { TeleOpRoutines.liftUp }

        gamepad1.rightBumper.pressedCommand = { Constants.drive.switchSpeed() }
        gamepad1.rightBumper.releasedCommand = { Constants.drive.switchSpeed() }

        gamepad2.dpadUp.pressedCommand = { Lift.up }
        gamepad2.dpadUp.releasedCommand = { Lift.stop }
        gamepad2.dpadDown.pressedCommand = { Lift.down }
        gamepad2.dpadDown.releasedCommand = { Lift.stop }
        /*
        gamepad1.a.pressedCommand = { TeleOpRoutines.outToIntake }
        gamepad1.b.pressedCommand = { TeleOpRoutines.inToTransfer }
        gamepad1.y.pressedCommand = { Intake.stop }
        gamepad1.y.releasedCommand = { Intake.start }
        gamepad1.x.pressedCommand = { TeleOpRoutines.scoreToRepeat }
        gamepad1.leftBumper.pressedCommand = { TeleOpRoutines.liftUp }
        */

    }
}