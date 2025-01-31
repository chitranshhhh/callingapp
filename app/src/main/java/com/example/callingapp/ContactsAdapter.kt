package com.example.callingapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.callingapp.models.Contact

class ContactsAdapter : RecyclerView.Adapter<ContactsAdapter.ContactViewHolder>() {

    private val contacts = mutableListOf<Contact>()

    fun submitList(newContacts: Result<List<Contact>>) {

        newContacts.onSuccess {
            contacts.clear()
            contacts.addAll(it)  // Add contacts if the result is successful
            notifyDataSetChanged()
        }.onFailure {
            // Handle failure case, e.g., log or show a toast
            it.printStackTrace()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_contact, parent, false)
        return ContactViewHolder(view)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        holder.bind(contacts[position])
    }

    override fun getItemCount(): Int = contacts.size

    class ContactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.contact_name)
        private val phoneTextView: TextView = itemView.findViewById(R.id.contact_phone)

        fun bind(contact: Contact) {
            nameTextView.text = contact.name
            phoneTextView.text = contact.phoneNumber
        }
    }
}

