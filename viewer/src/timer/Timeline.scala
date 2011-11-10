package timer

import java.util.{TimerTask, Timer}
import org.blackpanther.three.model.Scene
import time.Duration.int2durationConverter
import time.TimeRange
import org.blackpanther.three.ui.Canvas


/**
 * @author MACHIZAUD Andréa
 * @version 11/10/11
 */

class Timeline(
  scene : Scene,
  canvas : Canvas
) extends Timer("Animation Timer") {

  private var tick = 0l
  private val period = 100.ms;

  object Ticker extends TimerTask {
    def run() {
      tick += period.elapsedTime
      scene.updateTime(
        new TimeRange(tick, period)
      )
      scene.render()
      canvas.repaint()
    }
  }

  schedule(Ticker, 0l, period.elapsedTime)

}