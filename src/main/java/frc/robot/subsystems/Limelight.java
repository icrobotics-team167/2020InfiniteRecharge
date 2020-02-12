package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;

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
        setVisionMode();
    }

    public void setCameraMode() {
        getTable().getEntry("camMode").setNumber(1);
        visionEnabled = false;
    }

    public void setVisionMode() {
        getTable().getEntry("camMode").setNumber(0);
        visionEnabled = true;
    }

    public void update() {
        NetworkTable nt = NetworkTableInstance.getDefault().getTable("limelight");
        tx = nt.getEntry("tx").getDouble(0);
        ty = nt.getEntry("ty").getDouble(0);
        ta = nt.getEntry("ta").getDouble(0);
        ts = nt.getEntry("ts").getDouble(0);
        distance = ((double) 66.5) / Math.tan(Math.toRadians(29.0436377382 + ty));
    }

    public NetworkTable getTable() {
        return NetworkTableInstance.getDefault().getTable("limelight");
    }

    public double tx() {
        update();
        return tx;
    }

    public double ty() {
        update();
        return ty;
    }

    public double ts() {
        update();
        return ts;
    }

    public double ta() {
        update();
        return ta;
    }

    public double distance() {
        update();
        return distance;
    }

}
