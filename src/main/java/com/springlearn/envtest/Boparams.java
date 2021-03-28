package com.springlearn.envtest;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@IdClass(Boparams.PKClass.class)
public class Boparams {

    @Id
    String name_;
    @Id
    String value_;

    @Column(nullable = true)
    private char secured_ = 'N';

    @Data
    @NoArgsConstructor
    public static class PKClass implements Serializable {
        String name_;
        String value_;
    }

}