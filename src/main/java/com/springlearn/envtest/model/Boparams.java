package com.springlearn.envtest.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "BOPARAMS")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Boparams {

    @Id
    String name_;

    String value_;

    @Column(nullable = true)
    private boolean secured_ = false;

}