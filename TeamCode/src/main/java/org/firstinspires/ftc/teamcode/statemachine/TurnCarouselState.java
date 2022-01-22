package org.firstinspires.ftc.teamcode.statemachine;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.ArrayList;

public class TurnCarouselState extends State{

    boolean on = false;

    DcMotor carouselMotor;
    String movement;

    double carouselPower;

    private double Time;
    ElapsedTime mRuntime = new ElapsedTime();




    public TurnCarouselState(StateMachine statemachine, double time, ArrayList<DcMotor> motors, double power, String direction){
        super(statemachine);
        carouselMotor = motors.get(5);
        carouselPower = power;
        movement = direction;
        Time = time;
        mRuntime.reset();
    }

    @Override
    void start() {
        mRuntime.reset();
        carouselMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        on = true;

    }

    @Override
    void update() {
        while (mRuntime.seconds() < Time) {
            carouselMotor.setPower(carouselPower);
            on = true;

        }


        on = false;
        carouselMotor.setPower(0);

        this.stop();
    }

    @Override
    void stop() {

    }

    @Override
    void initialize() {

    }

    public boolean getOn(){
        return on;
    }
}
