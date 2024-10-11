package org.firstinspires.ftc.teamcode.teleop.opmodes

import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.rowanmcalpin.nextftc.Constants
import com.rowanmcalpin.nextftc.controls.Controls
import com.rowanmcalpin.nextftc.opmodes.NextFTCOpMode
import org.firstinspires.ftc.teamcode.mechanisms.Arm
import org.firstinspires.ftc.teamcode.mechanisms.Claw
import org.firstinspires.ftc.teamcode.mechanisms.Intake
import org.firstinspires.ftc.teamcode.mechanisms.IntakeExtension
import org.firstinspires.ftc.teamcode.mechanisms.IntakePivot
import org.firstinspires.ftc.teamcode.mechanisms.IntakeSensor
import org.firstinspires.ftc.teamcode.mechanisms.Lift
import org.firstinspires.ftc.teamcode.mechanisms.Lights
import org.firstinspires.ftc.teamcode.teleop.TeleOpRoutines

@TeleOp(name="TeleOp")
class TeleOp: NextFTCOpMode(Arm, Claw, Intake, IntakeExtension, IntakePivot, Lift, IntakeSensor) {
    override val controls: Controls = org.firstinspires.ftc.teamcode.teleop.Controls()
    override fun onInit() {
        Constants.opMode = this
        TeleOpRoutines.reset()
    }

    override fun onUpdate() {
        Lights.displayColor(IntakeSensor.detectColor())
    }

}