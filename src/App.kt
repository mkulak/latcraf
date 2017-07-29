import org.khronos.webgl.Uint8ClampedArray
import org.khronos.webgl.set
import org.w3c.dom.CanvasRenderingContext2D
import org.w3c.dom.events.Event
import kotlin.browser.document
import kotlin.browser.window


fun draw() {
    val canvas = document.getElementById("mainCanvas")!!
    println("inner: ${window.innerWidth}x${window.innerHeight} client: ${document.documentElement?.clientWidth}x${document.documentElement?.clientHeight}")
    canvas.setAttribute("width", window.innerWidth.toString())
    canvas.setAttribute("height", window.innerHeight.toString())
    val ctx = canvas.asDynamic().getContext("2d") as CanvasRenderingContext2D
    val width = canvas.clientWidth
    val height = canvas.clientHeight

    for (y in 0..height - 1) {
        for (x in 0..width - 1) {
//            val fit = ((y / 10) + (x / 10)) % 2 == 0
            val fit = doesFit(x, y, width, height)
            val color = if (fit) "#ffffff" else "#000000"
            ctx.fillStyle = color
            ctx.fillRect(x.toDouble(), y.toDouble(), 1.0, 1.0)
        }
    }
}

fun doesFit(x: Int, y: Int, width: Int, height: Int): Boolean {
//    (a + ib) * (c + id) = (ac - db) + i(ad + bc)
    // new = old * old + first
    val a = x - width / 2
    val b = y - height / 2
    fun nextA(curA: Int, curB: Int, firstA: Int, firstB: Int) = curA * curA - curB * curB + firstA
    fun nextB(curA: Int, curB: Int, firstA: Int, firstB: Int) = curA * curB + curA * curB + firstB

    var newA = a
    var newB = b

    repeat(10) {
        newA = nextA(newA, newB, a, b)
        newB = nextB(newA, newB, a, b)
    }
//    return a < 1.0 && a > -1.0 && b < 1.0 && b > -1.0
    return newA * newA + newB * newB < 400
}

fun main(args: Array<String>) {
    println("hello world")
    window.onload = { draw() }
    window.onresize= { draw() }
}


//    val img = ctx.createImageData(width.toDouble(), height.toDouble())
//    val data: Uint8ClampedArray = img.data
//    var i = 0
//    while (i < data.length) {
//        val x = i / 4 % width
//        val y = i / 4 / width
//        val color: dynamic = if (((x / 10) + (y / 10)) % 2 == 0) 0 else 255
//        val alpha: dynamic = 255
//        data[i + 0] = color
//        data[i + 1] = color
//        data[i + 2] = color
//        data[i + 3] = alpha
//        i += 4
//    }
//    ctx.putImageData(img, 0.5, 0.5)
//    ctx.translate(0.5, 0.5)
