package frc.robot.controls.controllers;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.Config;

public class XBController implements Controller {

    private XboxController controller;
    private int port;

    public XBController(int port) {
        controller = new XboxController(port);
        this.port = port;
        System.out.println("XB POV Count: " + controller.getPOVCount());
    }

    @Override
    public int getPort() {
        return port;
    }

    @Override
    public boolean isXBController() {
        return true;
    }

    @Override
    public boolean isPSController() {
        return false;
    }

    @Override
    public double getLeftTriggerValue() {
        return controller.getTriggerAxis(GenericHID.Hand.kLeft);
    }

    @Override
    public boolean getLeftTrigger() {
        return controller.getTriggerAxis(GenericHID.Hand.kLeft) >= Math.abs(Config.Tolerances.TRIGGER_PRESSED_THRESHOLD);
    }

    private boolean leftTriggerPrevious = false;
    private boolean leftTriggerCurrent = false;
    @Override
    public boolean getLeftTriggerToggled() {
        leftTriggerPrevious = leftTriggerCurrent;
        leftTriggerCurrent = getLeftTrigger();
        if (leftTriggerCurrent && !leftTriggerPrevious) {
            return true;
        }
        return false;
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
        return controller.getTriggerAxis(GenericHID.Hand.kRight);
    }

    @Override
    public boolean getRightTrigger() {
        return controller.getTriggerAxis(GenericHID.Hand.kRight) >= Math.abs(Config.Tolerances.TRIGGER_PRESSED_THRESHOLD);
    }

    private boolean rightTriggerPrevious = false;
    private boolean rightTriggerCurrent = false;
    @Override
    public boolean getRightTriggerToggled() {
        rightTriggerPrevious = rightTriggerCurrent;
        rightTriggerCurrent = getRightTrigger();
        if (rightTriggerCurrent && !rightTriggerPrevious) {
            return true;
        }
        return false;
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
        return controller.getX(GenericHID.Hand.kLeft);
    }

    @Override
    public double getLeftStickY() {
        return -controller.getY(GenericHID.Hand.kLeft);
    }

    @Override
    public boolean getLeftStickButton() {
        return controller.getStickButton(GenericHID.Hand.kLeft);
    }

    @Override
    public boolean getLeftStickButtonToggled() {
        return controller.getStickButtonPressed(GenericHID.Hand.kLeft);
    }

    @Override
    public double getRightStickX() {
        return controller.getX(GenericHID.Hand.kRight);
    }

    @Override
    public double getRightStickY() {
        return -controller.getY(GenericHID.Hand.kRight);
    }

    @Override
    public boolean getRightStickButton() {
        return controller.getStickButton(GenericHID.Hand.kRight);
    }

    @Override
    public boolean getRightStickButtonToggled() {
        return controller.getStickButtonPressed(GenericHID.Hand.kRight);
    }

    @Override
    public boolean getAButton() {
        return controller.getAButton();
    }

    @Override
    public boolean getAButtonToggled() {
        return controller.getAButtonPressed();
    }

    @Override
    public boolean getBButton() {
        return controller.getBButton();
    }

    @Override
    public boolean getBButtonToggled() {
        return controller.getBButtonPressed();
    }

    @Override
    public boolean getXButton() {
        return controller.getXButton();
    }

    @Override
    public boolean getXButtonToggled() {
        return controller.getXButtonPressed();
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

    private boolean dpadUpPrevious = false;
    private boolean dpadUpCurrent = false;
    @Override
    public boolean getDpadUpToggled() {
        dpadUpPrevious = dpadUpCurrent;
        dpadUpCurrent = getDpadUp();
        if (dpadUpCurrent && !dpadUpPrevious) {
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

    private boolean dpadRightPrevious = false;
    private boolean dpadRightCurrent = false;
    @Override
    public boolean getDpadRightToggled() {
        dpadRightPrevious = dpadRightCurrent;
        dpadRightCurrent = getDpadRight();
        if (dpadRightCurrent && !dpadRightPrevious) {
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

    private boolean dpadDownPrevious = false;
    private boolean dpadDownCurrent = false;
    @Override
    public boolean getDpadDownToggled() {
        dpadDownPrevious = dpadDownCurrent;
        dpadDownCurrent = getDpadDown();
        if (dpadDownCurrent && !dpadDownPrevious) {
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

    private boolean dpadLeftPrevious = false;
    private boolean dpadLeftCurrent = false;
    @Override
    public boolean getDpadLeftToggled() {
        dpadLeftPrevious = dpadLeftCurrent;
        dpadLeftCurrent = getDpadLeft();
        if (dpadLeftCurrent && !dpadLeftPrevious) {
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
        return controller.getBackButton();
    }

    @Override
    public boolean getViewButtonToggled() {
        return controller.getBackButtonPressed();
    }

    @Override
    public boolean getMenuButton() {
        return controller.getStartButton();
    }

    @Override
    public boolean getMenuButtonToggled() {
        return controller.getStartButtonPressed();
    }

}
