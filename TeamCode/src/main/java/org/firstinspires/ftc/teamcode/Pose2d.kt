package org.firstinspires.ftc.teamcode

class Pose2d(var x: Double, var y: Double, var heading: Double) {
    operator fun minus(other: Pose2d): Pose2d {
        return Pose2d(x - other.x, y - other.y, heading - other.heading)
    }
}