package com.example.myfamily

import android.os.Bundle
import android.provider.ContactsContract
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class HomeFragment : Fragment() {
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
        val inviteAdapter = InviteAdapter(fetchContacts()) // fetching contacts

        val inviteRecycler = requireView().findViewById<RecyclerView>(R.id.recycler_invite)
        inviteRecycler.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        inviteRecycler.adapter = inviteAdapter

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
