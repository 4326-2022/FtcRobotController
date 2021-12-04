package org.firstinspires.ftc.teamcode.statemachine;

import com.qualcomm.robotcore.hardware.DcMotor;

import java.util.ArrayList;

public class DriveState extends State {
    int newleftBackTarget;
    int newrightBackTarget;
    int newleftFrontTarget;
    int newrightFrontTarget;
    double distance;


    DcMotor leftFront;
    DcMotor rightFront;
    DcMotor leftBack;
    DcMotor rightBack;

    static final double COUNTS_PER_MOTOR_REV = 1120;    // eg: Andymark Motor Encoder
    static final double DRIVE_GEAR_REDUCTION = 1.0;     // This is < 1.0 if geared UP
    static final double WHEEL_DIAMETER_INCHES = 4.0;     // For figuring circumference
    static final double COUNTS_PER_INCH = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
            (WHEEL_DIAMETER_INCHES * 3.1415);
    static double driveSpeed = 0.6;
    static final double TURN_SPEED = 0.5;

    private String Movement;


    /**
     * This is the default State constructor.
     *
     * @param stateMachine The statemachine sequence to which the state belongs.
     */
    public DriveState(StateMachine stateMachine, ArrayList<DcMotor> motor, double speed, String direction, double target) {
        super(stateMachine);
        driveSpeed = speed;
        distance = target;
        leftFront = motor.get(0);
        rightFront = motor.get(1);
        leftBack = motor.get(2);
        rightBack = motor.get(3);
        Movement = direction;
    }

    @Override
    void start() {
        //Reset the encoders back to zero for the next movement
        rightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        //Bring them back to using encoders
        rightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        //Setting their target to their current encoder value (should be zero) to the amount of inches times the counts per inches

        newleftBackTarget = leftBack.getCurrentPosition() + (int) (distance * COUNTS_PER_INCH);
        newrightBackTarget = rightBack.getCurrentPosition() + (int) (distance * COUNTS_PER_INCH);
        newleftFrontTarget = leftFront.getCurrentPosition() + (int) (distance * COUNTS_PER_INCH);
        newrightFrontTarget = rightFront.getCurrentPosition() + (int) (distance * COUNTS_PER_INCH);

    }

    @Override
    void update() {
        if((!(Movement.equals("backward")))&&(newleftBackTarget > leftBack.getCurrentPosition() && newrightBackTarget > rightBack.getCurrentPosition() && newleftFrontTarget > leftFront.getCurrentPosition() && newrightFrontTarget > rightFront.getCurrentPosition() ) ) {

            if(Movement.equals("left")) {
                leftBack.setPower(driveSpeed);
                leftFront.setPower(-driveSpeed);
                rightBack.setPower(-driveSpeed);
                rightFront.setPower(driveSpeed);

            } else if(Movement.equals("right")) {
                leftBack.setPower(-driveSpeed);
                leftFront.setPower(driveSpeed);
                rightBack.setPower(driveSpeed);
                rightFront.setPower(-driveSpeed);

            }else if(Movement.equals("backward")) {
                leftBack.setPower(-driveSpeed);
                leftFront.setPower(-driveSpeed);
                rightBack.setPower(-driveSpeed);
                rightFront.setPower(-driveSpeed);

            }else if(Movement.equals("turnLeft")){
                leftBack.setPower(-driveSpeed);
                leftFront.setPower(-driveSpeed);
                rightBack.setPower(driveSpeed);
                rightFront.setPower(driveSpeed);
            }
            else if(Movement.equals("turnRight")){
                leftBack.setPower(driveSpeed);
                leftFront.setPower(driveSpeed);
                rightBack.setPower(-driveSpeed);
                rightFront.setPower(-driveSpeed);
            }
            else {

                leftBack.setPower(driveSpeed);
                leftFront.setPower(driveSpeed);
                rightBack.setPower(driveSpeed);
                rightFront.setPower(driveSpeed);
            }

        }

            else if (Movement.equals("backward")&&(newleftBackTarget < leftBack.getCurrentPosition() && newrightBackTarget < rightBack.getCurrentPosition() && newleftFrontTarget < leftFront.getCurrentPosition() && newrightFrontTarget < rightFront.getCurrentPosition() ) ){
            leftBack.setPower(-driveSpeed);
            leftFront.setPower(-driveSpeed);
            rightBack.setPower(-driveSpeed);
            rightFront.setPower(-driveSpeed);


        }   else {
            rightFront.setPower(0);
            leftFront.setPower(0);
            rightBack.setPower(0);
            leftBack.setPower(0);
            this.startNextState();
        }
    }

    @Override
    void stop() {
        rightFront.setPower(0);
        leftFront.setPower(0);
        rightBack.setPower(0);
        leftBack.setPower(0);
    }

    @Override
    void initialize() {

    }
}

