package com.dent.dent.entities;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Student extends User{
    private String number;
    @ManyToOne
    @JsonIgnoreProperties("student")
    private Groupe group;

    @OneToMany(mappedBy = "student")
    private List<StudentPW> studentPWS;

}
