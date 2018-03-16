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
@Table(name = "amb_cid_doencas_prioridades", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AmbCidDoencasPrioridades.findAll", query = "SELECT a FROM AmbCidDoencasPrioridades a"),
    @NamedQuery(name = "AmbCidDoencasPrioridades.findByPkIdDoencasPrioridades", query = "SELECT a FROM AmbCidDoencasPrioridades a WHERE a.pkIdDoencasPrioridades = :pkIdDoencasPrioridades"),
    @NamedQuery(name = "AmbCidDoencasPrioridades.findByDescricao", query = "SELECT a FROM AmbCidDoencasPrioridades a WHERE a.descricao = :descricao")})
public class AmbCidDoencasPrioridades implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "pk_id_doencas_prioridades", nullable = false)
    private Integer pkIdDoencasPrioridades;
    @Size(max = 60)
    @Column(name = "descricao", length = 60)
    private String descricao;
    @OneToMany(mappedBy = "fkIdPrioridades")
    private List<AmbCidPerfisDoencas> ambCidPerfisDoencasList;

    public AmbCidDoencasPrioridades() {
    }

    public AmbCidDoencasPrioridades(Integer pkIdDoencasPrioridades) {
        this.pkIdDoencasPrioridades = pkIdDoencasPrioridades;
    }

    public Integer getPkIdDoencasPrioridades() {
        return pkIdDoencasPrioridades;
    }

    public void setPkIdDoencasPrioridades(Integer pkIdDoencasPrioridades) {
        this.pkIdDoencasPrioridades = pkIdDoencasPrioridades;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @XmlTransient
    public List<AmbCidPerfisDoencas> getAmbCidPerfisDoencasList() {
        return ambCidPerfisDoencasList;
    }

    public void setAmbCidPerfisDoencasList(List<AmbCidPerfisDoencas> ambCidPerfisDoencasList) {
        this.ambCidPerfisDoencasList = ambCidPerfisDoencasList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdDoencasPrioridades != null ? pkIdDoencasPrioridades.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AmbCidDoencasPrioridades)) {
            return false;
        }
        AmbCidDoencasPrioridades other = (AmbCidDoencasPrioridades) object;
        if ((this.pkIdDoencasPrioridades == null && other.pkIdDoencasPrioridades != null) || (this.pkIdDoencasPrioridades != null && !this.pkIdDoencasPrioridades.equals(other.pkIdDoencasPrioridades))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.AmbCidDoencasPrioridades[ pkIdDoencasPrioridades=" + pkIdDoencasPrioridades + " ]";
    }
    
}
