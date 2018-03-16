/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entidade;

import java.io.Serializable;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author amed
 */
@Entity
@Table(name = "supi_turno_medico", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SupiTurnoMedico.findAll", query = "SELECT s FROM SupiTurnoMedico s"),
    @NamedQuery(name = "SupiTurnoMedico.findByPkIdTurnoMedico", query = "SELECT s FROM SupiTurnoMedico s WHERE s.pkIdTurnoMedico = :pkIdTurnoMedico"),
    @NamedQuery(name = "SupiTurnoMedico.findByDescricao", query = "SELECT s FROM SupiTurnoMedico s WHERE s.descricao = :descricao"),
    @NamedQuery(name = "SupiTurnoMedico.findBySigla", query = "SELECT s FROM SupiTurnoMedico s WHERE s.sigla = :sigla"),
    @NamedQuery(name = "SupiTurnoMedico.findByCargaHoraria", query = "SELECT s FROM SupiTurnoMedico s WHERE s.cargaHoraria = :cargaHoraria")})
public class SupiTurnoMedico implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_turno_medico", nullable = false)
    private Integer pkIdTurnoMedico;
    @Size(max = 100)
    @Column(name = "descricao", length = 100)
    private String descricao;
    @Size(max = 100)
    @Column(name = "sigla", length = 100)
    private String sigla;
    @Basic(optional = false)
    @NotNull
    @Column(name = "carga_horaria", nullable = false)
    private double cargaHoraria;
    @OneToMany(mappedBy = "fkIdTurnoMedico")
    private List<SupiMedicoHasEscala> supiMedicoHasEscalaList;

    public SupiTurnoMedico() {
    }

    public SupiTurnoMedico(Integer pkIdTurnoMedico) {
        this.pkIdTurnoMedico = pkIdTurnoMedico;
    }

    public SupiTurnoMedico(Integer pkIdTurnoMedico, double cargaHoraria) {
        this.pkIdTurnoMedico = pkIdTurnoMedico;
        this.cargaHoraria = cargaHoraria;
    }

    public Integer getPkIdTurnoMedico() {
        return pkIdTurnoMedico;
    }

    public void setPkIdTurnoMedico(Integer pkIdTurnoMedico) {
        this.pkIdTurnoMedico = pkIdTurnoMedico;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public double getCargaHoraria() {
        return cargaHoraria;
    }

    public void setCargaHoraria(double cargaHoraria) {
        this.cargaHoraria = cargaHoraria;
    }

    @XmlTransient
    public List<SupiMedicoHasEscala> getSupiMedicoHasEscalaList() {
        return supiMedicoHasEscalaList;
    }

    public void setSupiMedicoHasEscalaList(List<SupiMedicoHasEscala> supiMedicoHasEscalaList) {
        this.supiMedicoHasEscalaList = supiMedicoHasEscalaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdTurnoMedico != null ? pkIdTurnoMedico.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SupiTurnoMedico)) {
            return false;
        }
        SupiTurnoMedico other = (SupiTurnoMedico) object;
        if ((this.pkIdTurnoMedico == null && other.pkIdTurnoMedico != null) || (this.pkIdTurnoMedico != null && !this.pkIdTurnoMedico.equals(other.pkIdTurnoMedico))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.SupiTurnoMedico[ pkIdTurnoMedico=" + pkIdTurnoMedico + " ]";
    }
    
}
