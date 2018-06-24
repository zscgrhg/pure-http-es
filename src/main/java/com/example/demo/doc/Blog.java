package com.example.demo.doc;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Blog {
    //@JsonIgnore
    private Long id;
    private String title;
    private String text;
    private Date date;
    private int count;
}
