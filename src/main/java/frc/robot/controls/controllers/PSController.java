package frc.robot.controls.controllers;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;

public class PSController implements Controller {

    private XboxController controller;
    private int port;

    public PSController(int port) {
        controller = new XboxController(port);
        this.port = port;
        System.out.println("PS POV Count: " + controller.getPOVCount());
    }

    @Override
    public int getPort() {
        return port;
    }

    @Override
    public boolean isXBController() {
        return false;
    }

    @Override
    public boolean isPSController() {
        return true;
    }

    @Override
    public double getLeftTriggerValue() {
        return (controller.getRawAxis(3) + 1) / 2;
    }

    @Override
    public boolean getLeftTrigger() {
        return controller.getBackButton();
    }

    public boolean getLeftTriggerToggled() {
        return controller.getBackButtonPressed();
    }

    @Override
    public boolean getLeftBumper() {
        return controller.getBumper(GenericHID.Hand.kLeft);
    }

    @Override
    public boolean getLeftBumperToggled() {
        return controller.getBumperPressed(GenericHID.Hand.kLeft);
    }

    @Override
    public double getRightTriggerValue() {
        return (controller.getRawAxis(4) + 1) / 2;
    }

    @Override
    public boolean getRightTrigger() {
        return controller.getStartButton();
    }

    public boolean getRightTriggerToggled() {
        return controller.getStartButtonPressed();
    }

    @Override
    public boolean getRightBumper() {
        return controller.getBumper(GenericHID.Hand.kRight);
    }

    @Override
    public boolean getRightBumperToggled() {
        return controller.getBumperPressed(GenericHID.Hand.kRight);
    }

    @Override
    public double getLeftStickX() {
        return controller.getRawAxis(0);
    }

    @Override
    public double getLeftStickY() {
        return -controller.getRawAxis(1);
    }

    @Override
    public boolean getLeftStickButton() {
        return controller.getRawButton(11);
    }

    @Override
    public boolean getLeftStickButtonToggled() {
        return controller.getRawButtonPressed(11);
    }

    @Override
    public double getRightStickX() {
        return controller.getRawAxis(2);
    }

    @Override
    public double getRightStickY() {
        return -controller.getRawAxis(5);
    }

    @Override
    public boolean getRightStickButton() {
        return controller.getRawButton(12);
    }

    @Override
    public boolean getRightStickButtonToggled() {
        return controller.getRawButtonPressed(12);
    }

    @Override
    public boolean getAButton() {
        return controller.getBButton();
    }

    @Override
    public boolean getAButtonToggled() {
        return controller.getBButtonPressed();
    }

    @Override
    public boolean getBButton() {
        return controller.getXButton();
    }

    @Override
    public boolean getBButtonToggled() {
        return controller.getXButtonPressed();
    }

    @Override
    public boolean getXButton() {
        return controller.getAButton();
    }

    @Override
    public boolean getXButtonToggled() {
        return controller.getAButtonPressed();
    }

    @Override
    public boolean getYButton() {
        return controller.getYButton();
    }

    @Override
    public boolean getYButtonToggled() {
        return controller.getYButtonPressed();
    }

    @Override
    public boolean getDpadNeutral() {
        return controller.getPOV(0) == -1;
    }

    @Override
    public boolean getDpadUp() {
        return controller.getPOV(0) == 0;
    }

    private boolean dpadUpToggle = true;
    @Override
    public boolean getDpadUpToggled() {
        if (dpadUpToggle && getDpadUp()) {
            dpadUpToggle = false;
            return true;
        } else if (!getDpadUp()) {
            dpadUpToggle = true;
            return true;
        }
        return false;
    }

    @Override
    public boolean getDpadUpRight() {
        return controller.getPOV(0) == 45;
    }

    @Override
    public boolean getDpadRight() {
        return controller.getPOV(0) == 90;
    }

    private boolean dpadRightToggle = true;
    @Override
    public boolean getDpadRightToggled() {
        if (dpadRightToggle && getDpadRight()) {
            dpadRightToggle = false;
            return true;
        } else if (!getDpadRight()) {
            dpadRightToggle = true;
            return true;
        }
        return false;
    }

    @Override
    public boolean getDpadDownRight() {
        return controller.getPOV(0) == 135;
    }

    @Override
    public boolean getDpadDown() {
        return controller.getPOV(0) == 180;
    }

    private boolean dpadDownToggle = true;
    @Override
    public boolean getDpadDownToggled() {
        if (dpadDownToggle && getDpadDown()) {
            dpadDownToggle = false;
            return true;
        } else if (!getDpadDown()) {
            dpadDownToggle = true;
            return true;
        }
        return false;
    }

    @Override
    public boolean getDpadDownLeft() {
        return controller.getPOV(0) == 225;
    }

    @Override
    public boolean getDpadLeft() {
        return controller.getPOV(0) == 270;
    }

    private boolean dpadLeftToggle = true;
    @Override
    public boolean getDpadLeftToggled() {
        if (dpadLeftToggle && getDpadLeft()) {
            dpadLeftToggle = false;
            return true;
        } else if (!getDpadLeft()) {
            dpadLeftToggle = true;
            return true;
        }
        return false;
    }

    @Override
    public boolean getDpadUpLeft() {
        return controller.getPOV(0) == 315;
    }

    @Override
    public boolean getViewButton() {
        return controller.getStickButton(GenericHID.Hand.kLeft);
    }

    @Override
    public boolean getViewButtonToggled() {
        return controller.getStickButtonPressed(GenericHID.Hand.kLeft);
    }

    @Override
    public boolean getMenuButton() {
        return controller.getStickButton(GenericHID.Hand.kRight);
    }

    @Override
    public boolean getMenuButtonToggled() {
        return controller.getStickButtonPressed(GenericHID.Hand.kRight);
    }

    public boolean getPSButton() {
        return controller.getRawButton(13);
    }

    public boolean getPSButtonToggled() {
        return controller.getRawButtonPressed(13);
    }

    public boolean getTouchpadButton() {
        return controller.getRawButton(14);
    }

    public boolean getTouchpadButtonToggled() {
        return controller.getRawButtonPressed(14);
    }

}
