class Parser(jsonStr: String){
    private val lexer = Lexer(jsonStr)

    fun parse(): Any?{
        lexer.nextToken()
        val j = parseValue()
        if (lexer.token.kind == TokenKind.EOF)
            return j
        return null
    }

    private fun parseValue(): Any?{
        return when(lexer.token.kind) {
            TokenKind.Int -> valueNextToken(lexer.token.intValue())
            TokenKind.Float -> valueNextToken(lexer.token.floatValue())
            TokenKind.String -> valueNextToken(lexer.token.stringValue)
            TokenKind.Null -> valueNextToken(null)
            TokenKind.True -> valueNextToken(true)
            TokenKind.False -> valueNextToken(false)
            TokenKind.BeginArray -> parseArray()
            TokenKind.BeginObject -> parseObject()
            else -> someException("unexpected token ${lexer.token}")
        }
    }

    private fun parseObject(): Map<String, Any>{
        //nextTokenExpectObjectKey()
        lexer.nextToken()

        val map = mutableMapOf<String, Any>()

        if (lexer.token.kind != TokenKind.EndObject) {
            while (true) {
                if (lexer.token.kind != TokenKind.String)
                    someException("unexpected token")

                val key = lexer.token.stringValue

                lexer.nextToken()

                if (lexer.token.kind != TokenKind.Colon)
                    someException("unexpected token")

                lexer.nextToken()

                val value = parseValue()

                if (value != null) map.put(key, value)


                when (lexer.token.kind) {
                    TokenKind.Comma -> {
                        lexer.nextToken()
                        if (lexer.token.kind == TokenKind.EndObject)
                            someException("unexpected token")
                    }
                    TokenKind.EndObject -> break
                    else -> someException("unexpected token")
                }
            }
        }
        lexer.nextToken()
        return map
    }

    private fun nextTokenExpectObjectKey() {
        TODO("Not yet implemented")
    }

    private fun parseArray(): Any{
        lexer.nextToken()

        val arr = mutableListOf<Any>()

        if (lexer.token.kind != TokenKind.EndArray) {
            while (true) {
                parseValue()?.let { arr.add(it) }

                when (lexer.token.kind) {
                    TokenKind.Comma -> {
                        lexer.nextToken()
                        if (lexer.token.kind == TokenKind.EndArray)
                            someException("unexpected token")
                    }
                    TokenKind.EndArray -> break
                    else -> someException("unexpected token")
                }
            }
        }
        lexer.nextToken()

        return arr
    }

    private fun parseString() { TODO("Not yet implemented") }

    private fun parseNumber() { TODO("Not yet implemented") }

    private fun valueNextToken(value: Any?): Any?{
        lexer.nextToken()
        return value
    }

    private fun someException(msg: String) {
        throw IllegalArgumentException(
            "$msg in ${lexer.token.lineNumber}:${lexer.token.columnNumber}")
    }

}