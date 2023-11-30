package com.example.myfamily

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
@Dao // data access object

interface ContactDao {

    // suspend -> ye main thread per kabi nhi chalta y coroutines me hi chalta he
    // ye batata he ye func zra bad me hi return kere ga ya kaam kere ga
    // is per hum pause , resume , stop ya basic set of operations perform ker sakte he
    // ye single contact insertion he
    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun insert(contactsModel: ContactsModel)

    //ye list of contacts insertion he

    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun insertAll(contactsModel: List<ContactsModel>)
    @Query("SELECT * FROM ContactsModel")
    // liveData -> jha per b database me koi b changing ho jese insertion or deletion wagera live data hume jha jha getAllContacts() function use hua he wha per notify ker de ga
    fun getAllContacts(): LiveData<List<ContactsModel>>

}