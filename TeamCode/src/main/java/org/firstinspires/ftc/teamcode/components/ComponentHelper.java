package org.firstinspires.ftc.teamcode.components;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

/**
 * This class is responsible for getting component instances.
 */
public class ComponentHelper {

    private static HashMap<Class<? extends Component>, Object> instances = new HashMap<Class<? extends Component>, Object>();

    public static <T extends Component> T getComponent(Class<T> clazz, OpMode opMode, ElapsedTime elapsedTime) {
        Object instance = instances.get(clazz);
        if (instance != null) {
            T castedInstance = (T) instance;

            if (castedInstance.opMode == opMode) {
                return (T) instances.get(clazz);
            }
        }

        try {
            Constructor<T> ctor = clazz.getConstructor(OpMode.class, HardwareMap.class, Telemetry.class, ElapsedTime.class);
            T createdInstance = ctor.newInstance(opMode, opMode.hardwareMap, opMode.telemetry, elapsedTime);

            instances.put(clazz, createdInstance);

            return createdInstance;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return null;
    }
}
