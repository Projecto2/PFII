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
@Table(name = "grl_horario", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GrlHorario.findAll", query = "SELECT g FROM GrlHorario g"),
    @NamedQuery(name = "GrlHorario.findByPkIdHorario", query = "SELECT g FROM GrlHorario g WHERE g.pkIdHorario = :pkIdHorario"),
    @NamedQuery(name = "GrlHorario.findByCodigoHorario", query = "SELECT g FROM GrlHorario g WHERE g.codigoHorario = :codigoHorario"),
    @NamedQuery(name = "GrlHorario.findByHoraEntrada", query = "SELECT g FROM GrlHorario g WHERE g.horaEntrada = :horaEntrada"),
    @NamedQuery(name = "GrlHorario.findByHoraSaida", query = "SELECT g FROM GrlHorario g WHERE g.horaSaida = :horaSaida"),
    @NamedQuery(name = "GrlHorario.findByFkIdTurno", query = "SELECT g FROM GrlHorario g WHERE g.fkIdTurno = :fkIdTurno")})
public class GrlHorario implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_horario", nullable = false)
    private Integer pkIdHorario;
    @Size(max = 5)
    @Column(name = "codigo_horario", length = 5)
    private String codigoHorario;
    @Basic(optional = false)
    @NotNull
    @Column(name = "hora_entrada", nullable = false)
    @Temporal(TemporalType.TIME)
    private Date horaEntrada;
    @Basic(optional = false)
    @NotNull
    @Column(name = "hora_saida", nullable = false)
    @Temporal(TemporalType.TIME)
    private Date horaSaida;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fk_id_turno", nullable = false)
    private int fkIdTurno;

    public GrlHorario() {
    }

    public GrlHorario(Integer pkIdHorario) {
        this.pkIdHorario = pkIdHorario;
    }

    public GrlHorario(Integer pkIdHorario, Date horaEntrada, Date horaSaida, int fkIdTurno) {
        this.pkIdHorario = pkIdHorario;
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

    public String getCodigoHorario() {
        return codigoHorario;
    }

    public void setCodigoHorario(String codigoHorario) {
        this.codigoHorario = codigoHorario;
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
        if (!(object instanceof GrlHorario)) {
            return false;
        }
        GrlHorario other = (GrlHorario) object;
        if ((this.pkIdHorario == null && other.pkIdHorario != null) || (this.pkIdHorario != null && !this.pkIdHorario.equals(other.pkIdHorario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.GrlHorario[ pkIdHorario=" + pkIdHorario + " ]";
    }
    
}
