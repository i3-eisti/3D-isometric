package org.blackpanther.render;

import org.blackpanther.math.Point3D;
import org.blackpanther.math.Shape;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Logger;

/**
 * @author MACHIZAUD Andréa
 * @version 02/10/11
 */
public class PointOfViewHandler implements ActionListener {

    public static final Logger logger =
            Logger.getLogger(PointOfViewHandler.class.getCanonicalName());

    public static final String X_PLUS = "x-plus";
    public static final String X_MINUS = "x-minus";
    public static final String Y_PLUS = "y-plus";
    public static final String Y_MINUS = "y-minus";
    public static final String Z_PLUS = "z-plus";
    public static final String Z_MINUS = "z-minus";

    private final Renderer renderer;
    private final Canvas scene;

    private static final float step = 20.0f;

    public PointOfViewHandler(
            final Renderer renderer,
            final Canvas scene
    ) {
        this.renderer = renderer;
        this.scene = scene;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        final Point3D currentPointOfView = renderer.getPointOfView();
        Point3D nextPointOfView = currentPointOfView;

        logger.fine("Action : " + e.getActionCommand());
        if (e.getActionCommand().equals(X_PLUS)) {
            nextPointOfView = new Point3D(
                    currentPointOfView.getX() + step,
                    currentPointOfView.getY(),
                    currentPointOfView.getZ()
            );
        } else if (e.getActionCommand().equals(X_MINUS)) {
            nextPointOfView = new Point3D(
                    currentPointOfView.getX() - step,
                    currentPointOfView.getY(),
                    currentPointOfView.getZ()
            );
        } else if (e.getActionCommand().equals(Y_PLUS)) {
            nextPointOfView = new Point3D(
                    currentPointOfView.getX(),
                    currentPointOfView.getY() + step,
                    currentPointOfView.getZ()
            );
        } else if (e.getActionCommand().equals(Y_MINUS)) {
            nextPointOfView = new Point3D(
                    currentPointOfView.getX(),
                    currentPointOfView.getY() - step,
                    currentPointOfView.getZ()
            );
        } else if (e.getActionCommand().equals(Z_PLUS)) {
            if (nextPointOfView.getZ() < AbstractRenderer.MAXIMUM_POINT_OF_VIEW_HEIGHT)
                nextPointOfView = new Point3D(
                        currentPointOfView.getX(),
                        currentPointOfView.getY(),
                        currentPointOfView.getZ() + step
                );
        } else if (e.getActionCommand().equals(Z_MINUS)) {
            if (currentPointOfView.getZ() > 0f)
                nextPointOfView = new Point3D(
                        currentPointOfView.getX(),
                        currentPointOfView.getY(),
                        currentPointOfView.getZ() - step
                );
        }

        if (!collideWithShape(nextPointOfView, renderer.getShape())) {
            renderer.setPointOfView(nextPointOfView);
            logger.info("New Point of view : " + renderer.getPointOfView());
        } else {
            logger.info("No change of point of view because of collision");
        }

        renderer.render();
        scene.repaint();
    }

    private boolean collideWithShape(Point3D nextPointOfView, Shape shape) {
        final Point3D spherePoint = renderer.shapeReferential(nextPointOfView);
        final Point3D normalizedSpherePoint = renderer.normalized(spherePoint);
        return shape.contains(normalizedSpherePoint);
    }
}