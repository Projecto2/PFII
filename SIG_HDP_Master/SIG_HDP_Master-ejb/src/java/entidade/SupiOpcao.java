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
@Table(name = "supi_opcao", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SupiOpcao.findAll", query = "SELECT s FROM SupiOpcao s"),
    @NamedQuery(name = "SupiOpcao.findByPkIdOpcao", query = "SELECT s FROM SupiOpcao s WHERE s.pkIdOpcao = :pkIdOpcao"),
    @NamedQuery(name = "SupiOpcao.findByDescricao", query = "SELECT s FROM SupiOpcao s WHERE s.descricao = :descricao")})
public class SupiOpcao implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_opcao", nullable = false)
    private Integer pkIdOpcao;
    @Size(max = 100)
    @Column(name = "descricao", length = 100)
    private String descricao;

    public SupiOpcao() {
    }

    public SupiOpcao(Integer pkIdOpcao) {
        this.pkIdOpcao = pkIdOpcao;
    }

    public Integer getPkIdOpcao() {
        return pkIdOpcao;
    }

    public void setPkIdOpcao(Integer pkIdOpcao) {
        this.pkIdOpcao = pkIdOpcao;
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
        hash += (pkIdOpcao != null ? pkIdOpcao.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SupiOpcao)) {
            return false;
        }
        SupiOpcao other = (SupiOpcao) object;
        if ((this.pkIdOpcao == null && other.pkIdOpcao != null) || (this.pkIdOpcao != null && !this.pkIdOpcao.equals(other.pkIdOpcao))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.SupiOpcao[ pkIdOpcao=" + pkIdOpcao + " ]";
    }
    
}
