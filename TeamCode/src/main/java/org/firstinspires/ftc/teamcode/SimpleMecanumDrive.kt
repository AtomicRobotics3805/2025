package org.firstinspires.ftc.teamcode

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot
import com.qualcomm.robotcore.hardware.IMU
import com.rowanmcalpin.nextftc.driving.localizers.SubsystemLocalizer
import com.rowanmcalpin.nextftc.hardware.MotorEx
import com.rowanmcalpin.nextftc.subsystems.Subsystem
import org.firstinspires.ftc.ftccommon.internal.manualcontrol.parameters.ImuParameters
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.max
import kotlin.math.sin

class SimpleMecanumDrive(val constants: SimpleMecanumConstants, val localizer: SubsystemLocalizer): Subsystem {
    /**
     * Left front motor.
     */
    private lateinit var leftFront: MotorEx
    /**
     * Left back motor.
     */
    private lateinit var leftBack: MotorEx
    /**
     * Right front motor.
     */
    private lateinit var rightFront: MotorEx
    /**
     * Right back motor.
     */
    private lateinit var rightBack: MotorEx

    /**
     * List of the motors in the following order: LF, LB, RF, RB.
     */
    private lateinit var motors: List<MotorEx>

    private lateinit var imuParameters: IMU.Parameters

    private lateinit var imu: IMU

    /**
     * Current heading of the robot, reported by the IMU. Value is in radians.
     */
    val heading: Double
        get() = Util.toRadians(imu.robotYawPitchRollAngles.yaw)

    /**
     * Heading velocity of the robot, reported by the IMU. Value is in radians.
     */
    val headingVelocity: Double
        get() = imu.getRobotAngularVelocity(AngleUnit.RADIANS).zRotationRate.toDouble()

    val poseEstimate: Pose2d
        get() = Pose2d(localizer.poseEstimate.x, localizer.poseEstimate.y, localizer.poseEstimate.heading)

    /**
     * Converts field-centric values (x, y, and rotation) into robot-centric ones.
     * @param inputs Pose2d for the desired movement. x is horizontal, y is vertical when viewed from the red alliance box.
     * @return robot-oriented inputs (drive, turn, and strafe)
     */
    fun fieldToLocal(inputs: Pose2d): Pose2d {
        return Pose2d(inputs.x * cos(-heading) - inputs.y * sin(-heading), inputs.x * sin(-heading) + inputs.y * cos(-heading), inputs.heading)
    }

    /**
     * Calculates the powers for the motor given robot-centric input.
     * @param inputs inputs.x is drive, inputs.y is strafe, inputs.heading is turn.
     * @return list of motor powers in the following order: LF, LB, RF, RB.
     */
    fun calculateMotorPowers(inputs: Pose2d): List<Double> {
        return listOf(
            (inputs.x - inputs.y - inputs.heading),
            (inputs.x + inputs.y - inputs.heading),
            (inputs.x + inputs.y + inputs.heading),
            (inputs.x - inputs.y + inputs.heading)
        )
    }

    /**
     * Calculates the scaled powers for the motor given robot-centric input.
     * @param inputs inputs.x is drive, inputs.y is strafe, inputs.heading is turn.
     * @return list of motor powers in the following order: LF, LB, RF, RB.
     */
    fun calculateScaledMotorPowers(inputs: Pose2d): List<Double> {
        val denominator: Double = max(abs(inputs.x) + abs(inputs.y) + abs(inputs.heading), 1.0)
        return listOf(
            (inputs.x - inputs.y - inputs.heading) / denominator,
            (inputs.x + inputs.y - inputs.heading) / denominator,
            (inputs.x + inputs.y + inputs.heading) / denominator,
            (inputs.x - inputs.y + inputs.heading) / denominator
        )
    }

    /**
     * Sets the motor powers.
     * @param powers the list of motor powers. MUST be exactly 4 items.
     */
    fun setMotorPowers(powers: List<Double>) {
        if (powers.size != 4) {
            throw InvalidPowerListException("Motor power list must be exactly 4 lines")
        }

        for (i in 0..4) {
            motors[i].power = Util.clamp(powers[i], -1.0, 1.0)
        }
    }

    override fun initialize() {
        leftFront.initialize()
        leftBack.initialize()
        rightFront.initialize()
        rightBack.initialize()
        motors = listOf(leftFront, leftBack, rightFront, rightBack)

        imuParameters = IMU.Parameters(
            RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.UP,
                RevHubOrientationOnRobot.UsbFacingDirection.RIGHT
            )
        )
        imu.initialize(imuParameters)
    }
}