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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author amed
 */
@Entity
@Table(name = "fin_banco", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FinBanco.findAll", query = "SELECT f FROM FinBanco f"),
    @NamedQuery(name = "FinBanco.findByPkIdBanco", query = "SELECT f FROM FinBanco f WHERE f.pkIdBanco = :pkIdBanco"),
    @NamedQuery(name = "FinBanco.findByNomeBanco", query = "SELECT f FROM FinBanco f WHERE f.nomeBanco = :nomeBanco")})
public class FinBanco implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_banco", nullable = false)
    private Integer pkIdBanco;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "nome_banco", nullable = false, length = 100)
    private String nomeBanco;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdBanco")
    private List<FinContaBancaria> finContaBancariaList;

    public FinBanco() {
    }

    public FinBanco(Integer pkIdBanco) {
        this.pkIdBanco = pkIdBanco;
    }

    public FinBanco(Integer pkIdBanco, String nomeBanco) {
        this.pkIdBanco = pkIdBanco;
        this.nomeBanco = nomeBanco;
    }

    public Integer getPkIdBanco() {
        return pkIdBanco;
    }

    public void setPkIdBanco(Integer pkIdBanco) {
        this.pkIdBanco = pkIdBanco;
    }

    public String getNomeBanco() {
        return nomeBanco;
    }

    public void setNomeBanco(String nomeBanco) {
        this.nomeBanco = nomeBanco;
    }

    @XmlTransient
    public List<FinContaBancaria> getFinContaBancariaList() {
        return finContaBancariaList;
    }

    public void setFinContaBancariaList(List<FinContaBancaria> finContaBancariaList) {
        this.finContaBancariaList = finContaBancariaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdBanco != null ? pkIdBanco.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FinBanco)) {
            return false;
        }
        FinBanco other = (FinBanco) object;
        if ((this.pkIdBanco == null && other.pkIdBanco != null) || (this.pkIdBanco != null && !this.pkIdBanco.equals(other.pkIdBanco))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.FinBanco[ pkIdBanco=" + pkIdBanco + " ]";
    }
    
}
