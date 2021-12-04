package org.firstinspires.ftc.teamcode.teleopmodes;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.CommonVariables;
import org.firstinspires.ftc.teamcode.commands.TestCommand;
import org.firstinspires.ftc.teamcode.subsystems.TestSubsystem;

@TeleOp(name = "Sample TeleOp")
public class TestTeleOp extends CommandOpMode {

    private CommonVariables commonVariables;
    private ElapsedTime elapsedTime = new ElapsedTime();

    private GamepadEx gamepad;

    private TestSubsystem testSubsystem;

    @Override
    public void initialize() {
        this.commonVariables = new CommonVariables(
                this,
                this.telemetry,
                this.hardwareMap,
                this.elapsedTime
        );

        this.gamepad = new GamepadEx(this.gamepad1);
        this.testSubsystem = new TestSubsystem(this.telemetry);

        register(this.testSubsystem);

        this.testSubsystem.setDefaultCommand(new TestCommand(this.testSubsystem));

        telemetry.addData("Status", "Initialized");
    }
}
