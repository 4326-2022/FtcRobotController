package org.firstinspires.ftc.teamcode.vision;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.CommonVariables;
import org.firstinspires.ftc.teamcode.components.Component;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvWebcam;

public class DuckDetector extends Component {
    private OpenCvWebcam webcam;
    private DuckDetectionPipline pipeline;

    public DuckDetector(CommonVariables commonVariables) {
        super(commonVariables);

    }

    @Override
    public void initialize() {
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        this.webcam = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);
        this.pipeline = new DuckDetectionPipline();

        webcam.setPipeline(pipeline);

        webcam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                webcam.startStreaming(320,240, OpenCvCameraRotation.UPRIGHT);
            }

            @Override
            public void onError(int errorCode) {
                /*
                 * This will be called if the camera could not be opened
                 */
            }
        });
    }


    public void startStream() {
        try {
            webcam.startStreaming(320, 240, OpenCvCameraRotation.UPRIGHT);
        } catch (Exception exception) {
            // Exception closing
        }
    }

    public void stopStream() {
        try {
            webcam.stopStreaming();
        } catch (Exception exception) {
            // Exception closing
        }
    }

    @Override
    public void stop() {
        this.stopStream();
    }

    public DuckDetectionPipline.DuckPosition getPosition() {
        return this.pipeline.getAnalysis();
    }

    @Override
    public void update() {

    }
}
