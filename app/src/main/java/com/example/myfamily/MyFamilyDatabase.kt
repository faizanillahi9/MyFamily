package com.example.myfamily

import android.content.Context
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Room
import androidx.room.RoomDatabase

@Entity
@Database(entities = [ContactsModel::class], version = 1, exportSchema = false)
public abstract class MyFamilyDatabase : RoomDatabase() {

    abstract fun contactDao(): ContactDao

    // singleton classes -> ye aesi classes he jo ek hi baar instance banati he or phir us instance ko ham repeatedly use ker lete he in all over our application
    // hum database ka ek instance bana ker usko preserve ker lete he or phir usko bar bar use ker lete he

    // companion object -> ye aesa scope hota he jis ke andar jo functions hote he vo all over the app use ho sakte he yeni har jga accessible hote he
    companion object {
        // hum database ka ek instance bana ker usko preserve ker lete he or phir usko bar bar use ker lete he
        @Volatile // ye hamare instance ko "Thread safe" banta he yeni hum apne instance kisi b thread se access ker sakte he
        private var INSTANCE: MyFamilyDatabase? = null
        fun getDatabase(context: Context): MyFamilyDatabase {
            // it checks if Instance null nhi he to is scope ke andar ajao or phir is INSTANCE ki ek temporary copy bna leta " it " ke name se
            INSTANCE?.let {
                return it
            }
            //  if INSTANCE null nikalta he to hum synchronized call me instance create ker rhe he
            return synchronized(MyFamilyDatabase::class.java) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MyFamilyDatabase::class.java,
                    "my_family_db"
                ).build()
                INSTANCE =
                    instance // ye ek baar instance bna dega to instance ki koi value ban jye gi phir agli dfa instance null nhi aye ga to ye oper se hi return ho jye ga
                instance// return instance
            }


        }
    }
}