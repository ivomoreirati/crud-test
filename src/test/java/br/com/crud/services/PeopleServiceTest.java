package br.com.crud.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.crud.dto.PeopleDTO;
import br.com.crud.entities.People;
import br.com.crud.exceptions.PeopleBadRequestException;
import br.com.crud.repositories.PeopleRepository;
import br.com.crud.services.impl.PeopleServiceImpl;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PeopleServiceTest
{
    private static final Logger logger = LoggerFactory.getLogger(PeopleServiceTest.class);

    @Autowired
    private PeopleServiceImpl service;

    @MockBean
    private PeopleRepository peopleRepository;

    private static final String CPF = "26638683101";

    @Autowired
    private ObjectMapper mapper;

    private PeopleDTO createPeople;

    private People people;

    private PeopleDTO peopleWithoutPhone;

    private PeopleDTO invalidPeople;

    private List<People> peoples;

    public <T> T getObjectFromFile(String path, Class<T> objectClass) throws IOException {
        File file = new ClassPathResource(path).getFile();
        return mapper.readValue(file, objectClass);
    }

    public String createPayload(String path) {
        File file = null;
        String payload = null;
        try {
            file = new ClassPathResource(path).getFile();
            payload = new String(Files.readAllBytes(Paths.get(file.getPath())));
        } catch (Exception e) {
            logger.error("Error loading file - error:{} ", e.getMessage());
            throw new RuntimeException(e);
        }
        return payload;
    }

    @Before
    public void setUp() throws Exception {
        createPeople = getObjectFromFile("json/createPeople.json", PeopleDTO.class);
        peopleWithoutPhone = getObjectFromFile("json/createPeopleWithoutPhone.json", PeopleDTO.class);
        people = getObjectFromFile("json/returnPeople.json", People.class);
        peoples = getObjectFromFile("json/returnPeoples.json", ArrayList.class);
        invalidPeople = getObjectFromFile("json/createPeopleInvalid.json", PeopleDTO.class);
    }

    @Test
    public void createPeople() {
        this.service.createPeople(createPeople);
        verify(peopleRepository).save(Mockito.any());
    }

    @Test
    public void createPeopleWithoutPhone() {
        this.service.createPeople(peopleWithoutPhone);
        verify(peopleRepository).save(Mockito.any());
    }

    @Test(expected = PeopleBadRequestException.class)
    public void createPeopleNoValidCpf() {
        this.service.createPeople(invalidPeople);
    }

    @Test
    public void delete() {
        when(peopleRepository.findPeopleByCpf(CPF)).thenReturn(Optional.of(people));
        this.service.deletePeople(CPF);
        verify(this.peopleRepository).delete(Mockito.any());
    }

    @Test
    public void getById() {
        when(this.peopleRepository.findPeopleByCpf(CPF)).thenReturn(Optional.of(people));
        People people = this.service.getPeopleByCpf(CPF);
        assertNotNull(people);
    }

    @Test
    public void getAll() {
        when(this.peopleRepository.findAll()).thenReturn(peoples);
        List<People> peoples = this.service.getPeoples();
        assertFalse(peoples.isEmpty());
    }


    @Test
    public void getAllFail() {
        List<People> peoples = this.service.getPeoples();
        assertTrue(peoples.isEmpty());
    }
}