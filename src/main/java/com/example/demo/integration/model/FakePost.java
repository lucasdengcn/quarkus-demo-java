package com.example.demo.integration.model;

import lombok.Builder;
import lombok.Data;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Data
@Builder
@Schema
public class FakePost {

    @Schema
    private Integer id;

    @Schema
    private Integer userId;

    @Schema
    private String title;

    @Schema
    private String body;
}
