package frc.robot.Subsystems;

import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

// New Config Code
import frc.robot.config.ConfigManager;
import frc.robot.Constants;

public class Intake extends SubsystemBase {
    private TalonFX motor;

    // PID Config code 
    private PIDConfig pidConfig;

    

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
    
    //Intake code 
    public Intake() {
// Load PID Config
       loadPIDConfig();

        
        motor = new TalonFX(1, "rhino");
    }

    public void set(double speed) {
        motor.set(speed);
    }

    public void stop() {
        motor.stopMotor();
    }
}
