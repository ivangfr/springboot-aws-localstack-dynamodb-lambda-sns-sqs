package com.mycompany.producerservice.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CreateNewsRequest {

    @NotBlank
    @Schema(example = "Brazil's Palmeiras win title for second year")
    private String title;
}
