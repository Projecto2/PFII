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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author amed
 */
@Entity
@Table(name = "supi_horario", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SupiHorario.findAll", query = "SELECT s FROM SupiHorario s"),
    @NamedQuery(name = "SupiHorario.findByPkIdHorario", query = "SELECT s FROM SupiHorario s WHERE s.pkIdHorario = :pkIdHorario"),
    @NamedQuery(name = "SupiHorario.findBySiglaHorario", query = "SELECT s FROM SupiHorario s WHERE s.siglaHorario = :siglaHorario"),
    @NamedQuery(name = "SupiHorario.findByHoraEntrada", query = "SELECT s FROM SupiHorario s WHERE s.horaEntrada = :horaEntrada"),
    @NamedQuery(name = "SupiHorario.findByHoraSaida", query = "SELECT s FROM SupiHorario s WHERE s.horaSaida = :horaSaida"),
    @NamedQuery(name = "SupiHorario.findByFkIdTurno", query = "SELECT s FROM SupiHorario s WHERE s.fkIdTurno = :fkIdTurno")})
public class SupiHorario implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_horario", nullable = false)
    private Integer pkIdHorario;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "sigla_horario", nullable = false, length = 100)
    private String siglaHorario;
    @Basic(optional = false)
    @NotNull
    @Column(name = "hora_entrada", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date horaEntrada;
    @Basic(optional = false)
    @NotNull
    @Column(name = "hora_saida", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date horaSaida;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fk_id_turno", nullable = false)
    private int fkIdTurno;

    public SupiHorario() {
    }

    public SupiHorario(Integer pkIdHorario) {
        this.pkIdHorario = pkIdHorario;
    }

    public SupiHorario(Integer pkIdHorario, String siglaHorario, Date horaEntrada, Date horaSaida, int fkIdTurno) {
        this.pkIdHorario = pkIdHorario;
        this.siglaHorario = siglaHorario;
        this.horaEntrada = horaEntrada;
        this.horaSaida = horaSaida;
        this.fkIdTurno = fkIdTurno;
    }

    public Integer getPkIdHorario() {
        return pkIdHorario;
    }

    public void setPkIdHorario(Integer pkIdHorario) {
        this.pkIdHorario = pkIdHorario;
    }

    public String getSiglaHorario() {
        return siglaHorario;
    }

    public void setSiglaHorario(String siglaHorario) {
        this.siglaHorario = siglaHorario;
    }

    public Date getHoraEntrada() {
        return horaEntrada;
    }

    public void setHoraEntrada(Date horaEntrada) {
        this.horaEntrada = horaEntrada;
    }

    public Date getHoraSaida() {
        return horaSaida;
    }

    public void setHoraSaida(Date horaSaida) {
        this.horaSaida = horaSaida;
    }

    public int getFkIdTurno() {
        return fkIdTurno;
    }

    public void setFkIdTurno(int fkIdTurno) {
        this.fkIdTurno = fkIdTurno;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdHorario != null ? pkIdHorario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SupiHorario)) {
            return false;
        }
        SupiHorario other = (SupiHorario) object;
        if ((this.pkIdHorario == null && other.pkIdHorario != null) || (this.pkIdHorario != null && !this.pkIdHorario.equals(other.pkIdHorario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.SupiHorario[ pkIdHorario=" + pkIdHorario + " ]";
    }
    
}
