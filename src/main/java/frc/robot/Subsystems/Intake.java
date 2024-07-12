package frc.robot.Subsystems;

import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

// New Config Code
import frc.robot.config.ConfigManager;

public class Intake extends SubsystemBase {
    private TalonFX motor;

    // PID Config code 
    private PIDConfig pidConfig;

    public DrivetrainSubsystem() {
        pidConfig = ConfigManager.loadConfig("IntakePIDConfig.json", PIDConfig.class);
        // Use pidConfig to set up your PID controller
    }

    //Intake code 
    public Intake() {
        motor = new TalonFX(1, "rhino");
    }

    public void set(double speed) {
        motor.set(speed);
    }

    public void stop() {
        motor.stopMotor();
    }
}
