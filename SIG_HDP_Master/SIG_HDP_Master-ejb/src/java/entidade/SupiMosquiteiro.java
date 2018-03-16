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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author amed
 */
@Entity
@Table(name = "supi_mosquiteiro", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SupiMosquiteiro.findAll", query = "SELECT s FROM SupiMosquiteiro s"),
    @NamedQuery(name = "SupiMosquiteiro.findByPkIdMosquiteiro", query = "SELECT s FROM SupiMosquiteiro s WHERE s.pkIdMosquiteiro = :pkIdMosquiteiro"),
    @NamedQuery(name = "SupiMosquiteiro.findByObservacao", query = "SELECT s FROM SupiMosquiteiro s WHERE s.observacao = :observacao"),
    @NamedQuery(name = "SupiMosquiteiro.findByDataEmpregnacao", query = "SELECT s FROM SupiMosquiteiro s WHERE s.dataEmpregnacao = :dataEmpregnacao"),
    @NamedQuery(name = "SupiMosquiteiro.findByDataCadastro", query = "SELECT s FROM SupiMosquiteiro s WHERE s.dataCadastro = :dataCadastro")})
public class SupiMosquiteiro implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_mosquiteiro", nullable = false)
    private Long pkIdMosquiteiro;
    @Size(max = 45)
    @Column(name = "observacao", length = 45)
    private String observacao;
    @Basic(optional = false)
    @NotNull
    @Column(name = "data_empregnacao", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dataEmpregnacao;
    @Basic(optional = false)
    @NotNull
    @Column(name = "data_cadastro", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dataCadastro;
    @JoinColumn(name = "fk_id_estado_mosquiteiro", referencedColumnName = "pk_id_estado_mosquiteiro", nullable = false)
    @ManyToOne(optional = false)
    private SupiEstadoMosquiteiro fkIdEstadoMosquiteiro;

    public SupiMosquiteiro() {
    }

    public SupiMosquiteiro(Long pkIdMosquiteiro) {
        this.pkIdMosquiteiro = pkIdMosquiteiro;
    }

    public SupiMosquiteiro(Long pkIdMosquiteiro, Date dataEmpregnacao, Date dataCadastro) {
        this.pkIdMosquiteiro = pkIdMosquiteiro;
        this.dataEmpregnacao = dataEmpregnacao;
        this.dataCadastro = dataCadastro;
    }

    public Long getPkIdMosquiteiro() {
        return pkIdMosquiteiro;
    }

    public void setPkIdMosquiteiro(Long pkIdMosquiteiro) {
        this.pkIdMosquiteiro = pkIdMosquiteiro;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Date getDataEmpregnacao() {
        return dataEmpregnacao;
    }

    public void setDataEmpregnacao(Date dataEmpregnacao) {
        this.dataEmpregnacao = dataEmpregnacao;
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public SupiEstadoMosquiteiro getFkIdEstadoMosquiteiro() {
        return fkIdEstadoMosquiteiro;
    }

    public void setFkIdEstadoMosquiteiro(SupiEstadoMosquiteiro fkIdEstadoMosquiteiro) {
        this.fkIdEstadoMosquiteiro = fkIdEstadoMosquiteiro;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdMosquiteiro != null ? pkIdMosquiteiro.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SupiMosquiteiro)) {
            return false;
        }
        SupiMosquiteiro other = (SupiMosquiteiro) object;
        if ((this.pkIdMosquiteiro == null && other.pkIdMosquiteiro != null) || (this.pkIdMosquiteiro != null && !this.pkIdMosquiteiro.equals(other.pkIdMosquiteiro))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.SupiMosquiteiro[ pkIdMosquiteiro=" + pkIdMosquiteiro + " ]";
    }
    
}
