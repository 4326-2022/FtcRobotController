package org.firstinspires.ftc.teamcode.teleopmodes;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.CommonVariables;
import org.firstinspires.ftc.teamcode.commands.ManageTelemetry;
import org.firstinspires.ftc.teamcode.commands.TestCommand;
import org.firstinspires.ftc.teamcode.subsystems.TestSubsystem;

@TeleOp(name = "Sample TeleOp")
public class TestTeleOp extends CommandOpMode {

    private CommonVariables commonVariables;
    private ElapsedTime elapsedTime = new ElapsedTime();

    private TestSubsystem testSubsystem;

    @Override
    public void initialize() {
        this.commonVariables = new CommonVariables(
                this,
                this.telemetry,
                this.hardwareMap,
                this.elapsedTime
        );

        this.testSubsystem = new TestSubsystem(this.telemetry);

        register(this.testSubsystem);
        schedule(new ManageTelemetry(this.telemetry));

        telemetry.addData("Status", "Initialized");
    }
}
