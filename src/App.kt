import org.khronos.webgl.set
import org.w3c.dom.*
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
    val iterationsCount = 64
    val colors = arrayOf(color1, color2, color6, color8, color9, color10, color11)
    val ctx = canvas.asDynamic().getContext("2d") as CanvasRenderingContext2D
    var img = ctx.createImageData(500.0, 500.0)
    var zoom = 0
    var xOffset = 0
    var yOffset = 0

    var width: Int = 0
    var height: Int = 0

    var mouseDownX = 0
    var mouseDownY = 0
    var colorPalete = 0

    fun init() {
        window.onresize = { onResize() }
        window.onkeypress = { onKeyPress(it as KeyboardEvent) }
        window.onmousedown = { onMouse(it as MouseEvent, true) }
        window.onmouseup = { onMouse(it as MouseEvent, false) }
        onResize()
//        load(-width * 2, -height * 2, 300)
//        load((-width * 1.5).toInt(), -height * 2, 2400)
//        load(-6537, -2303, 8700)
//        load(-10202, -4136, 13700)
//        load(-2806, -2586, 4100)
//        load(-482, -2830, 3800)
//        load(-6921, -2390, 7400)
        load(-17145, -18709, 28100) //palete10
//        load(-23263, -26188, 40100)
        draw()
    }

    private fun load(x: Int, y: Int, z: Int) {
        xOffset = x
        yOffset = y
        zoom = z
    }

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
            "KeyQ" -> {
                setZoom(zoom + 1000)
            }
            "KeyA" -> {
                setZoom(zoom - 1000)
            }
            "KeyW" -> {
                setZoom(zoom * 12 / 10)
            }
            "KeyS" -> {
                setZoom(zoom * 10 / 12)
            }
            "KeyC" -> {
                colorPalete = (colorPalete + 1) % colors.size
            }
        }
        draw()
    }

    fun setZoom(newZoom: Int): Unit {
        val oldZoom = zoom
        zoom = newZoom
        xOffset = (width  / 2 + xOffset) * (zoom - oldZoom) / oldZoom + xOffset
        yOffset = (height / 2 + xOffset) * (zoom - oldZoom) / oldZoom + yOffset
    }

    fun onResize() {
        canvas.setAttribute("width", window.innerWidth.toString())
        canvas.setAttribute("height", window.innerHeight.toString())
        width = canvas.clientWidth
        height = canvas.clientHeight
        img = ctx.createImageData(width.toDouble(), height.toDouble())
        draw()
    }

    fun draw() {
        val start = Date().getTime()
        ctx.clearRect(0.0, 0.0, width.toDouble(), height.toDouble())
        for (y in 0..height - 1) {
            for (x in 0..width - 1) {
                val m = mandelbrot(x, y, zoom, xOffset, yOffset)
                img.setPixel(x, y, getColor(m))
            }
        }
        ctx.putImageData(img, 0.0, 0.0)
        val elapsed = Date().getTime() - start
        println("draw elapsed: $elapsed")
        println("load($xOffset, $yOffset, $zoom)")
    }

    private fun ImageData.setPixel(x: Int, y: Int, color: Int) {
        val offset = (y * width + x) * 4
        data[offset] = color.b2().asDynamic()          //r
        data[offset + 1] = color.b3().asDynamic()      //g
        data[offset + 2] = color.b4().asDynamic()      //b
        data[offset + 3] = color.b1().asDynamic()      //a
    }

    fun getColor(m: Double): Int {
        val index = (m * (colors[colorPalete].size - 1)).toInt()
        return colors[colorPalete][index]
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
