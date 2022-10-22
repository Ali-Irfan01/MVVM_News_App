package com.example.myapplication.rssNewsReader.parsers

import com.example.myapplication.rssNewsReader.model.GeneralNewsModel
import com.example.myapplication.rssNewsReader.util.namespace
import com.example.myapplication.rssNewsReader.util.readFromParser
import com.example.myapplication.rssNewsReader.util.skip
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import java.io.IOException

private var indexCount = -1

class UrduPointXmlParser {
    @Throws(XmlPullParserException::class, IOException::class)
    fun readFeed(parser: XmlPullParser): List<GeneralNewsModel> {
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
            } else if (parser.name == "enclosure" && newsList.size != 0) {
                newsList[indexCount].imgUrl = parser.getAttributeValue(namespace, "url")
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