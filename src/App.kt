import org.w3c.dom.CanvasRenderingContext2D
import kotlin.browser.document
import kotlin.browser.window
import kotlin.js.Math


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
            val g = mandelbrot(x, y, width, height)
            val color = "rgba($g, $g, $g, 255)"
            ctx.fillStyle = color
            ctx.fillRect(x.toDouble(), y.toDouble(), 1.0, 1.0)
        }
    }
}

fun mandelbrot2(x: Int, y: Int, width: Int, height: Int): Int {
    val a = (x.toDouble() - width / 2) / 300
    val b = (y.toDouble() - height / 2) / 300
    val z0 = Complex(a, b)
    var z = Complex(0.0, 0.0)
    repeat(255) {
        z = z * z + z0
        if (z.module() > 2) return it
    }
    return 255
}

fun mandelbrot(x: Int, y: Int, width: Int, height: Int): Int {
//    (a + ib) * (c + id) = ac + iad + ibc + iibd = (ac - bd) + i(ad + bc)
    // new = old * old + first
    val a = (x.toDouble() - width / 2) / 300
    val b = (y.toDouble() - height / 2) / 300
    fun nextA(curA: Double, curB: Double, firstA: Double, firstB: Double) = curA * curA - curB * curB + firstA
    fun nextB(curA: Double, curB: Double, firstA: Double, firstB: Double) = curA * curB + curA * curB + firstB

    var zA = 0.0
    var zB = 0.0

    repeat(255) {
        val zA2 = nextA(zA, zB, a, b)
        val zB2 = nextB(zA, zB, a, b)
        zA = zA2
        zB = zB2
        if (zA * zA + zB * zB > 4) {
            return it
        }
    }
    return 255
}

data class Complex(val a: Double, val b: Double) {
    operator fun times(o: Complex): Complex = Complex(a * o.a - b * o.b, a * o.b + b * o.b)
    operator fun plus(o: Complex): Complex = Complex(a + o.a, b + o.b)
    fun module(): Double = Math.sqrt(a * a + b * b)
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
