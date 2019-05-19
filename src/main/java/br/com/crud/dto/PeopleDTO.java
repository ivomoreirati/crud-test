package br.com.crud.dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import br.com.crud.entities.Address;
import br.com.crud.entities.Phone;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonInclude(value=Include.NON_NULL)
public class PeopleDTO
{
	@NotNull
	@ApiModelProperty(dataType = "String", required = true, example = "96932378892")
	private String cpf;

	@NotNull
	@NotEmpty
	@ApiModelProperty(dataType = "String", required = true, example = "João Pedro")
	private String name;

	@ApiModelProperty(dataType = "List")
	private List<Phone> phones = new ArrayList<>();

	@ApiModelProperty(dataType = "Address")
	private Address address;
}
