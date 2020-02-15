package frc.robot.subsystems.drive;

import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.EncoderType;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.SPI;
import frc.robot.Config;

public class SparkTankDriveBase implements TankDriveBase {

    private AHRS navx;
    private CANSparkMax[] leftMotorGroup;
    private CANSparkMax[] rightMotorGroup;
    private CANEncoder[] leftEncoders;
    private CANEncoder[] rightEncoders;
    private DoubleSolenoid doubleSolenoid;
    private boolean highGear;

    // Singleton
    private static SparkTankDriveBase instance;
    public static SparkTankDriveBase getInstance() {
        if (instance == null) {
            instance = new SparkTankDriveBase();
        }
        return instance;
    }

    private SparkTankDriveBase() {
        try {
            navx = new AHRS(SPI.Port.kMXP);
        } catch (RuntimeException e) {
            DriverStation.reportError("Error initializing the navX over SPI: " + e.toString(), e.getStackTrace());
        }

        leftMotorGroup = new CANSparkMax[3];
        leftMotorGroup[0] = new CANSparkMax(Config.Ports.SparkTank.LEFT_1, CANSparkMaxLowLevel.MotorType.kBrushless);
        leftMotorGroup[1] = new CANSparkMax(Config.Ports.SparkTank.LEFT_2, CANSparkMaxLowLevel.MotorType.kBrushless);
        leftMotorGroup[2] = new CANSparkMax(Config.Ports.SparkTank.LEFT_3, CANSparkMaxLowLevel.MotorType.kBrushless);
        rightMotorGroup = new CANSparkMax[3];
        rightMotorGroup[0] = new CANSparkMax(Config.Ports.SparkTank.RIGHT_1, CANSparkMaxLowLevel.MotorType.kBrushless);
        rightMotorGroup[1] = new CANSparkMax(Config.Ports.SparkTank.RIGHT_2, CANSparkMaxLowLevel.MotorType.kBrushless);
        rightMotorGroup[2] = new CANSparkMax(Config.Ports.SparkTank.RIGHT_3, CANSparkMaxLowLevel.MotorType.kBrushless);

        leftEncoders = new CANEncoder[3];
        rightEncoders = new CANEncoder[3];
        for (int i = 0; i <= 2; i++) {
            leftEncoders[i] = leftMotorGroup[i].getEncoder(EncoderType.kHallSensor, 4096);
            rightEncoders[i] = rightMotorGroup[i].getEncoder(EncoderType.kHallSensor, 4096);
        }

        doubleSolenoid = new DoubleSolenoid(
            Config.Settings.SPARK_TANK_ENABLED ? Config.Ports.SparkTank.PCM : Config.Ports.TalonTank.PCM,
            Config.Ports.SparkTank.SOLENOID_FORWARD,
            Config.Ports.SparkTank.SOLENOID_REVERSE
        );
        highGear = false;
    }

    @Override
    public void tankDrive(double leftSpeed, double rightSpeed) {
        for (CANSparkMax motorController : leftMotorGroup) {
            motorController.set(-leftSpeed);
        }
        for (CANSparkMax motorController : rightMotorGroup) {
            motorController.set(rightSpeed);
        }
    }

    @Override
    public void toggleGearing() {
        if (highGear) {
            setLowGear();
        } else {
            setHighGear();
        }
    }

    @Override
    public void setHighGear() {
        doubleSolenoid.set(DoubleSolenoid.Value.kForward);
        highGear = true;
    }

    @Override
    public void setLowGear() {
        doubleSolenoid.set(DoubleSolenoid.Value.kReverse);
        highGear = false;
    }

    @Override
    public boolean isHighGear() {
        return highGear;
    }

    @Override
    public boolean isLowGear() {
        return !highGear;
    }

    public void testMotor() {
        rightMotorGroup[2].set(1);
    }

}
