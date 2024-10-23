package com.example.demo.model.common;

import lombok.Builder;
import lombok.Data;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Schema
@Data
@Builder
public class SuccessOutput {

    @Schema(description = "operation status", example = "true")
    private boolean success = true;

}
