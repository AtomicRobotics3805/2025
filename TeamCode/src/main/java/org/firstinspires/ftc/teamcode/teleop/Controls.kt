package org.firstinspires.ftc.teamcode.teleop

import com.rowanmcalpin.nextftc.Constants
import com.rowanmcalpin.nextftc.controls.Controls
import com.rowanmcalpin.nextftc.driving.DriverControlled
import org.firstinspires.ftc.teamcode.drive.DriveConstants
import org.firstinspires.ftc.teamcode.mechanisms.Arm
import org.firstinspires.ftc.teamcode.mechanisms.Intake
import org.firstinspires.ftc.teamcode.mechanisms.IntakeExtension
import org.firstinspires.ftc.teamcode.mechanisms.Lift

class Controls: Controls() {
    override fun registerCommands() {
        val dc = DriverControlled(Constants.opMode.gamepad1, pov = DriverControlled.POV.FIELD_CENTRIC,
            reverseStrafe = DriveConstants.REVERSE_STRAFE,
            reverseStraight =  DriveConstants.REVERSE_STRAIGHT,
            reverseTurn =  DriveConstants.REVERSE_TURN)
        dc()

        gamepad2.leftStick.button.pressedCommand = { Lift.resetEncoder }


        gamepad1.x.pressedCommand = { dc.resetRotation }

        gamepad1.a.pressedCommand = { TeleOpRoutines.outToIntake }
        gamepad1.rightTrigger.pressedCommand = { Intake.start }
        gamepad1.rightTrigger.releasedCommand = { Intake.stop }
        gamepad1.leftTrigger.pressedCommand = { Intake.reverse }
        gamepad1.leftTrigger.releasedCommand = { Intake.stop }

        gamepad1.leftBumper.pressedCommand = { TeleOpRoutines.slightlyOutToIntake }

        gamepad1.dpadDown.pressedCommand = { Arm.toAscentOnePos }

        gamepad2.a.pressedCommand = { TeleOpRoutines.outToIntake }
        gamepad2.b.pressedCommand = { TeleOpRoutines.inToTransfer }
        gamepad2.rightTrigger.pressedCommand = { TeleOpRoutines.scoreToRepeat }
        gamepad2.y.toggleCommands = listOf({ TeleOpRoutines.toSpecimenPickup }, { TeleOpRoutines.specimenPickupToScore })
        gamepad2.x.pressedCommand = { TeleOpRoutines.liftUp }

        gamepad1.rightBumper.pressedCommand = { Constants.drive.switchSpeed() }
        gamepad1.rightBumper.releasedCommand = { Constants.drive.switchSpeed() }

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