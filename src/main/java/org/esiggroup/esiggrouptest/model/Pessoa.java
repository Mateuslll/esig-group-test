package org.esiggroup.esiggrouptest.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "pessoa_salario_consolidado")
public class Pessoa implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pessoa_id")
    private int pessoaId;
    @Column(name = "nome_pessoa")
    private String nomePessoa;
    @Column(name = "nome_cargo")
    private String nomeCargo;
    @Column(name = "salario")
    private double salario;

    // Getters e setters

    public int getPessoaId() {
        return pessoaId;
    }

    public void setPessoaId(int pessoaId) {
        this.pessoaId = pessoaId;
    }

    public String getNomePessoa() {
        return nomePessoa;
    }

    public void setNomePessoa(String nomePessoa) {
        this.nomePessoa = nomePessoa;
    }

    public String getNomeCargo() {
        return nomeCargo;
    }

    public void setNomeCargo(String nomeCargo) {
        this.nomeCargo = nomeCargo;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }
}
