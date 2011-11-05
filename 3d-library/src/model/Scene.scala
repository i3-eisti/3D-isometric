package org.blackpanther.three
package model

import java.awt.image.BufferedImage

import scala.swing.Graphics2D
import scala.swing.Dimension
import java.awt.Color

import Renderer._

/**
 * @author MACHIZAUD Andréa
 * @version 11/3/11
 */

class Scene(
  val dimension : Dimension
) {
  import Scene._

  private var models : Map[Shape,(FixedReferential,Color)] = Map.empty[Shape,(FixedReferential, Color)]

  val width = dimension.width
  val height = dimension.height

  val buffer : BufferedImage = new BufferedImage(
    width, height, BufferedImage.TYPE_INT_ARGB
  )

  def transformAll(trans : Transformation) {
    models =
      models map {
        case (shape : Shape, (position : FixedReferential, color : Color)) =>
          (shape.transform(trans), (position, color))
      }
  }

//  var pointOfView : FixedReferential = new Point3D(
//    width / 2f,
//    height / 2f,
//    MaximumPointOfView / 2f
//  ) with FixedReferential
  var pointOfView : FixedReferential = new Point3D(
    338f, 356f, 80f
  ) with FixedReferential

  def +=(kv: (Shape, (FixedReferential, Color))) = {
    models += ((kv._1, kv._2))
    this
  }

  def -=(key: Shape) = {
    models -= key
    this
  }

  def render() {
    val _buffer =
      new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB)

    val painter = _buffer.getGraphics.asInstanceOf[Graphics2D]

    painter.setColor(Color.WHITE)
    painter.fillRect(0, 0, width, height)

    painter.setColor(Color.BLACK)

    painter.drawLine(
      Padding, height - Padding,
      width - 2 * Padding, height - Padding
    )
    painter.drawString("Abscissa",
      5 + width - 2 * Padding,
      height - Padding
    )

    painter.drawLine(
      Padding, height - Padding,
      Padding, Padding
    )
    painter.drawString("Ordinate",
      Padding, Padding
    )

    painter.drawOval(
      (Padding * .75f).toInt,
      height - (Padding * 1.25f).toInt,
      Padding / 2,
      Padding / 2
    )
    painter.drawString("Height",
      (Padding * 1.25f), height - (Padding * .5f)
    )
    painter.dispose()

    models.foreach{
      case (shape : Shape, (position : FixedReferential, color : Color)) =>
        Renderer.forShape(shape)(
          _buffer,
          dimension,
          pointOfView,
          position,
          shape,
          color
        )
    }

    val _painter = buffer.createGraphics()
    _painter.drawImage(
      _buffer, 0, 0, width, height, null
    )
    _painter.dispose()

  }

}

object Scene {
  val Padding = 40
}