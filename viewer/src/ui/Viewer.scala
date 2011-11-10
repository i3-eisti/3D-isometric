package org.blackpanther
package ui

import org.blackpanther.three.ui.Canvas
import org.blackpanther.three.model.{Renderer, Scene}
import swing._
import event.{MouseClicked, MouseWheelMoved, MouseMoved}
import timer.Timeline

/**
 * @author MACHIZAUD Andréa
 * @version 11/5/11
 */

class Viewer (
  scene : Scene
) extends Frame {

  title = "Scene Viewer"

  contents = {

    val canvas = new Canvas(scene)

    new Timeline(scene, canvas)

    import TransformHandler._

    val rotationComponents = List(
      new Label("Rotation: "),
      new Button(TransformHandler(canvas, scene,  Abscissa, Minus)),
      new Label("X"),
      new Button(TransformHandler(canvas, scene,  Abscissa, Plus)),
      new Button(TransformHandler(canvas, scene,  Ordinate, Minus)),
      new Label("Y"),
      new Button(TransformHandler(canvas, scene,  Ordinate, Plus)),
      new Button(TransformHandler(canvas, scene,  Height, Minus)),
      new Label("Z"),
      new Button(TransformHandler(canvas, scene,  Height, Plus))
    )

    val lblPOV = new Label("Point of view: %s" format scene.pointOfView)

    val panRotationalButtons = new FlowPanel(FlowPanel.Alignment.Left)(rotationComponents : _*)
    panRotationalButtons.hGap = 10
    panRotationalButtons.vGap = 5

    val panPOV = new FlowPanel(lblPOV)

    val rootContainer = new BorderPanel()
    rootContainer.layout += ((canvas, BorderPanel.Position.Center))
    rootContainer.layout += ((panRotationalButtons, BorderPanel.Position.South))
    rootContainer.layout += ((panPOV, BorderPanel.Position.North))

    val povHandler = new PointOfViewHandler(scene, canvas, lblPOV)
    povHandler.listenTo(canvas.mouse.clicks)
    povHandler.listenTo(canvas.mouse.wheel)

    lblPOV.listenTo(canvas.mouse.moves)
    lblPOV.listenTo(canvas.mouse.clicks)
    lblPOV.listenTo(canvas.mouse.wheel)
    lblPOV.reactions += {
      case _ : MouseMoved | _ : MouseWheelMoved | _ : MouseClicked =>
        lblPOV.text = "Point of view: %s" format scene.pointOfView
    }

    rootContainer
  }

  override val resizable = false

}