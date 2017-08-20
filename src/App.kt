import org.w3c.dom.CanvasRenderingContext2D
import org.w3c.dom.Element
import org.w3c.dom.events.KeyboardEvent
import org.w3c.dom.events.MouseEvent
import kotlin.browser.document
import kotlin.browser.window
import kotlin.js.Date

fun main(args: Array<String>) {
    println("hello world")
    window.onload = { onLoad() }
}

fun onLoad() {
    val canvas = document.getElementById("mainCanvas")!!
    println(canvas.className)
    MainView(canvas).init()
}

class MainView(val canvas: Element) {
    val ctx = canvas.asDynamic().getContext("2d") as CanvasRenderingContext2D
    val iterationsCount = 64
    val colors = arrayOf(color11, color1, color2, color6, color8, color9, color10)
    var colorPalete = 0
    var zoom = 0
    var xOffset = 0
    var yOffset = 0
    var width: Int = 0
    var height: Int = 0

    fun init() {
        window.onresize = { onResize() }
        window.onkeypress = { onKeyPress(it as KeyboardEvent) }
        window.onmousedown = { onMouse(it as MouseEvent, true) }
        window.onmouseup = { onMouse(it as MouseEvent, false) }
        onResize()
        xOffset = (-width * 1.5).toInt()
        yOffset = -height * 2
        zoom = 2400
//        xOffset = -width / 2
//        yOffset = -height / 2
//        zoom = 300
//        xOffset = -6537
//        yOffset = -2303
//        zoom = 8700
//    zoom: 8700 xOffset: -6537 yOffset: -2303
//    zoom: 13700 xOffset: -10202 yOffset: -4136
//    zoom: 4100 xOffset: -2806 yOffset: -2586
//    zoom: 3800 xOffset: -482 yOffset: -2830
//    zoom: 7400 xOffset: -6921 yOffset: -2390
//        zoom: 28100 xOffset: -17145 yOffset: -18709 palete10
//        zoom: 40100 xOffset: -23263 yOffset: -26188
        zoom = 4100
        xOffset = -2806
        yOffset = -2586
        draw()
    }

    var mouseDownX = 0
    var mouseDownY = 0

    private fun onMouse(event: MouseEvent, down: Boolean): Unit {
        if (down) {
            mouseDownX = event.screenX
            mouseDownY = event.screenY
        } else {
            xOffset -= event.screenX - mouseDownX
            yOffset -= event.screenY - mouseDownY
            draw()
        }
    }

    fun onKeyPress(event: KeyboardEvent): Unit {
        when (event.code) {
            "KeyQ" -> {   //q
                changeZoom(1000)
            }
            "KeyA" -> {   //w
                changeZoom(-1000)
            }
            "KeyW" -> {   //q
                changeZoom(100)
            }
            "KeyS" -> {   //w
                changeZoom(-100)
            }
            "KeyC" -> {   //w
                colorPalete = (colorPalete + 1) % colors.size
            }
        }
        draw()
    }

//    (0, 0) - (500, 500)
//    (x + xoff, y + yoff) / zoom -> a, b
//    x1 = a * zoom - xoff
//    x2 = a * zoom - xoff
//    a * zoom1 - xOff1 = a  * zoom2 - xOff2
//    xOff2 = a * zoom2 - a * zoom1 + xOff1 = a (zoom2 - zoom1) + xOff1
//    a = (250 + xOff1) / zoom1
//      xOff2 = (width / 2 + xOff1) * (zoom2 - zoom1) / zoom1 + xOff1

//      a1 = a2
//    (x + xoff1) / zoom1 = (x + xoff2) / zoom2
//      xoff2 = (x + xoff1) / zoom1 * zoom2 - x

//    a = 0 zoom = 1 x = 250 (250 + xOff) / 1 = 0 -> xOff = -250
//    a = 0 zoom = 1000 xOff = -250 x = 250 (250 - 250) / 1 * 1000 - 250 -> -250
    //a = 0.01 zoom = 10 x = 250

//    x / zoom + xOff -> x = 250, zoom = 1, xOff = -250;
//    x / zoom + xOff -> x = 250, zoom = 100, xOff = -250;
    fun changeZoom(delta: Int): Unit {
        val oldZoom = zoom
        zoom += delta
//      xOff2 = (width / 2 + xOff1) * (zoom2 - zoom1) / zoom1 + xOff1
        xOffset = (width  / 2 + xOffset) * (zoom - oldZoom) / oldZoom + xOffset
        yOffset = (height / 2 + xOffset) * (zoom - oldZoom) / oldZoom + yOffset
    }

    fun onResize() {
//    canvas.setAttribute("width", "500")
//    canvas.setAttribute("height", "500")
        canvas.setAttribute("width", window.innerWidth.toString())
        canvas.setAttribute("height", window.innerHeight.toString())
        width = canvas.clientWidth
        height = canvas.clientHeight
        draw()
    }


    fun draw() {
        val start = Date().getTime()
        ctx.clearRect(0.0, 0.0, width.toDouble(), height.toDouble())
        ctx.beginPath()
        for (y in 0..height - 1) {
            for (x in 0..width - 1) {
                val m = mandelbrot(x, y, zoom, xOffset, yOffset)
                ctx.fillStyle = getColor(m)
                ctx.fillRect(x.toDouble(), y.toDouble(), 1.0, 1.0)
            }
        }
        ctx.stroke()
        val elapsed = Date().getTime() - start
        println("draw elapsed: $elapsed zoom: $zoom xOffset: $xOffset yOffset: $yOffset")
    }

    fun getColor(m: Double): String {
        val index = (m * (colors[colorPalete].size - 1)).toInt()
        val g = colors[colorPalete][index]
        return "#${g.toHex()}"
    }

    fun mandelbrot(x: Int, y: Int, zoom: Int, xOffset: Int, yOffset: Int): Double {
        val a = (x.toDouble() + xOffset) / zoom
        val b = (y.toDouble() + yOffset) / zoom
        var curA = 0.0
        var curB = 0.0
        repeat(iterationsCount) {
            val nextA = curA * curA - curB * curB + a
            val nextB = curA * curB + curA * curB + b
            curA = nextA
            curB = nextB
            if (curA * curA + curB * curB > 4) {
                return it / (iterationsCount + 1.toDouble())
            }
        }
        return 1.0
    }

}

fun Int.toHex(): String = listOf(b2(), b3(), b4()).map(::byteToHex).joinToString("")

fun byteToHex(v: Int): String = hexDigit(v / 16).toString() + hexDigit(v % 16)

fun hexDigit(v: Int): Char = charArrayOf('0' , '1' , '2' , '3' , '4' , '5' , '6' , '7' , '8' , '9' , 'a' , 'b' , 'c' , 'd' , 'e' , 'f')[v]

inline fun Int.b1(): Int = (this shr 24).lastByte()
inline fun Int.b2(): Int = (this shr 16).lastByte()
inline fun Int.b3(): Int = (this shr 8).lastByte()
inline fun Int.b4(): Int = lastByte()
inline fun Int.lastByte(): Int = this and 0x000000ff
