package org.firstinspires.ftc.teamcode.statemachine;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.components.ComponentHelper;
import org.firstinspires.ftc.teamcode.vision.DuckDetectionPipline;
import org.firstinspires.ftc.teamcode.vision.DuckDetector;

import java.util.ArrayList;

public class DetermineDuckStateLeft extends State {

    private DuckDetector duckDetector;

    private int position;
    private int measurements;
    private double lastMeasurement;
    private ArrayList<DcMotor> motors;
    private BNO055IMU imu;

    /**
     * This is the default State constructor.
     *
     * @param stateMachine The statemachine sequence to which the state belongs.
     */
    public DetermineDuckStateLeft(StateMachine stateMachine, ArrayList<DcMotor> motors, BNO055IMU imu) {
        super(stateMachine);
        this.motors = motors;
        this.imu = imu;
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
                    // get to shipping hub
                    new DriveState(stateMachine, this.motors, 5, "forward", 9),
                    new DriveState(stateMachine, this.motors, 0.5, "turnRight", 90),
                    new DriveState(stateMachine, motors, 5, "forward", 23),
                    new DriveState(stateMachine, motors, 0.5,"turnLeft", 90),
                    new DriveState(stateMachine, motors, 5, "forward", 5),

                    // drop off duck
                    new ExtendRail(stateMachine, 2),
            };
        } else if (position == DuckDetectionPipline.DuckPosition.CENTER) {
            nextStates = new State[]{
                    // get to shipping hub
                    new DriveState(stateMachine, this.motors, 5, "forward", 9),
                    new DriveState(stateMachine, this.motors, 0.5, "turnRight", 90),
                    new DriveState(stateMachine, motors, 5, "forward", 23),
                    new DriveState(stateMachine, motors, 0.5,"turnLeft", 90),
                    new DriveState(stateMachine, motors, 5, "forward", 5),

                    // drop off duck
                    new ExtendRail(stateMachine, 9),
            };
        } else  {
            nextStates = new State[]{
                    // get to shipping hub
                    new DriveState(stateMachine, this.motors, 5, "forward", 9),
                    new DriveState(stateMachine, this.motors, 0.5, "turnRight", 90),
                    new DriveState(stateMachine, motors, 5, "forward", 23),
                    new DriveState(stateMachine, motors, 0.5,"turnLeft", 90),
                    new DriveState(stateMachine, motors, 5, "forward", 5),

                    // drop off duck
                    new ExtendRail(stateMachine, 15),
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
