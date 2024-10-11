package org.firstinspires.ftc.teamcode.teleop

import com.rowanmcalpin.nextftc.Constants
import com.rowanmcalpin.nextftc.command.groups.SequentialCommandGroup
import com.rowanmcalpin.nextftc.controls.Controls
import com.rowanmcalpin.nextftc.driving.DriverControlled
import org.firstinspires.ftc.teamcode.drive.DriveConstants
import org.firstinspires.ftc.teamcode.mechanisms.Arm
import org.firstinspires.ftc.teamcode.mechanisms.Intake
import org.firstinspires.ftc.teamcode.mechanisms.IntakeExtension
import org.firstinspires.ftc.teamcode.mechanisms.IntakePivot
import org.firstinspires.ftc.teamcode.mechanisms.Lift

class Controls: Controls() {
    override fun registerCommands() {
        val dc = DriverControlled(Constants.opMode.gamepad1, pov = DriverControlled.POV.ROBOT_CENTRIC,
            reverseStrafe = DriveConstants.REVERSE_STRAFE,
            reverseStraight =  DriveConstants.REVERSE_STRAIGHT,
            reverseTurn =  DriveConstants.REVERSE_TURN)
        dc()

        gamepad1.a.pressedCommand = { SequentialCommandGroup(
            IntakeExtension.extensionOut,
            IntakePivot.intakePivotDown,
            Intake.start) }
        gamepad1.x.pressedCommand = { Intake.start }
        gamepad1.y.pressedCommand = { Intake.stop }
    }

    private fun handleDriving() {

    }
}