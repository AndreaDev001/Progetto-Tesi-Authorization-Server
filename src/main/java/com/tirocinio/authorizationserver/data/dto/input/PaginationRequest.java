package com.tirocinio.authorizationserver.data.dto.input;


import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaginationRequest
{
    @NotNull
    @Positive
    private Integer page;

    @NotNull
    @Positive
    @Max(20)
    private Integer pageSize;

    public Pageable toPageable() {
        return PageRequest.of(page,pageSize);
    }
}
