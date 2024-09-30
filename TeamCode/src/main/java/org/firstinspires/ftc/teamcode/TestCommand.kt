package org.firstinspires.ftc.teamcode

import com.rowanmcalpin.nextftc.Constants
import com.rowanmcalpin.nextftc.command.Command

class TestCommand: Command() {
    override val _isDone: Boolean
        get() = count >= target

    var count = 0
    var target = 10

    override fun onStart() {
        Constants.opMode.telemetry.addData("Test Command Started", true)
    }

    override fun onExecute() {
        count += 1;
        Constants.opMode.telemetry.addData("Count"+count, count)
    }

    override fun onEnd(interrupted: Boolean) {
        Constants.opMode.telemetry.addData("Finished", true)
    }
}