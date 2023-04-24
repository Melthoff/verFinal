package com.example.springsecurityapplication.services;

import com.example.springsecurityapplication.models.Person;
import com.example.springsecurityapplication.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PersonService {
    private final PersonRepository personRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public PersonService(PersonRepository personRepository, PasswordEncoder passwordEncoder) {
        this.personRepository = personRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Person findByLogin(Person person) {
        Optional<Person> person_db = personRepository.findByLogin(person.getLogin());
        return person_db.orElse(null);
    }

    @Transactional
    public void register(Person person) {
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        person.setRole("ROLE_USER");
        personRepository.save(person);
    }

    public List<Person> getAll() {
        return personRepository.findAll();
    }

    public void makeAdmin(int id) {
        Optional<Person> byId = personRepository.findById(id);
        if (byId.isPresent()) {
            Person person = byId.get();
            person.setRole("ROLE_ADMIN");
            personRepository.save(person);
        }
    }

    public void unMakeAdmin(int id) {
        Optional<Person> byId = personRepository.findById(id);
        if (byId.isPresent()) {
            Person person = byId.get();
            person.setRole("ROLE_USER");
            personRepository.save(person);
        }
    }
}
