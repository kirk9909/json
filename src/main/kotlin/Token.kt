class Token {
    var kind = TokenKind.EOF
    var lineNumber = 0
    var columnNumber = 0
    var stringValue = ""
    var rawValue = ""


    fun intValue(): Int {
        return rawValue.toInt()
    }

    fun floatValue(): Float {
        return rawValue.toFloat()
    }

    override fun toString(): String {
        return when (kind) {
            TokenKind.Null -> "null"
            TokenKind.True -> "true"
            TokenKind.False -> "false"
            TokenKind.Int -> rawValue
            TokenKind.Float -> rawValue
            TokenKind.String -> stringValue
            TokenKind.BeginArray -> "["
            TokenKind.EndArray -> "]"
            TokenKind.BeginObject -> "{"
            TokenKind.EndObject -> "}"
            TokenKind.Colon -> ":"
            TokenKind.Comma -> ","
            TokenKind.EOF -> "<EOF>"
            else -> throw IllegalArgumentException("unknown token kind |-> $kind <-|")
        }
    }
}