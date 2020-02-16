package frc.robot.routines.auto;

public enum AutoState {
    READY, // constructed
    FAILED, // error pulling trajectory
    PERIODIC, // periodic
    DONE // done
}