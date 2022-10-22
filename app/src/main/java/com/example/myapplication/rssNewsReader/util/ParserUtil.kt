package com.example.myapplication.rssNewsReader.util

import android.util.Xml
import com.example.myapplication.rssNewsReader.ParserEnum.*
import com.example.myapplication.rssNewsReader.model.GeneralNewsModel
import com.example.myapplication.rssNewsReader.parsers.*
import okhttp3.internal.trimSubstring
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import timber.log.Timber
import java.io.IOException
import java.io.InputStream
import java.net.URL
import javax.net.ssl.HttpsURLConnection

const val namespace: String = ""
lateinit var channelType: String

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
    val newsList: List<GeneralNewsModel> = if (channelType == TRIBUNE.name) {
        downloadUrl(url)?.use { stream ->
            TribuneXmlParser().parse(stream)
        } ?: emptyList()
    } else {
        downloadUrl(url)?.use { stream ->
            parse(stream)
        } ?: emptyList()
    }

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
fun parse(inputStream: InputStream): List<GeneralNewsModel> {
    inputStream.use {
        val parser: XmlPullParser = Xml.newPullParser()
        parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false)
        parser.setInput(it, null)
        parser.nextTag()
        parser.nextToken()
        parser.next()
        when (channelType) {
            AAJ.name -> return AajXmlParser().readFeed(parser)
            ABB_TAK.name -> return AbbTakXmlParser().readFeed(parser)
            ARY.name -> return AryXmlParser().readFeed(parser)
            DAILY_PAKISTAN.name -> return DailyPakistanXmlParser().readFeed(parser)
            EXPRESS.name -> return ExpressXmlParser().readFeed(parser)
            GEO.name -> return GeoXmlParser().readFeed(parser)
            HUM.name -> return HumXmlParser().readFeed(parser)
            JANG.name -> return JangXmlParser().readFeed(parser)
            SAMAA.name -> return SamaaXmlParser().readFeed(parser)
            URDU_POINT.name -> return UrduPointXmlParser().readFeed(parser)
            else -> return emptyList()
        }
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
            XmlPullParser.END_TAG -> depth--.also {
                Timber.tag("TAG").d("Skipped:  ${parser.name}")
            }
            XmlPullParser.START_TAG -> depth++
        }
    }
}