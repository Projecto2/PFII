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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author amed
 */
@Entity
@Table(name = "diag_exame_candidato", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DiagExameCandidato.findAll", query = "SELECT d FROM DiagExameCandidato d"),
    @NamedQuery(name = "DiagExameCandidato.findByPkIdExameDador", query = "SELECT d FROM DiagExameCandidato d WHERE d.pkIdExameDador = :pkIdExameDador"),
    @NamedQuery(name = "DiagExameCandidato.findByTa", query = "SELECT d FROM DiagExameCandidato d WHERE d.ta = :ta"),
    @NamedQuery(name = "DiagExameCandidato.findByPeso", query = "SELECT d FROM DiagExameCandidato d WHERE d.peso = :peso"),
    @NamedQuery(name = "DiagExameCandidato.findByDataExame", query = "SELECT d FROM DiagExameCandidato d WHERE d.dataExame = :dataExame"),
    @NamedQuery(name = "DiagExameCandidato.findByHb", query = "SELECT d FROM DiagExameCandidato d WHERE d.hb = :hb")})
public class DiagExameCandidato implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_exame_dador", nullable = false)
    private Integer pkIdExameDador;
    @Size(max = 45)
    @Column(name = "ta", length = 45)
    private String ta;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "peso", precision = 8, scale = 8)
    private Float peso;
    @Column(name = "data_exame")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataExame;
    @Column(name = "hb", precision = 8, scale = 8)
    private Float hb;
    @JoinColumn(name = "fk_id_candidato_dador", referencedColumnName = "pk_id_candidato_dador")
    @ManyToOne
    private DiagCandidatoDador fkIdCandidatoDador;
    @JoinColumn(name = "fk_id_estado_clinico", referencedColumnName = "pk_id_estado_clinico")
    @ManyToOne
    private DiagEstadoClinico fkIdEstadoClinico;
    @JoinColumn(name = "vdrl", referencedColumnName = "pk_id_resultado_exame_candidato")
    @ManyToOne
    private DiagResultadoExameCandidato vdrl;
    @JoinColumn(name = "aghbs", referencedColumnName = "pk_id_resultado_exame_candidato")
    @ManyToOne
    private DiagResultadoExameCandidato aghbs;
    @JoinColumn(name = "hcv", referencedColumnName = "pk_id_resultado_exame_candidato")
    @ManyToOne
    private DiagResultadoExameCandidato hcv;
    @JoinColumn(name = "hiv", referencedColumnName = "pk_id_resultado_exame_candidato")
    @ManyToOne
    private DiagResultadoExameCandidato hiv;
    @JoinColumn(name = "mal", referencedColumnName = "pk_id_resultado_exame_candidato")
    @ManyToOne
    private DiagResultadoExameCandidato mal;
    @JoinColumn(name = "conclusao", referencedColumnName = "pk_id_resultado_triagem")
    @ManyToOne
    private DiagResultadoTriagem conclusao;

    public DiagExameCandidato() {
    }

    public DiagExameCandidato(Integer pkIdExameDador) {
        this.pkIdExameDador = pkIdExameDador;
    }

    public Integer getPkIdExameDador() {
        return pkIdExameDador;
    }

    public void setPkIdExameDador(Integer pkIdExameDador) {
        this.pkIdExameDador = pkIdExameDador;
    }

    public String getTa() {
        return ta;
    }

    public void setTa(String ta) {
        this.ta = ta;
    }

    public Float getPeso() {
        return peso;
    }

    public void setPeso(Float peso) {
        this.peso = peso;
    }

    public Date getDataExame() {
        return dataExame;
    }

    public void setDataExame(Date dataExame) {
        this.dataExame = dataExame;
    }

    public Float getHb() {
        return hb;
    }

    public void setHb(Float hb) {
        this.hb = hb;
    }

    public DiagCandidatoDador getFkIdCandidatoDador() {
        return fkIdCandidatoDador;
    }

    public void setFkIdCandidatoDador(DiagCandidatoDador fkIdCandidatoDador) {
        this.fkIdCandidatoDador = fkIdCandidatoDador;
    }

    public DiagEstadoClinico getFkIdEstadoClinico() {
        return fkIdEstadoClinico;
    }

    public void setFkIdEstadoClinico(DiagEstadoClinico fkIdEstadoClinico) {
        this.fkIdEstadoClinico = fkIdEstadoClinico;
    }

    public DiagResultadoExameCandidato getVdrl() {
        return vdrl;
    }

    public void setVdrl(DiagResultadoExameCandidato vdrl) {
        this.vdrl = vdrl;
    }

    public DiagResultadoExameCandidato getAghbs() {
        return aghbs;
    }

    public void setAghbs(DiagResultadoExameCandidato aghbs) {
        this.aghbs = aghbs;
    }

    public DiagResultadoExameCandidato getHcv() {
        return hcv;
    }

    public void setHcv(DiagResultadoExameCandidato hcv) {
        this.hcv = hcv;
    }

    public DiagResultadoExameCandidato getHiv() {
        return hiv;
    }

    public void setHiv(DiagResultadoExameCandidato hiv) {
        this.hiv = hiv;
    }

    public DiagResultadoExameCandidato getMal() {
        return mal;
    }

    public void setMal(DiagResultadoExameCandidato mal) {
        this.mal = mal;
    }

    public DiagResultadoTriagem getConclusao() {
        return conclusao;
    }

    public void setConclusao(DiagResultadoTriagem conclusao) {
        this.conclusao = conclusao;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdExameDador != null ? pkIdExameDador.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DiagExameCandidato)) {
            return false;
        }
        DiagExameCandidato other = (DiagExameCandidato) object;
        if ((this.pkIdExameDador == null && other.pkIdExameDador != null) || (this.pkIdExameDador != null && !this.pkIdExameDador.equals(other.pkIdExameDador))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.DiagExameCandidato[ pkIdExameDador=" + pkIdExameDador + " ]";
    }
    
}
