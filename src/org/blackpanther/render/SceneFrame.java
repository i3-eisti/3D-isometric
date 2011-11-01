package org.blackpanther.render;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * @author MACHIZAUD Andréa
 * @version 01/10/11
 */
public final class SceneFrame extends JFrame {

    public static final Dimension DRAWING_AREA = new Dimension(500, 500);

    public SceneFrame(
            final Renderer renderer
    ) {
        super("Cube manipulation");

        final JPanel scene = new Scene(renderer, DRAWING_AREA);

        //rotation commands
        final JButton[] bRotationAbscissa = new JButton[]{
                new JButton("-"), new JButton("+")
        };
        final JButton[] bRotationOrdinate = new JButton[]{
                new JButton("-"), new JButton("+")
        };
        final JButton[] bRotationHeight = new JButton[]{
                new JButton("-"), new JButton("+")
        };

        final JLabel lblRotation = new JLabel("Rotation : ");
        final JLabel lblRotationAbscissa = new JLabel("    X ");
        final JLabel lblRotationOrdinate = new JLabel("    Y ");
        final JLabel lblRotationHeight = new JLabel("    Z ");

        final JLabel lblPointOfView = new JLabel("PointOfView : ");

        final ActionListener rotationHandler = new TransformHandler(renderer, scene);
        final ActionListener povHandler = new IncrementalPointOfView(renderer, scene, lblPointOfView);

        final MouseFollowingPointOfView povMouseHandler =
                new MouseFollowingPointOfView(renderer, scene, lblPointOfView);

        scene.addMouseMotionListener(povMouseHandler);
        scene.addMouseWheelListener(povMouseHandler);

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

        final JPanel panRotationJButtons = new JPanel();
        final LayoutManager squeezedFlow = new FlowLayout(
                FlowLayout.LEFT,
                10, 5
        );

        panRotationJButtons.setLayout(squeezedFlow);

        panRotationJButtons.add(lblRotation);
        panRotationJButtons.add(bRotationAbscissa[0]);
        panRotationJButtons.add(lblRotationAbscissa);
        panRotationJButtons.add(bRotationAbscissa[1]);
        panRotationJButtons.add(bRotationOrdinate[0]);
        panRotationJButtons.add(lblRotationOrdinate);
        panRotationJButtons.add(bRotationOrdinate[1]);
        panRotationJButtons.add(bRotationHeight[0]);
        panRotationJButtons.add(lblRotationHeight);
        panRotationJButtons.add(bRotationHeight[1]);

        final JPanel panPointOfView = new JPanel();

        panPointOfView.add(lblPointOfView);

        //content & size
        setLayout(new BorderLayout());
        add(scene, BorderLayout.CENTER);
        add(panRotationJButtons, BorderLayout.SOUTH);
        add(panPointOfView, BorderLayout.NORTH);
        pack();
        setResizable(false);

        //location
        setLocationRelativeTo(null);

        //close events
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                AbstractPointOfViewHandler.executor.shutdownNow();
                dispose();
            }
        });
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    AbstractPointOfViewHandler.executor.shutdownNow();
                    dispose();
                }
            }
        });
    }
}
