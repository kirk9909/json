class Json {
    companion object {
        fun parse(jsonStr: String): Any?{
            return Parser(jsonStr).parse()
        }

        // TODO("Not yet implemented")
        fun build(jsonData: Any) : String {
            return Builder(jsonData).build()
        }
    }
}