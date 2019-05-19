package br.com.crud.services;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.crud.dto.PeopleDTO;
import br.com.crud.entities.People;
import br.com.crud.exceptions.PeopleBadRequestException;
import br.com.crud.repositories.PeopleRepository;

public interface PeopleService {

	void createPeople(PeopleDTO peopleDTO);

	People updatePeople(PeopleDTO peopleDTO, String cpf);

	People getPeopleByCpf(String cpf);

	List<People> getPeoples();

	void deletePeople(String cpf);
}
