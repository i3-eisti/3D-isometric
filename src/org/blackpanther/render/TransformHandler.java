package org.blackpanther.render;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Logger;

import org.blackpanther.math.Rotation;
import org.blackpanther.math.Shape;
import org.blackpanther.math.Transformation;

import javax.swing.*;

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

    private final Renderer renderer;
    private final JPanel scene;

    private final double step = Math.PI / 12.0;

    public TransformHandler(
            final Renderer renderer,
            final JPanel scene) {
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
                new Rotation(matrix);

        //create the resulting cube
        final Shape rotatedShape =
                renderer.getShape().transform(transformation);

        logger.finer(String.format(
                "%s rotated to %s",
                renderer.getShape(),
                rotatedShape
        ));

        //pass it back to the renderer
        renderer.setShape(rotatedShape);

        //and render it
        renderer.render();
        scene.repaint();
    }
}
