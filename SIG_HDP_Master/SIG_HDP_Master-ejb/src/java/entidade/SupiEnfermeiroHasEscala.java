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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author amed
 */
@Entity
@Table(name = "supi_enfermeiro_has_escala", catalog = "sigh_db", schema = "public", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"fk_id_enfermeiro", "fk_id_escala", "data"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SupiEnfermeiroHasEscala.findAll", query = "SELECT s FROM SupiEnfermeiroHasEscala s"),
    @NamedQuery(name = "SupiEnfermeiroHasEscala.findByPkIdFuncionarioEscala", query = "SELECT s FROM SupiEnfermeiroHasEscala s WHERE s.pkIdFuncionarioEscala = :pkIdFuncionarioEscala"),
    @NamedQuery(name = "SupiEnfermeiroHasEscala.findByData", query = "SELECT s FROM SupiEnfermeiroHasEscala s WHERE s.data = :data")})
public class SupiEnfermeiroHasEscala implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_funcionario_escala", nullable = false)
    private Integer pkIdFuncionarioEscala;
    @Basic(optional = false)
    @NotNull
    @Column(name = "data", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date data;
    @JoinColumn(name = "fk_id_funcionario_supervisor", referencedColumnName = "pk_id_funcionario")
    @ManyToOne
    private RhFuncionario fkIdFuncionarioSupervisor;
    @JoinColumn(name = "fk_id_enfermeiro", referencedColumnName = "pk_id_funcionario", nullable = false)
    @ManyToOne(optional = false)
    private RhFuncionario fkIdEnfermeiro;
    @JoinColumn(name = "fk_id_escala", referencedColumnName = "pk_id_escala", nullable = false)
    @ManyToOne(optional = false)
    private SupiEscala fkIdEscala;
    @JoinColumn(name = "fk_id_turno", referencedColumnName = "pk_id_turno")
    @ManyToOne
    private SupiTurno fkIdTurno;

    public SupiEnfermeiroHasEscala() {
    }

    public SupiEnfermeiroHasEscala(Integer pkIdFuncionarioEscala) {
        this.pkIdFuncionarioEscala = pkIdFuncionarioEscala;
    }

    public SupiEnfermeiroHasEscala(Integer pkIdFuncionarioEscala, Date data) {
        this.pkIdFuncionarioEscala = pkIdFuncionarioEscala;
        this.data = data;
    }

    public Integer getPkIdFuncionarioEscala() {
        return pkIdFuncionarioEscala;
    }

    public void setPkIdFuncionarioEscala(Integer pkIdFuncionarioEscala) {
        this.pkIdFuncionarioEscala = pkIdFuncionarioEscala;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public RhFuncionario getFkIdFuncionarioSupervisor() {
        return fkIdFuncionarioSupervisor;
    }

    public void setFkIdFuncionarioSupervisor(RhFuncionario fkIdFuncionarioSupervisor) {
        this.fkIdFuncionarioSupervisor = fkIdFuncionarioSupervisor;
    }

    public RhFuncionario getFkIdEnfermeiro() {
        return fkIdEnfermeiro;
    }

    public void setFkIdEnfermeiro(RhFuncionario fkIdEnfermeiro) {
        this.fkIdEnfermeiro = fkIdEnfermeiro;
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
        hash += (pkIdFuncionarioEscala != null ? pkIdFuncionarioEscala.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SupiEnfermeiroHasEscala)) {
            return false;
        }
        SupiEnfermeiroHasEscala other = (SupiEnfermeiroHasEscala) object;
        if ((this.pkIdFuncionarioEscala == null && other.pkIdFuncionarioEscala != null) || (this.pkIdFuncionarioEscala != null && !this.pkIdFuncionarioEscala.equals(other.pkIdFuncionarioEscala))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.SupiEnfermeiroHasEscala[ pkIdFuncionarioEscala=" + pkIdFuncionarioEscala + " ]";
    }
    
}
