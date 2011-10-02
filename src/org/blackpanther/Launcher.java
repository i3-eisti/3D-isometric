package org.blackpanther;

import org.blackpanther.math.Cube;
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

        for (String arg : args) {
            if (arg.equals("--fill")) {
                mode = CubeRender.DrawMode.FILL;
            }
        }
        logger.info("Draw mode set to " + mode);

        //load model
        final Cube cube = new Cube();
        final int cubeSide = 50;

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

}
