import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import java.io.File

internal class ParserTest {

    @Test
    fun parseTrue() {
        val onlyTrue = "true"
        assertEquals(true, Json.parse(onlyTrue))
    }

    @Test
    fun parseFalse() {
        val onlyFalse = "false"
        assertEquals(false, Json.parse(onlyFalse))
    }

    @Test
    fun parseNull() {
        val onlyNull = "null"
        assertEquals(null, Json.parse(onlyNull))
    }

    @Test
    fun parseString() {
        val onlyString = "\"one line string\""
        assertEquals("one line string", Json.parse(onlyString))
    }

    @Test
    fun parseInt() {
        val onlyInt = "123456"
        assertEquals(123456, Json.parse(onlyInt))
    }

    @Test
    fun parseFloat() {
        val onlyFloat = "27.9909"
        println(Json.parse(onlyFloat)?.javaClass)
        println(Json.parse(onlyFloat))
    }

    @Test
    fun parseObject() {
        // object json file from https://datatracker.ietf.org/doc/html/rfc8259#section-13
        val objectJsonStr = """
            {
              "Image": {
                "Width":  800,
                "Height": 600,
                "Title":  "View from 15th Floor",
                "Thumbnail": {
                  "Url":    "http://www.example.com/image/481989943",
                  "Height": 125,
                  "Width":  100
                },
                "Animated" : false,
                "IDs": [116, 943, 234, 38793]
              }
            }
        """
        val objectJson = Json.parse(objectJsonStr)
        println(objectJson)
    }

    @Test
    fun parseObjectFile() {
        // object json file from https://datatracker.ietf.org/doc/html/rfc8259#section-13
        val homePath = System.getProperty("user.dir")
        val objectJsonPath = "${homePath}/src/test/resources/object.json"

        val objectJsonStr = File(objectJsonPath).readText()
        val objectJson = Json.parse(objectJsonStr)
        println(objectJson)
    }


    @Test
    fun parseArray() {
        // array json file from https://datatracker.ietf.org/doc/html/rfc8259#section-13
        val arrayJsonStr = """
            [
              {
                "precision": "zip",
                "Latitude":  37.7668,
                "Longitude": -122.3959,
                "Address":   "",
                "City":      "SAN FRANCISCO",
                "State":     "CA",
                "Zip":       "94107",
                "Country":   "US"
              },
              {
                "precision": "zip",
                "Latitude":  37.371991,
                "Longitude": -122.026020,
                "Address":   "",
                "City":      "SUNNYVALE",
                "State":     "CA",
                "Zip":       "94085",
                "Country":   "US"
              }
            ]
        """
        val arrayJson = Json.parse(arrayJsonStr)
        println(arrayJson)
    }

    @Test
    fun parseArrayFile() {
        // array json file from https://datatracker.ietf.org/doc/html/rfc8259#section-13
        val homePath = System.getProperty("user.dir")
        val arrayJsonPath = "${homePath}/src/test/resources/array.json"
        val arrayJsonStr = File(arrayJsonPath).readText()
        println(arrayJsonPath)
        val arrayJson = Json.parse(arrayJsonStr)
        println(arrayJson)
    }

    @Test
    fun parseLargeSizeJson() {

    }
}