package org.blackpanther.ui

import swing.event.{ActionEvent, Event}
import org.blackpanther.three.model.Scene
import org.blackpanther.three.model.Transformations.Rotation
import swing.Action
import org.blackpanther.three.ui.Canvas

/**
 * @author MACHIZAUD Andréa
 * @version 11/5/11
 */

object TransformHandler {

  sealed trait TransformAxe
  object Abscissa extends TransformAxe
  object Ordinate extends TransformAxe
  object Height extends TransformAxe

  sealed trait TransformDirection
  object Plus extends TransformDirection
  object Minus extends TransformDirection

  import scala.math.{cos, sin, Pi}

  val RotationStep : Float = (Pi / 12.0).toFloat

  def apply(canvas : Canvas, scene : Scene, axe : TransformAxe, dir : TransformDirection) : Action =
    new RotationTransformer(canvas, scene, axe, dir, RotationStep)

  private def titleOf(dir : TransformDirection) : String = dir match {
    case Plus => "+"
    case Minus => "-"
  }

  private class RotationTransformer(
    canvas : Canvas,
    scene : Scene,
    axe : TransformAxe,
    dir : TransformDirection,
    angle : Float = (Pi / 12.0).toFloat
  ) extends Action(titleOf(dir)) {

    val m : Array[Array[Float]] = {
        @inline def sign(value : Double) : Float = {
          val output =
            dir match {
              case Plus => value.toFloat
              case Minus => (-1.0 * value).toFloat
            }
          output
        }

        axe match {
          case Abscissa =>
            Array(
              Array(1f, 0f, 0f),
              Array(0f, cos(sign(angle)).toFloat, -sin(sign(angle)).toFloat),
              Array(0f, sin(sign(angle)).toFloat,  cos(sign(angle)).toFloat)
            )
          case Ordinate =>
            Array(
              Array(cos(sign(angle)).toFloat, 0f, sin(sign(angle)).toFloat),
              Array(0f, 1f, 0f),
              Array(-sin(sign(angle)).toFloat, 0f, cos(sign(angle)).toFloat)
            )
          case Height =>
            Array(
              Array(cos(sign(angle)).toFloat, -sin(sign(angle)).toFloat, 0f),
              Array(sin(sign(angle)).toFloat,  cos(sign(angle)).toFloat, 0f),
              Array(0f, 0f, 1f)
            )
        }
    }

    def apply() {
      scene.transformAll(new Rotation(
        m(0)(0),m(0)(1),m(0)(2),
        m(1)(0),m(1)(1),m(1)(2),
        m(2)(0),m(2)(1),m(2)(2)
      ))
      canvas.updateScene()
    }

  }

}