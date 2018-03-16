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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author amed
 */
@Entity
@Table(name = "supi_criacao_turma", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SupiCriacaoTurma.findAll", query = "SELECT s FROM SupiCriacaoTurma s"),
    @NamedQuery(name = "SupiCriacaoTurma.findByCodigoTurma", query = "SELECT s FROM SupiCriacaoTurma s WHERE s.codigoTurma = :codigoTurma"),
    @NamedQuery(name = "SupiCriacaoTurma.findByPkIdCriacaoTurma", query = "SELECT s FROM SupiCriacaoTurma s WHERE s.pkIdCriacaoTurma = :pkIdCriacaoTurma")})
public class SupiCriacaoTurma implements Serializable {
    private static final long serialVersionUID = 1L;
    @Size(max = 100)
    @Column(name = "codigo_turma", length = 100)
    private String codigoTurma;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_criacao_turma", nullable = false)
    private Integer pkIdCriacaoTurma;
    @JoinColumn(name = "fk_id_funcionario", referencedColumnName = "pk_id_funcionario")
    @ManyToOne
    private RhFuncionario fkIdFuncionario;
    @JoinColumn(name = "fk_id_seccao_trabalho", referencedColumnName = "pk_id_seccao_trabalho")
    @ManyToOne
    private RhSeccaoTrabalho fkIdSeccaoTrabalho;
    @OneToMany(mappedBy = "fkIdCriacaoTurma")
    private List<SupiAgendaAula> supiAgendaAulaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdCriacaoTurma")
    private List<SupiTurmaEstagiario> supiTurmaEstagiarioList;

    public SupiCriacaoTurma() {
    }

    public SupiCriacaoTurma(Integer pkIdCriacaoTurma) {
        this.pkIdCriacaoTurma = pkIdCriacaoTurma;
    }

    public String getCodigoTurma() {
        return codigoTurma;
    }

    public void setCodigoTurma(String codigoTurma) {
        this.codigoTurma = codigoTurma;
    }

    public Integer getPkIdCriacaoTurma() {
        return pkIdCriacaoTurma;
    }

    public void setPkIdCriacaoTurma(Integer pkIdCriacaoTurma) {
        this.pkIdCriacaoTurma = pkIdCriacaoTurma;
    }

    public RhFuncionario getFkIdFuncionario() {
        return fkIdFuncionario;
    }

    public void setFkIdFuncionario(RhFuncionario fkIdFuncionario) {
        this.fkIdFuncionario = fkIdFuncionario;
    }

    public RhSeccaoTrabalho getFkIdSeccaoTrabalho() {
        return fkIdSeccaoTrabalho;
    }

    public void setFkIdSeccaoTrabalho(RhSeccaoTrabalho fkIdSeccaoTrabalho) {
        this.fkIdSeccaoTrabalho = fkIdSeccaoTrabalho;
    }

    @XmlTransient
    public List<SupiAgendaAula> getSupiAgendaAulaList() {
        return supiAgendaAulaList;
    }

    public void setSupiAgendaAulaList(List<SupiAgendaAula> supiAgendaAulaList) {
        this.supiAgendaAulaList = supiAgendaAulaList;
    }

    @XmlTransient
    public List<SupiTurmaEstagiario> getSupiTurmaEstagiarioList() {
        return supiTurmaEstagiarioList;
    }

    public void setSupiTurmaEstagiarioList(List<SupiTurmaEstagiario> supiTurmaEstagiarioList) {
        this.supiTurmaEstagiarioList = supiTurmaEstagiarioList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdCriacaoTurma != null ? pkIdCriacaoTurma.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SupiCriacaoTurma)) {
            return false;
        }
        SupiCriacaoTurma other = (SupiCriacaoTurma) object;
        if ((this.pkIdCriacaoTurma == null && other.pkIdCriacaoTurma != null) || (this.pkIdCriacaoTurma != null && !this.pkIdCriacaoTurma.equals(other.pkIdCriacaoTurma))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.SupiCriacaoTurma[ pkIdCriacaoTurma=" + pkIdCriacaoTurma + " ]";
    }
    
}
