package org.firstinspires.ftc.teamcode.components;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class TestComponent extends Component {

    public TestComponent(OpMode opMode, HardwareMap hardwareMap, Telemetry telemetry, ElapsedTime elapsedTime) {
        super(opMode, hardwareMap, telemetry, elapsedTime);
    }

    @Override
    void initialize() {

    }

    @Override
    void stop() {

    }

    @Override
    void update() {

    }

    public void sayHi() {
        this.telemetry.addData("Comp", "Hello!");
    }
}
