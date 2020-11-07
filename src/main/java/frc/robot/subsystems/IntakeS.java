/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.wrappers.motorcontrollers.NomadSparkMax;

public class IntakeS extends SubsystemBase {
  /**
   * Creates a new IntakeS.
   */

   public NomadSparkMax intakeMotor;
   public DoubleSolenoid doubleSolenoid;

  public IntakeS() {
      intakeMotor = new NomadSparkMax(30);
      doubleSolenoid = new DoubleSolenoid(2, 3);
  }

  public void setSpeed(double speed) {
    intakeMotor.set(speed);
  }

  public void extend() {
    doubleSolenoid.set(Value.kForward);
  }

  public void retract() {
    doubleSolenoid.set(Value.kReverse);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
