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
@Table(name = "rh_profissao", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RhProfissao.findAll", query = "SELECT r FROM RhProfissao r"),
    @NamedQuery(name = "RhProfissao.findByPkIdProfissao", query = "SELECT r FROM RhProfissao r WHERE r.pkIdProfissao = :pkIdProfissao"),
    @NamedQuery(name = "RhProfissao.findByDescricao", query = "SELECT r FROM RhProfissao r WHERE r.descricao = :descricao")})
public class RhProfissao implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "pk_id_profissao", nullable = false)
    private Integer pkIdProfissao;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "descricao", nullable = false, length = 100)
    private String descricao;
    @OneToMany(mappedBy = "fkIdProfissao")
    private List<RhFuncionario> rhFuncionarioList;
    @OneToMany(mappedBy = "fkIdProfissao")
    private List<SupiFormadorAux> supiFormadorAuxList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdProfissao")
    private List<GrlEspecialidade> grlEspecialidadeList;
    @OneToMany(mappedBy = "fkIdProfissao")
    private List<RhCandidato> rhCandidatoList;
    @OneToMany(mappedBy = "fkIdProfissao")
    private List<RhEstagiario> rhEstagiarioList;

    public RhProfissao() {
    }

    public RhProfissao(Integer pkIdProfissao) {
        this.pkIdProfissao = pkIdProfissao;
    }

    public RhProfissao(Integer pkIdProfissao, String descricao) {
        this.pkIdProfissao = pkIdProfissao;
        this.descricao = descricao;
    }

    public Integer getPkIdProfissao() {
        return pkIdProfissao;
    }

    public void setPkIdProfissao(Integer pkIdProfissao) {
        this.pkIdProfissao = pkIdProfissao;
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
    public List<SupiFormadorAux> getSupiFormadorAuxList() {
        return supiFormadorAuxList;
    }

    public void setSupiFormadorAuxList(List<SupiFormadorAux> supiFormadorAuxList) {
        this.supiFormadorAuxList = supiFormadorAuxList;
    }

    @XmlTransient
    public List<GrlEspecialidade> getGrlEspecialidadeList() {
        return grlEspecialidadeList;
    }

    public void setGrlEspecialidadeList(List<GrlEspecialidade> grlEspecialidadeList) {
        this.grlEspecialidadeList = grlEspecialidadeList;
    }

    @XmlTransient
    public List<RhCandidato> getRhCandidatoList() {
        return rhCandidatoList;
    }

    public void setRhCandidatoList(List<RhCandidato> rhCandidatoList) {
        this.rhCandidatoList = rhCandidatoList;
    }

    @XmlTransient
    public List<RhEstagiario> getRhEstagiarioList() {
        return rhEstagiarioList;
    }

    public void setRhEstagiarioList(List<RhEstagiario> rhEstagiarioList) {
        this.rhEstagiarioList = rhEstagiarioList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdProfissao != null ? pkIdProfissao.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RhProfissao)) {
            return false;
        }
        RhProfissao other = (RhProfissao) object;
        if ((this.pkIdProfissao == null && other.pkIdProfissao != null) || (this.pkIdProfissao != null && !this.pkIdProfissao.equals(other.pkIdProfissao))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.RhProfissao[ pkIdProfissao=" + pkIdProfissao + " ]";
    }
    
}
