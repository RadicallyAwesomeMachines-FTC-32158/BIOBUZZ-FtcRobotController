/* Copyright (c) 2021 FIRST. All rights reserved.
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

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;


@Autonomous(name="MainAuto2", group="Linear OpMode")
// @Disabled
public class MainAuto2 extends LinearOpMode {

    // Declare OpMode members for each of the 4 motors.
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor frontLeftDrive = null;
    private DcMotor backLeftDrive = null;
    private DcMotor frontRightDrive = null;
    private DcMotor backRightDrive = null;
    private DcMotor Intake_Motor = null;
    private CRServo crServo_Left;
    private CRServo crServo_Right;
    private CRServo crServo_paddle;
    private DcMotor flyWheel = null;
    private static final double TICKS_PER_REV = 537.7;
    private double speed = 0.5;


    public void move(String direction, double rev) {
        //Directions are (Forward = "f", Backward = "b", Left = "l", Right = "r")
        double numRevs = rev;
        double numTicks = numRevs * TICKS_PER_REV;
        int axialM = 1;
        int lateralR = 1;
        int lateralL = 1;

        double axial = 0; // -gamepad1.left_stick_y;
        double lateral = 0; //gamepad1.left_stick_x;
        double yaw = 0; //gamepad1.right_stick_x;

        if (direction.equals("b")) {
            axialM = -1;
            axial = -1;
        }
        else if (direction.equals("l")) {
            lateralL =  -1;
            lateral = 1;
        }
        else if (direction.equals("r")) {
            lateralR = -1;
            lateral = -1;
        }
        else {
            axial = 1;
        }


        double startingTicksFL = frontLeftDrive.getCurrentPosition();
        frontLeftDrive.setTargetPosition((int)(startingTicksFL + numTicks * axialM * lateralL));

        double startingTicksFR = frontRightDrive.getCurrentPosition();
        frontRightDrive.setTargetPosition((int)(startingTicksFR + numTicks * axialM * lateralR));

        double startingTicksBL = backLeftDrive.getCurrentPosition();
        backLeftDrive.setTargetPosition((int)(startingTicksBL + numTicks * axialM * lateralR));

        double startingTicksBR = backRightDrive.getCurrentPosition();
        backRightDrive.setTargetPosition((int)(startingTicksBR + numTicks * axialM * lateralL));

        frontLeftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        frontLeftDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRightDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeftDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRightDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        double frontLeftPower = axial + lateral + yaw;
        double frontRightPower = axial - lateral - yaw;
        double backLeftPower = axial - lateral + yaw;
        double backRightPower = axial + lateral - yaw;

        frontLeftDrive.setPower(frontLeftPower);
        frontRightDrive.setPower(frontRightPower);
        backLeftDrive.setPower(backLeftPower);
        backRightDrive.setPower(backRightPower);

        while (opModeIsActive() &&
                (frontLeftDrive.isBusy() || frontRightDrive.isBusy() || backLeftDrive.isBusy() || backRightDrive.isBusy())) {
            idle();
        }

        // movement values
        /*
        double axial = 1; // -gamepad1.left_stick_y;
        double lateral = 0; //gamepad1.left_stick_x;
        double yaw = 0; //gamepad1.right_stick_x;

        // calculations for movement
        double frontLeftPower = axial + lateral + yaw;
        double frontRightPower = axial - lateral - yaw;
        double backLeftPower = axial - lateral + yaw;
        double backRightPower = axial + lateral - yaw;

        double max = Math.max(Math.abs(frontLeftPower), Math.abs(frontRightPower));
        max = Math.max(max, Math.abs(backLeftPower));
        max = Math.max(max, Math.abs(backRightPower));

        // lowers max wheel power if it exceeds threshold
        if (max > 1.0) {
            frontLeftPower /= max;
            frontRightPower /= max;
            backLeftPower /= max;
            backRightPower /= max;
        }

        // set power for wheels
        frontLeftDrive.setPower(frontLeftPower);
        frontRightDrive.setPower(frontRightPower);
        backLeftDrive.setPower(backLeftPower);
        backRightDrive.setPower(backRightPower);
        */
    }
    @Override
    public void runOpMode() {

        // Initialize the hardware variables. Note that the strings used here must correspond
        // to the names assigned during the robot configuration step on the DS or RC devices.
        frontLeftDrive = hardwareMap.get(DcMotor.class, "front_left_drive");
        backLeftDrive = hardwareMap.get(DcMotor.class, "back_left_drive");
        frontRightDrive = hardwareMap.get(DcMotor.class, "front_right_drive");
        backRightDrive = hardwareMap.get(DcMotor.class, "back_right_drive");
        Intake_Motor = hardwareMap.get(DcMotor.class, "Intake_Motor");
        crServo_Left = hardwareMap.get(CRServo.class, "servo_right");
        crServo_Right = hardwareMap.get(CRServo.class, "servo_left");
        crServo_paddle = hardwareMap.get(CRServo.class, "servo_paddle");
        flyWheel = hardwareMap.get(DcMotor.class, "Flywheel_Motor");

        frontLeftDrive.setDirection(DcMotor.Direction.REVERSE);
        backLeftDrive.setDirection(DcMotor.Direction.REVERSE);
        frontRightDrive.setDirection(DcMotor.Direction.FORWARD);
        backRightDrive.setDirection(DcMotor.Direction.FORWARD);
        Intake_Motor.setDirection(DcMotor.Direction.FORWARD);
        flyWheel.setDirection(DcMotor.Direction.FORWARD);

        // Wait for the game to start (driver presses START)
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();
        runtime.reset();

        move("f", 7);
        //move("l", 0.3);
        //move("b", 0.3);
        //move("l", 0.3);


        frontLeftDrive.setPower(0.0);
        frontRightDrive.setPower(0.0);
        backLeftDrive.setPower(0.0);
        backRightDrive.setPower(0.0);

        // number of revolutions the wheels will rotate

        while (opModeIsActive()) {
            /*
            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.addData("Front left/Right", "%4.2f, %4.2f", frontLeftPower, frontRightPower);
            telemetry.addData("Back  left/Right", "%4.2f, %4.2f", backLeftPower, backRightPower);
            telemetry.update();
            */
        }

        // run until the end of the match (driver presses STOP)
        /* while (opModeIsActive() && (runtime.seconds() < 2.0)) {
            double max;

            // sets power so it runs w/o controller input
            double axial = 0.75;// -gamepad1.left_stick_y;
            double lateral = 0; //gamepad1.left_stick_x;
            double yaw = 0; //gamepad1.right_stick_x;

            frontLeftDrive.setPower(0.75);
            frontRightDrive.setPower(0.75);
            backLeftDrive.setPower(0.75);
            backRightDrive.setPower(0.75);

            double frontLeftPower = axial + lateral + yaw;
            double frontRightPower = axial - lateral - yaw;
            double backLeftPower = axial - lateral + yaw;
            double backRightPower = axial + lateral - yaw;

            max = Math.max(Math.abs(frontLeftPower), Math.abs(frontRightPower));
            max = Math.max(max, Math.abs(backLeftPower));
            max = Math.max(max, Math.abs(backRightPower));

            if (max > 1.0) {
                frontLeftPower /= max;
                frontRightPower /= max;
                backLeftPower /= max;
                backRightPower /= max;
            }

            frontLeftDrive.setPower(frontLeftPower);
            frontRightDrive.setPower(frontRightPower);
            backLeftDrive.setPower(backLeftPower);
            backRightDrive.setPower(backRightPower);

            // Show the elapsed game time and wheel power.
            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.addData("Front left/Right", "%4.2f, %4.2f", frontLeftPower, frontRightPower);
            telemetry.addData("Back  left/Right", "%4.2f, %4.2f", backLeftPower, backRightPower);
            telemetry.addData("CRServo Power", "%5.2f", crServo_Right.getPower());
            telemetry.addData("CRServo Power", "%5.2f", crServo_Left.getPower());
            telemetry.addData("Paddle", "%5.2f", crServo_paddle.getPower());
            telemetry.update();
        } */

        frontLeftDrive.setPower(0.0);
        frontRightDrive.setPower(0.0);
        backLeftDrive.setPower(0.0);
        backRightDrive.setPower(0.0);
    }
}
