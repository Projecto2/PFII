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
@Table(name = "inter_vacina", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "InterVacina.findAll", query = "SELECT i FROM InterVacina i"),
    @NamedQuery(name = "InterVacina.findByPkIdVacina", query = "SELECT i FROM InterVacina i WHERE i.pkIdVacina = :pkIdVacina"),
    @NamedQuery(name = "InterVacina.findByDescricao", query = "SELECT i FROM InterVacina i WHERE i.descricao = :descricao")})
public class InterVacina implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_vacina", nullable = false)
    private Integer pkIdVacina;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "descricao", nullable = false, length = 45)
    private String descricao;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdVacina")
    private List<InterVacinacao> interVacinacaoList;

    public InterVacina() {
    }

    public InterVacina(Integer pkIdVacina) {
        this.pkIdVacina = pkIdVacina;
    }

    public InterVacina(Integer pkIdVacina, String descricao) {
        this.pkIdVacina = pkIdVacina;
        this.descricao = descricao;
    }

    public Integer getPkIdVacina() {
        return pkIdVacina;
    }

    public void setPkIdVacina(Integer pkIdVacina) {
        this.pkIdVacina = pkIdVacina;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @XmlTransient
    public List<InterVacinacao> getInterVacinacaoList() {
        return interVacinacaoList;
    }

    public void setInterVacinacaoList(List<InterVacinacao> interVacinacaoList) {
        this.interVacinacaoList = interVacinacaoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdVacina != null ? pkIdVacina.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InterVacina)) {
            return false;
        }
        InterVacina other = (InterVacina) object;
        if ((this.pkIdVacina == null && other.pkIdVacina != null) || (this.pkIdVacina != null && !this.pkIdVacina.equals(other.pkIdVacina))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.InterVacina[ pkIdVacina=" + pkIdVacina + " ]";
    }
    
}
