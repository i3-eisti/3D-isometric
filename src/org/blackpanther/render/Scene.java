package org.blackpanther.render;

import javax.swing.*;
import java.awt.*;

/**
 * @author MACHIZAUD Andréa
 * @version 9/29/11
 */
public class Scene extends JPanel {

    private static final java.util.logging.Logger logger =
            java.util.logging.Logger.getLogger(Scene.class.getCanonicalName());

    private final Renderer renderer;

    public Scene(Renderer renderer, Dimension dimension) {
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
