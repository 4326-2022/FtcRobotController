package org.firstinspires.ftc.teamcode.statemachine;

public class WaitState extends State {

    private int waitTime;

    public WaitState(int waitTime, StateMachine stateMachine) {
        super(stateMachine);

        this.waitTime = waitTime;
    }

    @Override
    void start() {
        this.startTimeOut(this.waitTime);
    }

    @Override
    void update() {

    }

    @Override
    void stop() {

    }

    @Override
    void initialize() {

    }
}
