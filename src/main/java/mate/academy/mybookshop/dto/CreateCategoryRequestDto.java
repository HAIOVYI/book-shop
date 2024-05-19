package mate.academy.mybookshop.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateCategoryRequestDto(@NotBlank String name, String description) {
}
