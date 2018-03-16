/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entidade;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "grl_dia_semana", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GrlDiaSemana.findAll", query = "SELECT g FROM GrlDiaSemana g"),
    @NamedQuery(name = "GrlDiaSemana.findByPkIdDiaSemana", query = "SELECT g FROM GrlDiaSemana g WHERE g.pkIdDiaSemana = :pkIdDiaSemana"),
    @NamedQuery(name = "GrlDiaSemana.findByCodigoDiaSemana", query = "SELECT g FROM GrlDiaSemana g WHERE g.codigoDiaSemana = :codigoDiaSemana"),
    @NamedQuery(name = "GrlDiaSemana.findByDescricao", query = "SELECT g FROM GrlDiaSemana g WHERE g.descricao = :descricao")})
public class GrlDiaSemana implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "pk_id_dia_semana", nullable = false)
    private Integer pkIdDiaSemana;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 5)
    @Column(name = "codigo_dia_semana", nullable = false, length = 5)
    private String codigoDiaSemana;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "descricao", nullable = false, length = 20)
    private String descricao;
    @OneToMany(mappedBy = "fkIdDiaDaSemana")
    private List<AdmsDiaHoraDeAtendimentoDoMedico> admsDiaHoraDeAtendimentoDoMedicoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkDiaDeSemana")
    private List<SupiHorarioFormacao> supiHorarioFormacaoList;

    public GrlDiaSemana() {
    }

    public GrlDiaSemana(Integer pkIdDiaSemana) {
        this.pkIdDiaSemana = pkIdDiaSemana;
    }

    public GrlDiaSemana(Integer pkIdDiaSemana, String codigoDiaSemana, String descricao) {
        this.pkIdDiaSemana = pkIdDiaSemana;
        this.codigoDiaSemana = codigoDiaSemana;
        this.descricao = descricao;
    }

    public Integer getPkIdDiaSemana() {
        return pkIdDiaSemana;
    }

    public void setPkIdDiaSemana(Integer pkIdDiaSemana) {
        this.pkIdDiaSemana = pkIdDiaSemana;
    }

    public String getCodigoDiaSemana() {
        return codigoDiaSemana;
    }

    public void setCodigoDiaSemana(String codigoDiaSemana) {
        this.codigoDiaSemana = codigoDiaSemana;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @XmlTransient
    public List<AdmsDiaHoraDeAtendimentoDoMedico> getAdmsDiaHoraDeAtendimentoDoMedicoList() {
        return admsDiaHoraDeAtendimentoDoMedicoList;
    }

    public void setAdmsDiaHoraDeAtendimentoDoMedicoList(List<AdmsDiaHoraDeAtendimentoDoMedico> admsDiaHoraDeAtendimentoDoMedicoList) {
        this.admsDiaHoraDeAtendimentoDoMedicoList = admsDiaHoraDeAtendimentoDoMedicoList;
    }

    @XmlTransient
    public List<SupiHorarioFormacao> getSupiHorarioFormacaoList() {
        return supiHorarioFormacaoList;
    }

    public void setSupiHorarioFormacaoList(List<SupiHorarioFormacao> supiHorarioFormacaoList) {
        this.supiHorarioFormacaoList = supiHorarioFormacaoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdDiaSemana != null ? pkIdDiaSemana.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GrlDiaSemana)) {
            return false;
        }
        GrlDiaSemana other = (GrlDiaSemana) object;
        if ((this.pkIdDiaSemana == null && other.pkIdDiaSemana != null) || (this.pkIdDiaSemana != null && !this.pkIdDiaSemana.equals(other.pkIdDiaSemana))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.GrlDiaSemana[ pkIdDiaSemana=" + pkIdDiaSemana + " ]";
    }
    
}
