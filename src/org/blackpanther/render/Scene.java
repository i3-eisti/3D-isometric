package org.blackpanther.render;

import java.awt.*;

/**
 * @author MACHIZAUD Andréa
 * @version 9/29/11
 */
public class Scene extends Canvas {
    private static final java.util.logging.Logger logger =
            java.util.logging.Logger.getLogger(Scene.class.getCanonicalName());

    private final Renderer renderer;

    public Scene(Renderer renderer, Dimension dimension) {
        setPreferredSize(dimension);
        this.renderer = renderer;

        final MouseFollowingPointOfView povMouseHandler =
                new MouseFollowingPointOfView(renderer,this);

        addMouseMotionListener(povMouseHandler);
        addMouseWheelListener(povMouseHandler);
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(
                renderer.getBuffer(),
                0,
                0,
                null
        );
    }
}
