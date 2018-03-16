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
@Table(name = "adms_tipo_servico", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AdmsTipoServico.findAll", query = "SELECT a FROM AdmsTipoServico a"),
    @NamedQuery(name = "AdmsTipoServico.findByPkIdTipoServico", query = "SELECT a FROM AdmsTipoServico a WHERE a.pkIdTipoServico = :pkIdTipoServico"),
    @NamedQuery(name = "AdmsTipoServico.findByDescricaoTipoServico", query = "SELECT a FROM AdmsTipoServico a WHERE a.descricaoTipoServico = :descricaoTipoServico")})
public class AdmsTipoServico implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "pk_id_tipo_servico", nullable = false)
    private Integer pkIdTipoServico;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "descricao_tipo_servico", nullable = false, length = 100)
    private String descricaoTipoServico;
    @OneToMany(mappedBy = "fkIdTipoServico")
    private List<AdmsServico> admsServicoList;

    public AdmsTipoServico() {
    }

    public AdmsTipoServico(Integer pkIdTipoServico) {
        this.pkIdTipoServico = pkIdTipoServico;
    }

    public AdmsTipoServico(Integer pkIdTipoServico, String descricaoTipoServico) {
        this.pkIdTipoServico = pkIdTipoServico;
        this.descricaoTipoServico = descricaoTipoServico;
    }

    public Integer getPkIdTipoServico() {
        return pkIdTipoServico;
    }

    public void setPkIdTipoServico(Integer pkIdTipoServico) {
        this.pkIdTipoServico = pkIdTipoServico;
    }

    public String getDescricaoTipoServico() {
        return descricaoTipoServico;
    }

    public void setDescricaoTipoServico(String descricaoTipoServico) {
        this.descricaoTipoServico = descricaoTipoServico;
    }

    @XmlTransient
    public List<AdmsServico> getAdmsServicoList() {
        return admsServicoList;
    }

    public void setAdmsServicoList(List<AdmsServico> admsServicoList) {
        this.admsServicoList = admsServicoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdTipoServico != null ? pkIdTipoServico.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AdmsTipoServico)) {
            return false;
        }
        AdmsTipoServico other = (AdmsTipoServico) object;
        if ((this.pkIdTipoServico == null && other.pkIdTipoServico != null) || (this.pkIdTipoServico != null && !this.pkIdTipoServico.equals(other.pkIdTipoServico))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.AdmsTipoServico[ pkIdTipoServico=" + pkIdTipoServico + " ]";
    }
    
}
