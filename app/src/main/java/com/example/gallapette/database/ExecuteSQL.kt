package com.example.gallapette.database

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.sql.*

class ExecuteSQL() {
    suspend fun initConnection(): Connection? {
        /*         val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
                 StrictMode.setThreadPolicy(policy)*/
        var connection: Connection? = null
        try {
            Class.forName(ExecuteSQL.Classes)
            connection = DriverManager.getConnection(url, username, password)
            customLog("Connection SUCCESS")
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
            customLog("Connection  ERROR")
        } catch (e: SQLException) {
            e.printStackTrace()
            customLog("Connection FAILURE")
        }
        return connection
    }

    suspend fun InsertStatement(insertStatement: String): Int {
        val connection: Connection? = initConnection()
        return sqlInsertStatmenet(connection = connection, insertStatement = insertStatement)
    }

    suspend fun sqlInsertStatmenet(connection: Connection?, insertStatement: String): Int {
        var updateReturnValue: Int = 0
        if (connection != null) {
            try {
                var statement: Statement? =
                    connection.createStatement(
                        ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_UPDATABLE)
                updateReturnValue = statement!!.executeUpdate(insertStatement)
                //statement!!.executeUpdate("insert into TEST_TABLE (C1) values ('NewRow');")
                if (updateReturnValue > 0) customLog("updateReturnValue is $updateReturnValue")
                else customLog("updateReturnValue is $updateReturnValue - Check")

            } catch (e: SQLException) {
                e.printStackTrace()
                customLog(e.toString())
            }
        } else {
            customLog("Connection is null")
        }
        return updateReturnValue
    }

    suspend fun sqlExecuteQuery(queryStatement: String): ResultSet? {
        val connection: Connection? = initConnection()

        if (connection != null) {
            try {
                var statement: Statement? =
                    connection.createStatement(
                        ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_UPDATABLE)
                var resultSet: ResultSet = statement!!.executeQuery(queryStatement)
                //printResultset(resultSet)
                return resultSet
            } catch (e: SQLException) {
                e.printStackTrace()
                customLog(e.toString())
            }
        } else {
            customLog("Connection is null")
        }
        return null
    }

    suspend fun printResultset(resultSet: ResultSet) {
        val noOfCols = resultSet.metaData.columnCount
        while (resultSet.next()) {
            var tempStr: String = ""
            for (i in 1..noOfCols) {
                tempStr = tempStr + " " + resultSet.getString(i)
            }
            //var rowData = resultSet.getString(1)
            Log.i("comment", tempStr)
        }
    }

    suspend fun customLog(message: String): Unit {
        Log.d("AppTest_Customers : ", message)
//        CoroutineScope(Dispatchers.Main).launch {
//            // MainActivity().customLog(message)
//            //(activity as MainActivity)!!.customLog("Invalid Transaction Details")
//        }
    }

    companion object {
        private const val ip = "192.168.29.31"
        //"49.37.151.194" "2405:201:c00e:482f:cc94:93ed:8eea:272c" "192.168.29.31"
        private const val port = "1433"
        private const val Classes = "net.sourceforge.jtds.jdbc.Driver"
        private const val database = "AndroidTestDB"
        private const val username = "Android"
        private const val password = "Android"
        private const val url = "jdbc:jtds:sqlserver://" + ip + ":" + port + "/" + database
    }
}