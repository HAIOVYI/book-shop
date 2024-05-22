package mate.academy.mybookshop.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateCategoryRequestDto(
        @NotBlank
        @Size(max = 255)
        String name,
        @Size(max = 65535)
        String description) {
}
