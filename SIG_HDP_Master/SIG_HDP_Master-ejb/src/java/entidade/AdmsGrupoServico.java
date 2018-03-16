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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "adms_grupo_servico", catalog = "sigh_db", schema = "public", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"descricao_grupo_servico"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AdmsGrupoServico.findAll", query = "SELECT a FROM AdmsGrupoServico a"),
    @NamedQuery(name = "AdmsGrupoServico.findByPkIdGrupoServico", query = "SELECT a FROM AdmsGrupoServico a WHERE a.pkIdGrupoServico = :pkIdGrupoServico"),
    @NamedQuery(name = "AdmsGrupoServico.findByDescricaoGrupoServico", query = "SELECT a FROM AdmsGrupoServico a WHERE a.descricaoGrupoServico = :descricaoGrupoServico"),
    @NamedQuery(name = "AdmsGrupoServico.findByObservacaoGrupoServico", query = "SELECT a FROM AdmsGrupoServico a WHERE a.observacaoGrupoServico = :observacaoGrupoServico")})
public class AdmsGrupoServico implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "pk_id_grupo_servico", nullable = false)
    private Integer pkIdGrupoServico;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 120)
    @Column(name = "descricao_grupo_servico", nullable = false, length = 120)
    private String descricaoGrupoServico;
    @Size(max = 500)
    @Column(name = "observacao_grupo_servico", length = 500)
    private String observacaoGrupoServico;
    @OneToMany(mappedBy = "fkIdGrupoServicoPai")
    private List<AdmsGrupoServico> admsGrupoServicoList;
    @JoinColumn(name = "fk_id_grupo_servico_pai", referencedColumnName = "pk_id_grupo_servico")
    @ManyToOne
    private AdmsGrupoServico fkIdGrupoServicoPai;
    @OneToMany(mappedBy = "fkIdGrupoServico")
    private List<AdmsServico> admsServicoList;

    public AdmsGrupoServico() {
    }

    public AdmsGrupoServico(Integer pkIdGrupoServico) {
        this.pkIdGrupoServico = pkIdGrupoServico;
    }

    public AdmsGrupoServico(Integer pkIdGrupoServico, String descricaoGrupoServico) {
        this.pkIdGrupoServico = pkIdGrupoServico;
        this.descricaoGrupoServico = descricaoGrupoServico;
    }

    public Integer getPkIdGrupoServico() {
        return pkIdGrupoServico;
    }

    public void setPkIdGrupoServico(Integer pkIdGrupoServico) {
        this.pkIdGrupoServico = pkIdGrupoServico;
    }

    public String getDescricaoGrupoServico() {
        return descricaoGrupoServico;
    }

    public void setDescricaoGrupoServico(String descricaoGrupoServico) {
        this.descricaoGrupoServico = descricaoGrupoServico;
    }

    public String getObservacaoGrupoServico() {
        return observacaoGrupoServico;
    }

    public void setObservacaoGrupoServico(String observacaoGrupoServico) {
        this.observacaoGrupoServico = observacaoGrupoServico;
    }

    @XmlTransient
    public List<AdmsGrupoServico> getAdmsGrupoServicoList() {
        return admsGrupoServicoList;
    }

    public void setAdmsGrupoServicoList(List<AdmsGrupoServico> admsGrupoServicoList) {
        this.admsGrupoServicoList = admsGrupoServicoList;
    }

    public AdmsGrupoServico getFkIdGrupoServicoPai() {
        return fkIdGrupoServicoPai;
    }

    public void setFkIdGrupoServicoPai(AdmsGrupoServico fkIdGrupoServicoPai) {
        this.fkIdGrupoServicoPai = fkIdGrupoServicoPai;
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
        hash += (pkIdGrupoServico != null ? pkIdGrupoServico.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AdmsGrupoServico)) {
            return false;
        }
        AdmsGrupoServico other = (AdmsGrupoServico) object;
        if ((this.pkIdGrupoServico == null && other.pkIdGrupoServico != null) || (this.pkIdGrupoServico != null && !this.pkIdGrupoServico.equals(other.pkIdGrupoServico))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.AdmsGrupoServico[ pkIdGrupoServico=" + pkIdGrupoServico + " ]";
    }
    
}
