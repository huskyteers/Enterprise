/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.CameraServer;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends TimedRobot {
  SpeedController frontLeft = new WPI_TalonSRX(1);
  SpeedController frontRight = new WPI_TalonSRX(2);
  SpeedController rearLeft = new WPI_TalonSRX(3);
  SpeedController rearRight = new WPI_TalonSRX(4);

  private final MecanumDrive m_robotDrive = new MecanumDrive(frontLeft, rearLeft, frontRight, rearRight);
  private final XboxController controller = new XboxController(0);
  private final Timer m_timer = new Timer();

  private DoubleSolenoid lifting = new DoubleSolenoid(0, 1);
  private DoubleSolenoid grabber = new DoubleSolenoid(2, 3);

  /**
   * This function is run when the robot is first started up and should be used
   * for any initialization code.
   */
  @Override
  public void robotInit() {
    CameraServer.getInstance().startAutomaticCapture();
  }

  /**
   * This function is run once each time the robot enters autonomous mode.
   */
  @Override
  public void autonomousInit() {
    m_timer.reset();
    m_timer.start();
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    // Drive for 2 seconds
    /*
     * if (m_timer.get() < 2.0) { m_robotDrive.arcadeDrive(0.5, 0.0); // drive
     * forwards half speed } else { m_robotDrive.stopMotor(); // stop robot }
     */
  }

  /**
   * This function is called once each time the robot enters teleoperated mode.
   */
  @Override
  public void teleopInit() {
  }

  /**
   * This function is called periodically during teleoperated mode.
   */
  @Override
  public void teleopPeriodic() {
    m_robotDrive.driveCartesian(controller.getX(Hand.kLeft), -controller.getY(Hand.kRight),
        controller.getX(Hand.kRight));

    if (controller.getAButtonPressed()) {
      lifting.set(DoubleSolenoid.Value.kForward);
    } else if (controller.getBButtonPressed()) {
      lifting.set(DoubleSolenoid.Value.kReverse);
    }

    if (controller.getXButtonPressed()) {
      grabber.set(DoubleSolenoid.Value.kForward);
    } else if (controller.getYButtonPressed()) {
      grabber.set(DoubleSolenoid.Value.kReverse);
    }
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
}