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
import frc.robot.routines.auto.Auto;
import frc.robot.routines.auto.AutoRoutine;
import frc.robot.subsystems.Subsystems;
import frc.robot.subsystems.drive.SparkTankDriveBase;

public class Robot extends TimedRobot {

    private SendableChooser<AutoRoutine> autoChooser = new SendableChooser<>();
    private DriverStation driverStation;
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

        driverStation = DriverStation.getInstance();

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
        }).start();

        Subsystems.setInitialStates();

        auto = new Routine(new Action[] {
            new Auto(AutoRoutine.FTR1),
            // new Auto(AutoRoutine.FTR2),
            // new Auto(AutoRoutine.FTR3),
            // new Auto(AutoRoutine.FTR4),
            // new Auto(AutoRoutine.FTR5),
            // new Auto(AutoRoutine.RT),
            // new Auto(AutoRoutine.RT),
            // new Auto(AutoRoutine.RT),
            // new Auto(AutoRoutine.RT),
        });
        // auto = new Auto(AutoRoutine.FRIENDLY_TRENCH_RUN);
        teleop = new Teleop(controls);
    }

    @Override
    public void robotPeriodic() {
        SmartDashboard.putNumber("drive/leftEncoder", Subsystems.driveBase.getLeftEncoderPosition());
        SmartDashboard.putNumber("drive/rightEncoder", Subsystems.driveBase.getRightEncoderPosition());
        // System.out.println("Left encoder: " + Subsystems.driveBase.());
        // System.out.println("Right encoder: " + Subsystems.driveBase.getRightEncoderPosition());
        
        SmartDashboard.putNumber("Speed: ", ((SparkTankDriveBase) Subsystems.driveBase).getSpeed());
        SmartDashboard.putNumber("Accel: ", ((SparkTankDriveBase) Subsystems.driveBase).getAccl());

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
    }

    @Override
    public void teleopInit() {
        teleop.init();
        Subsystems.driveBase.resetEncoders();
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
    }

    @Override
    public void disabledPeriodic() {
    }

}
