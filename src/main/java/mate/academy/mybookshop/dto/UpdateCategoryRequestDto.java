package mate.academy.mybookshop.dto;

import jakarta.validation.constraints.NotBlank;

public record UpdateCategoryRequestDto(@NotBlank String name, String description) {
}
