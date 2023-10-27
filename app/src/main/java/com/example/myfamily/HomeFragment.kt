package com.example.myfamily

import android.os.Bundle
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

        // fetching contacts
        fetchContacts()


        // list of  contacts and invite recycler view adapter  stuff
        val listContacts = listOf<ContactsModel>(
            ContactsModel("Akram", 12121315),
            ContactsModel("Khadija", 464631),
            ContactsModel("Muneeb", 252258523),
            ContactsModel("Ashraf", 8989781),
            ContactsModel("Kanwal", 78218),
            ContactsModel("Mubshir", 77781112),
            ContactsModel("Edward", 994512),
            ContactsModel("Neilophar", 21363121)
        )

        val inviteAdapter = InviteAdapter(listContacts)
        val inviteRecycler = requireView().findViewById<RecyclerView>(R.id.recycler_invite)
        inviteRecycler.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
        inviteRecycler.adapter = inviteAdapter

    }

    private fun fetchContacts() {
        TODO("Not yet implemented")
    }

    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment()
    }
}
