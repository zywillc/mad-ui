package com.mongodb.dw.mad.pojos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiResObject {
    private List<Link> links;

    private List<DbUser> results;

    private int totalcounts;
}
