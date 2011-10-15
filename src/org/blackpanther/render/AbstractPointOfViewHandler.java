package org.blackpanther.render;

import org.blackpanther.math.Point3D;
import org.blackpanther.math.Shape;

import java.awt.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

/**
 * @author MACHIZAUD Andréa
 * @version 10/10/11
 */
public abstract class AbstractPointOfViewHandler {

    public static final Logger logger =
            Logger.getLogger(AbstractPointOfViewHandler.class.getCanonicalName());

    public static final ExecutorService executor = Executors.newFixedThreadPool(1);

    private final Renderer renderer;
    private final Canvas scene;

    public AbstractPointOfViewHandler(
            final Renderer renderer,
            final Canvas scene
    ) {
        this.renderer = renderer;
        this.scene = scene;
    }

    protected final Renderer getRenderer() {
        return renderer;
    }

    protected final void setPointOfView(final Point3D pov) {
        if (!collideWithShape(pov, renderer.getShape())) {
            renderer.setPointOfView(pov);
            logger.finer("New Point of view : " + getPointOfView());

            executor.submit(new Runnable() {
                @Override
                public void run() {
                    renderer.render();
                    scene.repaint();
                }
            });
        } else {
            logger.finer("No change of point of view because of collision");
        }
    }

    protected final Point3D getPointOfView() {
        return renderer.getPointOfView();
    }

    private boolean collideWithShape(Point3D nextPointOfView, Shape shape) {
        final Point3D spherePoint = renderer.shapeReferential(nextPointOfView);
        final Point3D normalizedSpherePoint = renderer.normalized(spherePoint);
        return shape.contains(normalizedSpherePoint);
    }
}