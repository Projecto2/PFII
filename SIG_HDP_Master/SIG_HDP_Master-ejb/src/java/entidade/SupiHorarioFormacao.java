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
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author amed
 */
@Entity
@Table(name = "supi_horario_formacao", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SupiHorarioFormacao.findAll", query = "SELECT s FROM SupiHorarioFormacao s"),
    @NamedQuery(name = "SupiHorarioFormacao.findByHoraInicio", query = "SELECT s FROM SupiHorarioFormacao s WHERE s.horaInicio = :horaInicio"),
    @NamedQuery(name = "SupiHorarioFormacao.findByHoraTermino", query = "SELECT s FROM SupiHorarioFormacao s WHERE s.horaTermino = :horaTermino"),
    @NamedQuery(name = "SupiHorarioFormacao.findByPkHorarioFormacao", query = "SELECT s FROM SupiHorarioFormacao s WHERE s.pkHorarioFormacao = :pkHorarioFormacao")})
public class SupiHorarioFormacao implements Serializable {
    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Column(name = "hora_inicio", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date horaInicio;
    @Basic(optional = false)
    @NotNull
    @Column(name = "hora_termino", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date horaTermino;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_horario_formacao", nullable = false)
    private Integer pkHorarioFormacao;
    @JoinColumn(name = "fk_dia_de_semana", referencedColumnName = "pk_id_dia_semana", nullable = false)
    @ManyToOne(optional = false)
    private GrlDiaSemana fkDiaDeSemana;

    public SupiHorarioFormacao() {
    }

    public SupiHorarioFormacao(Integer pkHorarioFormacao) {
        this.pkHorarioFormacao = pkHorarioFormacao;
    }

    public SupiHorarioFormacao(Integer pkHorarioFormacao, Date horaInicio, Date horaTermino) {
        this.pkHorarioFormacao = pkHorarioFormacao;
        this.horaInicio = horaInicio;
        this.horaTermino = horaTermino;
    }

    public Date getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(Date horaInicio) {
        this.horaInicio = horaInicio;
    }

    public Date getHoraTermino() {
        return horaTermino;
    }

    public void setHoraTermino(Date horaTermino) {
        this.horaTermino = horaTermino;
    }

    public Integer getPkHorarioFormacao() {
        return pkHorarioFormacao;
    }

    public void setPkHorarioFormacao(Integer pkHorarioFormacao) {
        this.pkHorarioFormacao = pkHorarioFormacao;
    }

    public GrlDiaSemana getFkDiaDeSemana() {
        return fkDiaDeSemana;
    }

    public void setFkDiaDeSemana(GrlDiaSemana fkDiaDeSemana) {
        this.fkDiaDeSemana = fkDiaDeSemana;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkHorarioFormacao != null ? pkHorarioFormacao.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SupiHorarioFormacao)) {
            return false;
        }
        SupiHorarioFormacao other = (SupiHorarioFormacao) object;
        if ((this.pkHorarioFormacao == null && other.pkHorarioFormacao != null) || (this.pkHorarioFormacao != null && !this.pkHorarioFormacao.equals(other.pkHorarioFormacao))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.SupiHorarioFormacao[ pkHorarioFormacao=" + pkHorarioFormacao + " ]";
    }
    
}
