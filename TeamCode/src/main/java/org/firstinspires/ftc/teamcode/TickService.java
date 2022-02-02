package org.firstinspires.ftc.teamcode;

public final class TickService {

    private static final double wheelCircumference = 6.5;
    private static final int ticksPerTurn = 1120;

    public static int inchesToTicks(double inches) {
        double circumferenceTraveled = inches / wheelCircumference;
        int ticksTraveled = (int) (ticksPerTurn * circumferenceTraveled);

        return ticksTraveled;
    }
}