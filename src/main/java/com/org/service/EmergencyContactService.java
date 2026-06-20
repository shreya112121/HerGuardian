package com.org.service;

import com.org.model.EmergencyContact;
import com.org.repository.EmergencyContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EmergencyContactService {

    @Autowired
    private EmergencyContactRepository contactRepository;

    public List<EmergencyContact> getContactsByUserId(Long userId) {
        return contactRepository.findByUserId(userId);
    }

  
    public EmergencyContact addContact(EmergencyContact contact) {
        return contactRepository.save(contact);
    }

    public void deleteContact(Long contactId) {
        contactRepository.deleteById(contactId);
    }

  
    public int countContacts(Long userId) {
        return contactRepository.countByUserId(userId);
    }
}