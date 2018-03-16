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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author amed
 */
@Entity
@Table(name = "rh_seccao_trabalho", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RhSeccaoTrabalho.findAll", query = "SELECT r FROM RhSeccaoTrabalho r"),
    @NamedQuery(name = "RhSeccaoTrabalho.findByPkIdSeccaoTrabalho", query = "SELECT r FROM RhSeccaoTrabalho r WHERE r.pkIdSeccaoTrabalho = :pkIdSeccaoTrabalho"),
    @NamedQuery(name = "RhSeccaoTrabalho.findByDescricao", query = "SELECT r FROM RhSeccaoTrabalho r WHERE r.descricao = :descricao")})
public class RhSeccaoTrabalho implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "pk_id_seccao_trabalho", nullable = false)
    private Integer pkIdSeccaoTrabalho;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "descricao", nullable = false, length = 100)
    private String descricao;
    @OneToMany(mappedBy = "fkIdSeccaoTrabalho")
    private List<SupiCriacaoTurma> supiCriacaoTurmaList;
    @OneToMany(mappedBy = "fkIdSeccaoTrabalho")
    private List<RhFuncionario> rhFuncionarioList;
    @JoinColumn(name = "fk_id_departamento", referencedColumnName = "pk_id_departamento", nullable = false)
    @ManyToOne(optional = false)
    private RhDepartamento fkIdDepartamento;
    @OneToMany(mappedBy = "fkIdSeccaoTrabalho")
    private List<SupiEscalaMedico> supiEscalaMedicoList;
    @OneToMany(mappedBy = "fkIdSeccaoTrabalho")
    private List<RhEstagiario> rhEstagiarioList;

    public RhSeccaoTrabalho() {
    }

    public RhSeccaoTrabalho(Integer pkIdSeccaoTrabalho) {
        this.pkIdSeccaoTrabalho = pkIdSeccaoTrabalho;
    }

    public RhSeccaoTrabalho(Integer pkIdSeccaoTrabalho, String descricao) {
        this.pkIdSeccaoTrabalho = pkIdSeccaoTrabalho;
        this.descricao = descricao;
    }

    public Integer getPkIdSeccaoTrabalho() {
        return pkIdSeccaoTrabalho;
    }

    public void setPkIdSeccaoTrabalho(Integer pkIdSeccaoTrabalho) {
        this.pkIdSeccaoTrabalho = pkIdSeccaoTrabalho;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @XmlTransient
    public List<SupiCriacaoTurma> getSupiCriacaoTurmaList() {
        return supiCriacaoTurmaList;
    }

    public void setSupiCriacaoTurmaList(List<SupiCriacaoTurma> supiCriacaoTurmaList) {
        this.supiCriacaoTurmaList = supiCriacaoTurmaList;
    }

    @XmlTransient
    public List<RhFuncionario> getRhFuncionarioList() {
        return rhFuncionarioList;
    }

    public void setRhFuncionarioList(List<RhFuncionario> rhFuncionarioList) {
        this.rhFuncionarioList = rhFuncionarioList;
    }

    public RhDepartamento getFkIdDepartamento() {
        return fkIdDepartamento;
    }

    public void setFkIdDepartamento(RhDepartamento fkIdDepartamento) {
        this.fkIdDepartamento = fkIdDepartamento;
    }

    @XmlTransient
    public List<SupiEscalaMedico> getSupiEscalaMedicoList() {
        return supiEscalaMedicoList;
    }

    public void setSupiEscalaMedicoList(List<SupiEscalaMedico> supiEscalaMedicoList) {
        this.supiEscalaMedicoList = supiEscalaMedicoList;
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
        hash += (pkIdSeccaoTrabalho != null ? pkIdSeccaoTrabalho.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RhSeccaoTrabalho)) {
            return false;
        }
        RhSeccaoTrabalho other = (RhSeccaoTrabalho) object;
        if ((this.pkIdSeccaoTrabalho == null && other.pkIdSeccaoTrabalho != null) || (this.pkIdSeccaoTrabalho != null && !this.pkIdSeccaoTrabalho.equals(other.pkIdSeccaoTrabalho))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.RhSeccaoTrabalho[ pkIdSeccaoTrabalho=" + pkIdSeccaoTrabalho + " ]";
    }
    
}
