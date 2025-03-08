package com.ivanfranchin.newsproducer.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record CreateNewsRequest(
        @Schema(example = "Copa Libertadores: Brazil's Palmeiras win title for second year") @NotBlank String title) {
}
