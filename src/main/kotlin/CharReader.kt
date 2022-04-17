class CharReader(val string: String){
    var pos: Int = 0

    fun currentCharVal(): Int{
        if (pos >= string.length)
            return -1
        return string.get(pos).code
    }

    fun currentChar(): Char { TODO("Not yet implemented") }

    fun nextCharVal(): Int {
        pos += 1
        return currentCharVal()
    }

    fun nextChar(): Char {
        pos += 1
        return currentCharVal().toChar()
    }

    fun strRange(begin: Int, end: Int): String {
        return string.slice(begin..end)
    }
}