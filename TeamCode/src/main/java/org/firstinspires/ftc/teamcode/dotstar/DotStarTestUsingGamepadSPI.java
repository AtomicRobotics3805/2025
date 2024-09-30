package org.firstinspires.ftc.teamcode.dotstar;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

// 7/52024 John Courter. Works with DotstarLEDNew which uses Digital Outputs
// Trying to use simplest code to program Pixels.

/**
 *
 *
 * ! Warning: Make sure you have updated your REV Robotics module to firmware 1.7.2 or greater.
 *
 * ! Warning: It's your job to ensure the LEDs don't draw too much current from your robot.
 *
 * @author AJ Foster
 * @version 2.0.0
 */
@TeleOp()
public class DotStarTestUsingGamepadSPI extends LinearOpMode {


    DotStarBridgedLED leds;

    @Override
    public void runOpMode() {

        // Set up the LEDs. Change this to your configured name.
        leds = hardwareMap.get(DotStarBridgedLED.class, "leds");

        // Use ModernRoboticsDIM if using Modern Robotics hardware.
        leds.setController(DotStarBridgedLED.Controller.RevExpansionHub);


        // Default is 0.02 amps.  Set the do not exceed current.
        // 5v power plugs have maximum of 2 amps for both plugs.
        // If something else is plugged into the other 5v plug reduce so that both add
        // up to less than 2 amps.
        leds.setMaxOutputAmps(2.0);

        //map settings for joystick to hue full range.
        double minY = 1;
        double maxY = -1;
        double minHue = 0;
        double maxHue = 360;

        waitForStart();
        while (opModeIsActive()) {

            // set pixels 0-3 to blue when gamepad a is pressed
            if (gamepad1.a){
                for (int i = 0; i < 4; i++){
                    leds.pixels[i].setRGB(0,0, 255);
                }
            }
            // set pixels 0-3 to green when gamepad b is pressed.
            if (gamepad1.b){
                for (int i = 0; i < 4; i++){
                    leds.pixels[i].setRGB(0,255, 0);
                }
            }

            // set pixels 0-3 to off when gamepad x is pressed.
            if (gamepad1.x){
                for (int i = 0; i < 4; i++){
                    leds.pixels[i].setRGB(0,0, 0);
                }
            }

            // Select color to show using hue and gamepad left stick y.
            // Zero is red, 120 is green, 240 is blue.
            // Look up color picker calculator to see how HSV works.
            double hue = gamepad1.left_stick_y;
            hue = map(hue,minY, maxY,minHue, maxHue);
            telemetry.addData("Hue value ", hue);
            telemetry.update();

            // Update pixels 5-8 in the strip based on left stickY with a hue value.
            for (int i = 5; i < 9; i++) {
                // Calculate the new color based on its position and the current time.
                int color = Color.HSVToColor(new float[]{(float) hue, 1.0f, 0.25f});

                // Update individual pixels with their new color.
                leds.pixels[i].setRGB(Color.red(color), Color.green(color), Color.blue(color));
            }

            // Flush the current set of colors to the strip.
            leds.update();
        }
    }


    //---------------- Methods -------------------------------------------------

    int map(double x, double in_min, double in_max, double out_min, double out_max)
    {
        return (int)((x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min);
    }
}
