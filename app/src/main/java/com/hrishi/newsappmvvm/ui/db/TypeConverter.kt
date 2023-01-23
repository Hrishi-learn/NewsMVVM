package com.hrishi.newsappmvvm.ui.db

import androidx.room.TypeConverter
import com.hrishi.newsappmvvm.ui.util.Source

class TypeConverter {
    @TypeConverter
    fun sourceToString(source: Source):String{
        return source.name
    }
    @TypeConverter
    fun stringToSource(string: String): Source {
        return Source(string,string)
    }
}