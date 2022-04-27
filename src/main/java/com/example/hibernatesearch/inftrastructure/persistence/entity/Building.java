package com.example.hibernatesearch.inftrastructure.persistence.entity;

import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.search.annotations.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Table(name = "building")
@Indexed
@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
public class Building implements Serializable {
    @Id
    private String id;
    @Field(termVector = TermVector.YES)
    private String name;

    private Integer totalFloor;

    @Field(termVector = TermVector.YES)
    private String code;

    @Field(termVector = TermVector.YES)
    private String address;
    private Boolean deleted;
    private Float area;

    @Field(termVector = TermVector.YES)
    @Type(type = "text")
    private String note;

}
