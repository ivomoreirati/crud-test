package br.com.crud.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Phone
{

	@ApiModelProperty(dataType = "String", example = "11")
	private String ddd;

	@ApiModelProperty(dataType = "String", example = "9999378679")
	private String number;

}
