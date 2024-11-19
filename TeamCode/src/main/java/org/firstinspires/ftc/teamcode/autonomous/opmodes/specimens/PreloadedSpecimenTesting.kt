//package org.firstinspires.ftc.teamcode.autonomous.opmodes.specimens

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.rowanmcalpin.nextftc.Constants
import com.rowanmcalpin.nextftc.driving.drivers.MecanumDrive
import com.rowanmcalpin.nextftc.driving.localizers.TwoWheelOdometryLocalizer
import com.rowanmcalpin.nextftc.opmodes.NextFTCOpMode
import org.firstinspires.ftc.teamcode.autonomous.routines.Routines
import org.firstinspires.ftc.teamcode.autonomous.routines.SampleRoutines
import org.firstinspires.ftc.teamcode.autonomous.routines.SpecimenRoutines
import org.firstinspires.ftc.teamcode.autonomous.trajectories.TrajectoryFactory
import org.firstinspires.ftc.teamcode.drive.DriveConstants
import org.firstinspires.ftc.teamcode.localization.OdometryConstants
import org.firstinspires.ftc.teamcode.mechanisms.Arm
import org.firstinspires.ftc.teamcode.mechanisms.Claw
import org.firstinspires.ftc.teamcode.mechanisms.Intake
import org.firstinspires.ftc.teamcode.mechanisms.IntakeExtension
import org.firstinspires.ftc.teamcode.mechanisms.IntakePivot
import org.firstinspires.ftc.teamcode.mechanisms.IntakeSensor
import org.firstinspires.ftc.teamcode.mechanisms.Lift
import org.firstinspires.ftc.teamcode.mechanisms.Lights

@Autonomous(name = "Preloaded Specimen Testing", group = "specimens")
class PreloadedSpecimenTesting: NextFTCOpMode(Arm, Claw, Intake, IntakeExtension, IntakePivot, IntakeSensor, Lift, Lights) {
    override val color = Constants.Color.BLUE // Doesn't actually matter, since the field is rotationally symmetrical
    override val trajectoryFactory = TrajectoryFactory
    override val drive = MecanumDrive(
        DriveConstants,
        TwoWheelOdometryLocalizer(OdometryConstants)
    )
    { TrajectoryFactory.startPosRight }

    override fun onInit() {
        Routines.autonomousWithSpecimenInitializationRoutine()
    }

    override fun onStartButtonPressed() {
        SpecimenRoutines.singleSpecimenWithObservationZonePark()
    }
}