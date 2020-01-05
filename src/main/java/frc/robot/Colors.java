package frc.robot;

public enum Colors {

    // Control panel colors
    BLUE(new Color(1, 0, 0, 0), "B", "Blue"), // 0, 255, 255
    GREEN(new Color(1, 0, 1, 0), "G", "Green"), // 0, 255, 0
    RED(new Color(0, 1, 1, 0), "R", "Red"), // 255, 0, 0
    YELLOW(new Color(0, 0, 1, 0), "Y", "Yellow"); // 255, 255, 0

    public final Color color;
    public final String label;
    public final String name;

    Colors(Color color, String label, String name) {
        this.color = color;
        this.label = label;
        this.name = name;
    }

}
