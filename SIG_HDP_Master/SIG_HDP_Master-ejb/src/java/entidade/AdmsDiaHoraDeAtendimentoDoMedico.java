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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author amed
 */
@Entity
@Table(name = "adms_dia_hora_de_atendimento_do_medico", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AdmsDiaHoraDeAtendimentoDoMedico.findAll", query = "SELECT a FROM AdmsDiaHoraDeAtendimentoDoMedico a"),
    @NamedQuery(name = "AdmsDiaHoraDeAtendimentoDoMedico.findByPkIdDiaHoraDeAtendimentoDoMedico", query = "SELECT a FROM AdmsDiaHoraDeAtendimentoDoMedico a WHERE a.pkIdDiaHoraDeAtendimentoDoMedico = :pkIdDiaHoraDeAtendimentoDoMedico"),
    @NamedQuery(name = "AdmsDiaHoraDeAtendimentoDoMedico.findByHoraInicioTrabalho", query = "SELECT a FROM AdmsDiaHoraDeAtendimentoDoMedico a WHERE a.horaInicioTrabalho = :horaInicioTrabalho"),
    @NamedQuery(name = "AdmsDiaHoraDeAtendimentoDoMedico.findByHoraFimTrabalho", query = "SELECT a FROM AdmsDiaHoraDeAtendimentoDoMedico a WHERE a.horaFimTrabalho = :horaFimTrabalho"),
    @NamedQuery(name = "AdmsDiaHoraDeAtendimentoDoMedico.findByNumeroMaximoPaciente", query = "SELECT a FROM AdmsDiaHoraDeAtendimentoDoMedico a WHERE a.numeroMaximoPaciente = :numeroMaximoPaciente")})
public class AdmsDiaHoraDeAtendimentoDoMedico implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_dia_hora_de_atendimento_do_medico", nullable = false)
    private Integer pkIdDiaHoraDeAtendimentoDoMedico;
    @Column(name = "hora_inicio_trabalho")
    @Temporal(TemporalType.TIME)
    private Date horaInicioTrabalho;
    @Column(name = "hora_fim_trabalho")
    @Temporal(TemporalType.TIME)
    private Date horaFimTrabalho;
    @Column(name = "numero_maximo_paciente")
    private Integer numeroMaximoPaciente;
    @JoinColumn(name = "fk_id_dia_da_semana", referencedColumnName = "pk_id_dia_semana")
    @ManyToOne
    private GrlDiaSemana fkIdDiaDaSemana;
    @JoinColumn(name = "fk_id_medico", referencedColumnName = "pk_id_funcionario")
    @ManyToOne
    private RhFuncionario fkIdMedico;

    public AdmsDiaHoraDeAtendimentoDoMedico() {
    }

    public AdmsDiaHoraDeAtendimentoDoMedico(Integer pkIdDiaHoraDeAtendimentoDoMedico) {
        this.pkIdDiaHoraDeAtendimentoDoMedico = pkIdDiaHoraDeAtendimentoDoMedico;
    }

    public Integer getPkIdDiaHoraDeAtendimentoDoMedico() {
        return pkIdDiaHoraDeAtendimentoDoMedico;
    }

    public void setPkIdDiaHoraDeAtendimentoDoMedico(Integer pkIdDiaHoraDeAtendimentoDoMedico) {
        this.pkIdDiaHoraDeAtendimentoDoMedico = pkIdDiaHoraDeAtendimentoDoMedico;
    }

    public Date getHoraInicioTrabalho() {
        return horaInicioTrabalho;
    }

    public void setHoraInicioTrabalho(Date horaInicioTrabalho) {
        this.horaInicioTrabalho = horaInicioTrabalho;
    }

    public Date getHoraFimTrabalho() {
        return horaFimTrabalho;
    }

    public void setHoraFimTrabalho(Date horaFimTrabalho) {
        this.horaFimTrabalho = horaFimTrabalho;
    }

    public Integer getNumeroMaximoPaciente() {
        return numeroMaximoPaciente;
    }

    public void setNumeroMaximoPaciente(Integer numeroMaximoPaciente) {
        this.numeroMaximoPaciente = numeroMaximoPaciente;
    }

    public GrlDiaSemana getFkIdDiaDaSemana() {
        return fkIdDiaDaSemana;
    }

    public void setFkIdDiaDaSemana(GrlDiaSemana fkIdDiaDaSemana) {
        this.fkIdDiaDaSemana = fkIdDiaDaSemana;
    }

    public RhFuncionario getFkIdMedico() {
        return fkIdMedico;
    }

    public void setFkIdMedico(RhFuncionario fkIdMedico) {
        this.fkIdMedico = fkIdMedico;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdDiaHoraDeAtendimentoDoMedico != null ? pkIdDiaHoraDeAtendimentoDoMedico.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AdmsDiaHoraDeAtendimentoDoMedico)) {
            return false;
        }
        AdmsDiaHoraDeAtendimentoDoMedico other = (AdmsDiaHoraDeAtendimentoDoMedico) object;
        if ((this.pkIdDiaHoraDeAtendimentoDoMedico == null && other.pkIdDiaHoraDeAtendimentoDoMedico != null) || (this.pkIdDiaHoraDeAtendimentoDoMedico != null && !this.pkIdDiaHoraDeAtendimentoDoMedico.equals(other.pkIdDiaHoraDeAtendimentoDoMedico))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.AdmsDiaHoraDeAtendimentoDoMedico[ pkIdDiaHoraDeAtendimentoDoMedico=" + pkIdDiaHoraDeAtendimentoDoMedico + " ]";
    }
    
}
