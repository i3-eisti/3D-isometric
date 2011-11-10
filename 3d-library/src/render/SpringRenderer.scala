package org.blackpanther.three
package render

import shapes.Spring
import java.awt.image.BufferedImage
import java.awt.{Dimension, Color, Graphics2D}
import model.{Vector3D, FixedReferential, Renderer}

/**
 * @author MACHIZAUD Andréa
 * @version 11/10/11
 */

object SpringRenderer extends Renderer[Spring] {

  val MaxPoints = 200

  private var trace: Array[FixedReferential] = new Array[FixedReferential](MaxPoints)
  private var index: Int = 0
  private var length: Int = 0

  def apply(
             buffer: BufferedImage,
             pointOfView: FixedReferential,
             shapePosition: FixedReferential,
             model: Spring,
             modelColor: Color) {

    val localPosition =
      model.translation(shapePosition).asFixedReferential

    BoxRenderer(
      buffer,
      pointOfView,
      localPosition,
      model.boxSpring,
      modelColor
    )

    for {
      i <- 0 until length
    } {
      val pt = trace(i)
      trace(i) = scroll(pt)
    }

    trace(index) = localPosition
    if (length < MaxPoints)
      length += 1

    val dim = new Dimension(buffer.getWidth, buffer.getHeight)
    val _painter = buffer.createGraphics().asInstanceOf[Graphics2D]
    _painter.setColor(Color.BLUE)
    for {
      i <- 0 until length
    } {
      val mthPos = trace(i)
      val scrPos = Renderer.math2pixel(dim, mthPos)
      _painter.drawOval(
        scrPos.x, scrPos.y,
        10, 10
      )
    }
    index = (index + 1) % MaxPoints
    _painter.dispose()

  }

  private def scroll(pt: FixedReferential) =
    (pt translate Vector3D(0f, 10f, 0f)).asFixedReferential
}