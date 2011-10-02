package org.blackpanther;

import org.blackpanther.math.Cube;
import org.blackpanther.math.Point3D;
import org.blackpanther.render.CubeFrame;
import org.blackpanther.render.CubeRender;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.LogManager;

/**
 * @author MACHIZAUD Andréa
 * @version 9/29/11
 */
public class Launcher {

    private static final java.util.logging.Logger logger =
            java.util.logging.Logger.getLogger(Launcher.class.getCanonicalName());

    public static void main(String[] args) {

        try {
            InputStream loggerConfig =
                    Launcher.class.getClassLoader().getResourceAsStream(
                            "org/blackpanther/logging/logging.properties"
                    );
            LogManager.getLogManager().readConfiguration(loggerConfig);
            logger.info("Logger configuration loaded");
        } catch (IOException e) {
            logger.severe("Couldn't load logger configuration " + e.getLocalizedMessage());
        }

        CubeRender.DrawMode mode = CubeRender.DrawMode.LINE;

        //cube point data buffer
        final float[] apex = new float[24];
        int index = 0;

        //processing parameter
        for (String arg : args) {
            if (arg.equals("--fill")) {
                mode = CubeRender.DrawMode.FILL;
            } else {
                apex[index++] = Float.parseFloat(arg);
            }
        }

        logger.info("Draw mode set to " + mode);

        //load model
        final Cube cube;
        if (index == 0) { //no data given, assumed a default cube with CBGF visible only
            cube = new Cube();
        } else if (index == 24) { //create a cube (assume given data are correct) by given data
            cube = new Cube(buildApex(apex));
        } else { //not enough data given
            throw new IllegalArgumentException(
                    "Not enough point data received to build a cube, " +
                            "please provide exactly 8 3-dimensional points data"
            );
        }
        logger.info("Initial cube : \n" + cube);

        final int cubeSide = 200;

        //load render engine
        final CubeRender renderer = new CubeRender(
                cube, cubeSide,
                mode,
                CubeFrame.DRAWING_AREA);

        //wrap it into a nice frame
        final Frame frame = new CubeFrame(renderer);

        //first render
        renderer.render();

        //and finally display it
        frame.setVisible(true);
        frame.requestFocus();
    }

    private static Point3D[] buildApex(float[] floatApex) {
        final Point3D[] pointApex = new Point3D[8];
        for (int pointIndex = 0, floatIndex = 0;
             floatIndex < floatApex.length;
             pointIndex++, floatIndex += 3) {
            pointApex[pointIndex] = new Point3D(
                    floatApex[floatIndex],
                    floatApex[floatIndex + 1],
                    floatApex[floatIndex + 2]
            );
        }
        return pointApex;
    }

}
