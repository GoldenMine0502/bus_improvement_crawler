
interface Test {
    val test: Any
}

class TestImpl: Test {
    override val test = Any()
}

fun main() {
    val obj = TestImpl()
    for(i in 1..10) {
        println(obj.test.hashCode())
    }
}