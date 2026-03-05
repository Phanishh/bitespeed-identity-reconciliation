package com.Phanish.Bitespeed.Project.service;



import com.Phanish.Bitespeed.Project.dto.IdentifyRequest;
import com.Phanish.Bitespeed.Project.dto.IdentifyResponse;
import com.Phanish.Bitespeed.Project.entity.Contact;
import com.Phanish.Bitespeed.Project.repository.ContactRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ContactService {

    private final ContactRepository repo;

    public ContactService(ContactRepository repo){
        this.repo = repo;
    }

    public IdentifyResponse identify(IdentifyRequest request){

        String email = request.getEmail();
        String phone = request.getPhoneNumber();

        List<Contact> matches = repo.findByEmailOrPhoneNumber(email,phone);

        if(matches.isEmpty()){
            Contact contact = new Contact();
            contact.setEmail(email);
            contact.setPhoneNumber(phone);
            contact.setLinkPrecedence("primary");
            repo.save(contact);

            return buildResponse(contact, List.of(contact));
        }

        Contact primary = matches.stream()
                .min(Comparator.comparing(Contact::getCreatedAt))
                .get();

        List<Contact> allContacts = new ArrayList<>(matches);

        List<Contact> secondaries = repo.findByLinkedId(primary.getId());
        allContacts.addAll(secondaries);

        boolean newInfo = allContacts.stream()
                .noneMatch(c ->
                        Objects.equals(c.getEmail(),email) &&
                                Objects.equals(c.getPhoneNumber(),phone)
                );

        if(newInfo){
            Contact secondary = new Contact();
            secondary.setEmail(email);
            secondary.setPhoneNumber(phone);
            secondary.setLinkPrecedence("secondary");
            secondary.setLinkedId(primary.getId());
            repo.save(secondary);

            allContacts.add(secondary);
        }

        return buildResponse(primary,allContacts);
    }

    private IdentifyResponse buildResponse(Contact primary,List<Contact> contacts){

        IdentifyResponse response = new IdentifyResponse();
        IdentifyResponse.ContactResponse cr = new IdentifyResponse.ContactResponse();

        cr.setPrimaryContactId(primary.getId());

        cr.setEmails(
                contacts.stream()
                        .map(Contact::getEmail)
                        .filter(Objects::nonNull)
                        .distinct()
                        .collect(Collectors.toList())
        );

        cr.setPhoneNumbers(
                contacts.stream()
                        .map(Contact::getPhoneNumber)
                        .filter(Objects::nonNull)
                        .distinct()
                        .collect(Collectors.toList())
        );

        cr.setSecondaryContactIds(
                contacts.stream()
                        .filter(c -> "secondary".equals(c.getLinkPrecedence()))
                        .map(Contact::getId)
                        .distinct()
                        .collect(Collectors.toList())
        );

        response.setContact(cr);

        return response;
    }
}