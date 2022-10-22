package com.example.dbpoc

import android.content.Context
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import timber.log.Timber
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream


/**@RequiresApi(Build.VERSION_CODES.P)
class DbTools constructor(context: Context?, name: String?, version: Int, openParams: OpenParams)
: SQLiteOpenHelper(context, name, version, openParams) {


//    constructor(context: Context?)  {
//        super(
//            context,
//            "ContactsDB",
//            null,
//            1
//        ) //version is 1 q k me pehli bar bna rha hu OR constructor call nahi hoga har object ke liye nya database nahi bnega, can be changed in upgrade()
//        Log.d("TAG", "Database Created")
//    }

override fun onCreate(db: SQLiteDatabase) {
val createTableQuery = "CREATE TABLE CONTACTS(" +
"_id INTEGER PRIMARY KEY AUTOINCREMENT," +
"firstName TEXT," +
"lastName TEXT," +
"phoneNumber TEXT," +
"emailAddress TEXT," +
"homeAddress TEXT)"
db.execSQL(createTableQuery)
}
override fun onUpgrade(sqLiteDatabase: SQLiteDatabase?, i: Int, i1: Int) {}
} */

class DBHelperClass(private val context: Context) :
    SQLiteOpenHelper(context, DB_NAME, null, 3) {

    companion object {
        private const val DB_PATH = "/data/data/com.example.dbpoc/databases/"
        private const val DB_NAME = "common.sqlite"
        private const val COMPLETE_PATH = DB_PATH + DB_NAME
    }

    private var dbObj: SQLiteDatabase? = null

    @Throws(IOException::class)
    fun createDB() {
        this.readableDatabase
        Timber.tag("Readable ends....................").i("end")
        try {
            copyDB()
            Timber.tag("copy db ends....................").i("end")
        } catch (e: IOException) {
            throw Error("Error copying database")
        }
    }

    /**private fun checkDB(): Boolean {
    var checkDB: SQLiteDatabase? = null
    try {
    val path = DB_PATH + DB_NAME
    Log.i("myPath ......", path)
    checkDB = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY)
    Log.i("myPath ......", path)
    if (checkDB != null) {
    val c: Cursor = checkDB.rawQuery("SELECT * FROM bodybrands", null)
    Log.i("Cursor.......", c.getString(0))
    c.moveToFirst()
    val contents = arrayOfNulls<String>(80)
    var flag = 0
    while (!c.isAfterLast) {
    var temp = ""
    val s2: String = c.getString(0)
    val s3: String = c.getString(1)
    temp = "$temp\n Id:$s2\tType:$s3\t"
    contents[flag] = temp
    flag += 1
    Log.i("DB values.........", temp)
    c.moveToNext()
    }
    } else {
    return false
    }
    } catch (e: SQLiteException) {
    e.printStackTrace()
    }
    checkDB?.close()
    return checkDB != null
    } */

    @Throws(IOException::class)
    fun copyDB() {
        try {
            Timber.tag("inside copyDB....................").i("start")
            val ip: InputStream = context.assets.open(DB_NAME)
            Log.i("Input Stream....", ip.toString() + "")
            val op = COMPLETE_PATH
            val output: OutputStream = FileOutputStream(op)
            val buffer = ByteArray(1024)
            var length: Int
            while (ip.read(buffer).also { length = it } > 0) {
                output.write(buffer, 0, length)
                Log.i("Content.... ", length.toString() + "")
            }
            output.flush()
            output.close()
            ip.close()
        } catch (e: IOException) {
            Log.v("error", e.toString())
        }
    }

    @Throws(SQLException::class)
    fun openDB() {
        val myPath = COMPLETE_PATH
        dbObj = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE)
        Log.i("open DB......", dbObj.toString())
    }

    @Synchronized
    override fun close() {
        if (dbObj != null) dbObj!!.close()
        super.close()
    }

    override fun onCreate(db: SQLiteDatabase) {}
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {}
}





//IN MAIN
//        try {
//            db.createDB()
//        }catch (e: IOException) {
//            throw Error("Database not created...")
//        }