// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix6.Utils;
import com.ctre.phoenix6.mechanisms.swerve.SwerveRequest;
import com.ctre.phoenix6.mechanisms.swerve.SwerveModule.DriveRequestType;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;

import edu.wpi.first.wpilibj2.command.button.CommandPS4Controller; //Joystick 
import frc.robot.commands.RefreshConfigsCommand; //Refresh Config

import frc.robot.Subsystems.*;
import frc.robot.Commands.*;
import frc.robot.generated.TunerConstants;

public class RobotContainer {
  //Config Code
    private final DrivetrainSubsystem m_drivetrain = new DrivetrainSubsystem();
    private final ShooterSubsystem m_arm = new ShooterSubsystem();
    private final IntakeSubsystem m_intake = new IntakeSubsystem();
  
  // Generated Swerve Code

  private double MaxSpeed = TunerConstants.kSpeedAt12VoltsMps; // kSpeedAt12VoltsMps desired top speed
  private double MaxAngularRate = 1.5 * Math.PI; // 3/4 of a rotation per second max angular velocity

  /* Setting up bindings for necessary control of the swerve drive platform */
  private final CommandSwerveDrivetrain drivetrain = TunerConstants.DriveTrain; // My drivetrain

  private final SwerveRequest.FieldCentric drive = new SwerveRequest.FieldCentric()
      .withDeadband(MaxSpeed * 0.1).withRotationalDeadband(MaxAngularRate * 0.1) // Add a 10% deadband
      .withDriveRequestType(DriveRequestType.OpenLoopVoltage); // I want field-centric
                                                               // driving in open loop
  private final SwerveRequest.SwerveDriveBrake brake = new SwerveRequest.SwerveDriveBrake();
  private final SwerveRequest.PointWheelsAt point = new SwerveRequest.PointWheelsAt();
  private final Telemetry logger = new Telemetry(MaxSpeed);

  // Written Code

  private CommandPS4Controller controller;

  private Intake intake;
  private Intake_Commands intake_Commands;

  private Shooter shooter;
  private ShooterCommands shooterCommands;
  

  public RobotContainer() {
    controller = new CommandPS4Controller(0);

    intake = new Intake();
    intake_Commands = new Intake_Commands(intake);

    shooter = new Shooter();
    shooterCommands = new ShooterCommands(shooter);

    configureBindings();
  }

  private void configureBindings() {
    // Generated Swerve Code

    drivetrain.setDefaultCommand( // Drivetrain will execute this command periodically
        drivetrain.applyRequest(() -> drive.withVelocityX(-controller.getLeftY() * MaxSpeed) // Drive forward with
                                                                                           // negative Y (forward)
            .withVelocityY(-controller.getLeftX() * MaxSpeed) // Drive left with negative X (left)
            .withRotationalRate(-controller.getRightX() * MaxAngularRate) // Drive counterclockwise with negative X (left)
        ));

    controller.cross().whileTrue(drivetrain.applyRequest(() -> brake));
    controller.circle().whileTrue(drivetrain
        .applyRequest(() -> point.withModuleDirection(new Rotation2d(-controller.getLeftY(), -controller.getLeftX()))));

    // reset the field-centric heading on left bumper press
    controller.L1().onTrue(drivetrain.runOnce(() -> drivetrain.seedFieldRelative()));

    if (Utils.isSimulation()) {
      drivetrain.seedFieldRelative(new Pose2d(new Translation2d(), Rotation2d.fromDegrees(90)));
    }
    drivetrain.registerTelemetry(logger::telemeterize);

    // Written Code

    controller.R2()
    .onTrue(intake_Commands.runForward())
    .onFalse(intake_Commands.stop());

    controller.L2()
    .onTrue(intake_Commands.runBackward())
    .onFalse(intake_Commands.stop());
    
// Config file refresh tied to controller button
    controller.L3()
      .onTrue(new RefreshConfigsCommand(m_intake));

    controller.L1().onTrue(shooterCommands.forward());
    controller.R1().onTrue(shooterCommands.backward());
    controller.square().onTrue(shooterCommands.stopSpin());
  }

  public Command getAutonomousCommand() {
    return Commands.print("No autonomous command configured");
  }
}
