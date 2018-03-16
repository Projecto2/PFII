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
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "supi_turno", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SupiTurno.findAll", query = "SELECT s FROM SupiTurno s"),
    @NamedQuery(name = "SupiTurno.findByPkIdTurno", query = "SELECT s FROM SupiTurno s WHERE s.pkIdTurno = :pkIdTurno"),
    @NamedQuery(name = "SupiTurno.findBySigla", query = "SELECT s FROM SupiTurno s WHERE s.sigla = :sigla"),
    @NamedQuery(name = "SupiTurno.findByCargaHoraria", query = "SELECT s FROM SupiTurno s WHERE s.cargaHoraria = :cargaHoraria"),
    @NamedQuery(name = "SupiTurno.findByHoraEntrada", query = "SELECT s FROM SupiTurno s WHERE s.horaEntrada = :horaEntrada"),
    @NamedQuery(name = "SupiTurno.findByHoraSaida", query = "SELECT s FROM SupiTurno s WHERE s.horaSaida = :horaSaida"),
    @NamedQuery(name = "SupiTurno.findByDescricao", query = "SELECT s FROM SupiTurno s WHERE s.descricao = :descricao")})
public class SupiTurno implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_turno", nullable = false)
    private Integer pkIdTurno;
    @Size(max = 2147483647)
    @Column(name = "sigla", length = 2147483647)
    private String sigla;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "carga_horaria", precision = 17, scale = 17)
    private Double cargaHoraria;
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
    @Size(max = 100)
    @Column(name = "descricao", length = 100)
    private String descricao;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdTurno")
    private List<SupiEstagiarioHasEscala> supiEstagiarioHasEscalaList;
    @OneToMany(mappedBy = "fkIdTurno")
    private List<SupiEnfermeiroHasEscala> supiEnfermeiroHasEscalaList;
    @OneToMany(mappedBy = "fkIdTurno")
    private List<SupiSupervisorHasEscala> supiSupervisorHasEscalaList;

    public SupiTurno() {
    }

    public SupiTurno(Integer pkIdTurno) {
        this.pkIdTurno = pkIdTurno;
    }

    public SupiTurno(Integer pkIdTurno, Date horaEntrada, Date horaSaida) {
        this.pkIdTurno = pkIdTurno;
        this.horaEntrada = horaEntrada;
        this.horaSaida = horaSaida;
    }

    public Integer getPkIdTurno() {
        return pkIdTurno;
    }

    public void setPkIdTurno(Integer pkIdTurno) {
        this.pkIdTurno = pkIdTurno;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public Double getCargaHoraria() {
        return cargaHoraria;
    }

    public void setCargaHoraria(Double cargaHoraria) {
        this.cargaHoraria = cargaHoraria;
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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @XmlTransient
    public List<SupiEstagiarioHasEscala> getSupiEstagiarioHasEscalaList() {
        return supiEstagiarioHasEscalaList;
    }

    public void setSupiEstagiarioHasEscalaList(List<SupiEstagiarioHasEscala> supiEstagiarioHasEscalaList) {
        this.supiEstagiarioHasEscalaList = supiEstagiarioHasEscalaList;
    }

    @XmlTransient
    public List<SupiEnfermeiroHasEscala> getSupiEnfermeiroHasEscalaList() {
        return supiEnfermeiroHasEscalaList;
    }

    public void setSupiEnfermeiroHasEscalaList(List<SupiEnfermeiroHasEscala> supiEnfermeiroHasEscalaList) {
        this.supiEnfermeiroHasEscalaList = supiEnfermeiroHasEscalaList;
    }

    @XmlTransient
    public List<SupiSupervisorHasEscala> getSupiSupervisorHasEscalaList() {
        return supiSupervisorHasEscalaList;
    }

    public void setSupiSupervisorHasEscalaList(List<SupiSupervisorHasEscala> supiSupervisorHasEscalaList) {
        this.supiSupervisorHasEscalaList = supiSupervisorHasEscalaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdTurno != null ? pkIdTurno.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SupiTurno)) {
            return false;
        }
        SupiTurno other = (SupiTurno) object;
        if ((this.pkIdTurno == null && other.pkIdTurno != null) || (this.pkIdTurno != null && !this.pkIdTurno.equals(other.pkIdTurno))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.SupiTurno[ pkIdTurno=" + pkIdTurno + " ]";
    }
    
}
