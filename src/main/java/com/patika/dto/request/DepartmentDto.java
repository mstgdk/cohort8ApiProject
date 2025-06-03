package com.patika.dto.request;

import com.patika.entity.BaseResponse;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentDto extends BaseResponse {

    private Long id;

    private String departmentName;

    private String departmentCode;
}
