package gui

import tornadofx.View
import tornadofx.circle
import tornadofx.paddingAll
import tornadofx.vbox

class MapView : View() {

    override val root = vbox {
        paddingAll = 10.0
        spacing = 10.0


        circle {
            centerX = 100.0
            centerY = 100.0
            radius = 50.0
        }
    }
}
