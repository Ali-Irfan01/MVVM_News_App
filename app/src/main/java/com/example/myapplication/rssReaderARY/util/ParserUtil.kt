package com.example.myapplication.rssReaderARY.util

import android.util.Log
import com.example.myapplication.rssReaderARY.model.GeneralNewsModel
import com.example.myapplication.rssReaderARY.parsers.UrduPointXmlParser
import okhttp3.internal.trimSubstring
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import timber.log.Timber
import java.io.IOException
import java.io.InputStream
import java.net.URL
import javax.net.ssl.HttpsURLConnection

const val namespace: String = ""

// For Geo Description
fun trimDescription(description: String?, size: Int): String? =
    if (!description.isNullOrEmpty()) description.trimSubstring(
        13 + size + 15,
        description.length
    ) else null

fun getImgUrl(Description: String?): String? {
    if (Description.isNullOrEmpty())
        return null
    val start = Description.indexOf("src=\"") + 5
    val end = Description.indexOf("\"", start)
    return Description.substring((start), end)
}

fun loadXmlFromNetwork(url: String): List<GeneralNewsModel> {
    val newsList: List<GeneralNewsModel> = downloadUrl(url)?.use { stream ->
        UrduPointXmlParser().parse(stream)
    } ?: emptyList()

    var count = 0
    while (count < newsList.size) {
        Timber.tag("NOT_TAG").d("${newsList[count].imgUrl}")
        count++
    }
    return newsList
}

fun downloadUrl(urlToFeed: String): InputStream? {
    val url = URL(urlToFeed)
    return (url.openConnection() as HttpsURLConnection).run {
        requestMethod = "GET"
        connect()
        inputStream
    }
}

@Throws(XmlPullParserException::class, IOException::class)
fun readFromParser(parser: XmlPullParser, tagName: String): String {
    parser.require(XmlPullParser.START_TAG, namespace, tagName)
    val text = readText(parser)
    parser.require(XmlPullParser.END_TAG, namespace, tagName)
    return text
}

@Throws(XmlPullParserException::class, IOException::class)
fun readText(parser: XmlPullParser): String {
    var result = ""
    if (parser.next() == XmlPullParser.TEXT) {
        result = parser.text
        parser.nextTag()
    }
    return result
}

@Throws(XmlPullParserException::class, IOException::class)
fun skip(parser: XmlPullParser) {
    if (parser.eventType != XmlPullParser.START_TAG) {
        throw IllegalStateException()
    }
    var depth = 1
    while (depth != 0) {
        when (parser.next()) {
            XmlPullParser.END_TAG -> depth--.also { Log.d("TAG", "Skipped: ${parser.name}") }
            XmlPullParser.START_TAG -> depth++
        }
    }
}