package org.firstinspires.ftc.teamcode

class PIDCoefficients(val kP: Double, val kI: Double, val kD: Double) {
    fun asPDCoefficients(): PDCoefficients {
        return PDCoefficients(kP, kD)
    }
}

class PDCoefficients(val kP: Double, val kD: Double) {
    fun asPIDCoefficients(): PIDCoefficients {
        return PIDCoefficients(kP, 0.0, kD)
    }
}