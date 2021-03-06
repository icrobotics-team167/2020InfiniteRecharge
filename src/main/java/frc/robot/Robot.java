package frc.robot;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.controls.controllers.Controller;
import frc.robot.controls.controllers.PSController;
import frc.robot.controls.controllers.XBController;
import frc.robot.controls.controlschemes.ControlScheme;
import frc.robot.controls.controlschemes.DoubleController;
import frc.robot.controls.controlschemes.NullController;
import frc.robot.controls.controlschemes.SingleController;
import frc.robot.routines.Action;
import frc.robot.routines.Routine;
import frc.robot.routines.Teleop;
import frc.robot.routines.auto.AutoRoutine;
import frc.robot.routines.auto.FollowPath;
import frc.robot.routines.auto.RawDriveStraight;
import frc.robot.routines.auto.RawPointTurn;
import frc.robot.routines.auto.SecondSickoShoot;
import frc.robot.routines.auto.SickoShoot;
import frc.robot.routines.auto.SmartDriveStraight;
import frc.robot.routines.auto.SmartPointTurn;
import frc.robot.routines.auto.StartShooter;
import frc.robot.routines.auto.Wait;
import frc.robot.routines.auto.WaitInfinite;
import frc.robot.subsystems.Subsystems;

public class Robot extends TimedRobot {

    private SendableChooser<AutoRoutine> autoChooser = new SendableChooser<>();
    private ControlScheme controls;
    private Action auto;
    private Teleop teleop;

    public Robot() {
        super(Config.Settings.CPU_PERIOD);
    }

    @Override
    public void robotInit() {
        autoChooser.setDefaultOption(AutoRoutine.NULL.name, AutoRoutine.NULL);
        autoChooser.addOption(AutoRoutine.ENEMY_TENCH_RUN.name, AutoRoutine.ENEMY_TENCH_RUN);
        autoChooser.addOption(AutoRoutine.SHOOT_3.name, AutoRoutine.SHOOT_3);
        SmartDashboard.putData("Autonomous Routines", autoChooser);

        Controller primaryController = null;
        switch (Config.Settings.PRIMARY_CONTROLLER_TYPE) {
            case XB:
                primaryController = new XBController(Config.Ports.PRIMARY_CONTROLLER);
                break;
            case PS:
                primaryController = new PSController(Config.Ports.PRIMARY_CONTROLLER);
                break;
            case NONE:
                primaryController = null;
                break;
        }
        Controller secondaryController = null;
        switch (Config.Settings.SECONDARY_CONTROLLER_TYPE) {
            case XB:
                secondaryController = new XBController(Config.Ports.SECONDARY_CONTROLLER);
                break;
            case PS:
                secondaryController = new PSController(Config.Ports.SECONDARY_CONTROLLER);
                break;
            case NONE:
                secondaryController = null;
                break;
        }
        if (primaryController == null && secondaryController == null) {
            controls = new NullController();
        } else if (primaryController != null && secondaryController == null) {
            controls = new SingleController(primaryController);
        } else if (primaryController != null && secondaryController != null) {
            controls = new DoubleController(primaryController, secondaryController);
        } else {
            // Fallback
            // This should be unreachable in normal conditions
            // This could only occur if the secondary controller is configured but the primary controller isn't
            controls = new NullController();
        }

        new Thread(() -> {
            UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
//            camera.setFPS(30);
//            camera.setResolution(320, 240);
        }).start();

        Subsystems.setInitialStates();

        auto = new Routine(new Action[] {
            // Trench auto
            // new StartShooter(),
            // new SmartDriveStraight(114, 0.4, 7),
            // new SmartDriveStraight(15, -0.3),
            // new SickoShoot(3),
            // new SmartDriveStraight(83, 0.2, 3.5),
            // new SmartDriveStraight(83, -0.4, 3.5),
            // new SecondSickoShoot(3),

            // Center auto
            new StartShooter(),
            new SmartDriveStraight(82, 0.2, 10),
            new SmartDriveStraight(36, -0.2, 6),
            new SickoShoot(3),
        });
        teleop = new Teleop(controls);
    }

    @Override
    public void robotPeriodic() {
        SmartDashboard.putNumber("drive/leftEncoderPosition", Subsystems.driveBase.getLeftEncoderPosition());
        SmartDashboard.putNumber("drive/rightEncoderPosition", Subsystems.driveBase.getRightEncoderPosition());
        SmartDashboard.putNumber("drive/gyroAngle", Subsystems.driveBase.getAngle());

        SmartDashboard.putNumber("limelight/tx", Subsystems.limelight.tx());
        SmartDashboard.putNumber("limelight/ty", Subsystems.limelight.ty());
        SmartDashboard.putNumber("limelight/ta", Subsystems.limelight.ta());
        SmartDashboard.putNumber("limelight/ts", Subsystems.limelight.ts());
        SmartDashboard.putNumber("limelight/distance", Subsystems.limelight.distance());

        SmartDashboard.putNumber("shooter/rpm", Subsystems.shooter.getRPM());
        SmartDashboard.putBoolean("shooter/upToSpeed", Subsystems.shooter.isUpToSpeed());
        SmartDashboard.putNumber("shooter/left/voltage", Subsystems.shooter.getLeftVoltage());
        SmartDashboard.putNumber("shooter/right/voltage", Subsystems.shooter.getRightVoltage());

        SmartDashboard.putBoolean("indexer/gapAligned", Subsystems.indexer.isGapAligned());
        SmartDashboard.putBoolean("indexer/isRunningAntiJam", Subsystems.indexer.isRunningAntiJam());
        SmartDashboard.putBoolean("indexer/isShooting", Subsystems.indexer.isShooting());
        SmartDashboard.putNumber("indexer/rpm", Subsystems.indexer.getTurnRPM());
    }

    @Override
    public void autonomousInit() {
        Subsystems.driveBase.resetEncoders();
        Subsystems.driveBase.setHighGear();
        auto.exec();
        System.out.println("Auto selected: " + autoChooser.getSelected().name);
    }

    @Override
    public void autonomousPeriodic() {
        auto.exec();
        Subsystems.intake.run();
        Subsystems.indexer.run();
        Subsystems.shooter.run();
        Subsystems.turret.run();
    }

    @Override
    public void teleopInit() {
        teleop.init();
    }

    @Override
    public void teleopPeriodic() {
        teleop.periodic();
    }

    @Override
    public void testInit() {
    }

    @Override
    public void testPeriodic() {
    }

    @Override
    public void disabledInit() {
        Subsystems.limelight.setCameraMode();
    }

    @Override
    public void disabledPeriodic() {
    }

}
