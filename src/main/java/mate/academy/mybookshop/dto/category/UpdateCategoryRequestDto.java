package mate.academy.mybookshop.dto.category;

import jakarta.validation.constraints.NotBlank;

public record UpdateCategoryRequestDto(@NotBlank String name, String description) {
}
