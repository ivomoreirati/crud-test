package br.com.crud.entities;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Document(collection = "people")
public class People extends BaseEntity {

	@Id
	private String id;

	@NotNull
	@NotEmpty
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
