package com.jongkeun.backend.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TodoUpdateDto {
    private String title;
    private String description;

    @NotNull(message = "완료 상태 지정")
    private Boolean completed;
}
