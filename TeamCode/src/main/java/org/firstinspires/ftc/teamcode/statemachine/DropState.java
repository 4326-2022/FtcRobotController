package org.firstinspires.ftc.teamcode.statemachine;

import com.qualcomm.robotcore.hardware.Servo;

public class DropState extends State {

    private Servo armServo;
    private final int waitTime = 3;

    /**
     * This is the default State constructor.
     *
     * @param stateMachine The statemachine sequence to which the state belongs.
     */
    public DropState(StateMachine stateMachine) {
        super(stateMachine);
    }

    @Override
    void start() {
        this.armServo.setPosition(0);
        this.startTimeOut(this.waitTime);
    }

    @Override
    void update() {

    }

    @Override
    void stop() {
        this.armServo.setPosition(1);
    }

    @Override
    void initialize() {
        this.armServo = this.hardwareMap.get(Servo.class, "cs");
    }
}
