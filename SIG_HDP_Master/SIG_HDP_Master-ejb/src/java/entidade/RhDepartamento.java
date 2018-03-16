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
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author amed
 */
@Entity
@Table(name = "rh_departamento", catalog = "sigh_db", schema = "public", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"descricao"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RhDepartamento.findAll", query = "SELECT r FROM RhDepartamento r"),
    @NamedQuery(name = "RhDepartamento.findByPkIdDepartamento", query = "SELECT r FROM RhDepartamento r WHERE r.pkIdDepartamento = :pkIdDepartamento"),
    @NamedQuery(name = "RhDepartamento.findByDescricao", query = "SELECT r FROM RhDepartamento r WHERE r.descricao = :descricao")})
public class RhDepartamento implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "pk_id_departamento", nullable = false)
    private Integer pkIdDepartamento;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "descricao", nullable = false, length = 100)
    private String descricao;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdDepartamento")
    private List<RhSeccaoTrabalho> rhSeccaoTrabalhoList;

    public RhDepartamento() {
    }

    public RhDepartamento(Integer pkIdDepartamento) {
        this.pkIdDepartamento = pkIdDepartamento;
    }

    public RhDepartamento(Integer pkIdDepartamento, String descricao) {
        this.pkIdDepartamento = pkIdDepartamento;
        this.descricao = descricao;
    }

    public Integer getPkIdDepartamento() {
        return pkIdDepartamento;
    }

    public void setPkIdDepartamento(Integer pkIdDepartamento) {
        this.pkIdDepartamento = pkIdDepartamento;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @XmlTransient
    public List<RhSeccaoTrabalho> getRhSeccaoTrabalhoList() {
        return rhSeccaoTrabalhoList;
    }

    public void setRhSeccaoTrabalhoList(List<RhSeccaoTrabalho> rhSeccaoTrabalhoList) {
        this.rhSeccaoTrabalhoList = rhSeccaoTrabalhoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdDepartamento != null ? pkIdDepartamento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RhDepartamento)) {
            return false;
        }
        RhDepartamento other = (RhDepartamento) object;
        if ((this.pkIdDepartamento == null && other.pkIdDepartamento != null) || (this.pkIdDepartamento != null && !this.pkIdDepartamento.equals(other.pkIdDepartamento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.RhDepartamento[ pkIdDepartamento=" + pkIdDepartamento + " ]";
    }
    
}
