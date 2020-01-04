package frc.robot;

public final class Color {

    public static final Color BLUE = new Color(1, 0, 0, 0);
    public static final Color GREEN = new Color(1, 0, 1, 0);
    public static final Color RED = new Color(0, 1, 1, 0);
    public static final Color YELLOW = new Color(0, 0, 1, 0);

    public final double red;
    public final double green;
    public final double blue;

    public Color(double red, double green, double blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public Color(double cyan, double magenta, double yellow, double black) {
        this(255 * (1 - cyan) * (1 - black), 255 * (1 - magenta) * (1 - black), 255 * (1 - yellow) * (1 - black));
    }

    public boolean isBlue() {
        return Math.abs(this.red - BLUE.red) < 25 && Math.abs(this.green - BLUE.green) < 25 && Math.abs(this.blue - BLUE.blue) < 25;
    }

    public boolean isGreen() {
        return Math.abs(this.red - GREEN.red) < 25 && Math.abs(this.green - GREEN.green) < 25 && Math.abs(this.blue - GREEN.blue) < 25;
    }

    public boolean isRed() {
        return Math.abs(this.red - RED.red) < 25 && Math.abs(this.green - RED.green) < 25 && Math.abs(this.blue - RED.blue) < 25;
    }

    public boolean isYellow() {
        return Math.abs(this.red - YELLOW.red) < 25 && Math.abs(this.green - YELLOW.green) < 25 && Math.abs(this.blue - YELLOW.blue) < 25;
    }

}
