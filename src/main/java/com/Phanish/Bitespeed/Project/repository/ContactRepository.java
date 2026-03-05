package com.Phanish.Bitespeed.Project.repository;


import com.Phanish.Bitespeed.Project.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ContactRepository extends JpaRepository<Contact,Integer> {

    List<Contact> findByEmailOrPhoneNumber(String email,String phoneNumber);

    List<Contact> findByLinkedId(Integer linkedId);
}
