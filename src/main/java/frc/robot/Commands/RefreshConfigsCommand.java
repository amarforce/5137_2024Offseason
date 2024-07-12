package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DrivetrainSubsystem;
import frc.robot.subsystems.ArmSubsystem;


public class RefreshConfigsCommand extends CommandBase {
    private final DrivetrainSubsystem m_drivetrain;
    private final ArmSubsystem m_arm;

    public RefreshConfigsCommand(DrivetrainSubsystem drivetrain, ArmSubsystem arm) {
        m_drivetrain = drivetrain;
        m_arm = arm;
    }

    @Override
    public void initialize() {
        m_drivetrain.reloadPIDConfig();
        m_arm.reloadPIDConfig();
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
