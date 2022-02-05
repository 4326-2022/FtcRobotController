package org.firstinspires.ftc.teamcode.statemachine;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.TickService;

public class ExtendRail extends State {

    private final double power = .5;
    private int inches;
    private int targetTicks;
    private DcMotor railMotor;
    private Servo armServo;

    /**
     * This is the default State constructor.
     *
     * @param stateMachine The statemachine sequence to which the state belongs.
     */
    public ExtendRail(StateMachine stateMachine, int inches) {
        super(stateMachine);

        this.inches = inches;
        this.targetTicks = TickService.inchesToTicks(this.inches);
    }

    void changeTarget(int newInches, boolean running) {
        this.inches = newInches;
        this.targetTicks = TickService.inchesToTicks(this.inches);

        if (running) {
            this.railMotor.setTargetPosition(this.targetTicks);
        }
    }

    @Override
    void start() {
        this.railMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        this.railMotor.setTargetPosition(this.targetTicks);
        this.railMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        this.railMotor.setPower(this.power);
    }

    @Override
    void update() {
        if (this.railMotor.getCurrentPosition() < this.railMotor.getTargetPosition()) {
            this.railMotor.setPower(this.power);
        }

        if (this.railMotor.getCurrentPosition() >= this.railMotor.getTargetPosition()) {
            this.armServo.setPosition(0);
            this.startTimeOut(1);
        }
    }

    @Override
    void stop() {
        this.armServo.setPosition(1);
        this.railMotor.setPower(0);
    }

    @Override
    void initialize() {
        this.railMotor = this.hardwareMap.get(DcMotor.class, "xr");
        this.armServo = this.hardwareMap.get(Servo.class, "cs");
    }
}
