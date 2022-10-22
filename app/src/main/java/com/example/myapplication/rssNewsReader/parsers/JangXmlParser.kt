package com.example.myapplication.rssNewsReader.parsers

import com.example.myapplication.rssNewsReader.model.GeneralNewsModel
import com.example.myapplication.rssNewsReader.util.*
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import java.io.IOException

private var indexCount = -1
private const val SKIPPED_COUNT = 22

class JangXmlParser {
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
            } else if (parser.name == "description" && newsList.size != 0) {
                val description = readFromParser(parser, "description")
                newsList[indexCount].imgUrl = getImgUrl(description)
                newsList[indexCount].description = trimDescription(
                    description,
                    newsList[indexCount].imgUrl!!.length + SKIPPED_COUNT
                )
            } else {
                skip(parser)
            }
        }
        indexCount = -1
        return newsList
    }

    @Throws(XmlPullParserException::class, IOException::class)
    fun readEntry(parser: XmlPullParser): GeneralNewsModel {
        parser.require(XmlPullParser.START_TAG, namespace, "item")
        var title: String? = null
        var link: String? = null
        var description: String? = null
        var pubDate: String? = null
        var imgUrl: String? = null

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.eventType != XmlPullParser.START_TAG) {
                continue
            }
            when (parser.name) {
                "title" -> title = readFromParser(parser, "title")
                "link" -> link = readFromParser(parser, "link")
                "pubDate" -> pubDate = readFromParser(parser, "pubDate")
                "description" -> description =
                    readFromParser(parser, "description").also { imgUrl = getImgUrl(description) }
            }
        }
        return GeneralNewsModel(
            title = title,
            link = link,
            description = description,
            content = "content",
            imgUrl = imgUrl,
            pubDate = pubDate
        )
    }
}