package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.subsystems.TestSubsystem;

public class ManageTelemetry extends CommandBase {
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    private final Telemetry telemetry;

    public ManageTelemetry(Telemetry telemetry) {
        this.telemetry = telemetry;
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        this.telemetry.addData("Status", "Running");

        this.telemetry.update();
        this.telemetry.clearAll();
    }
}
