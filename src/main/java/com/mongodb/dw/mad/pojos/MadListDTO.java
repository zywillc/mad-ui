package com.mongodb.dw.mad.pojos;

import lombok.Data;

import java.util.List;

@Data
public class MadListDTO {
    private final List<MadDTO> mads;
}
