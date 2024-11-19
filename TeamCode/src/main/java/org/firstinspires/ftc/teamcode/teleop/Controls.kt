package org.firstinspires.ftc.teamcode.teleop

import com.rowanmcalpin.nextftc.Constants
import com.rowanmcalpin.nextftc.controls.Controls
import com.rowanmcalpin.nextftc.driving.DriverControlled
import org.firstinspires.ftc.teamcode.drive.DriveConstants
import org.firstinspires.ftc.teamcode.mechanisms.Arm
import org.firstinspires.ftc.teamcode.mechanisms.Claw
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

        Lift.ManualLiftControl(10, gamepad2.rightStick)
        IntakeExtension.ManualExtensionControl(10, gamepad1.rightTrigger, gamepad1.leftTrigger)

        gamepad2.leftStick.button.pressedCommand = { Lift.Zero() }

        gamepad1.x.pressedCommand = { dc.resetRotation }

        gamepad1.a.pressedCommand = { TeleOpRoutines.outToIntake }
        gamepad1.dpadRight.pressedCommand = { Intake.start }
        gamepad1.dpadLeft.pressedCommand = { Intake.reverse }

        gamepad1.leftBumper.pressedCommand = { TeleOpRoutines.slightlyOutToIntake }

        gamepad1.dpadDown.pressedCommand = { Arm.toAscentOnePos }

        gamepad1.b.pressedCommand = { Claw.open }

        gamepad2.a.pressedCommand = { Claw.open }
        gamepad2.b.pressedCommand = { TeleOpRoutines.inToTransfer }
        gamepad2.rightTrigger.pressedCommand = { TeleOpRoutines.scoreToRepeat }
        gamepad2.y.toggleCommands = listOf({ TeleOpRoutines.toSpecimenPickup }, { TeleOpRoutines.specimenPickupToScore })
        gamepad2.x.pressedCommand = { TeleOpRoutines.liftUp }

        gamepad1.rightBumper.pressedCommand = { Constants.drive.switchSpeed() }
        gamepad1.rightBumper.releasedCommand = { Constants.drive.switchSpeed() }

        gamepad2.dpadUp.pressedCommand = { TeleOpRoutines.toHang }
        gamepad2.dpadDown.pressedCommand = { Lift.toHang }


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