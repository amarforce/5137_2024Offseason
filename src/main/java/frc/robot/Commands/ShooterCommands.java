package frc.robot.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.Subsystems.Shooter;

public class ShooterCommands {
    private Shooter botShooter;

    public ShooterCommands(Shooter botShooter){
        this.botShooter=botShooter;
    }

    public InstantCommand forward(){
        return new InstantCommand(()->botShooter.setSpeed(2));
    }

    public InstantCommand backward(){
        return new InstantCommand(()->botShooter.setSpeed(-2));
    }

    public InstantCommand stopSpin(){
        return new InstantCommand(()->botShooter.setSpeed(0));
    }
}
