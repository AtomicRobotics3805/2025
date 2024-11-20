package org.firstinspires.ftc.teamcode.drive

import com.acmerobotics.dashboard.config.Config
import com.qualcomm.robotcore.hardware.DcMotorSimple
import com.qualcomm.robotcore.hardware.PIDFCoefficients
import com.rowanmcalpin.nextftc.driving.DriverControlled
import com.rowanmcalpin.nextftc.driving.MecanumDriveConstants
import com.rowanmcalpin.nextftc.hardware.MotorEx
import com.rowanmcalpin.nextftc.roadrunner.AxisDirection
import com.acmerobotics.roadrunner.control.PIDCoefficients
import com.rowanmcalpin.nextftc.trajectories.toRadians

@Config
object DriveConstants: MecanumDriveConstants{
    @JvmField
    var _DRIFT_MULTIPLIER = 1.0
    @JvmField
    var _DRIFT_TURN_MULTIPLIER = 1.0
    @JvmField
    var _DRIVER_SPEEDS = listOf(1.0, 0.5)
    @JvmField
    var _GEAR_RATIO = 1.0
    @JvmField
    var _HEADING_PID = PIDCoefficients(8.0, 0.0, 0.0)
    @JvmField
    var _TRANSLATIONAL_PID = PIDCoefficients(8.0, 0.0, 0.0)
    @JvmField
    var _IS_RUN_USING_ENCODER = false
    @JvmField
    var _LATERAL_MULTIPLIER = 1.0
    @JvmField
    var _LEFT_BACK_MOTOR = MotorEx("LB", MotorEx.MotorType.GOBILDA_YELLOWJACKET, 13.7, direction = DcMotorSimple.Direction.REVERSE)
    @JvmField
    var _LEFT_FRONT_MOTOR = MotorEx("LF", MotorEx.MotorType.GOBILDA_YELLOWJACKET, 13.7, direction = DcMotorSimple.Direction.REVERSE)
    @JvmField
    var _RIGHT_FRONT_MOTOR = MotorEx("RF", MotorEx.MotorType.GOBILDA_YELLOWJACKET, 13.7, direction = DcMotorSimple.Direction.FORWARD)
    @JvmField
    var _RIGHT_BACK_MOTOR = MotorEx("RB", MotorEx.MotorType.GOBILDA_YELLOWJACKET, 13.7, direction = DcMotorSimple.Direction.FORWARD)

    @JvmField
    var _MAX_ANG_ACCEL = 123.7.toRadians
    @JvmField
    var _MAX_ANG_VEL = 123.7.toRadians

    // Theoretical max velocity assuming no load on motors: 86.0846416947 in/sec.
    @JvmField
    var _MAX_VEL = 45.0
    @JvmField
    var _MAX_ACCEL = 45.0

    @JvmField
    var _MAX_RPM = 435.0

    @JvmField
    var _POV = DriverControlled.POV.FIELD_CENTRIC

    //region Drivewheel encoders, unused
    @JvmField
    var _MOTOR_VEL_PID = PIDFCoefficients(0.0, 0.0, 0.0, getMotorVelocityF(MAX_RPM / 60 * TICKS_PER_REV))
    @JvmField
    var _TICKS_PER_REV = 24.0 // TO BE REMOVED
    //endregion

    @JvmField
    var _REVERSE_STRAFE = true
    @JvmField
    var _REVERSE_STRAIGHT = true
    @JvmField
    var _REVERSE_TURN = true
    @JvmField
    var _RIGHT_DRIFT_MULTIPLIER = 1.0
    @JvmField
    var _BACKWARD_DRIFT_MULTIPLIER = 1.0
    @JvmField
    var _TRACK_WIDTH = 11.125 // Inches
    @JvmField
    var _WHEEL_RADIUS = 1.889765 // Inches
    @JvmField
    var _VERTICAL_AXIS = AxisDirection.POS_Z
    @JvmField
    var _kA = 0.005
    @JvmField
    var _kStatic = 0.0
    @JvmField
    var _kV = 0.0125

    //region Backend
    override val BACKWARD_DRIFT_MULTIPLIER: Double
        get() = _BACKWARD_DRIFT_MULTIPLIER
    override val DRIFT_MULTIPLIER: Double
        get() = _DRIFT_MULTIPLIER
    override val DRIFT_TURN_MULTIPLIER: Double
        get() = _DRIFT_TURN_MULTIPLIER
    override val DRIVER_SPEEDS: List<Double>
        get() = _DRIVER_SPEEDS
    override val GEAR_RATIO: Double
        get() = _GEAR_RATIO
    override val HEADING_PID: PIDCoefficients
        get() = _HEADING_PID
    override val IS_RUN_USING_ENCODER: Boolean
        get() = _IS_RUN_USING_ENCODER
    override val LATERAL_MULTIPLIER: Double
        get() = _LATERAL_MULTIPLIER
    override val LEFT_BACK_MOTOR: MotorEx
        get() = _LEFT_BACK_MOTOR
    override val LEFT_FRONT_MOTOR: MotorEx
        get() = _LEFT_FRONT_MOTOR
    override val MAX_ACCEL: Double
        get() = _MAX_ACCEL
    override val MAX_ANG_ACCEL: Double
        get() = _MAX_ANG_ACCEL
    override val MAX_ANG_VEL: Double
        get() = _MAX_ANG_VEL
    override val MAX_RPM: Double
        get() = _MAX_RPM
    override val MAX_VEL: Double
        get() = _MAX_VEL
    override val MOTOR_VEL_PID: PIDFCoefficients
        get() = _MOTOR_VEL_PID
    override val POV: DriverControlled.POV
        get() = _POV
    override val REVERSE_STRAFE: Boolean
        get() = _REVERSE_STRAFE
    override val REVERSE_STRAIGHT: Boolean
        get() = _REVERSE_STRAIGHT
    override val REVERSE_TURN: Boolean
        get() = _REVERSE_TURN
    override val RIGHT_BACK_MOTOR: MotorEx
        get() = _RIGHT_BACK_MOTOR
    override val RIGHT_DRIFT_MULTIPLIER: Double
        get() = _RIGHT_DRIFT_MULTIPLIER
    override val RIGHT_FRONT_MOTOR: MotorEx
        get() = _RIGHT_FRONT_MOTOR
    override val TICKS_PER_REV: Double
        get() = _TICKS_PER_REV
    override val TRACK_WIDTH: Double
        get() = _TRACK_WIDTH
    override val TRANSLATIONAL_PID: PIDCoefficients
        get() = _TRANSLATIONAL_PID
    override val VERTICAL_AXIS: AxisDirection
        get() = _VERTICAL_AXIS
    override val WHEEL_RADIUS: Double
        get() = _WHEEL_RADIUS
    override val kA: Double
        get() = _kA
    override val kStatic: Double
        get() = _kStatic
    override val kV: Double
        get() = _kV
    //endregion
}