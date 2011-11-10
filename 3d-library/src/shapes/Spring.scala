package org.blackpanther.three
package shapes

import model.Transformations.Translation
import model.{Transformation, Shape, Point3D, Vector3D}
import time.TimeRange

/**
 * @author MACHIZAUD Andréa
 * @version 11/10/11
 */

class Spring(
  speed : Vector3D = Vector3D(0f, 0f, 0f),
  edge : Point3D = Point3D(100f, 30f, -20f),
  anchor : Point3D = Point3D(0f, 0f, 0f)
) extends Shape {

  val boxSpring = Spring.BoxSpring

  val translation = new Translation(
    edge.x,
    edge.y,
    edge.z
  )

  import Spring._
  private val acceleration = {
    val b = edge
    val a = anchor
    val ratio = K / M
    Vector3D(
      ratio * (a.x - b.x),
      ratio * (a.y - b.y),
      ratio * (a.z - b.z)
    )
  }

  def update(time: TimeRange) = {
    val nextSpeed : Vector3D = speed + acceleration
    val nextEdge : Point3D = edge translate nextSpeed

//    println("[Spring](tick: %d) move from %s to %s (speed: %s)(acceleration: %s)" format (
//      time.tick,
//      edge,
//      nextEdge,
//      speed,
//      acceleration
//    ))
    new Spring(
      nextSpeed,
      nextEdge
    )
  }

  def transform(trans: Transformation) =
    sys.error("Not yet implemented")

}

object Spring {

  private[Spring] val BoxSpring = Box(15f,25f,15f)
  private[Spring] val K = 1f
  private[Spring] val M = 10f

  println("K / M: " + (K / M))



}