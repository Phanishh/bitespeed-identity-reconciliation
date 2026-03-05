package com.Phanish.Bitespeed.Project.dto;

import java.util.List;

public class IdentifyResponse {

    private ContactResponse contact;

    public ContactResponse getContact() {
        return contact;
    }

    public void setContact(ContactResponse contact) {
        this.contact = contact;
    }

    public static class ContactResponse {

        private Integer primaryContactId;
        private List<String> emails;
        private List<String> phoneNumbers;
        private List<Integer> secondaryContactIds;

        public Integer getPrimaryContactId() {
            return primaryContactId;
        }

        public void setPrimaryContactId(Integer primaryContactId) {
            this.primaryContactId = primaryContactId;
        }

        public List<String> getEmails() {
            return emails;
        }

        public void setEmails(List<String> emails) {
            this.emails = emails;
        }

        public List<String> getPhoneNumbers() {
            return phoneNumbers;
        }

        public void setPhoneNumbers(List<String> phoneNumbers) {
            this.phoneNumbers = phoneNumbers;
        }

        public List<Integer> getSecondaryContactIds() {
            return secondaryContactIds;
        }

        public void setSecondaryContactIds(List<Integer> secondaryContactIds) {
            this.secondaryContactIds = secondaryContactIds;
        }
    }
}