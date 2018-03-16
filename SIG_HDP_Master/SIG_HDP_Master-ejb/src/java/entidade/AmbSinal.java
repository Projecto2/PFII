/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entidade;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
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
@Table(name = "amb_sinal", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AmbSinal.findAll", query = "SELECT a FROM AmbSinal a"),
    @NamedQuery(name = "AmbSinal.findByDescricao", query = "SELECT a FROM AmbSinal a WHERE a.descricao = :descricao"),
    @NamedQuery(name = "AmbSinal.findByPkIdSinal", query = "SELECT a FROM AmbSinal a WHERE a.pkIdSinal = :pkIdSinal")})
public class AmbSinal implements Serializable {
    private static final long serialVersionUID = 1L;
    @Size(max = 50)
    @Column(name = "descricao", length = 50)
    private String descricao;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "pk_id_sinal", nullable = false)
    private Integer pkIdSinal;
    @OneToMany(mappedBy = "fkIdSinal")
    private List<AmbTriagemHasSinal> ambTriagemHasSinalList;

    public AmbSinal() {
    }

    public AmbSinal(Integer pkIdSinal) {
        this.pkIdSinal = pkIdSinal;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getPkIdSinal() {
        return pkIdSinal;
    }

    public void setPkIdSinal(Integer pkIdSinal) {
        this.pkIdSinal = pkIdSinal;
    }

    @XmlTransient
    public List<AmbTriagemHasSinal> getAmbTriagemHasSinalList() {
        return ambTriagemHasSinalList;
    }

    public void setAmbTriagemHasSinalList(List<AmbTriagemHasSinal> ambTriagemHasSinalList) {
        this.ambTriagemHasSinalList = ambTriagemHasSinalList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdSinal != null ? pkIdSinal.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AmbSinal)) {
            return false;
        }
        AmbSinal other = (AmbSinal) object;
        if ((this.pkIdSinal == null && other.pkIdSinal != null) || (this.pkIdSinal != null && !this.pkIdSinal.equals(other.pkIdSinal))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.AmbSinal[ pkIdSinal=" + pkIdSinal + " ]";
    }
    
}
