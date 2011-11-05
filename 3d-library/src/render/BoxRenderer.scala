package org.blackpanther.three
package render

import swing._
import java.awt.geom.Dimension2D
import java.awt.{BasicStroke, Color}
import org.blackpanther.three.model._
import java.awt.image.BufferedImage

/**
 * @author MACHIZAUD Andréa
 * @version 11/5/11
 */

object BoxRenderer extends Renderer[Box] {
  import Renderer._

  sealed trait DrawMode
  object Fill extends DrawMode
  object Line extends DrawMode

  val drawMode: DrawMode = Fill

  def apply(
    buffer : BufferedImage,
    bufferDimension : Dimension2D,
    pointOfView : FixedReferential,
    shapePosition : FixedReferential,
    model : Box,
    modelColor : Color
  ) {

    @inline def mathToScreen(point : ShapeReferential) : Point =
      math2pixel(
        bufferDimension,
        changeReferential(
          shapePosition,
          applyPointOfView(
            pointOfView, point, model.depth
          )
        )
      )

    val painter : Graphics2D = buffer.getGraphics.asInstanceOf[Graphics2D]

    val mthPoints : Vector[ShapeReferential] =
       for(pt <- model.vertices)
         yield pt.asShapeReferential
    val scrPoints = mthPoints map mathToScreen

    painter.setColor(Color.BLACK)

    {
      val defaultStroke = painter.getStroke

      painter.setStroke(new BasicStroke(1f))

      @inline def drawScreenPoint(tuple : (Int,  Int)) {
        val (s : Point, e : Point) = (scrPoints(tuple._1), scrPoints(tuple._2))
        painter.drawLine(
          s.x, s.y,
          e.x, e.y
        )
      }

      drawMode match {
        case Fill =>
          val perspectivePoints : IndexedSeq[Point3D] =
            mthPoints map  {
              applyPointOfView(pointOfView, _, model.depth / 2f)
            }

          Box.FacesCode foreach {
            (faceCode : Int) =>
              val normal = Box.normalFromFace(perspectivePoints, faceCode)
              if(normal.cz > 0f) {
                Box.egdesCodeFromFace(faceCode) foreach drawScreenPoint
              }
          }
        case Line =>
          Box.EdgesCode foreach drawScreenPoint
      }

      painter.setStroke(defaultStroke)
    }

    {
      import Box._
      @inline def selectColor(verticeCode : Int) : Color = verticeCode match {
        case A => Color.BLUE
        case B => Color.CYAN
        case C => Color.RED
        case D => Color.PINK
        case E => Color.GREEN
        case F => Color.YELLOW
        case G => Color.MAGENTA
        case H => Color.ORANGE
      }
      scrPoints.zipWithIndex.foreach {
        case (point, code) =>
          painter.setColor(selectColor(code))
          painter.drawString(Box.labelFromVertices(code),
            point.x, point.y
          )
      }
    }

    painter.dispose()

  }

}