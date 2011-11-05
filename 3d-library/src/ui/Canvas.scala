package org.blackpanther.three
package ui

import swing.{Panel, Graphics2D}

import org.blackpanther.three.model.Scene

/**
 * @author MACHIZAUD Andréa
 * @version 11/5/11
 */

class Canvas(
  scene : Scene
) extends Panel {

  background = java.awt.Color.LIGHT_GRAY
  preferredSize = scene.dimension

  override def paint(g: Graphics2D) {
    g.drawImage(scene.buffer, 0, 0, null)
  }

  def updateScene(callback: => Unit) {
      scene.render()
      repaint()
      callback
  }

  focusable = true
  requestFocus()

}