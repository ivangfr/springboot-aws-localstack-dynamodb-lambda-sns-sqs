package com.ivanfranchin.newsproducer.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateNewsRequest {

    @NotBlank
    @Schema(example = "Copa Libertadores: Brazil's Palmeiras win title for second year")
    private String title;
}
