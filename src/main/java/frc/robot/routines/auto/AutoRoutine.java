package frc.robot.routines.auto;

public enum AutoRoutine {
    FRIENDLY_TRENCH_RUN("Friendly Trench Run"),
    ENEMY_TENCH_RUN("Enemy Trench Run"),
    SHOOT_3("Shoot 3"),
    NULL("Null Auto");

    public String name;
    AutoRoutine(String name) {
        this.name = name;
    }
}
