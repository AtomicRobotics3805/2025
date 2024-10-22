package org.firstinspires.ftc.teamcode.teleop.opmodes

import com.acmerobotics.roadrunner.geometry.Pose2d
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.rowanmcalpin.nextftc.driving.drivers.MecanumDrive
import com.rowanmcalpin.nextftc.driving.localizers.TwoWheelOdometryLocalizer
import com.rowanmcalpin.nextftc.opmodes.NextFTCOpMode
import org.firstinspires.ftc.teamcode.drive.DriveConstants
import org.firstinspires.ftc.teamcode.localization.OdometryConstants

@TeleOp(name="Tuning TeleOp")
class TuningTeleOp: NextFTCOpMode() {
    override val drive = MecanumDrive(DriveConstants, TwoWheelOdometryLocalizer(OdometryConstants), { Pose2d(0.0, 0.0, 0.0) })

    override fun onUpdate() {
        telemetry.addData("Estimated X", drive.localizer.poseEstimate.x)
        telemetry.addData("Estimated Y", drive.localizer.poseEstimate.y)
        telemetry.addData("Estimated heading", drive.localizer.poseEstimate.heading)
    }
}