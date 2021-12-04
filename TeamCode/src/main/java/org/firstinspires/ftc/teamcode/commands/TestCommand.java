package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.TestSubsystem;

public class TestCommand extends CommandBase {
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    private final TestSubsystem testSubsystem;

    public TestCommand(TestSubsystem testSubsystem) {
        this.testSubsystem = testSubsystem;

        addRequirements(this.testSubsystem);
    }

    @Override
    public void execute() {
        this.testSubsystem.periodic();
    }

}
