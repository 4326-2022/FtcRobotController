package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class TestSubsystem extends SubsystemBase {

    private Telemetry telemetry;

    public TestSubsystem(Telemetry telemetry) {
        this.telemetry = telemetry;
    }

    @Override
    public void periodic() {
        this.telemetry.addData("Subsystem", "Here");
    }
}
