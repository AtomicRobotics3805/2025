package org.firstinspires.ftc.teamcode.teleop.opmodes

import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.rowanmcalpin.nextftc.Constants
import com.rowanmcalpin.nextftc.command.utility.TelemetryCommand
import com.rowanmcalpin.nextftc.controls.Controls
import com.rowanmcalpin.nextftc.opmodes.NextFTCOpMode
import org.firstinspires.ftc.teamcode.TestCommand
import org.firstinspires.ftc.teamcode.mechanisms.Arm
import org.firstinspires.ftc.teamcode.mechanisms.Claw
import org.firstinspires.ftc.teamcode.mechanisms.Intake
import org.firstinspires.ftc.teamcode.mechanisms.IntakeExtension
import org.firstinspires.ftc.teamcode.mechanisms.IntakePivot
import org.firstinspires.ftc.teamcode.mechanisms.Lift
import org.firstinspires.ftc.teamcode.teleop.TeleOpRoutines

@TeleOp(name="Testing TeleOp")
class TestingTeleOp: NextFTCOpMode(Arm, Claw, Intake, IntakeExtension, IntakePivot, Lift) {
    override val controls: Controls = org.firstinspires.ftc.teamcode.teleop.Controls()
    override fun onInit() {
        Constants.opMode = this
//        TeleOpRoutines.reset()
        IntakePivot.intakePivotUp()
        Arm.toIntakePos()
        Claw.clawOpen()
        TestCommand()
        super.telemetry.addData("Lift position", Lift.motor1.currentPosition)
        super.telemetry.addData("Lift target", Lift.motor1.targetPosition)
        super.telemetry.addData("Lift power", Lift.motor1.power)
        super.telemetry.update()
    }

    override fun onUpdate() {
        telemetryData.add(Pair("Lift position", Lift.motor1.currentPosition))
        telemetryData.add(Pair("Lift target", Lift.motor1.targetPosition))
        telemetryData.add(Pair("Lift power", Lift.motor1.power))
        telemetryData.add(Pair("IntakeExtension", IntakeExtension.motor.currentPosition))
        updateOurTelemetry()
    }

    companion object {
        public var telemetryData: MutableList<Pair<String, Any>> = mutableListOf()
    }

    fun updateOurTelemetry() {
        telemetryData.forEach {
            super.telemetry.addData(it.first, it.second)
        }

        super.telemetry.update()

        telemetryData.clear()
    }
}