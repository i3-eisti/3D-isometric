package org.blackpanther.render;

import java.awt.*;
import java.awt.event.*;

/**
 * @author MACHIZAUD Andréa
 * @version 01/10/11
 */
public final class CubeFrame extends Frame {

    public static final Dimension DRAWING_AREA = new Dimension(500, 500);

    public CubeFrame(
            final CubeRender renderer
    ) throws HeadlessException {
        super("Cube manipulation");

        final Canvas scene = new CubeScene(renderer, DRAWING_AREA);

        final Button[] bAbscissa = new Button[]{
                new Button("-"), new Button("+")
        };
        final Button[] bOrdinate = new Button[]{
                new Button("-"), new Button("+")
        };
        final Button[] bHeight = new Button[]{
                new Button("-"), new Button("+")
        };

        final Label lblAbscissa = new Label("   X rotation :");
        final Label lblOrdinate = new Label("   Y rotation :");
        final Label lblHeight = new Label("   Z rotation :");

        final ActionListener handler = new TransformHandler(renderer, scene);

        bAbscissa[0].setActionCommand(TransformHandler.X_MINUS);
        bAbscissa[1].setActionCommand(TransformHandler.X_PLUS);
        bOrdinate[0].setActionCommand(TransformHandler.Y_MINUS);
        bOrdinate[1].setActionCommand(TransformHandler.Y_PLUS);
        bHeight[0].setActionCommand(TransformHandler.Z_MINUS);
        bHeight[1].setActionCommand(TransformHandler.Z_PLUS);

        bAbscissa[0].addActionListener(handler);
        bAbscissa[1].addActionListener(handler);
        bOrdinate[0].addActionListener(handler);
        bOrdinate[1].addActionListener(handler);
        bHeight[0].addActionListener(handler);
        bHeight[1].addActionListener(handler);

        final Panel panButtons = new Panel();

        panButtons.add(lblAbscissa);
        panButtons.add(bAbscissa[0]);
        panButtons.add(bAbscissa[1]);
        panButtons.add(lblOrdinate);
        panButtons.add(bOrdinate[0]);
        panButtons.add(bOrdinate[1]);
        panButtons.add(lblHeight);
        panButtons.add(bHeight[0]);
        panButtons.add(bHeight[1]);

        //content & size
        setLayout(new BorderLayout());
        add(scene, BorderLayout.CENTER);
        add(panButtons, BorderLayout.SOUTH);
        pack();
        setResizable(false);

        //location
        setLocationRelativeTo(null);

        //close events
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    dispose();
                }
            }
        });
    }
}
