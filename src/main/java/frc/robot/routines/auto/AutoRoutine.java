package frc.robot.routines.auto;

public enum AutoRoutine {
    FRIENDLY_TRENCH_RUN("Friendly Trench Run", "FTR"),
    ENEMY_TENCH_RUN("Enemy Trench Run", "ETR"),
    SHOOT_3("Shoot 3", "S3"),
    NULL("Null Auto", "");

    public String name;
    public String trajectoryFile;
    AutoRoutine(String name, String trajectoryFile) {
        this.name = name;
        this.trajectoryFile = trajectoryFile;
    }
}
