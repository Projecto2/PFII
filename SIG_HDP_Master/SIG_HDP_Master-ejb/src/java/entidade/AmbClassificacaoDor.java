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
@Table(name = "amb_classificacao_dor", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AmbClassificacaoDor.findAll", query = "SELECT a FROM AmbClassificacaoDor a"),
    @NamedQuery(name = "AmbClassificacaoDor.findByPkIdClassificacaoDor", query = "SELECT a FROM AmbClassificacaoDor a WHERE a.pkIdClassificacaoDor = :pkIdClassificacaoDor"),
    @NamedQuery(name = "AmbClassificacaoDor.findByDescricao", query = "SELECT a FROM AmbClassificacaoDor a WHERE a.descricao = :descricao")})
public class AmbClassificacaoDor implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "pk_id_classificacao_dor", nullable = false)
    private Integer pkIdClassificacaoDor;
    @Size(max = 12)
    @Column(name = "descricao", length = 12)
    private String descricao;
    @OneToMany(mappedBy = "fkIdClassificacaoDor")
    private List<AmbTriagem> ambTriagemList;
    @OneToMany(mappedBy = "fkIdClassificacaoDor")
    private List<AmbConsulta> ambConsultaList;

    public AmbClassificacaoDor() {
    }

    public AmbClassificacaoDor(Integer pkIdClassificacaoDor) {
        this.pkIdClassificacaoDor = pkIdClassificacaoDor;
    }

    public Integer getPkIdClassificacaoDor() {
        return pkIdClassificacaoDor;
    }

    public void setPkIdClassificacaoDor(Integer pkIdClassificacaoDor) {
        this.pkIdClassificacaoDor = pkIdClassificacaoDor;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @XmlTransient
    public List<AmbTriagem> getAmbTriagemList() {
        return ambTriagemList;
    }

    public void setAmbTriagemList(List<AmbTriagem> ambTriagemList) {
        this.ambTriagemList = ambTriagemList;
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
        hash += (pkIdClassificacaoDor != null ? pkIdClassificacaoDor.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AmbClassificacaoDor)) {
            return false;
        }
        AmbClassificacaoDor other = (AmbClassificacaoDor) object;
        if ((this.pkIdClassificacaoDor == null && other.pkIdClassificacaoDor != null) || (this.pkIdClassificacaoDor != null && !this.pkIdClassificacaoDor.equals(other.pkIdClassificacaoDor))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.AmbClassificacaoDor[ pkIdClassificacaoDor=" + pkIdClassificacaoDor + " ]";
    }
    
}
