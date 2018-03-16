/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entidade;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author amed
 */
@Entity
@Table(name = "supi_area_formacao", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SupiAreaFormacao.findAll", query = "SELECT s FROM SupiAreaFormacao s"),
    @NamedQuery(name = "SupiAreaFormacao.findByPkIdAreaFormacao", query = "SELECT s FROM SupiAreaFormacao s WHERE s.pkIdAreaFormacao = :pkIdAreaFormacao"),
    @NamedQuery(name = "SupiAreaFormacao.findByDescricao", query = "SELECT s FROM SupiAreaFormacao s WHERE s.descricao = :descricao")})
public class SupiAreaFormacao implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_area_formacao", nullable = false)
    private Integer pkIdAreaFormacao;
    @Size(max = 100)
    @Column(name = "descricao", length = 100)
    private String descricao;
    @OneToMany(mappedBy = "fkIdAreaFormacao")
    private List<SupiFormadorAux> supiFormadorAuxList;
    @OneToMany(mappedBy = "fkIdAreaFormacao")
    private List<SupiFormador> supiFormadorList;

    public SupiAreaFormacao() {
    }

    public SupiAreaFormacao(Integer pkIdAreaFormacao) {
        this.pkIdAreaFormacao = pkIdAreaFormacao;
    }

    public Integer getPkIdAreaFormacao() {
        return pkIdAreaFormacao;
    }

    public void setPkIdAreaFormacao(Integer pkIdAreaFormacao) {
        this.pkIdAreaFormacao = pkIdAreaFormacao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @XmlTransient
    public List<SupiFormadorAux> getSupiFormadorAuxList() {
        return supiFormadorAuxList;
    }

    public void setSupiFormadorAuxList(List<SupiFormadorAux> supiFormadorAuxList) {
        this.supiFormadorAuxList = supiFormadorAuxList;
    }

    @XmlTransient
    public List<SupiFormador> getSupiFormadorList() {
        return supiFormadorList;
    }

    public void setSupiFormadorList(List<SupiFormador> supiFormadorList) {
        this.supiFormadorList = supiFormadorList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdAreaFormacao != null ? pkIdAreaFormacao.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SupiAreaFormacao)) {
            return false;
        }
        SupiAreaFormacao other = (SupiAreaFormacao) object;
        if ((this.pkIdAreaFormacao == null && other.pkIdAreaFormacao != null) || (this.pkIdAreaFormacao != null && !this.pkIdAreaFormacao.equals(other.pkIdAreaFormacao))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.SupiAreaFormacao[ pkIdAreaFormacao=" + pkIdAreaFormacao + " ]";
    }
    
}
