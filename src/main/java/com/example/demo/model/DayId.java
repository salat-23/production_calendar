package com.example.demo.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Embeddable
public class DayId implements Serializable {

    @ManyToOne
    @Column(name = "calendar_month_id")
    private Month month;
    private Integer number;

}
