package frc.robot.controls.controllers;

public interface Controller {

    int getPort();
    boolean isXBController();
    boolean isPSController();

    double getLeftTriggerValue();
    boolean getLeftTrigger();
    boolean getLeftTriggerToggled();
    boolean getLeftBumper();
    boolean getLeftBumperToggled();
    double getRightTriggerValue();
    boolean getRightTrigger();
    boolean getRightTriggerToggled();
    boolean getRightBumper();
    boolean getRightBumperToggled();

    double getLeftStickX();
    double getLeftStickY();
    boolean getLeftStickButton();
    boolean getLeftStickButtonToggled();
    double getRightStickX();
    double getRightStickY();
    boolean getRightStickButton();
    boolean getRightStickButtonToggled();

    boolean getAButton();
    boolean getAButtonToggled();
    boolean getBButton();
    boolean getBButtonToggled();
    boolean getXButton();
    boolean getXButtonToggled();
    boolean getYButton();
    boolean getYButtonToggled();

    boolean getDpadNeutral();
    boolean getDpadUp();
    boolean getDpadUpToggled();
    boolean getDpadUpRight();
    boolean getDpadRight();
    boolean getDpadRightToggled();
    boolean getDpadDownRight();
    boolean getDpadDown();
    boolean getDpadDownToggled();
    boolean getDpadDownLeft();
    boolean getDpadLeft();
    boolean getDpadLeftToggled();
    boolean getDpadUpLeft();

    boolean getViewButton();
    boolean getViewButtonToggled();
    boolean getMenuButton();
    boolean getMenuButtonToggled();

}
