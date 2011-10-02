package org.blackpanther.render;

import org.blackpanther.math.Cube;
import org.blackpanther.math.Transformation;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Logger;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

/**
 * @author MACHIZAUD Andréa
 * @version 01/10/11
 */
public class TransformHandler implements ActionListener {

    public static final Logger logger =
            Logger.getLogger(TransformHandler.class.getCanonicalName());

    public static final String X_PLUS = "x-plus";
    public static final String X_MINUS = "x-minus";
    public static final String Y_PLUS = "y-plus";
    public static final String Y_MINUS = "y-minus";
    public static final String Z_PLUS = "z-plus";
    public static final String Z_MINUS = "z-minus";

    private final CubeRender renderer;
    private final Canvas scene;

    private final double step = Math.PI / 4.0;

    public TransformHandler(
            final CubeRender renderer,
            final Canvas scene
    ) {
        this.renderer = renderer;
        this.scene = scene;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //build a transformation matrix
        float[][] matrix = {
                {1f, 0f, 0f},
                {0f, 1f, 0f},
                {0f, 0f, 1f},
        };

        logger.fine("Action : " + e.getActionCommand());
        if (e.getActionCommand().equals(X_PLUS)) {
            //x-rotation
            matrix = new float[][]{
                    {1f, 0f, 0f},
                    {0f, (float) cos(step), (float) -sin(step)},
                    {0f, (float) sin(step), (float) cos(step)},
            };
        } else if (e.getActionCommand().equals(X_MINUS)) {
            //x-rotation
            matrix = new float[][]{
                    {1f, 0f, 0f},
                    {0f, (float) cos(-step), (float) -sin(-step)},
                    {0f, (float) sin(-step), (float) cos(-step)},
            };
        } else if (e.getActionCommand().equals(Y_PLUS)) {
            //y-rotation
            matrix = new float[][]{
                    {(float) cos(step), 0f, (float) sin(step)},
                    {0f, 1f, 0f},
                    {(float) -sin(step), 0f, (float) cos(step)},
            };
        } else if (e.getActionCommand().equals(Y_MINUS)) {
            //y-rotation
            matrix = new float[][]{
                    {(float) cos(-step), 0f, (float) sin(-step)},
                    {0f, 1f, 0f},
                    {(float) -sin(-step), 0f, (float) cos(-step)},
            };
        } else if (e.getActionCommand().equals(Z_PLUS)) {
            //z-rotation
            matrix = new float[][]{
                    {(float) cos(step), (float) -sin(step), 0f},
                    {(float) sin(step), (float) cos(step), 0f},
                    {0f, 0f, 1f},
            };
        } else if (e.getActionCommand().equals(Z_MINUS)) {
            //z-rotation
            matrix = new float[][]{
                    {(float) cos(-step), (float) -sin(-step), 0f},
                    {(float) sin(-step), (float) cos(-step), 0f},
                    {0f, 0f, 1f},
            };
        }
        final Transformation transformation =
                new Transformation(matrix);

        //create the resulting cube
        final Cube rotatedCube =
                renderer.getCube().transform(transformation);

        logger.finer(String.format(
                "%s rotated to %s",
                renderer.getCube(),
                rotatedCube
        ));

        //pass it back to the renderer
        renderer.setCube(rotatedCube);

        //and render it
        renderer.render();
        scene.repaint();
    }
}
