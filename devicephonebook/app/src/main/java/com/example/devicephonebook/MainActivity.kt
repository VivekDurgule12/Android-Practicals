package com.example.devicephonebook

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.devicephonebook.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var contactAdapter: ContactAdapter
    private var selectedContact: ContactItem? = null

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            loadContacts()
        } else {
            showPermissionDeniedUI()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupRecyclerView()
        setupListeners()
        checkPermissionAndLoadContacts()
    }

    private fun setupRecyclerView() {
        contactAdapter = ContactAdapter(emptyList()) { contact ->
            selectedContact = contact
            binding.tvSelectedName.text = contact.name
            binding.tvSelectedNumber.text = contact.phoneNumber
            binding.btnCall.isEnabled = contact.phoneNumber.isNotBlank()
        }
        binding.rvContacts.adapter = contactAdapter
    }

    private fun setupListeners() {
        binding.btnCall.setOnClickListener {
            val contact = selectedContact ?: return@setOnClickListener
            if (contact.phoneNumber.isNotBlank()) {
                val dialIntent = Intent(Intent.ACTION_DIAL).apply {
                    data = Uri.parse("tel:${contact.phoneNumber}")
                }
                startActivity(dialIntent)
            }
        }

        binding.btnPermission.setOnClickListener {
            requestPermissionLauncher.launch(Manifest.permission.READ_CONTACTS)
        }
    }

    private fun checkPermissionAndLoadContacts() {
        when {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_CONTACTS
            ) == PackageManager.PERMISSION_GRANTED -> {
                loadContacts()
            }
            else -> {
                requestPermissionLauncher.launch(Manifest.permission.READ_CONTACTS)
            }
        }
    }

    private fun loadContacts() {
        val contactsList = mutableListOf<ContactItem>()

        val projection = arrayOf(
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.NUMBER
        )

        val sortOrder = "${ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME} ASC"

        val cursor: Cursor? = contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            projection,
            null,
            null,
            sortOrder
        )

        cursor?.use {
            val nameIndex = it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
            val numberIndex = it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)

            while (it.moveToNext()) {
                val name = if (nameIndex != -1) it.getString(nameIndex) ?: "Unknown" else "Unknown"
                val number = if (numberIndex != -1) it.getString(numberIndex) ?: "" else ""

                if (number.isNotBlank()) {
                    contactsList.add(ContactItem(name, number))
                }
            }
        }

        if (contactsList.isEmpty()) {
            showEmptyStateUI()
        } else {
            showContactsListUI(contactsList)
        }
    }

    private fun showContactsListUI(contacts: List<ContactItem>) {
        binding.layoutStatus.visibility = View.GONE
        binding.rvContacts.visibility = View.VISIBLE
        contactAdapter.updateContacts(contacts)
    }

    private fun showPermissionDeniedUI() {
        binding.rvContacts.visibility = View.GONE
        binding.layoutStatus.visibility = View.VISIBLE
        binding.tvStatusMessage.setText(R.string.permission_denied_message)
        binding.btnPermission.visibility = View.VISIBLE
    }

    private fun showEmptyStateUI() {
        binding.rvContacts.visibility = View.GONE
        binding.layoutStatus.visibility = View.VISIBLE
        binding.tvStatusMessage.setText(R.string.no_contacts_found)
        binding.btnPermission.visibility = View.GONE
    }
}
