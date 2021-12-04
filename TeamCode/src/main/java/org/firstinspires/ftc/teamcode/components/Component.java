package org.firstinspires.ftc.teamcode.components;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public abstract class Component {

    protected final OpMode opMode;
    protected final HardwareMap hardwareMap;
    protected final Telemetry telemetry;
    protected final ElapsedTime elapsedTime;

    public Component(OpMode opMode, HardwareMap hardwareMap, Telemetry telemetry, ElapsedTime elapsedTime) {
        this.opMode = opMode;
        this.hardwareMap = hardwareMap;
        this.telemetry = telemetry;
        this.elapsedTime = elapsedTime;
    }

    abstract void initialize();
    abstract void stop();
    abstract void update();

}
