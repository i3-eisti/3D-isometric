package org.blackpanther;

import org.blackpanther.math.*;
import org.blackpanther.math.Shape;
import org.blackpanther.render.BoxRender;
import org.blackpanther.render.Renderer;
import org.blackpanther.render.SceneFrame;
import org.blackpanther.render.SphereRender;

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

        BoxRender.DrawMode mode = BoxRender.DrawMode.LINE;

        final float side = 200f;

        Shape shape = new Box();
        Renderer renderer = new BoxRender(
                (Box) shape,
                side,
                mode,
                SceneFrame.DRAWING_AREA
        );

        //processing parameter
        for (String arg : args) {
            if (arg.equals("--fill")) {
                mode = BoxRender.DrawMode.FILL;
            } else if (arg.equals("--line")) {
                mode = BoxRender.DrawMode.LINE;
            } else if (arg.equals("--cube")) {
                shape = new Cube();
                renderer = new BoxRender(
                        (Box) shape,
                        side,
                        mode,
                        SceneFrame.DRAWING_AREA
                );
            } else if (arg.equals("--sphere")) {
                shape = new Sphere();
                renderer = new SphereRender(
                        (Sphere) shape,
                        side,
                        SceneFrame.DRAWING_AREA
                );
            }
        }

        if (renderer instanceof BoxRender)
            logger.info("Draw mode set to " + mode);
        logger.info("Initial shape : " + shape);

        //wrap it into a nice frame
        final Frame frame = new SceneFrame(renderer);

        //first render
        renderer.render();

        //and finally display it
        frame.setVisible(true);
        frame.requestFocus();
    }

}
