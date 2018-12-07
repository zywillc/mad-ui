package com.mongodb.dw.mad.pojos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class DbUser {

    private String databaseName;
    private String groupId;
    private String ldapAuthType;
    private List<Link> links;
    private List<Role> roles;
    private String username;
}
