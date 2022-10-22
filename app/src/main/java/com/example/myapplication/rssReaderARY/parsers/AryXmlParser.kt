package com.example.myapplication.rssReaderARY.parsers

import android.util.Xml
import com.example.myapplication.rssReaderARY.model.GeneralNewsModel
import com.example.myapplication.rssReaderARY.util.getImgUrl
import com.example.myapplication.rssReaderARY.util.namespace
import com.example.myapplication.rssReaderARY.util.readFromParser
import com.example.myapplication.rssReaderARY.util.skip
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import java.io.IOException
import java.io.InputStream

private var indexCount = -1

class AryXmlParser {

    @Throws(XmlPullParserException::class, IOException::class)
    fun parse(inputStream: InputStream): List<GeneralNewsModel> {
        inputStream.use {
            val parser: XmlPullParser = Xml.newPullParser()
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false)
            parser.setInput(it, null)
            parser.nextTag()
            parser.nextToken()
            parser.next()
            return readFeed(parser)
        }
    }

    @Throws(XmlPullParserException::class, IOException::class)
    private fun readFeed(parser: XmlPullParser): List<GeneralNewsModel> {
        val newsList = mutableListOf<GeneralNewsModel>()

        parser.require(XmlPullParser.START_TAG, namespace, "channel")
        while (parser.next() != XmlPullParser.END_DOCUMENT) {
            if (parser.eventType != XmlPullParser.START_TAG) {
                continue
            }
            if (parser.name == "item") {
                newsList.add(readEntry(parser))
                indexCount++
            } else if (parser.name == "pubDate" && newsList.size != 0) {
                newsList[indexCount].pubDate = readFromParser(parser, "pubDate")
            } else if (parser.name == "description" && newsList.size != 0) {
                newsList[indexCount].description = readFromParser(parser, "description")
                newsList[indexCount].imgUrl = getImgUrl(newsList[indexCount].description!!)
            } else if (parser.name == "content:encoded" && newsList.size != 0) {
                newsList[indexCount].content = readFromParser(parser, "content:encoded")
            } else {
                skip(parser)
            }
        }
        indexCount = -1
        return newsList
    }

    @Throws(XmlPullParserException::class, IOException::class)
    private fun readEntry(parser: XmlPullParser): GeneralNewsModel {
        parser.require(XmlPullParser.START_TAG, namespace, "item")
        var title: String? = null
        var link: String? = null

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.eventType != XmlPullParser.START_TAG) {
                continue
            }
            when (parser.name) {
                "title" -> title = readFromParser(parser, "title")
                "link" -> link = readFromParser(parser, "link")
            }
        }
        return GeneralNewsModel(
            title = title,
            link = link,
            description = "description",
            content = "content",
            imgUrl = "imgUrl",
            pubDate = "pubDate"
        )
    }
}