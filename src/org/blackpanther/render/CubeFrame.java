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

        //rotation commands
        final Button[] bRotationAbscissa = new Button[]{
                new Button("-"), new Button("+")
        };
        final Button[] bRotationOrdinate = new Button[]{
                new Button("-"), new Button("+")
        };
        final Button[] bRotationHeight = new Button[]{
                new Button("-"), new Button("+")
        };

        final Label lblRotation = new Label("Rotation : ");
        final Label lblRotationAbscissa = new Label("    X ");
        final Label lblRotationOrdinate = new Label("    Y ");
        final Label lblRotationHeight =   new Label("    Z ");

        //point of view commands
        final Button[] bPointOfViewAbscissa = new Button[]{
                new Button("-"), new Button("+")
        };
        final Button[] bPointOfViewOrdinate = new Button[]{
                new Button("-"), new Button("+")
        };
        final Button[] bPointOfViewHeight = new Button[]{
                new Button("-"), new Button("+")
        };

        final Label lblPointOfView = new Label("PointOfView : ");
        final Label lblPointOfViewAbscissa = new Label("    X ");
        final Label lblPointOfViewOrdinate = new Label("    Y ");
        final Label lblPointOfViewHeight =   new Label("    Z ");

        final ActionListener rotationHandler = new TransformHandler(renderer, scene);
        final ActionListener povHandler = new PointOfViewHandler(renderer, scene);

        bRotationAbscissa[0].setActionCommand(TransformHandler.X_MINUS);
        bRotationAbscissa[1].setActionCommand(TransformHandler.X_PLUS);
        bRotationOrdinate[0].setActionCommand(TransformHandler.Y_MINUS);
        bRotationOrdinate[1].setActionCommand(TransformHandler.Y_PLUS);
        bRotationHeight[0].setActionCommand(TransformHandler.Z_MINUS);
        bRotationHeight[1].setActionCommand(TransformHandler.Z_PLUS);

        bRotationAbscissa[0].addActionListener(rotationHandler);
        bRotationAbscissa[1].addActionListener(rotationHandler);
        bRotationOrdinate[0].addActionListener(rotationHandler);
        bRotationOrdinate[1].addActionListener(rotationHandler);
        bRotationHeight[0].addActionListener(rotationHandler);
        bRotationHeight[1].addActionListener(rotationHandler);

        bPointOfViewAbscissa[0].setActionCommand(PointOfViewHandler.X_MINUS);
        bPointOfViewAbscissa[1].setActionCommand(PointOfViewHandler.X_PLUS);
        bPointOfViewOrdinate[0].setActionCommand(PointOfViewHandler.Y_MINUS);
        bPointOfViewOrdinate[1].setActionCommand(PointOfViewHandler.Y_PLUS);
        bPointOfViewHeight[0].setActionCommand(PointOfViewHandler.Z_MINUS);
        bPointOfViewHeight[1].setActionCommand(PointOfViewHandler.Z_PLUS);

        bPointOfViewAbscissa[0].addActionListener(povHandler);
        bPointOfViewAbscissa[1].addActionListener(povHandler);
        bPointOfViewOrdinate[0].addActionListener(povHandler);
        bPointOfViewOrdinate[1].addActionListener(povHandler);
        bPointOfViewHeight[0].addActionListener(povHandler);
        bPointOfViewHeight[1].addActionListener(povHandler);

        final Panel panRotationButtons = new Panel();
        final LayoutManager squeezedFlow = new FlowLayout(
                FlowLayout.LEFT,
                10, 5
        );

        panRotationButtons.setLayout(squeezedFlow);

        panRotationButtons.add(lblRotation);
        panRotationButtons.add(bRotationAbscissa[0]);
        panRotationButtons.add(lblRotationAbscissa);
        panRotationButtons.add(bRotationAbscissa[1]);
        panRotationButtons.add(bRotationOrdinate[0]);
        panRotationButtons.add(lblRotationOrdinate);
        panRotationButtons.add(bRotationOrdinate[1]);
        panRotationButtons.add(bRotationHeight[0]);
        panRotationButtons.add(lblRotationHeight);
        panRotationButtons.add(bRotationHeight[1]);

        final Panel panPointOfViewButtons = new Panel();

        panPointOfViewButtons.setLayout(squeezedFlow);

        panPointOfViewButtons.add(lblPointOfView);
        panPointOfViewButtons.add(bPointOfViewAbscissa[0]);
        panPointOfViewButtons.add(lblPointOfViewAbscissa);
        panPointOfViewButtons.add(bPointOfViewAbscissa[1]);
        panPointOfViewButtons.add(bPointOfViewOrdinate[0]);
        panPointOfViewButtons.add(lblPointOfViewOrdinate);
        panPointOfViewButtons.add(bPointOfViewOrdinate[1]);
        panPointOfViewButtons.add(bPointOfViewHeight[0]);
        panPointOfViewButtons.add(lblPointOfViewHeight);
        panPointOfViewButtons.add(bPointOfViewHeight[1]);

        //content & size
        setLayout(new BorderLayout());
        add(scene, BorderLayout.CENTER);
        add(panRotationButtons, BorderLayout.SOUTH);
        add(panPointOfViewButtons, BorderLayout.NORTH);
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
