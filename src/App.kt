import org.khronos.webgl.Uint8ClampedArray
import org.khronos.webgl.set
import org.w3c.dom.CanvasRenderingContext2D
import org.w3c.dom.events.Event
import kotlin.browser.document
import kotlin.browser.window


fun draw() {
//    val contentDiv = document.getElementById("content")
//    contentDiv?.textContent = "Hello from Kotlin"
    val canvas = document.getElementById("mainCanvas")!!
    println("inner: ${window.innerWidth}x${window.innerHeight} client: ${document.documentElement?.clientWidth}x${document.documentElement?.clientHeight}")
    canvas.setAttribute("width", window.innerWidth.toString())
    canvas.setAttribute("height", window.innerHeight.toString())
    val ctx = canvas.asDynamic().getContext("2d") as CanvasRenderingContext2D
    val width = canvas.clientWidth
    val height = canvas.clientHeight

    for (i in 0..height - 1) {
        for (j in 0..width - 1) {
            val a = ((i / 10) + (j / 10)) % 2 == 0
            val color = if (a) "#ffffff" else "#000000"
            ctx.fillStyle = color
            ctx.fillRect(j.toDouble(), i.toDouble(), 1.0, 1.0)
        }
    }
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
