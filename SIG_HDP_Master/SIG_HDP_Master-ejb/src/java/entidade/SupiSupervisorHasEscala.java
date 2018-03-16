/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entidade;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author amed
 */
@Entity
@Table(name = "supi_supervisor_has_escala", catalog = "sigh_db", schema = "public", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"data", "fk_id_escala", "fk_id_funcionario"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SupiSupervisorHasEscala.findAll", query = "SELECT s FROM SupiSupervisorHasEscala s"),
    @NamedQuery(name = "SupiSupervisorHasEscala.findByPkIdFuncionarioSupervisionaEscala", query = "SELECT s FROM SupiSupervisorHasEscala s WHERE s.pkIdFuncionarioSupervisionaEscala = :pkIdFuncionarioSupervisionaEscala"),
    @NamedQuery(name = "SupiSupervisorHasEscala.findByObservacao", query = "SELECT s FROM SupiSupervisorHasEscala s WHERE s.observacao = :observacao"),
    @NamedQuery(name = "SupiSupervisorHasEscala.findByData", query = "SELECT s FROM SupiSupervisorHasEscala s WHERE s.data = :data")})
public class SupiSupervisorHasEscala implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_funcionario_supervisiona_escala", nullable = false)
    private Integer pkIdFuncionarioSupervisionaEscala;
    @Size(max = 2147483647)
    @Column(name = "observacao", length = 2147483647)
    private String observacao;
    @Basic(optional = false)
    @NotNull
    @Column(name = "data", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date data;
    @JoinColumn(name = "fk_id_enfermaria", referencedColumnName = "pk_id_enfermaria")
    @ManyToOne
    private InterEnfermaria fkIdEnfermaria;
    @JoinColumn(name = "fk_id_funcionario", referencedColumnName = "pk_id_funcionario", nullable = false)
    @ManyToOne(optional = false)
    private RhFuncionario fkIdFuncionario;
    @JoinColumn(name = "fk_id_escala", referencedColumnName = "pk_id_escala", nullable = false)
    @ManyToOne(optional = false)
    private SupiEscala fkIdEscala;
    @JoinColumn(name = "fk_id_turno", referencedColumnName = "pk_id_turno")
    @ManyToOne
    private SupiTurno fkIdTurno;

    public SupiSupervisorHasEscala() {
    }

    public SupiSupervisorHasEscala(Integer pkIdFuncionarioSupervisionaEscala) {
        this.pkIdFuncionarioSupervisionaEscala = pkIdFuncionarioSupervisionaEscala;
    }

    public SupiSupervisorHasEscala(Integer pkIdFuncionarioSupervisionaEscala, Date data) {
        this.pkIdFuncionarioSupervisionaEscala = pkIdFuncionarioSupervisionaEscala;
        this.data = data;
    }

    public Integer getPkIdFuncionarioSupervisionaEscala() {
        return pkIdFuncionarioSupervisionaEscala;
    }

    public void setPkIdFuncionarioSupervisionaEscala(Integer pkIdFuncionarioSupervisionaEscala) {
        this.pkIdFuncionarioSupervisionaEscala = pkIdFuncionarioSupervisionaEscala;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public InterEnfermaria getFkIdEnfermaria() {
        return fkIdEnfermaria;
    }

    public void setFkIdEnfermaria(InterEnfermaria fkIdEnfermaria) {
        this.fkIdEnfermaria = fkIdEnfermaria;
    }

    public RhFuncionario getFkIdFuncionario() {
        return fkIdFuncionario;
    }

    public void setFkIdFuncionario(RhFuncionario fkIdFuncionario) {
        this.fkIdFuncionario = fkIdFuncionario;
    }

    public SupiEscala getFkIdEscala() {
        return fkIdEscala;
    }

    public void setFkIdEscala(SupiEscala fkIdEscala) {
        this.fkIdEscala = fkIdEscala;
    }

    public SupiTurno getFkIdTurno() {
        return fkIdTurno;
    }

    public void setFkIdTurno(SupiTurno fkIdTurno) {
        this.fkIdTurno = fkIdTurno;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdFuncionarioSupervisionaEscala != null ? pkIdFuncionarioSupervisionaEscala.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SupiSupervisorHasEscala)) {
            return false;
        }
        SupiSupervisorHasEscala other = (SupiSupervisorHasEscala) object;
        if ((this.pkIdFuncionarioSupervisionaEscala == null && other.pkIdFuncionarioSupervisionaEscala != null) || (this.pkIdFuncionarioSupervisionaEscala != null && !this.pkIdFuncionarioSupervisionaEscala.equals(other.pkIdFuncionarioSupervisionaEscala))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.SupiSupervisorHasEscala[ pkIdFuncionarioSupervisionaEscala=" + pkIdFuncionarioSupervisionaEscala + " ]";
    }
    
}
