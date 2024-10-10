package org.firstinspires.ftc.teamcode.mechanisms

import android.text.Selection
import com.rowanmcalpin.nextftc.subsystems.Subsystem
import org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap
import org.firstinspires.ftc.teamcode.dotstar.DotStarBridgedLED
import org.firstinspires.ftc.teamcode.mechanisms.IntakeSensor.sensor

object Lights: Subsystem {

    val leds = hardwareMap.get<DotStarBridgedLED>(DotStarBridgedLED::class.java, "leds")

    override fun initialize() {
        leds.setController(DotStarBridgedLED.Controller.RevExpansionHub)
    }

    fun displayColor(selection: Int) {
        for (i in leds.pixels.indices) {
            when (selection) {
                1 -> {
                    leds.setPixel(i, 255, 0, 0)
                }

                2 -> {
                    leds.setPixel(i, 0, 0, 255)
                }

                3 -> {
                    leds.setPixel(i, 255, 255, 0)
                }

                else -> {
                    leds.clear()
                }
            }
        }
        leds.update()
    }

}