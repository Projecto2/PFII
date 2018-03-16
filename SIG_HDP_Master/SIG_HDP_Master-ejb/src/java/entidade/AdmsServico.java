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
@Table(name = "adms_servico", catalog = "sigh_db", schema = "public", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"pk_id_servico"}),
    @UniqueConstraint(columnNames = {"nome_servico", "fk_id_tipo_servico", "fk_id_area", "fk_id_especialidade"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AdmsServico.findAll", query = "SELECT a FROM AdmsServico a"),
    @NamedQuery(name = "AdmsServico.findByPkIdServico", query = "SELECT a FROM AdmsServico a WHERE a.pkIdServico = :pkIdServico"),
    @NamedQuery(name = "AdmsServico.findByNomeServico", query = "SELECT a FROM AdmsServico a WHERE a.nomeServico = :nomeServico"),
    @NamedQuery(name = "AdmsServico.findByPodeTerMedico", query = "SELECT a FROM AdmsServico a WHERE a.podeTerMedico = :podeTerMedico"),
    @NamedQuery(name = "AdmsServico.findByCodServico", query = "SELECT a FROM AdmsServico a WHERE a.codServico = :codServico")})
public class AdmsServico implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "pk_id_servico", nullable = false)
    private Integer pkIdServico;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "nome_servico", nullable = false, length = 200)
    private String nomeServico;
    @Basic(optional = false)
    @NotNull
    @Column(name = "pode_ter_medico", nullable = false)
    private boolean podeTerMedico;
    @Size(max = 75)
    @Column(name = "cod_servico", length = 75)
    private String codServico;
    @OneToMany(mappedBy = "fkIdServico")
    private List<AdmsCategoriaServico> admsCategoriaServicoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdServico")
    private List<InterEnfermaria> interEnfermariaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdServico")
    private List<AdmsServicoSolicitado> admsServicoSolicitadoList;
    @JoinColumn(name = "fk_id_grupo_servico", referencedColumnName = "pk_id_grupo_servico")
    @ManyToOne
    private AdmsGrupoServico fkIdGrupoServico;
    @JoinColumn(name = "fk_id_tipo_servico", referencedColumnName = "pk_id_tipo_servico")
    @ManyToOne
    private AdmsTipoServico fkIdTipoServico;
    @JoinColumn(name = "fk_id_area", referencedColumnName = "pk_id_area_interna")
    @ManyToOne
    private GrlAreaInterna fkIdArea;
    @JoinColumn(name = "fk_id_especialidade", referencedColumnName = "pk_id_especialidade")
    @ManyToOne
    private GrlEspecialidade fkIdEspecialidade;

    public AdmsServico() {
    }

    public AdmsServico(Integer pkIdServico) {
        this.pkIdServico = pkIdServico;
    }

    public AdmsServico(Integer pkIdServico, String nomeServico, boolean podeTerMedico) {
        this.pkIdServico = pkIdServico;
        this.nomeServico = nomeServico;
        this.podeTerMedico = podeTerMedico;
    }

    public Integer getPkIdServico() {
        return pkIdServico;
    }

    public void setPkIdServico(Integer pkIdServico) {
        this.pkIdServico = pkIdServico;
    }

    public String getNomeServico() {
        return nomeServico;
    }

    public void setNomeServico(String nomeServico) {
        this.nomeServico = nomeServico;
    }

    public boolean getPodeTerMedico() {
        return podeTerMedico;
    }

    public void setPodeTerMedico(boolean podeTerMedico) {
        this.podeTerMedico = podeTerMedico;
    }

    public String getCodServico() {
        return codServico;
    }

    public void setCodServico(String codServico) {
        this.codServico = codServico;
    }

    @XmlTransient
    public List<AdmsCategoriaServico> getAdmsCategoriaServicoList() {
        return admsCategoriaServicoList;
    }

    public void setAdmsCategoriaServicoList(List<AdmsCategoriaServico> admsCategoriaServicoList) {
        this.admsCategoriaServicoList = admsCategoriaServicoList;
    }

    @XmlTransient
    public List<InterEnfermaria> getInterEnfermariaList() {
        return interEnfermariaList;
    }

    public void setInterEnfermariaList(List<InterEnfermaria> interEnfermariaList) {
        this.interEnfermariaList = interEnfermariaList;
    }

    @XmlTransient
    public List<AdmsServicoSolicitado> getAdmsServicoSolicitadoList() {
        return admsServicoSolicitadoList;
    }

    public void setAdmsServicoSolicitadoList(List<AdmsServicoSolicitado> admsServicoSolicitadoList) {
        this.admsServicoSolicitadoList = admsServicoSolicitadoList;
    }

    public AdmsGrupoServico getFkIdGrupoServico() {
        return fkIdGrupoServico;
    }

    public void setFkIdGrupoServico(AdmsGrupoServico fkIdGrupoServico) {
        this.fkIdGrupoServico = fkIdGrupoServico;
    }

    public AdmsTipoServico getFkIdTipoServico() {
        return fkIdTipoServico;
    }

    public void setFkIdTipoServico(AdmsTipoServico fkIdTipoServico) {
        this.fkIdTipoServico = fkIdTipoServico;
    }

    public GrlAreaInterna getFkIdArea() {
        return fkIdArea;
    }

    public void setFkIdArea(GrlAreaInterna fkIdArea) {
        this.fkIdArea = fkIdArea;
    }

    public GrlEspecialidade getFkIdEspecialidade() {
        return fkIdEspecialidade;
    }

    public void setFkIdEspecialidade(GrlEspecialidade fkIdEspecialidade) {
        this.fkIdEspecialidade = fkIdEspecialidade;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdServico != null ? pkIdServico.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AdmsServico)) {
            return false;
        }
        AdmsServico other = (AdmsServico) object;
        if ((this.pkIdServico == null && other.pkIdServico != null) || (this.pkIdServico != null && !this.pkIdServico.equals(other.pkIdServico))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.AdmsServico[ pkIdServico=" + pkIdServico + " ]";
    }
    
}
