package com.example.demo.model.common;

import lombok.Builder;
import lombok.Data;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Schema
@Data
@Builder
public class DangerOpOutput {

    @Schema(description = "operation status", example = "true")
    private boolean success = true;

    @Schema(description = "failed reason", example = "due to some reason")
    private String reason;

    @Schema(description = "business rule code", example = "order-r001")
    private String businessCode;

}
