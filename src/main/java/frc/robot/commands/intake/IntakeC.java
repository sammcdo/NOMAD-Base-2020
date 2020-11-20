/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.intake;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.IntakeS;
import frc.robot.wrappers.inputdevices.NomadDriverController;

public class IntakeC extends CommandBase {
  /**
   * Creates a new IntakeC.
   */

  IntakeS intakeS;
  NomadDriverController driveStick;

  public IntakeC(final IntakeS intakeS, final NomadDriverController stick) {
    this.intakeS = intakeS;
    addRequirements(intakeS);
    driveStick = stick;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    intakeS.extend();
    intakeS.setSpeed(0.25); 
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
        
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(final boolean interrupted) {
    intakeS.retract();
    intakeS.setSpeed(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
