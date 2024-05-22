package mate.academy.mybookshop.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.util.Set;
import lombok.Data;

@Data
public class UpdateBookRequestDto {
    @NotBlank
    @NotNull
    private String title;
    @NotBlank
    @NotNull
    private String author;
    @Pattern(regexp = "\\d{13}", message = "must contain exactly 13 digits")
    private String isbn;
    @Min(value = 0)
    @NotNull
    private BigDecimal price;
    private String description;
    private String coverImage;
    @NotEmpty
    private Set<Long> categoryIds;
}
