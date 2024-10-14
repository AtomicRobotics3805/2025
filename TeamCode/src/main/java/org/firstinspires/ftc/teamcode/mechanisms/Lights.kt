package org.firstinspires.ftc.teamcode.mechanisms

import android.text.Selection
import com.rowanmcalpin.nextftc.Constants
import com.rowanmcalpin.nextftc.command.Command
import com.rowanmcalpin.nextftc.subsystems.Subsystem
import org.firstinspires.ftc.teamcode.dotstar.DotStarBridgedLED
import org.firstinspires.ftc.teamcode.mechanisms.IntakeSensor.sensor

object Lights: Subsystem {

    lateinit var leds: DotStarBridgedLED

    override fun initialize() {
        leds =  Constants.opMode.hardwareMap.get(DotStarBridgedLED::class.java, "leds")
        leds.setController(DotStarBridgedLED.Controller.RevExpansionHub)
        leds.length = 30
    }

    class DisplayColor: Command() {
        override val _isDone = false

        override fun onExecute() {
            for (i in 0 until leds.length) {
                when (IntakeSensor.selection) {
                    1 -> {
                        leds.setPixel(i, 255, 0, 0)
                    }

                    2 -> {
                        leds.setPixel(i, 0, 0, 255)
                    }

                    3 -> {
                        leds.setPixel(i, 255, 255, 0)
                    }

                    4 -> {
                        leds.setPixel(i, 0, 0, 0)
                    }
                    else -> {
                        leds.setPixel(i, 25, 239, 0)
                    }
                }
            }
            leds.update()
        }

        override fun onEnd(interrupted: Boolean) {
            isDone = true
            onExecute()
//            for (i in 0 until leds.length) {
////                leds.setPixel(i,0, 0, 0)
//            }
//            leds.update()
        }
    }
}