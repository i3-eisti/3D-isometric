package org.blackpanther.render;

import org.blackpanther.math.Point3D;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Logger;

/**
 * @author MACHIZAUD Andréa
 * @version 02/10/11
 */
public class IncrementalPointOfView
        extends AbstractPointOfViewHandler
        implements ActionListener {

    public static final Logger logger =
            Logger.getLogger(IncrementalPointOfView.class.getCanonicalName());

    public static final String X_PLUS = "x-plus";
    public static final String X_MINUS = "x-minus";
    public static final String Y_PLUS = "y-plus";
    public static final String Y_MINUS = "y-minus";
    public static final String Z_PLUS = "z-plus";
    public static final String Z_MINUS = "z-minus";

    private static final float STEP = 20.0f;

    public IncrementalPointOfView(
            final Renderer renderer,
            final Canvas scene
    ) {
        super(renderer, scene);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        final Point3D currentPointOfView = getPointOfView();
        Point3D nextPointOfView = currentPointOfView;

        logger.fine("Action : " + e.getActionCommand());
        if (e.getActionCommand().equals(X_PLUS)) {
            nextPointOfView = new Point3D(
                    currentPointOfView.getX() + STEP,
                    currentPointOfView.getY(),
                    currentPointOfView.getZ()
            );
        } else if (e.getActionCommand().equals(X_MINUS)) {
            nextPointOfView = new Point3D(
                    currentPointOfView.getX() - STEP,
                    currentPointOfView.getY(),
                    currentPointOfView.getZ()
            );
        } else if (e.getActionCommand().equals(Y_PLUS)) {
            nextPointOfView = new Point3D(
                    currentPointOfView.getX(),
                    currentPointOfView.getY() + STEP,
                    currentPointOfView.getZ()
            );
        } else if (e.getActionCommand().equals(Y_MINUS)) {
            nextPointOfView = new Point3D(
                    currentPointOfView.getX(),
                    currentPointOfView.getY() - STEP,
                    currentPointOfView.getZ()
            );
        } else if (e.getActionCommand().equals(Z_PLUS)) {
            if (nextPointOfView.getZ() < AbstractRenderer.MAXIMUM_POINT_OF_VIEW_HEIGHT)
                nextPointOfView = new Point3D(
                        currentPointOfView.getX(),
                        currentPointOfView.getY(),
                        currentPointOfView.getZ() + STEP
                );
        } else if (e.getActionCommand().equals(Z_MINUS)) {
            if (currentPointOfView.getZ() > 0f)
                nextPointOfView = new Point3D(
                        currentPointOfView.getX(),
                        currentPointOfView.getY(),
                        currentPointOfView.getZ() - STEP
                );
        }

        setPointOfView(nextPointOfView);
    }
}
