package com.example.addcontact

class ContactRepository(private val contactDao: ContactDao) {

    fun getContactsSortedByNameAsc() = contactDao.getContactsSortedByNameAsc()
    fun getContactsSortedByNameDesc() = contactDao.getContactsSortedByNameDesc()
    fun findContacts(query: String) = contactDao.findContacts(query)

    suspend fun insert(contact: Contact) = contactDao.insert(contact)
    suspend fun delete(contact: Contact) = contactDao.delete(contact)

}