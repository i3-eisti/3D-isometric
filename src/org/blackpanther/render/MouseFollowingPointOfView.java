package org.blackpanther.render;

import org.blackpanther.math.Point3D;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * @author MACHIZAUD Andréa
 * @version 13/10/11
 */
public final class MouseFollowingPointOfView
        extends AbstractPointOfViewHandler
        implements MouseMotionListener, MouseWheelListener {

    private Point3D selectedCoordinate = null;

    private static final float STEP = 20.0f;

    public MouseFollowingPointOfView(
            final Renderer renderer,
            final JPanel scene,
            final JLabel lblPointOfView) {
        super(renderer, scene, lblPointOfView);
        selectedCoordinate = getPointOfView();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        Point3D screenPoint = getRenderer().pixel2math(
                e.getPoint()
        );
        selectedCoordinate = new Point3D(
                screenPoint.getX(),
                screenPoint.getY(),
                selectedCoordinate.getZ()
        );
        setPointOfView(selectedCoordinate);
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
            final float nextZ;
            if (e.getWheelRotation() > 0) { //up/away the user
                nextZ = selectedCoordinate.getZ() + STEP;
            } else {//down / toward the user
                nextZ = selectedCoordinate.getZ() - STEP;
            }
            selectedCoordinate = new Point3D(
                    selectedCoordinate.getX(),
                    selectedCoordinate.getY(),
                    nextZ
            );
            setPointOfView(selectedCoordinate);
    }
}
