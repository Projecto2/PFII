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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author amed
 */
@Entity
@Table(name = "supi_medico_has_escala", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SupiMedicoHasEscala.findAll", query = "SELECT s FROM SupiMedicoHasEscala s"),
    @NamedQuery(name = "SupiMedicoHasEscala.findByPkIdMedicoEscala", query = "SELECT s FROM SupiMedicoHasEscala s WHERE s.pkIdMedicoEscala = :pkIdMedicoEscala"),
    @NamedQuery(name = "SupiMedicoHasEscala.findByData", query = "SELECT s FROM SupiMedicoHasEscala s WHERE s.data = :data")})
public class SupiMedicoHasEscala implements Serializable {
    @OneToMany(mappedBy = "fkMedico")
    private List<TbConsulta> tbConsultaList;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_medico_escala", nullable = false)
    private Integer pkIdMedicoEscala;
    @Basic(optional = false)
    @NotNull
    @Column(name = "data", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date data;
    @JoinColumn(name = "fk_id_consultorio", referencedColumnName = "pk_id_consultorio")
    @ManyToOne
    private AmbConsultorio fkIdConsultorio;
    @JoinColumn(name = "fk_id_medico", referencedColumnName = "pk_id_funcionario", nullable = false)
    @ManyToOne(optional = false)
    private RhFuncionario fkIdMedico;
    @JoinColumn(name = "fk_id_atividade_medico", referencedColumnName = "pk_id_atividade_medico")
    @ManyToOne
    private SupiAtividadeMedico fkIdAtividadeMedico;
    @JoinColumn(name = "fk_id_escala_medico", referencedColumnName = "pk_id_escala_medico")
    @ManyToOne
    private SupiEscalaMedico fkIdEscalaMedico;
    @JoinColumn(name = "fk_id_local_consulta", referencedColumnName = "pk_id_local_consulta")
    @ManyToOne
    private SupiLocalConsulta fkIdLocalConsulta;
    @JoinColumn(name = "fk_id_turno_medico", referencedColumnName = "pk_id_turno_medico")
    @ManyToOne
    private SupiTurnoMedico fkIdTurnoMedico;
    @OneToMany(mappedBy = "fkIdMedicoHasEscala")
    private List<AmbConsultorioAtendimento> ambConsultorioAtendimentoList;

    public SupiMedicoHasEscala() {
    }

    public SupiMedicoHasEscala(Integer pkIdMedicoEscala) {
        this.pkIdMedicoEscala = pkIdMedicoEscala;
    }

    public SupiMedicoHasEscala(Integer pkIdMedicoEscala, Date data) {
        this.pkIdMedicoEscala = pkIdMedicoEscala;
        this.data = data;
    }

    public Integer getPkIdMedicoEscala() {
        return pkIdMedicoEscala;
    }

    public void setPkIdMedicoEscala(Integer pkIdMedicoEscala) {
        this.pkIdMedicoEscala = pkIdMedicoEscala;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public AmbConsultorio getFkIdConsultorio() {
        return fkIdConsultorio;
    }

    public void setFkIdConsultorio(AmbConsultorio fkIdConsultorio) {
        this.fkIdConsultorio = fkIdConsultorio;
    }

    public RhFuncionario getFkIdMedico() {
        return fkIdMedico;
    }

    public void setFkIdMedico(RhFuncionario fkIdMedico) {
        this.fkIdMedico = fkIdMedico;
    }

    public SupiAtividadeMedico getFkIdAtividadeMedico() {
        return fkIdAtividadeMedico;
    }

    public void setFkIdAtividadeMedico(SupiAtividadeMedico fkIdAtividadeMedico) {
        this.fkIdAtividadeMedico = fkIdAtividadeMedico;
    }

    public SupiEscalaMedico getFkIdEscalaMedico() {
        return fkIdEscalaMedico;
    }

    public void setFkIdEscalaMedico(SupiEscalaMedico fkIdEscalaMedico) {
        this.fkIdEscalaMedico = fkIdEscalaMedico;
    }

    public SupiLocalConsulta getFkIdLocalConsulta() {
        return fkIdLocalConsulta;
    }

    public void setFkIdLocalConsulta(SupiLocalConsulta fkIdLocalConsulta) {
        this.fkIdLocalConsulta = fkIdLocalConsulta;
    }

    public SupiTurnoMedico getFkIdTurnoMedico() {
        return fkIdTurnoMedico;
    }

    public void setFkIdTurnoMedico(SupiTurnoMedico fkIdTurnoMedico) {
        this.fkIdTurnoMedico = fkIdTurnoMedico;
    }

    @XmlTransient
    public List<AmbConsultorioAtendimento> getAmbConsultorioAtendimentoList() {
        return ambConsultorioAtendimentoList;
    }

    public void setAmbConsultorioAtendimentoList(List<AmbConsultorioAtendimento> ambConsultorioAtendimentoList) {
        this.ambConsultorioAtendimentoList = ambConsultorioAtendimentoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdMedicoEscala != null ? pkIdMedicoEscala.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SupiMedicoHasEscala)) {
            return false;
        }
        SupiMedicoHasEscala other = (SupiMedicoHasEscala) object;
        if ((this.pkIdMedicoEscala == null && other.pkIdMedicoEscala != null) || (this.pkIdMedicoEscala != null && !this.pkIdMedicoEscala.equals(other.pkIdMedicoEscala))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.SupiMedicoHasEscala[ pkIdMedicoEscala=" + pkIdMedicoEscala + " ]";
    }

    @XmlTransient
    public List<TbConsulta> getTbConsultaList() {
        return tbConsultaList;
    }

    public void setTbConsultaList(List<TbConsulta> tbConsultaList) {
        this.tbConsultaList = tbConsultaList;
    }
    
}
