package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Limelight {

    private static Limelight instance;
    public static Limelight getInstance() {
        if (instance == null) {
            instance = new Limelight();
        }
        return instance;
    }

    private boolean visionEnabled;
    private double tx;
    private double ty;
    private double ta;
    private double ts;
    private double distance;

    private Limelight() {
        getTable().getEntry("camMode").setNumber(0);
        visionEnabled = true;
    }

    public boolean toggleMode() {
        getTable().getEntry("camMode").setNumber(visionEnabled ? 1 : 0);
        visionEnabled = !visionEnabled;
        return visionEnabled;
    }

    public void update() {
        NetworkTable nt = NetworkTableInstance.getDefault().getTable("limelight");
        tx = nt.getEntry("tx").getDouble(0);
        ty = nt.getEntry("ty").getDouble(0);
        ta = nt.getEntry("ta").getDouble(0);
        ts = nt.getEntry("ts").getDouble(0);
        distance = ((double) 66.5) / Math.tan(Math.toRadians(29.0436377382 + ty));
        SmartDashboard.putNumber("tx", tx);
        SmartDashboard.putNumber("ty", ty);
        SmartDashboard.putNumber("ta", ta);
        SmartDashboard.putNumber("ts", ts);
        SmartDashboard.putNumber("Limelight Distance", distance);
    }

    public NetworkTable getTable() {
        return NetworkTableInstance.getDefault().getTable("limelight");
    }

    public double tx() {
        return tx;
    }

    public double ty() {
        return ty;
    }

    public double ts() {
        return ts;
    }

    public double ta() {
        return ta;
    }

}
