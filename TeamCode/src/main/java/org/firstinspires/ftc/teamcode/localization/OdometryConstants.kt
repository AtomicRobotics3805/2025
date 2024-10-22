package org.firstinspires.ftc.teamcode.localization

import com.acmerobotics.dashboard.config.Config
import com.rowanmcalpin.nextftc.driving.localizers.TwoWheelOdometryConstants

@Config
object OdometryConstants: TwoWheelOdometryConstants {
    @JvmField
    var _PARALLEL_X = -1.0766 // Inches, forward offset of the wheel
    @JvmField
    var _PARALLEL_Y = 4.4469 // Inches, left offset of the wheel
    @JvmField
    var _PERPENDICULAR_X = -5.3917 // Inches, forward offset of the wheel
    @JvmField
    var _PERPENDICULAR_Y = 0.7616 // Inches, left offset of the wheel

    @JvmField
    var _PARALLEL_NAME = "RB" // PORT 1
    @JvmField
    var _PERPENDICULAR_NAME = "LB" // PORT 3

    @JvmField
    var _TICKS_PER_REV = 1440.0 // S4T-360-236-S-B Encoder (360 CPR * 4 to get PPR)
    @JvmField
    var _WHEEL_RADIUS = 0.6889764 // Inches, 35mm (diameter) rotacaster omni wheel
    @JvmField
    var _GEAR_RATIO = 1.0 // Direct drive

    // If the robot drives at full speed without stopping, one or both of these need to be changed
    @JvmField
    var _PARALLEL_REVERSED = true
    @JvmField
    var _PERPENDICULAR_REVERSED = true

    // Tuning variables
    @JvmField
    var _X_MULTIPLIER = 0.9826 // FRONT AND BACK
    @JvmField
    var _Y_MULTIPLIER = 1.0119 // SIDE TO SIDE

    @JvmField
    var _CORRECTED_VELOCITY = false

    //region Backend
    override val PARALLEL_X: Double
        get() = _PARALLEL_X
    override val PARALLEL_Y: Double
        get() = _PARALLEL_Y
    override val PERPENDICULAR_X: Double
        get() = _PERPENDICULAR_X
    override val PERPENDICULAR_Y: Double
        get() = _PERPENDICULAR_Y
    override val PARALLEL_NAME: String
        get() = _PARALLEL_NAME
    override val PERPENDICULAR_NAME: String
        get() = _PERPENDICULAR_NAME
    override val TICKS_PER_REV: Double
        get() = _TICKS_PER_REV
    override val WHEEL_RADIUS: Double
        get() = _WHEEL_RADIUS
    override val GEAR_RATIO: Double
        get() = _GEAR_RATIO
    override val PARALLEL_REVERSED: Boolean
        get() = _PARALLEL_REVERSED
    override val PERPENDICULAR_REVERSED: Boolean
        get() = _PERPENDICULAR_REVERSED
    override val X_MULTIPLIER: Double
        get() = _X_MULTIPLIER
    override val Y_MULTIPLIER: Double
        get() = _Y_MULTIPLIER
    override val CORRECTED_VELOCITY: Boolean
        get() = _CORRECTED_VELOCITY
    //endregion
}