package com.example.myfamily

import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeFragment : Fragment() {
    lateinit var inviteAdapter: InviteAdapter

    // ARRAY LIST FOR STORING CONTACTS WHEN RETURNS FROM COROUNTINES/ IO THREAD
    private val listContacts: ArrayList<ContactsModel> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // giving data to recycler view of home frag and of member recycler view adapter stuff
        val listMembers = listOf<MemberModel>(
            MemberModel("Ahmad", "Lahore", 35, "334M"),
            MemberModel("Imran", "Gujrat", 55, "4334M"),
            MemberModel("Faisal", "Sindh", 55, "7334M"),
            MemberModel("Deepika", "Heart", 55, "2334M"),
            MemberModel("Sunny", "Mind", 55, "134M"),
        )

        val adapter = MemberAdapter(listMembers)

        val recycler = requireView().findViewById<RecyclerView>(R.id.recycler_member)
        recycler.layoutManager = LinearLayoutManager(requireContext())
        recycler.adapter = adapter

        // invite adapter or contacts recyclerview adapter
        Log.d("fetchcontacts53", "fetchcontacts: start ${listContacts.size} ")
        inviteAdapter =
            InviteAdapter(listContacts) // initializing invite adapter && adding listContacts to invite adapter
        fetchDatabaseContacts() // fetching contacts from database or ye function ek live observer he
        // ye ek observer call he jo UI ke baad load hota he

        Log.d("fetchcontacts53", "fetchcontacts: end  ")


        Log.d("fetchcontacts53", "fetchcontacts: start ")
        // threads -> Coroutines in Kotlin or Android
        CoroutineScope(Dispatchers.IO).launch {
            Log.d("fetchcontacts53", "fetchcontacts: start corountine")
            // ye scope background/IO thread per chale ga

//            listContacts.clear() // clearing the list contacts
//            listContacts.addAll(fetchDatabaseContacts()) // fetching contacts from database

            // insert contacts to database
            insertDatabaseContacts(fetchContacts())

            // notifyDataSetChanged() -> notify k  list or recyclerview me  changes hui he usko update ker lo
            // it is connected to recyclerview which is in "UI(main) thread"
            // so we have to switch from this "IO(background) thread" to "UI(main) thread"
//            withContext(Dispatchers.Main) {
//                inviteAdapter.notifyDataSetChanged()
//            } ab UI ko update nhi ker rhe is liye is ki zroorat nhi he ab


            Log.d("fetchcontacts53", "fetchcontacts: end corountine ${listContacts.size}")

        }


        // invite adapter or contacts recyclerview adapter
        val inviteRecycler = requireView().findViewById<RecyclerView>(R.id.recycler_invite)
        inviteRecycler.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        inviteRecycler.adapter = inviteAdapter

    }

    // fetching contacts from database or ye fun ek live observer he
    private fun fetchDatabaseContacts() {
        val database = MyFamilyDatabase.getDatabase(requireContext())
        // livedata ko use kerne ke liye hume us function ko observe kerna perta he
        database.contactDao().getAllContacts().observe(viewLifecycleOwner) {
            Log.d("fetchcontacts53", "fetchDatabaseContacts: ")
            listContacts.clear()
            listContacts.addAll(it)
            inviteAdapter.notifyDataSetChanged()

        }
    }

    // insert contacts to database
    private suspend fun insertDatabaseContacts(listContacts: ArrayList<ContactsModel>) {
        val database = MyFamilyDatabase.getDatabase(requireContext())
        database.contactDao().insertAll(listContacts)
    }

    // fetching contacts
    private fun fetchContacts(): ArrayList<ContactsModel> {
        // cr= content resolver -> ye hume contact provider se data la ker deta he
        val cr = requireActivity().contentResolver
        // cursor -> ye database se col by col data la ker deta he
        val cursor = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null)

        // declaring arrayList to store the fetched contacts
        // ContactsModel -> self made data class
        val listContacts: ArrayList<ContactsModel> = ArrayList()

        // check if the cursor has value or not
        if (cursor != null && cursor.count > 0) {
            cursor.moveToFirst()
            // cursor. moveToNext() -> ye batata he k kia cursor ke next me koi value he b k nhi
            while (cursor != null && cursor.moveToNext()) {
                // cursor.getColumnIndex ->  method returns the index of the specified column in the current row of the result set
                val id =
                    cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.Contacts._ID))
                val name =
                    cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME))
                val hasPhoneNo =
                    cursor.getInt(cursor.getColumnIndexOrThrow(ContactsContract.Contacts.HAS_PHONE_NUMBER)) // returns 0 or 1 accordingly
                if (hasPhoneNo > 0) {
                    // phone no are in  another database of so we have to fetch them from their
                    // pCur-> phone cursor
                    val pCur = cr.query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null,
                        // The ? placeholder is a parameter that will be replaced with the actual contact ID when the query is executed.
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "= ?",
                        arrayOf(id),
                        ""
                    )

                    if (pCur != null && pCur.count > 0) {
                        pCur.moveToFirst()
                        while (pCur != null && pCur.moveToNext()) {
                            val phoneNum =
                                pCur.getString(pCur.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER))
                            listContacts.add(ContactsModel(name, phoneNum))
                        }
                        pCur.close()
                    }
                }
            }

            if (cursor != null) {
                cursor.close()
            }
        }

        return listContacts
    }

    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment()
    }
}
