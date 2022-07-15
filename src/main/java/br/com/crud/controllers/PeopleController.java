package br.com.crud.controllers;

import br.com.crud.dto.PeopleDTO;
import br.com.crud.entities.People;
import br.com.crud.exceptions.PeopleBadRequestException;
import br.com.crud.services.impl.PeopleServiceImpl;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api")
public class PeopleController
{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PeopleController.class);
	
	@Autowired
	private PeopleServiceImpl peopleService;
	
	@PostMapping(value="/v1/peoples")
	@ApiOperation(value = "Create people", response = String.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Create people sucessfully"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 500, message = "Internal Server Error, check the Body Error Response")

	})
	public ResponseEntity<String> createPeople(@RequestBody PeopleDTO peopleDTO) {
		LOGGER.debug("PeopleController createPeople - start. Param {}", peopleDTO);
		this.peopleService.createPeople(peopleDTO);
		LOGGER.debug("PeopleController createPeople - end.");
		return ResponseEntity.ok("Create people sucessfully!");
	}
	
	@GetMapping(value="/v1/peoples")
	@ApiOperation(value = "View peoples", response = People.class, responseContainer="List")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "View people sucessfully"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 500, message = "Internal Server Error, check the Body Error Response")

	})
	public ResponseEntity<List<People>> getPeoples() {
		LOGGER.debug("PeopleController getPeoples ");

		if(this.peopleService.getPeoples().isEmpty()){
			throw new PeopleBadRequestException("Not exists people in database!");
		}else{
			return ResponseEntity.ok(this.peopleService.getPeoples());
		}
	}
	
	@GetMapping(value="/v1/peoples/{cpf}")
	@ApiOperation(value = "View people by cpf", response = People.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "View people by cpf sucessfully"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 500, message = "Internal Server Error, check the Body Error Response")

	})
	public ResponseEntity<People> getPeopleByCpf(@PathVariable("cpf") String cpf) {
		LOGGER.debug("PeopleController getByCpf - Param {}", cpf);
		return ResponseEntity.ok(this.peopleService.getPeopleByCpf(cpf));
	}
	
	@PutMapping(value="/v1/peoples/{cpf}")
	@ApiOperation(value = "Update people by cpf", response = People.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Update people by cpf sucessfully"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 500, message = "Internal Server Error, check the Body Error Response")

	})
	public ResponseEntity<People> update(@PathVariable("cpf") String cpf, @RequestBody PeopleDTO peopleDTO) {
		LOGGER.debug("PeopleController update - Params {} - {}", cpf, peopleDTO);
		return ResponseEntity.ok(this.peopleService.updatePeople(peopleDTO, cpf));
	}
	
	@DeleteMapping(value="/v1/peoples/{cpf}")
	@ApiOperation(value = "Delete people by cpf", response = String.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Delete people by cpf sucessfully"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 500, message = "Internal Server Error, check the Body Error Response")

	})
	public ResponseEntity<String> delete(@PathVariable("cpf") String cpf){
		LOGGER.debug("PeopleController delete - start. Param {}", cpf);
		this.peopleService.deletePeople(cpf);
		LOGGER.debug("PeopleController delete - end.");
        return ResponseEntity.ok("Delete people by cpf sucessfully!");
	}

}
