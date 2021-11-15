package org.firstinspires.ftc.teamcode.statemachine;

import com.qualcomm.robotcore.util.ElapsedTime;

public abstract class State {

    protected StateMachine stateMachine;
    private ElapsedTime elapsedTime;

    private double startTime;
    private double timeoutTime = 0;

    private State nextState = null;

    private boolean running = false;

    private String name = this.getClass().getSimpleName();

    // CONSTRUCTORS
    public State(StateMachine stateMachine) {
        this.stateMachine = stateMachine;
        this.elapsedTime = this.stateMachine.elapsedTime;
    }

    // METHODS

    public String toString(){
        return this.name;
    }

    public String getName() {
        return this.toString();
    }

    public void setName(String newName) {
        this.name = newName;
    }

    public State getNextState() {
        return this.nextState;
    }

    public void setNextState(State nextState) {
        this.nextState = nextState;
    }

    public boolean isRunning() {
        return this.running;
    }

    public void insert(State newState) {
        if (this.nextState != null) {
            newState.setNextState(this.nextState);
        }

        this.nextState = newState;
    }

    protected void sleep(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    protected void startNextState() {
        this._stop();

        if (this.nextState != null) {
            this.nextState._start();
        }
    }

    abstract void start();

    public void _start() {
        this.elapsedTime.reset();
        this.startTime = this.elapsedTime.milliseconds();

        this.running = true;

        this.start();
    }

    abstract void update();

    public void _update() {
        double currentTime = this.elapsedTime.milliseconds();
        if ((this.timeoutTime > 0) && (currentTime > this.timeoutTime)) {
            this.startNextState();
            return;
        }

        this.update();
    }

    abstract void stop();

    public void _stop() {
        this.running = false;
        this.stop();
    }

    abstract void initialize();

    protected void startTimeOut(int timeoutLength) {
        this.timeoutTime = this.elapsedTime.milliseconds() + (1000 * timeoutLength);
    }
}
