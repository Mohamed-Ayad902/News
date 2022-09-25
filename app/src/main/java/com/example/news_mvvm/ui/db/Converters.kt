package com.example.news_mvvm.ui.db

import androidx.room.TypeConverter
import com.example.news_mvvm.ui.models.Source

class Converters {

    @TypeConverter
    fun fromSource(source: Source)= source.name

    @TypeConverter
    fun toSource(name:String)=Source(name,name)
}