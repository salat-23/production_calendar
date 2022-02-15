package com.example.demo.model;

import javax.persistence.*;

@Entity
@Table(name = "calendar_month")
public class Month {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "calendar_year")
    private Year year;

    @Column(name = "month_name")
    private String name;

    @Column(name = "month_number")
    private Integer number;

}
