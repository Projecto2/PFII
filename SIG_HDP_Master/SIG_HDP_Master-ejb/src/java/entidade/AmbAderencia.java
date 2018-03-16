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
@Table(name = "amb_aderencia", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AmbAderencia.findAll", query = "SELECT a FROM AmbAderencia a"),
    @NamedQuery(name = "AmbAderencia.findByPkIdAderencia", query = "SELECT a FROM AmbAderencia a WHERE a.pkIdAderencia = :pkIdAderencia"),
    @NamedQuery(name = "AmbAderencia.findByDescricao", query = "SELECT a FROM AmbAderencia a WHERE a.descricao = :descricao")})
public class AmbAderencia implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "pk_id_aderencia", nullable = false)
    private Integer pkIdAderencia;
    @Size(max = 12)
    @Column(name = "descricao", length = 12)
    private String descricao;
    @OneToMany(mappedBy = "fkIdAderencia")
    private List<AmbConsulta> ambConsultaList;

    public AmbAderencia() {
    }

    public AmbAderencia(Integer pkIdAderencia) {
        this.pkIdAderencia = pkIdAderencia;
    }

    public Integer getPkIdAderencia() {
        return pkIdAderencia;
    }

    public void setPkIdAderencia(Integer pkIdAderencia) {
        this.pkIdAderencia = pkIdAderencia;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @XmlTransient
    public List<AmbConsulta> getAmbConsultaList() {
        return ambConsultaList;
    }

    public void setAmbConsultaList(List<AmbConsulta> ambConsultaList) {
        this.ambConsultaList = ambConsultaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdAderencia != null ? pkIdAderencia.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AmbAderencia)) {
            return false;
        }
        AmbAderencia other = (AmbAderencia) object;
        if ((this.pkIdAderencia == null && other.pkIdAderencia != null) || (this.pkIdAderencia != null && !this.pkIdAderencia.equals(other.pkIdAderencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.AmbAderencia[ pkIdAderencia=" + pkIdAderencia + " ]";
    }
    
}
