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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author amed
 */
@Entity
@Table(name = "rh_horario_geral_de_trabalho", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RhHorarioGeralDeTrabalho.findAll", query = "SELECT r FROM RhHorarioGeralDeTrabalho r"),
    @NamedQuery(name = "RhHorarioGeralDeTrabalho.findByPkIdHorarioGeralDeTrabalho", query = "SELECT r FROM RhHorarioGeralDeTrabalho r WHERE r.pkIdHorarioGeralDeTrabalho = :pkIdHorarioGeralDeTrabalho"),
    @NamedQuery(name = "RhHorarioGeralDeTrabalho.findByHoraEntrada", query = "SELECT r FROM RhHorarioGeralDeTrabalho r WHERE r.horaEntrada = :horaEntrada"),
    @NamedQuery(name = "RhHorarioGeralDeTrabalho.findByHoraSaida", query = "SELECT r FROM RhHorarioGeralDeTrabalho r WHERE r.horaSaida = :horaSaida"),
    @NamedQuery(name = "RhHorarioGeralDeTrabalho.findByHoraIntervalo", query = "SELECT r FROM RhHorarioGeralDeTrabalho r WHERE r.horaIntervalo = :horaIntervalo"),
    @NamedQuery(name = "RhHorarioGeralDeTrabalho.findByMinutosIntervalo", query = "SELECT r FROM RhHorarioGeralDeTrabalho r WHERE r.minutosIntervalo = :minutosIntervalo"),
    @NamedQuery(name = "RhHorarioGeralDeTrabalho.findByDataDaUltimaAlteracao", query = "SELECT r FROM RhHorarioGeralDeTrabalho r WHERE r.dataDaUltimaAlteracao = :dataDaUltimaAlteracao")})
public class RhHorarioGeralDeTrabalho implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_horario_geral_de_trabalho", nullable = false)
    private Integer pkIdHorarioGeralDeTrabalho;
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
    @Column(name = "hora_intervalo")
    @Temporal(TemporalType.TIME)
    private Date horaIntervalo;
    @Column(name = "minutos_intervalo")
    private Integer minutosIntervalo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "data_da_ultima_alteracao", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataDaUltimaAlteracao;
    @OneToMany(mappedBy = "fkIdHorarioGeralDeTrabalho")
    private List<RhFuncionario> rhFuncionarioList;

    public RhHorarioGeralDeTrabalho() {
    }

    public RhHorarioGeralDeTrabalho(Integer pkIdHorarioGeralDeTrabalho) {
        this.pkIdHorarioGeralDeTrabalho = pkIdHorarioGeralDeTrabalho;
    }

    public RhHorarioGeralDeTrabalho(Integer pkIdHorarioGeralDeTrabalho, Date horaEntrada, Date horaSaida, Date dataDaUltimaAlteracao) {
        this.pkIdHorarioGeralDeTrabalho = pkIdHorarioGeralDeTrabalho;
        this.horaEntrada = horaEntrada;
        this.horaSaida = horaSaida;
        this.dataDaUltimaAlteracao = dataDaUltimaAlteracao;
    }

    public Integer getPkIdHorarioGeralDeTrabalho() {
        return pkIdHorarioGeralDeTrabalho;
    }

    public void setPkIdHorarioGeralDeTrabalho(Integer pkIdHorarioGeralDeTrabalho) {
        this.pkIdHorarioGeralDeTrabalho = pkIdHorarioGeralDeTrabalho;
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

    public Date getHoraIntervalo() {
        return horaIntervalo;
    }

    public void setHoraIntervalo(Date horaIntervalo) {
        this.horaIntervalo = horaIntervalo;
    }

    public Integer getMinutosIntervalo() {
        return minutosIntervalo;
    }

    public void setMinutosIntervalo(Integer minutosIntervalo) {
        this.minutosIntervalo = minutosIntervalo;
    }

    public Date getDataDaUltimaAlteracao() {
        return dataDaUltimaAlteracao;
    }

    public void setDataDaUltimaAlteracao(Date dataDaUltimaAlteracao) {
        this.dataDaUltimaAlteracao = dataDaUltimaAlteracao;
    }

    @XmlTransient
    public List<RhFuncionario> getRhFuncionarioList() {
        return rhFuncionarioList;
    }

    public void setRhFuncionarioList(List<RhFuncionario> rhFuncionarioList) {
        this.rhFuncionarioList = rhFuncionarioList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdHorarioGeralDeTrabalho != null ? pkIdHorarioGeralDeTrabalho.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RhHorarioGeralDeTrabalho)) {
            return false;
        }
        RhHorarioGeralDeTrabalho other = (RhHorarioGeralDeTrabalho) object;
        if ((this.pkIdHorarioGeralDeTrabalho == null && other.pkIdHorarioGeralDeTrabalho != null) || (this.pkIdHorarioGeralDeTrabalho != null && !this.pkIdHorarioGeralDeTrabalho.equals(other.pkIdHorarioGeralDeTrabalho))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.RhHorarioGeralDeTrabalho[ pkIdHorarioGeralDeTrabalho=" + pkIdHorarioGeralDeTrabalho + " ]";
    }
    
}
