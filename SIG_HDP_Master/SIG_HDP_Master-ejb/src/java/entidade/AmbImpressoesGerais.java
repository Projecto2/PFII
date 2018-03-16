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
@Table(name = "amb_impressoes_gerais", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AmbImpressoesGerais.findAll", query = "SELECT a FROM AmbImpressoesGerais a"),
    @NamedQuery(name = "AmbImpressoesGerais.findByPkIdImpressoesGerais", query = "SELECT a FROM AmbImpressoesGerais a WHERE a.pkIdImpressoesGerais = :pkIdImpressoesGerais"),
    @NamedQuery(name = "AmbImpressoesGerais.findByDescricao", query = "SELECT a FROM AmbImpressoesGerais a WHERE a.descricao = :descricao")})
public class AmbImpressoesGerais implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "pk_id_impressoes_gerais", nullable = false)
    private Integer pkIdImpressoesGerais;
    @Size(max = 35)
    @Column(name = "descricao", length = 35)
    private String descricao;
    @OneToMany(mappedBy = "fkIdImpressoesGerais")
    private List<AmbConsultaHasImpressoesGerais> ambConsultaHasImpressoesGeraisList;

    public AmbImpressoesGerais() {
    }

    public AmbImpressoesGerais(Integer pkIdImpressoesGerais) {
        this.pkIdImpressoesGerais = pkIdImpressoesGerais;
    }

    public Integer getPkIdImpressoesGerais() {
        return pkIdImpressoesGerais;
    }

    public void setPkIdImpressoesGerais(Integer pkIdImpressoesGerais) {
        this.pkIdImpressoesGerais = pkIdImpressoesGerais;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @XmlTransient
    public List<AmbConsultaHasImpressoesGerais> getAmbConsultaHasImpressoesGeraisList() {
        return ambConsultaHasImpressoesGeraisList;
    }

    public void setAmbConsultaHasImpressoesGeraisList(List<AmbConsultaHasImpressoesGerais> ambConsultaHasImpressoesGeraisList) {
        this.ambConsultaHasImpressoesGeraisList = ambConsultaHasImpressoesGeraisList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdImpressoesGerais != null ? pkIdImpressoesGerais.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AmbImpressoesGerais)) {
            return false;
        }
        AmbImpressoesGerais other = (AmbImpressoesGerais) object;
        if ((this.pkIdImpressoesGerais == null && other.pkIdImpressoesGerais != null) || (this.pkIdImpressoesGerais != null && !this.pkIdImpressoesGerais.equals(other.pkIdImpressoesGerais))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.AmbImpressoesGerais[ pkIdImpressoesGerais=" + pkIdImpressoesGerais + " ]";
    }
    
}
