package org.blackpanther.render;

import java.awt.*;

/**
 * @author MACHIZAUD Andréa
 * @version 9/29/11
 */
public class CubeScene extends Canvas {
    private static final java.util.logging.Logger logger =
            java.util.logging.Logger.getLogger(CubeScene.class.getCanonicalName());

    private final CubeRender renderer;

    public CubeScene(CubeRender renderer, Dimension dimension) {
        setPreferredSize(dimension);
        this.renderer = renderer;
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
