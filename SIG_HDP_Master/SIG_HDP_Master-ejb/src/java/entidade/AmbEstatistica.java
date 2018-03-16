/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entidade;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "amb_estatistica", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AmbEstatistica.findAll", query = "SELECT a FROM AmbEstatistica a"),
    @NamedQuery(name = "AmbEstatistica.findByPkIdEstatistica", query = "SELECT a FROM AmbEstatistica a WHERE a.pkIdEstatistica = :pkIdEstatistica"),
    @NamedQuery(name = "AmbEstatistica.findByNumeroDeDoentes", query = "SELECT a FROM AmbEstatistica a WHERE a.numeroDeDoentes = :numeroDeDoentes"),
    @NamedQuery(name = "AmbEstatistica.findByData", query = "SELECT a FROM AmbEstatistica a WHERE a.data = :data")})
public class AmbEstatistica implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_estatistica", nullable = false)
    private Long pkIdEstatistica;
    @Column(name = "numero_de_doentes")
    private BigInteger numeroDeDoentes;
    @Column(name = "data")
    @Temporal(TemporalType.TIMESTAMP)
    private Date data;

    public AmbEstatistica() {
    }

    public AmbEstatistica(Long pkIdEstatistica) {
        this.pkIdEstatistica = pkIdEstatistica;
    }

    public Long getPkIdEstatistica() {
        return pkIdEstatistica;
    }

    public void setPkIdEstatistica(Long pkIdEstatistica) {
        this.pkIdEstatistica = pkIdEstatistica;
    }

    public BigInteger getNumeroDeDoentes() {
        return numeroDeDoentes;
    }

    public void setNumeroDeDoentes(BigInteger numeroDeDoentes) {
        this.numeroDeDoentes = numeroDeDoentes;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdEstatistica != null ? pkIdEstatistica.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AmbEstatistica)) {
            return false;
        }
        AmbEstatistica other = (AmbEstatistica) object;
        if ((this.pkIdEstatistica == null && other.pkIdEstatistica != null) || (this.pkIdEstatistica != null && !this.pkIdEstatistica.equals(other.pkIdEstatistica))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.AmbEstatistica[ pkIdEstatistica=" + pkIdEstatistica + " ]";
    }
    
}
