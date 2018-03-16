/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entidade;

import java.io.Serializable;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author amed
 */
@Entity
@Table(name = "inter_tratamento_especifico", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "InterTratamentoEspecifico.findAll", query = "SELECT i FROM InterTratamentoEspecifico i"),
    @NamedQuery(name = "InterTratamentoEspecifico.findByPkIdTratamentoEspecifico", query = "SELECT i FROM InterTratamentoEspecifico i WHERE i.pkIdTratamentoEspecifico = :pkIdTratamentoEspecifico"),
    @NamedQuery(name = "InterTratamentoEspecifico.findByAntibiotico2", query = "SELECT i FROM InterTratamentoEspecifico i WHERE i.antibiotico2 = :antibiotico2"),
    @NamedQuery(name = "InterTratamentoEspecifico.findByAntibiotico3", query = "SELECT i FROM InterTratamentoEspecifico i WHERE i.antibiotico3 = :antibiotico3"),
    @NamedQuery(name = "InterTratamentoEspecifico.findByResomal", query = "SELECT i FROM InterTratamentoEspecifico i WHERE i.resomal = :resomal"),
    @NamedQuery(name = "InterTratamentoEspecifico.findBySondang", query = "SELECT i FROM InterTratamentoEspecifico i WHERE i.sondang = :sondang"),
    @NamedQuery(name = "InterTratamentoEspecifico.findByTransf", query = "SELECT i FROM InterTratamentoEspecifico i WHERE i.transf = :transf"),
    @NamedQuery(name = "InterTratamentoEspecifico.findByPerf", query = "SELECT i FROM InterTratamentoEspecifico i WHERE i.perf = :perf")})
public class InterTratamentoEspecifico implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_tratamento_especifico", nullable = false)
    private Long pkIdTratamentoEspecifico;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "antibiotico2", nullable = false, length = 45)
    private String antibiotico2;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "antibiotico3", nullable = false, length = 45)
    private String antibiotico3;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "resomal", precision = 8, scale = 8)
    private Float resomal;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "sondang", nullable = false, length = 45)
    private String sondang;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "transf", nullable = false, length = 45)
    private String transf;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "perf", nullable = false, length = 45)
    private String perf;
    @JoinColumn(name = "fk_id_tratamento_malnutricao", referencedColumnName = "pk_id_tratamento_malnutricao", nullable = false)
    @ManyToOne(optional = false)
    private InterTratamentoMalnutricao fkIdTratamentoMalnutricao;

    public InterTratamentoEspecifico() {
    }

    public InterTratamentoEspecifico(Long pkIdTratamentoEspecifico) {
        this.pkIdTratamentoEspecifico = pkIdTratamentoEspecifico;
    }

    public InterTratamentoEspecifico(Long pkIdTratamentoEspecifico, String antibiotico2, String antibiotico3, String sondang, String transf, String perf) {
        this.pkIdTratamentoEspecifico = pkIdTratamentoEspecifico;
        this.antibiotico2 = antibiotico2;
        this.antibiotico3 = antibiotico3;
        this.sondang = sondang;
        this.transf = transf;
        this.perf = perf;
    }

    public Long getPkIdTratamentoEspecifico() {
        return pkIdTratamentoEspecifico;
    }

    public void setPkIdTratamentoEspecifico(Long pkIdTratamentoEspecifico) {
        this.pkIdTratamentoEspecifico = pkIdTratamentoEspecifico;
    }

    public String getAntibiotico2() {
        return antibiotico2;
    }

    public void setAntibiotico2(String antibiotico2) {
        this.antibiotico2 = antibiotico2;
    }

    public String getAntibiotico3() {
        return antibiotico3;
    }

    public void setAntibiotico3(String antibiotico3) {
        this.antibiotico3 = antibiotico3;
    }

    public Float getResomal() {
        return resomal;
    }

    public void setResomal(Float resomal) {
        this.resomal = resomal;
    }

    public String getSondang() {
        return sondang;
    }

    public void setSondang(String sondang) {
        this.sondang = sondang;
    }

    public String getTransf() {
        return transf;
    }

    public void setTransf(String transf) {
        this.transf = transf;
    }

    public String getPerf() {
        return perf;
    }

    public void setPerf(String perf) {
        this.perf = perf;
    }

    public InterTratamentoMalnutricao getFkIdTratamentoMalnutricao() {
        return fkIdTratamentoMalnutricao;
    }

    public void setFkIdTratamentoMalnutricao(InterTratamentoMalnutricao fkIdTratamentoMalnutricao) {
        this.fkIdTratamentoMalnutricao = fkIdTratamentoMalnutricao;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdTratamentoEspecifico != null ? pkIdTratamentoEspecifico.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InterTratamentoEspecifico)) {
            return false;
        }
        InterTratamentoEspecifico other = (InterTratamentoEspecifico) object;
        if ((this.pkIdTratamentoEspecifico == null && other.pkIdTratamentoEspecifico != null) || (this.pkIdTratamentoEspecifico != null && !this.pkIdTratamentoEspecifico.equals(other.pkIdTratamentoEspecifico))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.InterTratamentoEspecifico[ pkIdTratamentoEspecifico=" + pkIdTratamentoEspecifico + " ]";
    }
    
}
