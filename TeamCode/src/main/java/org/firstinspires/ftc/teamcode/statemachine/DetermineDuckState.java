package org.firstinspires.ftc.teamcode.statemachine;

import org.firstinspires.ftc.teamcode.components.ComponentHelper;
import org.firstinspires.ftc.teamcode.vision.DuckDetectionPipline;
import org.firstinspires.ftc.teamcode.vision.DuckDetector;

public class DetermineDuckState extends State {

    private DuckDetector duckDetector;

    private int position;
    private int measurements;
    private double lastMeasurement;

    /**
     * This is the default State constructor.
     *
     * @param stateMachine The statemachine sequence to which the state belongs.
     */
    public DetermineDuckState(StateMachine stateMachine) {
        super(stateMachine);

        this.duckDetector = ComponentHelper.getComponent(DuckDetector.class, this.commonVariables);
    }

    @Override
    void start() {
        this.duckDetector.startStream();
        this.elapsedTime.reset();
    }

    void setUpNext(DuckDetectionPipline.DuckPosition position) {
        State[] nextStates;

        if (position == DuckDetectionPipline.DuckPosition.LEFT) {
            nextStates = new State[]{
                    new WaitState(5, "State Left 1", this.stateMachine),
                    new WaitState(5, "State Left 2", this.stateMachine),
            };
        } else if (position == DuckDetectionPipline.DuckPosition.CENTER) {
            nextStates = new State[]{
                    new WaitState(5, "State Center 1", this.stateMachine),
                    new WaitState(5, "State Center 2", this.stateMachine),
            };
        } else  {
            nextStates = new State[]{
                    new WaitState(5, "State Right 1", this.stateMachine),
                    new WaitState(5, "State Right 2", this.stateMachine),
            };
        }

        this.insert(nextStates);
    }

    @Override
    void update() {
        if (this.measurements > 10) {
            int averagePosition = Math.round(this.position / this.measurements);
            DuckDetectionPipline.DuckPosition finalPosition = DuckDetectionPipline.DuckPosition.LEFT;

            if (averagePosition == 2) {
                finalPosition = DuckDetectionPipline.DuckPosition.CENTER;
            } else if (averagePosition == 3) {
                finalPosition = DuckDetectionPipline.DuckPosition.RIGHT;
            }

            this.setUpNext(finalPosition);

            this.startNextState();
            return;
        }

        if ((this.elapsedTime.milliseconds() - this.lastMeasurement) > 50) {
            this.lastMeasurement = this.elapsedTime.milliseconds();

            DuckDetectionPipline.DuckPosition position = this.duckDetector.getPosition();

            if (position == DuckDetectionPipline.DuckPosition.LEFT) {
                this.position += 1;
            } else if (position == DuckDetectionPipline.DuckPosition.CENTER) {
                this.position += 2;
            } else if (position == DuckDetectionPipline.DuckPosition.RIGHT) {
                this.position += 3;
            }

            this.measurements += 1;
        }
    }

    @Override
    void stop() {
        this.duckDetector.stopStream();
    }

    @Override
    void initialize() {

    }
}
