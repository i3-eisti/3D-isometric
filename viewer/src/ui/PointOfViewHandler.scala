package org.blackpanther.ui

import swing.event.{MouseWheelMoved, MouseMoved, Event}
import java.awt.geom.Point2D
import org.blackpanther.three.model.{Point3D, FixedReferential, Renderer, Scene}
import swing.{Publisher, Label, Panel}
import org.blackpanther.three.ui.Canvas

/**
 * @author MACHIZAUD Andréa
 * @version 11/5/11
 */

class PointOfViewHandler(
  scene : Scene,
  canvas : Canvas,
  label : Label
) extends Publisher {

  val Step = 20f

  def isDefinedAt(x: Event) = x match {
    case _ : MouseMoved => true
    case _ : MouseWheelMoved => true
    case otherwise => false
  }

  import PointOfViewHandler._

  reactions += {

    case MouseMoved(_, mouseScreenPoint, _) =>
      updatePointOfView(
        scene,
        canvas,
        label,
        scene.pointOfView map {
          (ox: Float, oy: Float, oz: Float) =>
            //TODO Point of view determination depends also on pov height
            val mouseMathPoint : Point2D = Renderer.pixel2math(scene.dimension, mouseScreenPoint)
            (mouseMathPoint.getX.toFloat, mouseMathPoint.getY.toFloat, oz)
        }
      )

    case MouseWheelMoved(_, mouseScreenPoint, _, rotation) =>
      updatePointOfView(
        scene,
        canvas,
        label,
        scene.pointOfView map  {
          (ox: Float, oy: Float, oz: Float) =>
            (
              ox,
              oy,
              if(rotation > 0)
                oz + Step
              else
                oz - Step
            )
        }
      )
  }
}

object PointOfViewHandler {

  def updatePointOfView(
    scene : Scene,
    canvas : Canvas,
    label : Label,
    pov : Point3D
  ) {
    if( scene.pointOfView != pov) {
      scene.pointOfView = new Point3D(pov.x, pov.y, pov.z) with FixedReferential
      canvas.updateScene()
    }
  }

}