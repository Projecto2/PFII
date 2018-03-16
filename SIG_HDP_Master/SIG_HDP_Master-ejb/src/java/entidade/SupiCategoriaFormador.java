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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author amed
 */
@Entity
@Table(name = "supi_categoria_formador", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SupiCategoriaFormador.findAll", query = "SELECT s FROM SupiCategoriaFormador s"),
    @NamedQuery(name = "SupiCategoriaFormador.findByPkIdCategoriaFormador", query = "SELECT s FROM SupiCategoriaFormador s WHERE s.pkIdCategoriaFormador = :pkIdCategoriaFormador"),
    @NamedQuery(name = "SupiCategoriaFormador.findByDescricao", query = "SELECT s FROM SupiCategoriaFormador s WHERE s.descricao = :descricao")})
public class SupiCategoriaFormador implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_categoria_formador", nullable = false)
    private Integer pkIdCategoriaFormador;
    @Size(max = 100)
    @Column(name = "descricao", length = 100)
    private String descricao;

    public SupiCategoriaFormador() {
    }

    public SupiCategoriaFormador(Integer pkIdCategoriaFormador) {
        this.pkIdCategoriaFormador = pkIdCategoriaFormador;
    }

    public Integer getPkIdCategoriaFormador() {
        return pkIdCategoriaFormador;
    }

    public void setPkIdCategoriaFormador(Integer pkIdCategoriaFormador) {
        this.pkIdCategoriaFormador = pkIdCategoriaFormador;
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
        hash += (pkIdCategoriaFormador != null ? pkIdCategoriaFormador.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SupiCategoriaFormador)) {
            return false;
        }
        SupiCategoriaFormador other = (SupiCategoriaFormador) object;
        if ((this.pkIdCategoriaFormador == null && other.pkIdCategoriaFormador != null) || (this.pkIdCategoriaFormador != null && !this.pkIdCategoriaFormador.equals(other.pkIdCategoriaFormador))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.SupiCategoriaFormador[ pkIdCategoriaFormador=" + pkIdCategoriaFormador + " ]";
    }
    
}
