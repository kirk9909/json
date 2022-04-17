fun main(args: Array<String>) {
    // Json Parser Examples

     val jsonStr = "{\"key\": \"value\", \"k\":\"v\", \"arr\": [\"arr1\", \"arr2\"], \"IDs\": [116, 943, 234, 38793] }"
    // JSON String => Map or List or Single-Value
    // Json.parse(jsonStr) // => Map or List or Single-Value\

    val res = Json.parse(jsonStr).toString() // same as jsonStr

    println(res) // => {key=value, k=v, arr=[arr1, arr2], IDs=[116, 943, 234, 38793]}

    // TODO may be build with one by one ?
    val jsonData = mapOf("key1" to 1, "key2" to 2, "key3" to 3, "key4" to 1)
    Json.build(jsonData) // => String

}