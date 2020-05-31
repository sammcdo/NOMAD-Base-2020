package frc.robot;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import frc.robot.constants.DriveConstants;
import frc.robot.constants.Trajectories;
import frc.robot.constants.VisionConstants;
import frc.robot.constants.DriveConstants.CONTROLLER_TYPE;
import frc.robot.commands.BasicAutoCG;
import frc.robot.commands.auto.NomadPathFollowerCommandBuilder;
import frc.robot.commands.drivebase.DrivebaseVisionC;
import frc.robot.subsystems.DrivebaseS;
import frc.robot.subsystems.LimelightS;
import frc.utility.Point2d;
import io.github.oblarg.oblog.annotations.Log;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

/**
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  
  //changed to public\/
  @Log(name="DrivebaseS")
  public final DrivebaseS drivebaseS = new DrivebaseS();
  @Log(name="LimelightS")
  public final LimelightS limelightS = new LimelightS();

  public final PowerDistributionPanel pdp = new PowerDistributionPanel();

  private final BasicAutoCG basicAutoCG = new BasicAutoCG();
  private final SequentialCommandGroup sCurveRightAutoCG 
    = new NomadPathFollowerCommandBuilder(Trajectories.sCurveRight, drivebaseS).buildPathFollowerCommandGroup();
    private final SequentialCommandGroup straight2mAutoCG 
    = new NomadPathFollowerCommandBuilder(Trajectories.straight2m, drivebaseS).buildPathFollowerCommandGroup();  
  public final GenericHID driveController;
  private final Command driveStickC;
  private DoubleSupplier fwdBackAxis;
  private final DrivebaseVisionC visionAlignC; 

  private Point2d point = new Point2d(0.7, 0.4);

  /**
   * The container for the robot.  Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    //Initializes driveController as either a Joystick or Xbox depending on DriveConstants.DRIVE_CONTROLLER_TYPE.
    if (DriveConstants.DRIVE_CONTROLLER_TYPE == CONTROLLER_TYPE.Joystick) {
      driveController = new Joystick(DriveConstants.OI_DRIVE_CONTROLLER);
    }
    else {
      driveController = new XboxController(DriveConstants.OI_DRIVE_CONTROLLER);
    }
    fwdBackAxis = () -> -driveController.getRawAxis(DriveConstants.AXIS_DRIVE_FWD_BACK);
    //Initializes the driveStickC command inline. Simply passes the drive controller axes into the drivebaseS arcadeDrive.
    driveStickC = new RunCommand(() -> drivebaseS.arcadeDrive(-driveController.getRawAxis(DriveConstants.AXIS_DRIVE_FWD_BACK), driveController.getRawAxis(DriveConstants.AXIS_DRIVE_TURN)), drivebaseS);
    visionAlignC = new DrivebaseVisionC(drivebaseS, limelightS, VisionConstants.VISION_PIPELINE);
    //Turn off LiveWindow telemetry. We don't use it and it takes 90% of the loop time.
    LiveWindow.disableAllTelemetry();
    // Configure the button bindings
    configureButtonBindings();

    drivebaseS.setDefaultCommand(driveStickC);

    Shuffleboard.getTab("TEST")
                .add("Test Point", point)
                .withWidget("MyPoint2D");
  }

  /**
   * Use this method to define your button->command mappings.  Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a
   * {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    new JoystickButton(driveController, 4).whileHeld(visionAlignC);
    
  }

  
  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return sCurveRightAutoCG;
  }
}
