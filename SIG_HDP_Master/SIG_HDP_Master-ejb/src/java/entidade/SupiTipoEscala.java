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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author amed
 */
@Entity
@Table(name = "supi_tipo_escala", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SupiTipoEscala.findAll", query = "SELECT s FROM SupiTipoEscala s"),
    @NamedQuery(name = "SupiTipoEscala.findByPkIdTipoEscala", query = "SELECT s FROM SupiTipoEscala s WHERE s.pkIdTipoEscala = :pkIdTipoEscala"),
    @NamedQuery(name = "SupiTipoEscala.findByDescricao", query = "SELECT s FROM SupiTipoEscala s WHERE s.descricao = :descricao")})
public class SupiTipoEscala implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_tipo_escala", nullable = false)
    private Integer pkIdTipoEscala;
    @Size(max = 2147483647)
    @Column(name = "descricao", length = 2147483647)
    private String descricao;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdTipoEscala")
    private List<SupiEscala> supiEscalaList;

    public SupiTipoEscala() {
    }

    public SupiTipoEscala(Integer pkIdTipoEscala) {
        this.pkIdTipoEscala = pkIdTipoEscala;
    }

    public Integer getPkIdTipoEscala() {
        return pkIdTipoEscala;
    }

    public void setPkIdTipoEscala(Integer pkIdTipoEscala) {
        this.pkIdTipoEscala = pkIdTipoEscala;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @XmlTransient
    public List<SupiEscala> getSupiEscalaList() {
        return supiEscalaList;
    }

    public void setSupiEscalaList(List<SupiEscala> supiEscalaList) {
        this.supiEscalaList = supiEscalaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdTipoEscala != null ? pkIdTipoEscala.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SupiTipoEscala)) {
            return false;
        }
        SupiTipoEscala other = (SupiTipoEscala) object;
        if ((this.pkIdTipoEscala == null && other.pkIdTipoEscala != null) || (this.pkIdTipoEscala != null && !this.pkIdTipoEscala.equals(other.pkIdTipoEscala))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.SupiTipoEscala[ pkIdTipoEscala=" + pkIdTipoEscala + " ]";
    }
    
}
