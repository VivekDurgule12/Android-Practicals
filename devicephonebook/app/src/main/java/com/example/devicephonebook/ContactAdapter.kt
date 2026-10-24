package com.example.devicephonebook

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.devicephonebook.databinding.ItemContactBinding

class ContactAdapter(
    private var contacts: List<ContactItem>,
    private val onContactSelected: (ContactItem) -> Unit,
) : RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {

    private var selectedPosition = RecyclerView.NO_POSITION

    class ContactViewHolder(val binding: ItemContactBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val binding = ItemContactBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ContactViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val contact = contacts[position]
        with(holder.binding) {
            tvContactName.text = contact.name
            tvContactNumber.text = contact.phoneNumber
            tvAvatar.text = contact.name.trim().takeIf { it.isNotEmpty() }?.substring(0, 1)?.uppercase() ?: "?"

            cardContact.isChecked = (position == selectedPosition)

            cardContact.setOnClickListener {
                val currentPos = holder.bindingAdapterPosition
                if (currentPos != RecyclerView.NO_POSITION) {
                    val previousSelected = selectedPosition
                    selectedPosition = currentPos
                    if (previousSelected != RecyclerView.NO_POSITION) {
                        notifyItemChanged(previousSelected)
                    }
                    notifyItemChanged(selectedPosition)

                    onContactSelected(contact)
                }
            }
        }
    }

    override fun getItemCount(): Int = contacts.size

    fun updateContacts(newContacts: List<ContactItem>) {
        contacts = newContacts
        selectedPosition = RecyclerView.NO_POSITION
        notifyDataSetChanged()
    }
}
