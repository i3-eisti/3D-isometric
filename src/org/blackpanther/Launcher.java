package org.blackpanther;

import org.blackpanther.model.Cube;
import org.blackpanther.render.CubeRender;
import org.blackpanther.render.CubeScene;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * @author MACHIZAUD Andréa
 * @version 9/29/11
 */
public class Launcher {

    private static final java.util.logging.Logger logger =
            java.util.logging.Logger.getLogger(Launcher.class.getCanonicalName());

    public static void main(String[] args) {

        CubeRender.DrawMode mode = CubeRender.DrawMode.LINE;

        if( args.length > 0 && args[0].equals("--fill") ) {
            mode = CubeRender.DrawMode.FILL;
        }

        logger.info("Draw mode changed to " + mode);

        final Frame frame = new Frame("Cube manipulation");
        final Dimension drawingAreaDimension = new Dimension(500, 500);
        final Cube cube = new Cube();
        final CubeRender renderer = new CubeRender(
                cube, 40,
                drawingAreaDimension);
        final CubeScene scene = new CubeScene(renderer, drawingAreaDimension);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                frame.dispose();
            }
        });

        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    frame.dispose();
                }
            }
        });

        frame.add(scene);
        frame.pack();

        frame.setLocationRelativeTo(null);
        renderer.render();

        frame.setVisible(true);

    }

}
