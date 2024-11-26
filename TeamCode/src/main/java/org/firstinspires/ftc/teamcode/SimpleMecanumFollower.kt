package org.firstinspires.ftc.teamcode

import com.qualcomm.robotcore.util.ElapsedTime
import com.rowanmcalpin.nextftc.command.Command

class SimpleMecanumFollower(val mecanumDrive: SimpleMecanumDrive, private val linearPIDCoefficients: PIDCoefficients = PDCoefficients(0.003, 0.002).asPIDCoefficients(), private val angularPIDCoefficients: PIDCoefficients = PDCoefficients(0.003, 0.002).asPIDCoefficients()): Command() {
    override val _isDone = false

    val lastError: Pose2d = Pose2d(0.0, 0.0, 0.0)

    val timer = ElapsedTime()

    companion object {
        var targetPosition: Pose2d = Pose2d(0.0, 0.0, 0.0)
    }

    override fun onStart() {
        timer.reset()
    }

    override fun onExecute() {
        updatePID()
    }

    /**
     * Recalculates and applies motor powers
     */
    private fun updatePID() {
        val currentPosition = mecanumDrive.poseEstimate
        val error: Pose2d = targetPosition - currentPosition

        val derivative = error - lastError

        val xOutput = (error.x * linearPIDCoefficients.kP) + (derivative.x * linearPIDCoefficients.kD)
        val yOutput = (error.y * linearPIDCoefficients.kP) + (derivative.y * linearPIDCoefficients.kD)
        val headingOutput = (error.heading * angularPIDCoefficients.kP) + (derivative.heading * angularPIDCoefficients.kD)

        mecanumDrive.setMotorPowers(mecanumDrive.calculateScaledMotorPowers(mecanumDrive.fieldToLocal(Pose2d(xOutput, yOutput, headingOutput))))
    }
}