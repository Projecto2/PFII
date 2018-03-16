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
@Table(name = "diag_resultado_teste_compatibilidade", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DiagResultadoTesteCompatibilidade.findAll", query = "SELECT d FROM DiagResultadoTesteCompatibilidade d"),
    @NamedQuery(name = "DiagResultadoTesteCompatibilidade.findByPkIdResultadoTesteCompatibilidade", query = "SELECT d FROM DiagResultadoTesteCompatibilidade d WHERE d.pkIdResultadoTesteCompatibilidade = :pkIdResultadoTesteCompatibilidade"),
    @NamedQuery(name = "DiagResultadoTesteCompatibilidade.findByDescricao", query = "SELECT d FROM DiagResultadoTesteCompatibilidade d WHERE d.descricao = :descricao")})
public class DiagResultadoTesteCompatibilidade implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_resultado_teste_compatibilidade", nullable = false)
    private Integer pkIdResultadoTesteCompatibilidade;
    @Size(max = 45)
    @Column(name = "descricao", length = 45)
    private String descricao;
    @OneToMany(mappedBy = "fkIdResultadoTesteCompatibilidade")
    private List<DiagTesteCompatibilidade> diagTesteCompatibilidadeList;

    public DiagResultadoTesteCompatibilidade() {
    }

    public DiagResultadoTesteCompatibilidade(Integer pkIdResultadoTesteCompatibilidade) {
        this.pkIdResultadoTesteCompatibilidade = pkIdResultadoTesteCompatibilidade;
    }

    public Integer getPkIdResultadoTesteCompatibilidade() {
        return pkIdResultadoTesteCompatibilidade;
    }

    public void setPkIdResultadoTesteCompatibilidade(Integer pkIdResultadoTesteCompatibilidade) {
        this.pkIdResultadoTesteCompatibilidade = pkIdResultadoTesteCompatibilidade;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @XmlTransient
    public List<DiagTesteCompatibilidade> getDiagTesteCompatibilidadeList() {
        return diagTesteCompatibilidadeList;
    }

    public void setDiagTesteCompatibilidadeList(List<DiagTesteCompatibilidade> diagTesteCompatibilidadeList) {
        this.diagTesteCompatibilidadeList = diagTesteCompatibilidadeList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdResultadoTesteCompatibilidade != null ? pkIdResultadoTesteCompatibilidade.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DiagResultadoTesteCompatibilidade)) {
            return false;
        }
        DiagResultadoTesteCompatibilidade other = (DiagResultadoTesteCompatibilidade) object;
        if ((this.pkIdResultadoTesteCompatibilidade == null && other.pkIdResultadoTesteCompatibilidade != null) || (this.pkIdResultadoTesteCompatibilidade != null && !this.pkIdResultadoTesteCompatibilidade.equals(other.pkIdResultadoTesteCompatibilidade))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.DiagResultadoTesteCompatibilidade[ pkIdResultadoTesteCompatibilidade=" + pkIdResultadoTesteCompatibilidade + " ]";
    }
    
}
