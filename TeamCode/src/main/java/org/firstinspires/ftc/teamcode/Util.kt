package org.firstinspires.ftc.teamcode

import kotlin.math.PI

object Util {
    fun clamp(value: Double, max: Double, min: Double): Double {
        if (value > max) {
            return max
        }
        if (value < min) {
            return min
        }

        return value
    }

    fun toRadians(value: Double): Double {
        return value * PI/180
    }
}