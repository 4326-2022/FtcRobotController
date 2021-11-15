package org.firstinspires.ftc.teamcode.statemachine;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class StateMachine {

    protected final HardwareMap hardwareMap;
    protected final Telemetry telemetry;
    protected final ElapsedTime elapsedTime;
    private final OpMode opMode;

    private State headerState = null;

    public StateMachine(HardwareMap hardwareMap, Telemetry telemetry, ElapsedTime elapsedTime, OpMode opMode) {
        this.hardwareMap = hardwareMap;
        this.telemetry = telemetry;
        this.elapsedTime = elapsedTime;
        this.opMode = opMode;
    }

    public State getLastState() {
        if (this.headerState == null) {
            return null;
        }

        State lastState = headerState;

        while (lastState.getNextState() != null) { // Loop through to get the last state
            lastState = lastState.getNextState();
        }

        return lastState;
    }

    public void feed(State newState) {
        if (this.headerState == null) {
            this.headerState = newState;
            return;
        }

        newState.initialize();

        State lastState = this.getLastState();
        lastState.setNextState(newState);
    }

    public void feed(State[] stateChain) {
        State firstState = null;
        State topState = null;
        for (State state : stateChain) {
            if (firstState == null) {
                firstState = state;
                topState = state;
            }

            state.initialize();

            topState.setNextState(state);
            topState = state;
        }
    }

    public void start() {
        if (this.headerState != null) {
            this.headerState._start();
        }
    }

    public void update() {
        if (this.headerState == null) {
            return;
        }

        if (!this.headerState.isRunning()) {
            this.headerState = this.headerState.getNextState();
            this.update();
            return;
        }

        this.telemetry.addData("Running State: ", this.headerState.getName());
        this.headerState._update();
    }

    public void stop() {
        if (this.headerState != null && this.headerState.isRunning()) {
            this.headerState._stop();
        }
    }
}
