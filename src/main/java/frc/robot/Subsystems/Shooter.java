package frc.robot.Subsystems;

import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Shooter extends SubsystemBase{
    private TalonFX topMotor = new TalonFX(3, "rhino");
    private TalonFX bottomMotor = new TalonFX(2, "rhino");
    private PIDController topPid = new PIDController(0.17, 0, 0);
    private PIDController bottomPid = new PIDController(0.18, 0, 0);
    private double speed;

    public Shooter(){
        bottomMotor.setInverted(true);
    }

    public void setSpeed(double speed){
        this.speed = speed;
    }

    @Override
    public void periodic(){
        topMotor.setVoltage(topPid.calculate(topMotor.getVelocity().getValue(),speed));
        bottomMotor.setVoltage(bottomPid.calculate(bottomMotor.getVelocity().getValue(),speed));
        SmartDashboard.putNumber("Velocity Top Motor", topMotor.getVelocity().getValue());
        SmartDashboard.putNumber("PID Speed",topPid.calculate(topMotor.getVelocity().getValue(),speed));
        SmartDashboard.putNumber("Desired Speed",speed);
    }
}
