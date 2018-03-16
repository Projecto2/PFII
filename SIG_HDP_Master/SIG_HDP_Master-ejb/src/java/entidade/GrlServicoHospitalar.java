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
@Table(name = "grl_servico_hospitalar", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GrlServicoHospitalar.findAll", query = "SELECT g FROM GrlServicoHospitalar g"),
    @NamedQuery(name = "GrlServicoHospitalar.findByPkIdServicoHospitalar", query = "SELECT g FROM GrlServicoHospitalar g WHERE g.pkIdServicoHospitalar = :pkIdServicoHospitalar"),
    @NamedQuery(name = "GrlServicoHospitalar.findByCodigoServicoHospitalar", query = "SELECT g FROM GrlServicoHospitalar g WHERE g.codigoServicoHospitalar = :codigoServicoHospitalar"),
    @NamedQuery(name = "GrlServicoHospitalar.findByDescricao", query = "SELECT g FROM GrlServicoHospitalar g WHERE g.descricao = :descricao")})
public class GrlServicoHospitalar implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_servico_hospitalar", nullable = false)
    private Integer pkIdServicoHospitalar;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 5)
    @Column(name = "codigo_servico_hospitalar", nullable = false, length = 5)
    private String codigoServicoHospitalar;
    @Size(max = 100)
    @Column(name = "descricao", length = 100)
    private String descricao;

    public GrlServicoHospitalar() {
    }

    public GrlServicoHospitalar(Integer pkIdServicoHospitalar) {
        this.pkIdServicoHospitalar = pkIdServicoHospitalar;
    }

    public GrlServicoHospitalar(Integer pkIdServicoHospitalar, String codigoServicoHospitalar) {
        this.pkIdServicoHospitalar = pkIdServicoHospitalar;
        this.codigoServicoHospitalar = codigoServicoHospitalar;
    }

    public Integer getPkIdServicoHospitalar() {
        return pkIdServicoHospitalar;
    }

    public void setPkIdServicoHospitalar(Integer pkIdServicoHospitalar) {
        this.pkIdServicoHospitalar = pkIdServicoHospitalar;
    }

    public String getCodigoServicoHospitalar() {
        return codigoServicoHospitalar;
    }

    public void setCodigoServicoHospitalar(String codigoServicoHospitalar) {
        this.codigoServicoHospitalar = codigoServicoHospitalar;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdServicoHospitalar != null ? pkIdServicoHospitalar.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GrlServicoHospitalar)) {
            return false;
        }
        GrlServicoHospitalar other = (GrlServicoHospitalar) object;
        if ((this.pkIdServicoHospitalar == null && other.pkIdServicoHospitalar != null) || (this.pkIdServicoHospitalar != null && !this.pkIdServicoHospitalar.equals(other.pkIdServicoHospitalar))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.GrlServicoHospitalar[ pkIdServicoHospitalar=" + pkIdServicoHospitalar + " ]";
    }
    
}
