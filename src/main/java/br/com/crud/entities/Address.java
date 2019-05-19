package br.com.crud.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Address {

	@ApiModelProperty(dataType = "String", example = "Avenue Leonardo da Vinci")
	private String description;

	@ApiModelProperty(dataType = "String", example = "372")
	private String number;

	@ApiModelProperty(dataType = "String", example = "04313001")
	private String zipcode;

}
