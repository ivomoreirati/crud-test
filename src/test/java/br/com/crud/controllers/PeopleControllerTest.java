package br.com.crud.controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.crud.dto.PeopleDTO;
import br.com.crud.entities.People;
import br.com.crud.services.impl.PeopleServiceImpl;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@EnableSpringDataWebSupport
@SpringBootTest
@AutoConfigureMockMvc
public class PeopleControllerTest {

    private static final Logger logger = LoggerFactory.getLogger(PeopleControllerTest.class);

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PeopleServiceImpl peopleService;

    @Autowired
    @InjectMocks
    private PeopleController controller;

    @Autowired
    private ObjectMapper mapper;

    private static final String CPF = "26638683101";

    private static final String ENDPOINT = "/v1/peoples";

    private PeopleDTO peopleDTO;

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
    public void setup() throws Exception {
        peopleDTO = getObjectFromFile("json/createPeople.json", PeopleDTO.class);
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void created() throws Exception {

        doNothing().when(peopleService).createPeople(Mockito.any(PeopleDTO.class));

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post(ENDPOINT).accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(peopleDTO)).contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder).andExpect(status().isOk()).andReturn();

    }

    @Test
    public void update() throws Exception {
        String uri = ENDPOINT+"/"+CPF;

        PeopleDTO peopleDTO = getObjectFromFile("json/createPeople.json", PeopleDTO.class);
        People createdPeople = getObjectFromFile("json/returnPeople.json", People.class);

        when(peopleService.updatePeople(peopleDTO, CPF)).thenReturn(createdPeople);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.put(uri).accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(peopleDTO)).contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder).andExpect(status().isOk()).andExpect(content().string(createPayload("json/returnPeople.json"))).andReturn();
    }

    @Test
    public void delete() throws Exception {
        String uri = ENDPOINT+ "/"+CPF;

        doNothing().when(peopleService).deletePeople(CPF);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.delete(uri).accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder).andExpect(status().isOk()).andReturn();
    }

    @Test
    public void get() throws Exception {
        String uri = ENDPOINT;

        List<People> peoples = getObjectFromFile("json/returnPeoples.json", ArrayList.class);

        when(peopleService.getPeoples()).thenReturn(peoples);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder).andExpect(status().isOk()).andExpect(content().string(createPayload("json/returnPeoples.json"))).andReturn();
    }

    @Test
    public void getById() throws Exception {
        String uri = ENDPOINT + "/"+CPF;

        People people = getObjectFromFile("json/returnPeople.json", People.class);
        when(peopleService.getPeopleByCpf(CPF)).thenReturn(people);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder).andExpect(status().isOk()).andExpect(content().string(createPayload("json/returnPeople.json"))).andReturn();
    }
}