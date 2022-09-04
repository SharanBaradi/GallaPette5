package com.example.gallapette.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.gallapette.data.dao.CashFlowDao
import com.example.gallapette.data.model.CashFlow

@Database(entities = [CashFlow::class], version = 1, exportSchema = false)
@TypeConverters(Converters :: class)
abstract class CashFlowDatabase : RoomDatabase() {

    abstract fun cashFlowDao() : CashFlowDao
    companion object{
        @Volatile
        private var INSTANCE : CashFlowDatabase? = null
        fun getDatabase(context: Context): CashFlowDatabase {
            val tempInstance = INSTANCE
            if(tempInstance!=null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CashFlowDatabase::class.java,
                    "cashflow_database"
                ).build()
                INSTANCE =instance
                return instance
            }
        }
    }
}