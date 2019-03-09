package com.graphql.second.pojo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="book")
@Setter
@Getter
public class BookPo {

    @Id
    @GeneratedValue
    private Long id;
    @Column(name="userid")
    private Long userid;
    @Column(name="bookname")
    private Long bookname;
    @Column(name="bookprice")
    private Long bookprice;
}
