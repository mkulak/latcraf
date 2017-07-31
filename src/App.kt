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
            val m = mandelbrot(x, y, width, height)
            ctx.fillStyle = getColor(m)
            ctx.fillRect(x.toDouble(), y.toDouble(), 1.0, 1.0)
        }
    }
}

fun getColor(m: Double): String {
//    val colors = intArrayOf(-13882322, -13816786, -13751250, -13685714, -13620178, -13554642, -13489106, -13423571, -13358035, -13226963, -13161427, -13095891, -12964819, -12899283, -12768211, -12702676, -12571604, -12440532, -12374996, -12243924, -12112852, -12047316, -11915988, -11784917, -11653845, -11522773, -11391701, -11260629, -11129557, -10998229, -10867157, -10736085, -10605013, -10473685, -10342613, -10211541, -10080213, -9949141, -9883605, -9817813, -9752277, -9686741, -9621205, -9555669, -9424341, -9358805, -9293269, -9227733, -9162197, -9030869, -8965332, -8899796, -8768724, -8703188, -8637396, -8506324, -8440788, -8375252, -8243924, -8178388, -8112852, -7981780, -7916244, -7850452, -7719380, -7653844, -7522772, -7456980, -7325907, -7260371, -7194579, -7063507, -6997971, -6866899, -6801107, -6735571, -6604499, -6538707, -6407635, -6342098, -6210770, -6145234, -6079698, -5948370, -5882834, -5751506, -5685970, -5620434, -5489105, -5423569, -5357777, -5226705, -5160913, -5095377, -4964048, -4898512, -4832720, -4767184, -4635856, -4570320, -4504527, -4438991, -4373199, -4241871, -4176335, -4110542, -4044750, -3979214, -3913422, -3847629, -3782093, -3716301, -3650509, -3584716, -3519180, -3453388, -3387596, -3321804, -3256011, -3190475, -3190219, -3124427, -3058634, -2992842, -2927050, -2861258, -2795465, -2729673, -2729417, -2663625, -2597832, -2532040, -2466248, -2400456, -2400199, -2334407, -2268615, -2202823, -2202566, -2136774, -2070982, -2005190, -2004933, -1938885, -1873093, -1807300, -1807044, -1741252, -1675459, -1675203, -1609155, -1543362, -1543106, -1477314, -1411521, -1411009, -1345217, -1344960, -1279168, -1213375, -1212863, -1147071, -1146814, -1081022, -1080765, -1014717, -1014460, -948668, -948411, -882619, -882106, -816314, -816057, -750265, -750008, -683960, -683703, -617910, -617654, -617397, -551348, -551092, -550835, -485042, -484786, -418993, -418736, -418480, -418224, -418223, -417967, -417967, -417710, -417710, -417454, -417453, -417197, -417196, -416940, -416940, -416683, -416683, -416427, -416426, -416170, -416169, -415913, -415913, -415656, -415656, -415399, -415399, -415143, -415142, -414886, -414885, -414629, -414628, -414372, -414371, -414115, -413859, -413858, -413602, -413601, -413345, -413344, -413088, -413087, -412831, -412574, -412574, -412317, -412317, -412060, -412060, -411803, -411803, -411546, -411290, -411289, -411033, -411032, -410776, -410775, -410519, -410262, -410262, -410005, -410005, -409748, -409747, -409491, -409490, -409234, -408977, -408977, -408720, -408720, -408463, -408463, -408206, -407950, -407949, -407692, -407692, -407435, -407435, -407178, -406922, -406921, -406665, -406664, -406408, -406407, -406150, -406150, -405893, -405637, -405636, -405380, -405379, -405123, -405122, -404866, -404865, -404608, -404608, -404351, -404351, -404094, -403838, -403837, -403581, -403580, -403324, -403323, -403067, -403066, -402810, -402809, -402553, -402552, -402295, -402295, -402038, -402038, -401781, -401781, -401524, -401524, -401267, -401267, -401010, -401010, -400753, -400753, -400497, -400496, -400240, -400239, -400239, -399982, -399982, -399725, -399725, -465004, -465004, -464747, -464747, -464747, -464490, -464490, -464233, -464233, -464232, -463976, -463976, -463719, -463719, -463718, -463462, -463462, -463205, -463205, -463205, -462948, -462948, -528483, -528227, -528227, -528226, -527970, -527970, -527969, -527713, -527713, -527712, -527456, -527456, -527456, -527199, -527199, -527199, -527198, -526942, -526942, -526942, -526685, -526685, -5816014, -5881553, -5881553, -5881553, -5881553)
//    val colors = intArrayOf(0xff0000, 0x00ff00, 0x0000ff,0xff0000, 0x00ff00, 0x0000ff, 0xff0000, 0x00ff00, 0x0000ff)
    val colors = IntArray(255) { it * 10 }
    val index = (m * 10 * (colors.size - 1)).toInt().coerceAtMost(colors.size - 1)
    val g = colors[index]
    return "#${g.asDynamic().toString(16)}"
}

fun mandelbrot(x: Int, y: Int, width: Int, height: Int): Double {
    val a = (x.toDouble() - width * 1.5) / 2400
    val b = (y.toDouble() - height * 2) / 2400
    fun nextA(curA: Double, curB: Double, firstA: Double, firstB: Double) = curA * curA - curB * curB + firstA
    fun nextB(curA: Double, curB: Double, firstA: Double, firstB: Double) = curA * curB + curA * curB + firstB

    var zA = 0.0
    var zB = 0.0

    repeat(512) {
        val zA2 = nextA(zA, zB, a, b)
        val zB2 = nextB(zA, zB, a, b)
        zA = zA2
        zB = zB2
        if (zA * zA + zB * zB > 4) {
            return it / 512.0
        }
    }
    return 1.0
}

fun main(args: Array<String>) {
    println("hello world")
    window.onload = { draw() }
    window.onresize= { draw() }
}

data class Complex(val a: Double, val b: Double) {
    operator fun times(o: Complex): Complex = Complex(a * o.a - b * o.b, a * o.b + b * o.a)
    operator fun plus(o: Complex): Complex = Complex(a + o.a, b + o.b)
    fun module(): Double = Math.sqrt(a * a + b * b)
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

//    (a + ib) * (c + id) = ac + iad + ibc + iibd = (ac - bd) + i(ad + bc)
// new = old * old + first

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
