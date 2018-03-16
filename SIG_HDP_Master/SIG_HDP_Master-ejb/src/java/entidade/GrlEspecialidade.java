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
@Table(name = "grl_especialidade", catalog = "sigh_db", schema = "public", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"descricao", "fk_id_profissao"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GrlEspecialidade.findAll", query = "SELECT g FROM GrlEspecialidade g"),
    @NamedQuery(name = "GrlEspecialidade.findByPkIdEspecialidade", query = "SELECT g FROM GrlEspecialidade g WHERE g.pkIdEspecialidade = :pkIdEspecialidade"),
    @NamedQuery(name = "GrlEspecialidade.findByDescricao", query = "SELECT g FROM GrlEspecialidade g WHERE g.descricao = :descricao")})
public class GrlEspecialidade implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "pk_id_especialidade", nullable = false)
    private Integer pkIdEspecialidade;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "descricao", nullable = false, length = 45)
    private String descricao;
    @OneToMany(mappedBy = "fkIdEspecialidade1")
    private List<RhFuncionario> rhFuncionarioList;
    @OneToMany(mappedBy = "fkIdEspecialidade2")
    private List<RhFuncionario> rhFuncionarioList1;
    @JoinColumn(name = "fk_id_profissao", referencedColumnName = "pk_id_profissao", nullable = false)
    @ManyToOne(optional = false)
    private RhProfissao fkIdProfissao;
    @OneToMany(mappedBy = "fkIdEspecialidades")
    private List<AmbCidPerfis> ambCidPerfisList;
    @OneToMany(mappedBy = "fkIdEspecialidade")
    private List<AmbConsulta> ambConsultaList;
    @OneToMany(mappedBy = "fkIdEspecialidade2")
    private List<RhCandidato> rhCandidatoList;
    @OneToMany(mappedBy = "fkIdEspecialidade1")
    private List<RhCandidato> rhCandidatoList1;
    @OneToMany(mappedBy = "fkIdEspecialidade")
    private List<AdmsServico> admsServicoList;

    public GrlEspecialidade() {
    }

    public GrlEspecialidade(Integer pkIdEspecialidade) {
        this.pkIdEspecialidade = pkIdEspecialidade;
    }

    public GrlEspecialidade(Integer pkIdEspecialidade, String descricao) {
        this.pkIdEspecialidade = pkIdEspecialidade;
        this.descricao = descricao;
    }

    public Integer getPkIdEspecialidade() {
        return pkIdEspecialidade;
    }

    public void setPkIdEspecialidade(Integer pkIdEspecialidade) {
        this.pkIdEspecialidade = pkIdEspecialidade;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @XmlTransient
    public List<RhFuncionario> getRhFuncionarioList() {
        return rhFuncionarioList;
    }

    public void setRhFuncionarioList(List<RhFuncionario> rhFuncionarioList) {
        this.rhFuncionarioList = rhFuncionarioList;
    }

    @XmlTransient
    public List<RhFuncionario> getRhFuncionarioList1() {
        return rhFuncionarioList1;
    }

    public void setRhFuncionarioList1(List<RhFuncionario> rhFuncionarioList1) {
        this.rhFuncionarioList1 = rhFuncionarioList1;
    }

    public RhProfissao getFkIdProfissao() {
        return fkIdProfissao;
    }

    public void setFkIdProfissao(RhProfissao fkIdProfissao) {
        this.fkIdProfissao = fkIdProfissao;
    }

    @XmlTransient
    public List<AmbCidPerfis> getAmbCidPerfisList() {
        return ambCidPerfisList;
    }

    public void setAmbCidPerfisList(List<AmbCidPerfis> ambCidPerfisList) {
        this.ambCidPerfisList = ambCidPerfisList;
    }

    @XmlTransient
    public List<AmbConsulta> getAmbConsultaList() {
        return ambConsultaList;
    }

    public void setAmbConsultaList(List<AmbConsulta> ambConsultaList) {
        this.ambConsultaList = ambConsultaList;
    }

    @XmlTransient
    public List<RhCandidato> getRhCandidatoList() {
        return rhCandidatoList;
    }

    public void setRhCandidatoList(List<RhCandidato> rhCandidatoList) {
        this.rhCandidatoList = rhCandidatoList;
    }

    @XmlTransient
    public List<RhCandidato> getRhCandidatoList1() {
        return rhCandidatoList1;
    }

    public void setRhCandidatoList1(List<RhCandidato> rhCandidatoList1) {
        this.rhCandidatoList1 = rhCandidatoList1;
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
        hash += (pkIdEspecialidade != null ? pkIdEspecialidade.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GrlEspecialidade)) {
            return false;
        }
        GrlEspecialidade other = (GrlEspecialidade) object;
        if ((this.pkIdEspecialidade == null && other.pkIdEspecialidade != null) || (this.pkIdEspecialidade != null && !this.pkIdEspecialidade.equals(other.pkIdEspecialidade))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.GrlEspecialidade[ pkIdEspecialidade=" + pkIdEspecialidade + " ]";
    }
    
}
