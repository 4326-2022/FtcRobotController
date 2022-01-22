package org.firstinspires.ftc.teamcode.statemachine;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
import java.util.ArrayList;
import com.qualcomm.robotcore.util.ElapsedTime;

public class ExtendPulleyState extends State {

    DcMotor pulley;
    Servo leftHand;
    Servo rightHand;
    boolean on = false;

    private double Power;
    private String Movement;

    private State NextState;

    private double Time;
    ElapsedTime mRuntime = new ElapsedTime();


    public ExtendPulleyState(StateMachine statemachine, double time, double power, ArrayList<DcMotor> motor, Servo right, Servo left) {
        super(statemachine);
        Time = time;
        pulley = motor.get(4);
        leftHand = left;
        rightHand = right;
        Power = power;
        mRuntime.reset();

    }

    public void setNextState(State state) {
        NextState = state;

    }

    @Override
    public void start() {
        //  this.update();
        mRuntime.reset();
        pulley.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);



    }

    @Override
    public void update() {


        while (mRuntime.seconds() < Time) {
            pulley.setPower(Power);

        }


        //on = false;
        pulley.setPower(0);

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