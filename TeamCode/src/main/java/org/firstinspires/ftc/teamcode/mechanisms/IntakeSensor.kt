package org.firstinspires.ftc.teamcode.mechanisms

import android.graphics.Color
import com.qualcomm.hardware.rev.RevColorSensorV3
import com.qualcomm.robotcore.util.ElapsedTime
import com.rowanmcalpin.nextftc.Constants
import com.rowanmcalpin.nextftc.TelemetryController
import com.rowanmcalpin.nextftc.command.Command
import com.rowanmcalpin.nextftc.subsystems.Subsystem
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit
import org.firstinspires.ftc.teamcode.teleop.opmodes.TestingTeleOp


object IntakeSensor: Subsystem {

    lateinit var sensor: RevColorSensorV3

    var selection = 0 // 1 red, 2 blue, 3 yellow, 0 atomic green, 4 off

    var hsv: FloatArray = FloatArray(3);

    class DetectColor(): Command() {
        override var _isDone = false

        private val timer: ElapsedTime = ElapsedTime()
        private var lastTimestamp = 0.0
        private val checkFrequency = 0.5

        override fun onStart() {
            timer.reset()
        }

        override fun onExecute() {
            if (timer.seconds() - lastTimestamp >= checkFrequency) {
                Color.colorToHSV(sensor.normalizedColors.toColor(), hsv)
                selection = if (sensor.getDistance(DistanceUnit.CM) < 4) {
                    if (hsv[0] <= 26) {
                        1
                    } else if (hsv[0] <= 85) {
                        3
                    } else {
                        2
                    }
                } else {
                    if (Constants.opMode.isStopRequested) {
                        4
                    } else {
                        0
                    }
                }
                lastTimestamp = timer.seconds()
            }

        }
    }

    public class IntakeStopOnIntaken(): Command() {
        override val _isDone: Boolean
            get() = false

        override fun onExecute() {
            if (sensor.getDistance(DistanceUnit.CM) < 4) {
                Intake.stop()
            }
        }
    }

    public class BlockUntilDetected(val watchdogThreshold: Double = 2.0): Command() {
        private val watchdog = ElapsedTime()
        override val _isDone
            get() = sensor.getDistance(DistanceUnit.CM) < 4 || watchdog.seconds() > watchdogThreshold

        override fun onStart() {
            watchdog.reset()
        }
    }

    public class BlockUntilDetectedNoWatchdog(): Command() {
        override val _isDone
            get() = sensor.getDistance(DistanceUnit.CM) < 4
    }

    override fun initialize() {
        sensor = Constants.opMode.hardwareMap.get(RevColorSensorV3::class.java, "intake_sensor")
    }
}