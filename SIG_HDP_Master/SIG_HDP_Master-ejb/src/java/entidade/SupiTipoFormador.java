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
@Table(name = "supi_tipo_formador", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SupiTipoFormador.findAll", query = "SELECT s FROM SupiTipoFormador s"),
    @NamedQuery(name = "SupiTipoFormador.findByPkIdTipoFormador", query = "SELECT s FROM SupiTipoFormador s WHERE s.pkIdTipoFormador = :pkIdTipoFormador"),
    @NamedQuery(name = "SupiTipoFormador.findByDescricao", query = "SELECT s FROM SupiTipoFormador s WHERE s.descricao = :descricao")})
public class SupiTipoFormador implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "pk_id_tipo_formador", nullable = false)
    private Integer pkIdTipoFormador;
    @Size(max = 100)
    @Column(name = "descricao", length = 100)
    private String descricao;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdTipoFormador")
    private List<SupiFormadorAux> supiFormadorAuxList;
    @OneToMany(mappedBy = "fkIdTipoFormador")
    private List<SupiFormador> supiFormadorList;

    public SupiTipoFormador() {
    }

    public SupiTipoFormador(Integer pkIdTipoFormador) {
        this.pkIdTipoFormador = pkIdTipoFormador;
    }

    public Integer getPkIdTipoFormador() {
        return pkIdTipoFormador;
    }

    public void setPkIdTipoFormador(Integer pkIdTipoFormador) {
        this.pkIdTipoFormador = pkIdTipoFormador;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @XmlTransient
    public List<SupiFormadorAux> getSupiFormadorAuxList() {
        return supiFormadorAuxList;
    }

    public void setSupiFormadorAuxList(List<SupiFormadorAux> supiFormadorAuxList) {
        this.supiFormadorAuxList = supiFormadorAuxList;
    }

    @XmlTransient
    public List<SupiFormador> getSupiFormadorList() {
        return supiFormadorList;
    }

    public void setSupiFormadorList(List<SupiFormador> supiFormadorList) {
        this.supiFormadorList = supiFormadorList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdTipoFormador != null ? pkIdTipoFormador.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SupiTipoFormador)) {
            return false;
        }
        SupiTipoFormador other = (SupiTipoFormador) object;
        if ((this.pkIdTipoFormador == null && other.pkIdTipoFormador != null) || (this.pkIdTipoFormador != null && !this.pkIdTipoFormador.equals(other.pkIdTipoFormador))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.SupiTipoFormador[ pkIdTipoFormador=" + pkIdTipoFormador + " ]";
    }
    
}
