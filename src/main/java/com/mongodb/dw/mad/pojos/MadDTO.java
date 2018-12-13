package com.mongodb.dw.mad.pojos;

import lombok.Data;

import java.util.List;
import javax.validation.constraints.NotNull;

@Data(staticConstructor = "of")
public class MadDTO {
    @NotNull
    private String accessType;

    @NotNull
    private String cluster;

    @NotNull
    private String database;

    @NotNull
    private String collection;

    private List<String> tags;

    @NotNull
    private String title;

    @NotNull
    private String description;

    private String approvalPolicy;
}
