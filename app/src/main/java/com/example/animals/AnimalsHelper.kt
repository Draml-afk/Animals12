package com.example.animals

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class AnimalsHelper(context: Context) : SQLiteOpenHelper(context, mDatabase, null, mVersion) {

    init {
        instance = this
    }

    companion object {
        const val COLUMN_ID = "_id" // идентификатор
        const val COLUMN_ANIMAL = "animal"
        const val TABLE_ANIMALS = "animals"
        private var instance: AnimalsHelper? = null
        private const val mDatabase = "animals.db"
        private const val mVersion = 1
        const val ANIMALS_CREATE = " create table animals (_id integer primary key autoincrement, animal text not null);   "

        @Synchronized
        fun getInstance(ctx: Context) = instance ?: AnimalsHelper(ctx.applicationContext)
    }

    // запускается ОДИН раз при первом доступе к  этой базе данных
    override fun onCreate(db: SQLiteDatabase) {
        // 1. создать структуру таблиц, индексов ... в SQLite
        db.execSQL(ANIMALS_CREATE)

        // 2. Добавить с таблицы начальные данные
        db.execSQL(" insert into animals (animal) values ('crocodile')   ")
        db.execSQL(" insert into animals (animal) values ('leo')   ")
        db.execSQL(" insert into animals (animal) values ('wolf')   ")
        db.execSQL(" insert into animals (animal) values ('cat')   ")
        db.execSQL(" insert into animals (animal) values ('hippo')   ")
        db.execSQL(" insert into animals (animal) values ('butterfly')   ")
        db.execSQL(" insert into animals (animal) values ('bird')   ")
        db.execSQL(" insert into animals (animal) values ('fox')   ")
        db.execSQL(" insert into animals (animal) values ('whale')   ")

    }

    // запускается каждый раз при апргейде приложения
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // изменеие структуры таблиц, колонок таблиц и т.п.
    }
}

val Context.database : AnimalsHelper
    get() = AnimalsHelper.getInstance(this)