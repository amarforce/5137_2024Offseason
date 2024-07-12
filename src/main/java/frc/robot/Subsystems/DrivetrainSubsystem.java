package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.config.ConfigManager;
import frc.robot.Constants;

public class DrivetrainSubsystem extends SubsystemBase {
    private PIDConfig pidConfig;

    public DrivetrainSubsystem() {
        loadPIDConfig();
    }

    private void loadPIDConfig() {
        pidConfig = ConfigManager.loadConfig(Constants.ConfigFiles.DRIVETRAIN_PID, PIDConfig.class);
        // Use pidConfig to set up your PID controller
    }

    public void reloadPIDConfig() {
        pidConfig = ConfigManager.reloadConfig(Constants.ConfigFiles.DRIVETRAIN_PID, PIDConfig.class);
        // Update your PID controller with new values
    }

    // Inner class to match JSON structure
    public static class PIDConfig {
        public double kP;
        public double kI;
        public double kD;
        public double kF;
    }
}
