package frc.robot.subsystems;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import frc.robot.Color;

public class LEDStrip {

    private int length;
    private AddressableLED led;
    private AddressableLEDBuffer ledBuffer;
    private long counter = 0;
    private Color color;

    public LEDStrip(int port, int length) {
        this.length = length;
        led = new AddressableLED(port);
        led.setLength(length);
        ledBuffer = new AddressableLEDBuffer(length);
        led.setData(ledBuffer);
        led.start();

        color = Color.fromRGB(0, 0, 0);
    }

    public void setRGBColor(int r, int g, int b) {
        color = Color.fromRGB(r, g, b);
        for (int i = 0; i < length; i++) {
            ledBuffer.setRGB(i, r, g, b);
        }
        led.setData(ledBuffer);
    }

    // set color based on hue [0-180], saturation [0-255], and value [0-255]
    public void setHSVColor(int h, int s, int v) {
        color = Color.fromHSV(h, s, v);
        /*for (int i = 0; i < length; i++) {
            ledBuffer.setHSV(i, h, s, v);
        }
        led.setData(ledBuffer);*/
    }

    public void setCMYKColor(int c, int m, int y, int k) {
        color = Color.fromCMYK(c, m, y, k);
    }

    public void solidColor() {
        for (int i = 0; i < length; i++) {
            ledBuffer.setRGB(color.getRed(), color.getGreen(), color.getBlue());
        }
        led.setData(ledBuffer);
    }

    public void blink(int period) {
        update();
        if (counter % period == 0) {
            ledBuffer.setRGB(color.getRed(), color.getGreen(), color.getBlue());
        }
        else {
            ledBuffer.setRGB(0, 0, 0);
        }

    }

    private void update() {
        counter++;

    }

    public Color getColor() {
        return color;
    }

    public int getLength() {
        return lenth;
    }

}