package org.firstinspires.ftc.teamcode.statemachine;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * This is the statemachine is in charge and responsible for managing states.
 * @author Lawson
 * @version 1.0
 * @since November 20, 2021
 */
public class StateMachine {

    protected final HardwareMap hardwareMap;
    protected final Telemetry telemetry;
    protected final ElapsedTime elapsedTime;
    protected final OpMode opMode;

    private State headerState = null;

    /**
     * The default state machine constructor.
     * @param hardwareMap The HardwareMap instance belonging to the OpMode.
     * @param telemetry The Telemetry instance belonging to the OpMode.
     * @param elapsedTime The ElapsedTime instance belonging to the OpMode.
     * @param opMode The OpMode that is constructing the State Machine.
     */
    public StateMachine(HardwareMap hardwareMap, Telemetry telemetry, ElapsedTime elapsedTime, OpMode opMode) {
        this.hardwareMap = hardwareMap;
        this.telemetry = telemetry;
        this.elapsedTime = elapsedTime;
        this.opMode = opMode;
    }

    /**
     * Returns the last state in the current sequence.
     * @return The last state.
     */
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

    /**
     * Adds a state to the end of the current sequence.
     * @param newState The state to add.
     */
    public void feed(State newState) {
        if (this.headerState == null) {
            this.headerState = newState;
            return;
        }

        newState.initialize();

        State lastState = this.getLastState();
        lastState.setNextState(newState);
    }

    /**
     * Adds a list of states to the end of the current sequence.
     * @param stateChain The list of states to add.
     */
    public void feed(State[] stateChain) {
        State firstState = null;
        State topState = null;
        for (State state : stateChain) {

            state.initialize();

            if (firstState == null) {
                firstState = state;
                topState = state;
                continue;
            }

            topState.setNextState(state);
            topState = state;
        }

        State lastState = this.getLastState();
        lastState.setNextState(topState);
    }

    /**
     * Starts the first state in the sequence.
     */
    public void start() {
        if (this.headerState != null) {
            this.headerState._start();
        }
    }

    /**
     * Updates the currently running state in the sequence.
     */
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

    /**
     * Stops the currently running state in the sequence.
     */
    public void stop() {
        if (this.headerState != null && this.headerState.isRunning()) {
            this.headerState._stop();
        }
    }
}
