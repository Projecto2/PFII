/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entidade;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author amed
 */
@Entity
@Table(name = "rh_categoria_profissional", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RhCategoriaProfissional.findAll", query = "SELECT r FROM RhCategoriaProfissional r"),
    @NamedQuery(name = "RhCategoriaProfissional.findByPkIdCategoriaProfissional", query = "SELECT r FROM RhCategoriaProfissional r WHERE r.pkIdCategoriaProfissional = :pkIdCategoriaProfissional"),
    @NamedQuery(name = "RhCategoriaProfissional.findByDescricao", query = "SELECT r FROM RhCategoriaProfissional r WHERE r.descricao = :descricao"),
    @NamedQuery(name = "RhCategoriaProfissional.findByIndice", query = "SELECT r FROM RhCategoriaProfissional r WHERE r.indice = :indice"),
    @NamedQuery(name = "RhCategoriaProfissional.findBySalarioBase", query = "SELECT r FROM RhCategoriaProfissional r WHERE r.salarioBase = :salarioBase"),
    @NamedQuery(name = "RhCategoriaProfissional.findByDespesasDeRepresentacao", query = "SELECT r FROM RhCategoriaProfissional r WHERE r.despesasDeRepresentacao = :despesasDeRepresentacao"),
    @NamedQuery(name = "RhCategoriaProfissional.findByRemuneracaoTotal", query = "SELECT r FROM RhCategoriaProfissional r WHERE r.remuneracaoTotal = :remuneracaoTotal")})
public class RhCategoriaProfissional implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "pk_id_categoria_profissional", nullable = false)
    private Integer pkIdCategoriaProfissional;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 150)
    @Column(name = "descricao", nullable = false, length = 150)
    private String descricao;
    @Basic(optional = false)
    @NotNull
    @Column(name = "indice", nullable = false)
    private int indice;
    @Basic(optional = false)
    @NotNull
    @Column(name = "salario_base", nullable = false)
    private double salarioBase;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "despesas_de_representacao", precision = 17, scale = 17)
    private Double despesasDeRepresentacao;
    @Column(name = "remuneracao_total", precision = 17, scale = 17)
    private Double remuneracaoTotal;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdCategoria")
    private List<RhFuncionario> rhFuncionarioList;
    @OneToMany(mappedBy = "fkIdCategoriaPretendida")
    private List<RhCandidato> rhCandidatoList;

    public RhCategoriaProfissional() {
    }

    public RhCategoriaProfissional(Integer pkIdCategoriaProfissional) {
        this.pkIdCategoriaProfissional = pkIdCategoriaProfissional;
    }

    public RhCategoriaProfissional(Integer pkIdCategoriaProfissional, String descricao, int indice, double salarioBase) {
        this.pkIdCategoriaProfissional = pkIdCategoriaProfissional;
        this.descricao = descricao;
        this.indice = indice;
        this.salarioBase = salarioBase;
    }

    public Integer getPkIdCategoriaProfissional() {
        return pkIdCategoriaProfissional;
    }

    public void setPkIdCategoriaProfissional(Integer pkIdCategoriaProfissional) {
        this.pkIdCategoriaProfissional = pkIdCategoriaProfissional;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getIndice() {
        return indice;
    }

    public void setIndice(int indice) {
        this.indice = indice;
    }

    public double getSalarioBase() {
        return salarioBase;
    }

    public void setSalarioBase(double salarioBase) {
        this.salarioBase = salarioBase;
    }

    public Double getDespesasDeRepresentacao() {
        return despesasDeRepresentacao;
    }

    public void setDespesasDeRepresentacao(Double despesasDeRepresentacao) {
        this.despesasDeRepresentacao = despesasDeRepresentacao;
    }

    public Double getRemuneracaoTotal() {
        return remuneracaoTotal;
    }

    public void setRemuneracaoTotal(Double remuneracaoTotal) {
        this.remuneracaoTotal = remuneracaoTotal;
    }

    @XmlTransient
    public List<RhFuncionario> getRhFuncionarioList() {
        return rhFuncionarioList;
    }

    public void setRhFuncionarioList(List<RhFuncionario> rhFuncionarioList) {
        this.rhFuncionarioList = rhFuncionarioList;
    }

    @XmlTransient
    public List<RhCandidato> getRhCandidatoList() {
        return rhCandidatoList;
    }

    public void setRhCandidatoList(List<RhCandidato> rhCandidatoList) {
        this.rhCandidatoList = rhCandidatoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdCategoriaProfissional != null ? pkIdCategoriaProfissional.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RhCategoriaProfissional)) {
            return false;
        }
        RhCategoriaProfissional other = (RhCategoriaProfissional) object;
        if ((this.pkIdCategoriaProfissional == null && other.pkIdCategoriaProfissional != null) || (this.pkIdCategoriaProfissional != null && !this.pkIdCategoriaProfissional.equals(other.pkIdCategoriaProfissional))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.RhCategoriaProfissional[ pkIdCategoriaProfissional=" + pkIdCategoriaProfissional + " ]";
    }
    
}
