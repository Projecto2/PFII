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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author amed
 */
@Entity
@Table(name = "supi_escala", catalog = "sigh_db", schema = "public", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"ano", "mes", "fk_id_seccao", "fk_id_tipo_escala"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SupiEscala.findAll", query = "SELECT s FROM SupiEscala s"),
    @NamedQuery(name = "SupiEscala.findByPkIdEscala", query = "SELECT s FROM SupiEscala s WHERE s.pkIdEscala = :pkIdEscala"),
    @NamedQuery(name = "SupiEscala.findByDataEscala", query = "SELECT s FROM SupiEscala s WHERE s.dataEscala = :dataEscala"),
    @NamedQuery(name = "SupiEscala.findByMes", query = "SELECT s FROM SupiEscala s WHERE s.mes = :mes"),
    @NamedQuery(name = "SupiEscala.findByAno", query = "SELECT s FROM SupiEscala s WHERE s.ano = :ano"),
    @NamedQuery(name = "SupiEscala.findByCargaHoraria", query = "SELECT s FROM SupiEscala s WHERE s.cargaHoraria = :cargaHoraria")})
public class SupiEscala implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_escala", nullable = false)
    private Integer pkIdEscala;
    @Basic(optional = false)
    @NotNull
    @Column(name = "data_escala", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dataEscala;
    @Basic(optional = false)
    @NotNull
    @Column(name = "mes", nullable = false)
    private int mes;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ano", nullable = false)
    private int ano;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "carga_horaria", precision = 17, scale = 17)
    private Double cargaHoraria;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdEscala")
    private List<SupiEstagiarioHasEscala> supiEstagiarioHasEscalaList;
    @JoinColumn(name = "fk_id_seccao", referencedColumnName = "pk_id_seccao")
    @ManyToOne
    private SupiSeccao fkIdSeccao;
    @JoinColumn(name = "fk_id_tipo_escala", referencedColumnName = "pk_id_tipo_escala", nullable = false)
    @ManyToOne(optional = false)
    private SupiTipoEscala fkIdTipoEscala;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdEscala")
    private List<SupiEnfermeiroHasEscala> supiEnfermeiroHasEscalaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdEscala")
    private List<SupiSupervisorHasEscala> supiSupervisorHasEscalaList;

    public SupiEscala() {
    }

    public SupiEscala(Integer pkIdEscala) {
        this.pkIdEscala = pkIdEscala;
    }

    public SupiEscala(Integer pkIdEscala, Date dataEscala, int mes, int ano) {
        this.pkIdEscala = pkIdEscala;
        this.dataEscala = dataEscala;
        this.mes = mes;
        this.ano = ano;
    }

    public Integer getPkIdEscala() {
        return pkIdEscala;
    }

    public void setPkIdEscala(Integer pkIdEscala) {
        this.pkIdEscala = pkIdEscala;
    }

    public Date getDataEscala() {
        return dataEscala;
    }

    public void setDataEscala(Date dataEscala) {
        this.dataEscala = dataEscala;
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public Double getCargaHoraria() {
        return cargaHoraria;
    }

    public void setCargaHoraria(Double cargaHoraria) {
        this.cargaHoraria = cargaHoraria;
    }

    @XmlTransient
    public List<SupiEstagiarioHasEscala> getSupiEstagiarioHasEscalaList() {
        return supiEstagiarioHasEscalaList;
    }

    public void setSupiEstagiarioHasEscalaList(List<SupiEstagiarioHasEscala> supiEstagiarioHasEscalaList) {
        this.supiEstagiarioHasEscalaList = supiEstagiarioHasEscalaList;
    }

    public SupiSeccao getFkIdSeccao() {
        return fkIdSeccao;
    }

    public void setFkIdSeccao(SupiSeccao fkIdSeccao) {
        this.fkIdSeccao = fkIdSeccao;
    }

    public SupiTipoEscala getFkIdTipoEscala() {
        return fkIdTipoEscala;
    }

    public void setFkIdTipoEscala(SupiTipoEscala fkIdTipoEscala) {
        this.fkIdTipoEscala = fkIdTipoEscala;
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
        hash += (pkIdEscala != null ? pkIdEscala.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SupiEscala)) {
            return false;
        }
        SupiEscala other = (SupiEscala) object;
        if ((this.pkIdEscala == null && other.pkIdEscala != null) || (this.pkIdEscala != null && !this.pkIdEscala.equals(other.pkIdEscala))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.SupiEscala[ pkIdEscala=" + pkIdEscala + " ]";
    }
    
}
