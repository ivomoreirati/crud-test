package br.com.crud.services.impl;

import br.com.crud.dto.PeopleDTO;
import br.com.crud.entities.People;
import br.com.crud.exceptions.PeopleBadRequestException;
import br.com.crud.repositories.PeopleRepository;
import br.com.crud.services.PeopleService;
import br.com.crud.util.DateUtil;
import br.com.crud.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PeopleServiceImpl implements PeopleService {

	private static final Logger logger = LoggerFactory.getLogger(PeopleServiceImpl.class);

	@Autowired
	private PeopleRepository peopleRepository;

	@Autowired
	private ModelMapper mapper;

	@Autowired
	private ValidationUtil validationUtil;

	public void createPeople(PeopleDTO peopleDTO) {
		logger.debug("PeopleService createPeople - start. PeopleDTO request {}", peopleDTO);

		if(!validationUtil.isValid(peopleDTO).equals("")){
			throw new PeopleBadRequestException(validationUtil.isValid(peopleDTO));
		}
		if(!ValidationUtil.isValidCPF(peopleDTO.getCpf())){
			throw new PeopleBadRequestException("Please input valid cpf !");
		}
		createDBPeople(peopleDTO);
		logger.debug("PeopleService createPeople - end.");
	}

	private void createDBPeople(PeopleDTO peopleDTO) {
		LocalDateTime parseTimestamp = DateUtil.now();
		People people = getPeopleByCpf(peopleDTO.getCpf());
		if(people == null) {
			People entity = mapper.map(peopleDTO, People.class);

			entity.setCreatedAt(parseTimestamp);
			this.peopleRepository.save(entity);
			logger.debug("PeopleService createDBPeople - People created: {}", entity.getName());
			return;
		}

		throw new PeopleBadRequestException("People %s for cpf %s already exists in database", peopleDTO.getName(), peopleDTO
				.getCpf());
	}

	public People updatePeople(PeopleDTO peopleDTO, String cpf) {
		logger.debug("PeopleService updatePeople - PeopleDTO request {}, id {}", peopleDTO, cpf);

		if(!ValidationUtil.isValidCPF(cpf)){
			throw new PeopleBadRequestException("Please input valid cpf !");
		}

		if(peopleDTO != null) {
			if (peopleDTO.getName() == null) {
				throw new PeopleBadRequestException("At Least the field name is necessary!");
			}
		}else {
			throw new PeopleBadRequestException("At Least the field name is necessary!");
		}
		
		return updateDBPeople(peopleDTO, cpf);
	}

	private People updateDBPeople(PeopleDTO peopleDTO, String cpf) {
		LocalDateTime parseTimestamp = DateUtil.now();
		People queryPeople = getPeopleByCpf(cpf);
		if(queryPeople != null) {
			queryPeople.setName(peopleDTO.getName());
			queryPeople.setPhones(peopleDTO.getPhones());
			queryPeople.setAddress(peopleDTO.getAddress());
			queryPeople.setModifiedAt(parseTimestamp);
			this.peopleRepository.save(queryPeople);
			
			logger.debug("PeopleService updateDBPeople - People updated: {}.", queryPeople.getName());
			return queryPeople;
		} else {
			throw new PeopleBadRequestException("Cpf %s not exists in database", cpf);
		}
	}

	public People getPeopleByCpf(String cpf) {
		logger.debug("PeopleService getPeopleByCpf - cpf {}", cpf);
		Optional<People> people = this.peopleRepository.findOneByCpf(cpf);
		return people.orElse(null);
	}

	public List<People> getPeoples() {
		return this.peopleRepository.findAll();
	}

	public void deletePeople(String cpf) {
		if(!ValidationUtil.isValidCPF(cpf)){
			throw new PeopleBadRequestException("Please input valid cpf !");
		}
		List<People> peopleList = this.peopleRepository.findAllByCpf(cpf).orElse(null);
		if(peopleList !=null) {
			this.peopleRepository.deleteAll(peopleList);
		}else {
			throw new PeopleBadRequestException("Cpf %s not exists in database", cpf);
		}
	}
}
