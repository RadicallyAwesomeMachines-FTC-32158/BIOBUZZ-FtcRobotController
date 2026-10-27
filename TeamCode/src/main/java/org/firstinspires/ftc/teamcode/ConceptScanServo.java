/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;

/*
 * This OpMode scans a single servo back and forward until Stop is pressed.
 * The code is structured as a LinearOpMode
 * INCREMENT sets how much to increase/decrease the servo position each cycle
 * CYCLE_MS sets the update period.
 *
 * This code assumes a Servo configured with the name "left_hand" as is found on a Robot.
 *
 * NOTE: When any servo position is set, ALL attached servos are activated, so ensure that any other
 * connected servos are able to move freely before running this test.
 *
 * Use Android Studio to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this OpMode to the Driver Station OpMode list
 */
@TeleOp(name = "CRServo control", group = "Concept")
public class ConceptScanServo extends LinearOpMode {

    // Define class members
    private CRServo crServo_Left;
    private CRServo crServo_Right;
    private CRServo crServo_paddle;
    @Override
    public void runOpMode() {

        // Connect to servo (Assume Robot Left Hand)
        // Change the text in quotes to match any servo name on your robot.
        crServo_Left = hardwareMap.get(CRServo.class, "servo_right");
        crServo_Right = hardwareMap.get(CRServo.class, "servo_left");
        crServo_paddle = hardwareMap.get(CRServo.class, "servo_paddle");


        // Wait for the start button
        telemetry.addData(">", "Press Start to control CRServo." );
        telemetry.update();
        waitForStart();


        // Scan servo till stop pressed.
        while(opModeIsActive()){

// Control continuous rotation direction with Gamepad 1 buttons
            if (gamepad1.left_trigger_pressed) {
                crServo_Left.setPower(1.0);  // Spin forward at 100% power
            } else if (gamepad1.left_bumper) {
                crServo_Left.setPower(0.0); // Spin reverse at 100% power
            } /*else {
                crServo_Left.setPower(0.0);  // Stop spinning
            }*/
            if (gamepad1.right_trigger_pressed) {
                crServo_Right.setPower(-1.0);  // Spin forward at 100% power
            } else if (gamepad1.right_bumper) {
                crServo_Right.setPower(0.0); // Spin reverse at 100% power
            }
            if (gamepad1.xWasPressed()) {
                crServo_paddle.setPower(1.0);
            }
                /*else {
                crServo_Right.setPower(0.0);  // Stop spinning
            }*/

            // Display the current value
            telemetry.addData("CRServo Power", "%5.2f", crServo_Right.getPower());
            telemetry.addData("CRServo Power", "%5.2f", crServo_Left.getPower());
            telemetry.addData("CRServo Power", "%5.2f", crServo_paddle.getPower());


            telemetry.addData(">", "Press Stop to end test." );
            telemetry.update();
        }

        // Signal done;
        telemetry.addData(">", "Done");
        telemetry.update();
    }
}



