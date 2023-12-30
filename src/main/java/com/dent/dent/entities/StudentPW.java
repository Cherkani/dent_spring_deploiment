package com.dent.dent.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentPW {
    @EmbeddedId
    private StudentPWPK id;
    private String time;

    @Temporal(TemporalType.DATE)
    private Date date;

    @ManyToOne
    @JoinColumn(name="student_id",referencedColumnName="id",insertable = false,updatable = false)
    @JsonIgnoreProperties("studentPWS")
    private Student student;

    @ManyToOne
    @JoinColumn(name="pw_id",referencedColumnName="id",insertable = false,updatable = false)
    private PW pw;

    public StudentPW(StudentPWPK studentPWPK) {
        this.id = studentPWPK;
    }
}
