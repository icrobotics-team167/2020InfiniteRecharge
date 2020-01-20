package frc.robot.controllers;

public interface GameController {

    int getPort();
    boolean isXBController();
    boolean isPSController();

    double getLeftTriggerValue();
    boolean getLeftTrigger();
    boolean getLeftBumper();
    boolean getLeftBumperToggled();
    double getRightTriggerValue();
    boolean getRightTrigger();
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
    boolean getDpadUpRight();
    boolean getDpadRight();
    boolean getDpadDownRight();
    boolean getDpadDown();
    boolean getDpadDownLeft();
    boolean getDpadLeft();
    boolean getDpadUpLeft();

    boolean getViewButton();
    boolean getViewButtonToggled();
    boolean getMenuButton();
    boolean getMenuButtonToggled();

}
