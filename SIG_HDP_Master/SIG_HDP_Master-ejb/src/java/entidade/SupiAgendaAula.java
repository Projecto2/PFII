/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entidade;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author amed
 */
@Entity
@Table(name = "supi_agenda_aula", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SupiAgendaAula.findAll", query = "SELECT s FROM SupiAgendaAula s"),
    @NamedQuery(name = "SupiAgendaAula.findByTema", query = "SELECT s FROM SupiAgendaAula s WHERE s.tema = :tema"),
    @NamedQuery(name = "SupiAgendaAula.findByTelefone", query = "SELECT s FROM SupiAgendaAula s WHERE s.telefone = :telefone"),
    @NamedQuery(name = "SupiAgendaAula.findByData", query = "SELECT s FROM SupiAgendaAula s WHERE s.data = :data"),
    @NamedQuery(name = "SupiAgendaAula.findByPkIdAgendaAula", query = "SELECT s FROM SupiAgendaAula s WHERE s.pkIdAgendaAula = :pkIdAgendaAula"),
    @NamedQuery(name = "SupiAgendaAula.findByNumeroAula", query = "SELECT s FROM SupiAgendaAula s WHERE s.numeroAula = :numeroAula")})
public class SupiAgendaAula implements Serializable {
    private static final long serialVersionUID = 1L;
    @Size(max = 100)
    @Column(name = "tema", length = 100)
    private String tema;
    @Size(max = 100)
    @Column(name = "telefone", length = 100)
    private String telefone;
    @Basic(optional = false)
    @NotNull
    @Column(name = "data", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date data;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_agenda_aula", nullable = false)
    private Integer pkIdAgendaAula;
    @Column(name = "numero_aula")
    private Integer numeroAula;
    @OneToMany(mappedBy = "fkIdAgendaAula")
    private List<SupiControloPresenca> supiControloPresencaList;
    @JoinColumn(name = "fk_id_funcionario", referencedColumnName = "pk_id_funcionario")
    @ManyToOne
    private RhFuncionario fkIdFuncionario;
    @JoinColumn(name = "fk_id_criacao_turma", referencedColumnName = "pk_id_criacao_turma")
    @ManyToOne
    private SupiCriacaoTurma fkIdCriacaoTurma;

    public SupiAgendaAula() {
    }

    public SupiAgendaAula(Integer pkIdAgendaAula) {
        this.pkIdAgendaAula = pkIdAgendaAula;
    }

    public SupiAgendaAula(Integer pkIdAgendaAula, Date data) {
        this.pkIdAgendaAula = pkIdAgendaAula;
        this.data = data;
    }

    public String getTema() {
        return tema;
    }

    public void setTema(String tema) {
        this.tema = tema;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Integer getPkIdAgendaAula() {
        return pkIdAgendaAula;
    }

    public void setPkIdAgendaAula(Integer pkIdAgendaAula) {
        this.pkIdAgendaAula = pkIdAgendaAula;
    }

    public Integer getNumeroAula() {
        return numeroAula;
    }

    public void setNumeroAula(Integer numeroAula) {
        this.numeroAula = numeroAula;
    }

    @XmlTransient
    public List<SupiControloPresenca> getSupiControloPresencaList() {
        return supiControloPresencaList;
    }

    public void setSupiControloPresencaList(List<SupiControloPresenca> supiControloPresencaList) {
        this.supiControloPresencaList = supiControloPresencaList;
    }

    public RhFuncionario getFkIdFuncionario() {
        return fkIdFuncionario;
    }

    public void setFkIdFuncionario(RhFuncionario fkIdFuncionario) {
        this.fkIdFuncionario = fkIdFuncionario;
    }

    public SupiCriacaoTurma getFkIdCriacaoTurma() {
        return fkIdCriacaoTurma;
    }

    public void setFkIdCriacaoTurma(SupiCriacaoTurma fkIdCriacaoTurma) {
        this.fkIdCriacaoTurma = fkIdCriacaoTurma;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdAgendaAula != null ? pkIdAgendaAula.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SupiAgendaAula)) {
            return false;
        }
        SupiAgendaAula other = (SupiAgendaAula) object;
        if ((this.pkIdAgendaAula == null && other.pkIdAgendaAula != null) || (this.pkIdAgendaAula != null && !this.pkIdAgendaAula.equals(other.pkIdAgendaAula))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.SupiAgendaAula[ pkIdAgendaAula=" + pkIdAgendaAula + " ]";
    }
    
}
