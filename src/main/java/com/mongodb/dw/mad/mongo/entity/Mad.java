package com.mongodb.dw.mad.mongo.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.persistence.Entity;

@Data
@NoArgsConstructor
@ToString(includeFieldNames=true)
@Entity
@Document(collection = "access")
public class Mad {

    @Id
    private String id;

    private String accessType;
    private String cluster;
    private String database;
    private String collection;
    @Field
    private List<String> tags = new ArrayList<>();
    private String title;
    private String mongodb_srv;
    private String description;
    private List<String> ldapGroup;

    private Mad(Builder builder) {
        this.id = builder.id;
        this.accessType = builder.accessType;
        this.cluster = builder.cluster;
        this.mongodb_srv = builder.mongodb_srv;
        this.database = builder.database;
        this.mongodb_srv = builder.mongodb_srv;
        this.collection = builder.collection;
        this.description = builder.description;
        this.title = builder.title;
        this.ldapGroup = builder.ldapGroup;
        this.tags = builder.tags;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String id;
        private String accessType;
        private String cluster;
        private String database;
        private String collection;
        private List<String> tags;
        private String title;
        private String description;
        private List<String> ldapGroup;
        private String mongodb_srv;

        private Builder() {}

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder accesstype(String at) {
            this.accessType = at;
            return this;
        }

        public Builder cluster(String cluster) {
            this.cluster = cluster;
            return this;
        }

        public Builder mongosrv(String srv) {
            this.mongodb_srv = srv;
            return this;
        }

        public Builder database(String db) {
            this.database = db;
            return this;
        }

        public Builder collection(String coll) {
            this.collection = coll;
            return this;
        }

        public Builder ldapGrop(String... lg) {
            List<String> ldapGroup = Arrays.asList(lg);
            this.ldapGroup = ldapGroup;
            return this;
        }

        public Builder description(String desc) {
            this.description = desc;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder tags(List<String> tags) {
            this.tags = tags;
            return this;
        }

        public Mad build() {
            Mad mad = new Mad(this);
            return mad;
        }
    }


}
