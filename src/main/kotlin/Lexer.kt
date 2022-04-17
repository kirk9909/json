
class Lexer(private val jsonStr: String){
    private val reader = CharReader(jsonStr)
    var token = Token()
    var lineNumber = 1
    var columnNumber = 1
    var numStart = 0

    fun nextToken(): Token {
        skipWhitespace()

        token.lineNumber = lineNumber
        token.columnNumber = columnNumber

        if (currentCharVal() == -1) {
            token.kind = TokenKind.EOF
        } else {
            when (currentCharVal().toChar()) {
                '{' -> nextChar(TokenKind.BeginObject)
                '}' -> nextChar(TokenKind.EndObject)
                '[' -> nextChar(TokenKind.BeginArray)
                ']' -> nextChar(TokenKind.EndArray)
                ',' -> nextChar(TokenKind.Comma)
                ':' -> nextChar(TokenKind.Colon)
                'f' -> toggleFalse()
                'n' -> toggleNull()
                't' -> toggleTrue()
                '"' -> {
                    token.kind = TokenKind.String
                    toggleString()
                }
                else -> toggleNumber()
            }
        }

        return token
    }

    private fun someException(msg: String, ch: Char = currentChar()) {
        throw IllegalArgumentException(
            "$msg |-> $ch <-| in $lineNumber:$columnNumber")
    }


    private fun skipWhitespace() {
        while (isWhitespace(currentChar())) {
            if (currentChar() == '\n') {
                lineNumber += 1
                columnNumber = 0
            }
            nextChar()
        }
    }

    private fun isWhitespace(ch: Char): Boolean {
        return when (ch) {
            ' ', '\t', '\n', '\r' -> true
            else -> false
        }
    }

    private fun toggleNull() {
        if (nextChar() == 'u' && nextChar() == 'l' &&
            nextChar() == 'l') {
            nextChar()
            token.kind = TokenKind.Null
        } else {
            someException("unexpected char")
        }
    }


    private fun toggleNumber() {
        numberStart()

        if (currentChar() == '-'){
            appendNumberChar()
            nextChar()
        }

        when (currentChar()) {
            '0' -> {
                when (nextChar()) {
                    '.'      -> toggleFloat()
                    'e', 'E' -> toggleExponent()
                    in '0'..'9' -> someException("unexpected char")
                    else -> {
                        token.kind = TokenKind.Int
                        numberEnd()
                    }
                }
            }
            in '1'..'9' -> {
                appendNumberChar()
                var ch = nextChar()
                while (ch in '0'..'9') {
                    appendNumberChar()
                    ch = nextChar()
                }

                when (ch) {
                    '.' -> toggleFloat()
                    'e', 'E', -> toggleExponent()
                    else -> {
                        token.kind = TokenKind.Int
                        numberEnd()
                    }
                }
            }
            else -> someException("unexpected char")
        }
    }

    private fun numberStart() {
        numStart = reader.pos
    }

    private fun appendNumberChar() {
    }

    private fun toggleExponent() {
        appendNumberChar()

        var ch = nextChar()

        if (ch == '+') {
            appendNumberChar()
            ch = nextChar()
        } else if (ch == '-') {
            appendNumberChar()
            ch = nextChar()
        }

        if (ch in '0'..'9') {
            while (ch in '0'..'9') {
                appendNumberChar()
                ch = nextChar()
            }
        } else {
            someException("unexpected char")
        }

        token.kind = TokenKind.Float

        numberEnd()
    }

    private fun toggleFloat() {
        appendNumberChar()
        var ch = nextChar()
        if (ch !in '0'..'9')
            someException("unexpected char")

        while (ch in '0'..'9'){
            appendNumberChar()
            ch = nextChar()
        }

        if (ch == 'e' || ch == 'E')
            toggleExponent()
        else {
            token.kind = TokenKind.Float
            numberEnd()
        }
    }

    private fun numberEnd() {
        token.rawValue = numberString()
    }

    private fun numberString(): String {
        return reader.strRange(numStart, reader.pos-1) // may be bugging
    }

    private fun toggleString() {
        val startPos = reader.pos

        while (true) {
            val charVal = reader.nextCharVal()
            if (charVal == -1) {
                someException("Unterminated string")
            } else if (charVal.toChar() == '"') {
                nextChar()
                break
            } else {
                if (charVal in 0..31)
                    someException("unexpected char")
            }
        }

        // reuse the object key ???
        token.stringValue = reader.strRange(begin = startPos+1, end = reader.pos-2)
    }

    private fun toggleTrue() {
        if (nextChar() == 'r' && nextChar() == 'u' &&
            nextChar() == 'e') {
            nextChar()
            token.kind = TokenKind.True
        } else {
            someException("unexpected char")
        }
    }

    private fun toggleFalse() {
        if (nextChar() == 'a' && nextChar() == 'l' &&
            nextChar() == 's' && nextChar() == 'e') {
            nextChar()
            token.kind = TokenKind.False
        } else {
            someException("unexpected char")
        }
    }

    private fun currentChar(): Char {
        return currentCharVal().toChar()
    }

    private fun currentCharVal(): Int {
        return reader.currentCharVal()
    }

    private fun nextChar(tokenKind: TokenKind) {
        token.kind = tokenKind

        nextChar()
    }

    private fun nextChar(): Char{
        columnNumber += 1
        return nextCharNoColInc()
    }

    private fun nextCharNoColInc(): Char{
        val charVal = reader.nextCharVal()

        if (charVal == -1) {
            if (reader.pos != reader.string.length) {
                someException("unexpected char")
            }
        }
        return charVal.toChar()
    }
}