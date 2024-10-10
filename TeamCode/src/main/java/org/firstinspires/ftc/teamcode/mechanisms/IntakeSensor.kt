package org.firstinspires.ftc.teamcode.mechanisms

import com.qualcomm.hardware.rev.RevColorSensorV3
import com.rowanmcalpin.nextftc.subsystems.Subsystem
import org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit


object IntakeSensor: Subsystem {

    val sensor = hardwareMap.get(RevColorSensorV3::class.java, "sensor")

    var selection = 0




    fun detectColor(): Int {


        selection = if (sensor.getDistance(DistanceUnit.CM) < 5) {
            // Update each pixel in the strip.
            if (sensor.green() > sensor.red() && sensor.red() > sensor.blue()) {
                3
            } else if (sensor.red() > sensor.blue() && sensor.green() < sensor.red()) {
                1
            } else {
                2
            }
        } else {
            0
        }
        return selection
    }
}