package com.example.myapplication.rssNewsReader.parsers

import com.example.myapplication.rssNewsReader.model.GeneralNewsModel
import com.example.myapplication.rssNewsReader.util.namespace
import com.example.myapplication.rssNewsReader.util.readFromParser
import com.example.myapplication.rssNewsReader.util.skip
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import java.io.IOException

private var indexCount = -1

class AajXmlParser {
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
            } else if (parser.name == "media:content" && newsList.size != 0) {
                parser.nextTag()
                newsList[indexCount].imgUrl = parser.getAttributeValue(0)
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
        var description: String? = null
        var content: String? = null

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.eventType != XmlPullParser.START_TAG) {
                continue
            }
            when (parser.name) {
                "title" -> title = readFromParser(parser, "title")
                "link" -> link = readFromParser(parser, "link")
                "description" -> description = readFromParser(parser, "description")
                "content:encoded" -> content = readFromParser(parser, "content:encoded")
            }
        }
        return GeneralNewsModel(
            title = title,
            link = link,
            description = description,
            content = content,
            imgUrl = "imgUrl",
            pubDate = "pubDate"
        )
    }
}