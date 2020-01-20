package frc.robot.subsystems;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;

public class LEDStrip {

    private int length;
    private AddressableLED led;
    private AddressableLEDBuffer ledBuffer;

    public LEDStrip(int port, int length) {
        this.length = length;
        led = new AddresableLED(port);
        led.setLength(length);
        ledBuffer = new AddressableLEDBuffer(length);
        led.setData(ledBuffer);
        led.start();
    }

    public void setRGBColor(int r, int g, int b) {
        for(int i = 0; i < length; i++) {
            ledBuffer.setRGB(i, r, g, b);
        }
        led.setData(ledBuffer);
    }

    // set color based on hue [0-180], saturation [0-255], and value [0-255]
    public void setHSVColor(int h, int s, int v) {
        for(int i = 0; i < length; i++) {
            ledBuffer.setHSV(i, h, s, v);
        }
        led.setData(ledBuffer);
    }

}