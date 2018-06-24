package com.example.demo.doc;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class Blog {
    //@JsonIgnore
    private Long id;
    private String title;
    private String text;
    private Date date;
}
