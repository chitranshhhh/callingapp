package com.example.callingapp

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.callingapp.models.PermissionHandler

class ContactsFragment : Fragment(R.layout.fragment_contacts) {

    private lateinit var contactsAdapter: ContactsAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var contactsManager: ContactsManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_contacts, container, false)
        recyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        contactsManager = ContactsManager(requireContext())
        contactsAdapter = ContactsAdapter()
        recyclerView.adapter = contactsAdapter

        loadContacts()

        return view
    }
    

    private fun loadContacts() {
        val context = requireContext()

        val permissionHandler = PermissionHandler(context)
        if (permissionHandler.checkPermission(Manifest.permission.READ_CONTACTS)) {
            val contacts = contactsManager.getContacts() // Now directly get the List<Contact>
            contactsAdapter.submitList(contacts) // Submit the list directly
        } else {
            Toast.makeText(
                context,
                "Permission to access contacts not granted",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}
