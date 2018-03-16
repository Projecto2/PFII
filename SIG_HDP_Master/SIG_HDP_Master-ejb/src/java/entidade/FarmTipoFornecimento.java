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
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author amed
 */
@Entity
@Table(name = "farm_tipo_fornecimento", catalog = "sigh_db", schema = "public", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"descricao_tipo_fornecimento"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FarmTipoFornecimento.findAll", query = "SELECT f FROM FarmTipoFornecimento f"),
    @NamedQuery(name = "FarmTipoFornecimento.findByPkIdTipoFornecimento", query = "SELECT f FROM FarmTipoFornecimento f WHERE f.pkIdTipoFornecimento = :pkIdTipoFornecimento"),
    @NamedQuery(name = "FarmTipoFornecimento.findByDescricaoTipoFornecimento", query = "SELECT f FROM FarmTipoFornecimento f WHERE f.descricaoTipoFornecimento = :descricaoTipoFornecimento")})
public class FarmTipoFornecimento implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "pk_id_tipo_fornecimento", nullable = false)
    private Integer pkIdTipoFornecimento;
    @Size(max = 40)
    @Column(name = "descricao_tipo_fornecimento", length = 40)
    private String descricaoTipoFornecimento;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdTipoFornecimento")
    private List<FarmFornecimento> farmFornecimentoList;

    public FarmTipoFornecimento() {
    }

    public FarmTipoFornecimento(Integer pkIdTipoFornecimento) {
        this.pkIdTipoFornecimento = pkIdTipoFornecimento;
    }

    public Integer getPkIdTipoFornecimento() {
        return pkIdTipoFornecimento;
    }

    public void setPkIdTipoFornecimento(Integer pkIdTipoFornecimento) {
        this.pkIdTipoFornecimento = pkIdTipoFornecimento;
    }

    public String getDescricaoTipoFornecimento() {
        return descricaoTipoFornecimento;
    }

    public void setDescricaoTipoFornecimento(String descricaoTipoFornecimento) {
        this.descricaoTipoFornecimento = descricaoTipoFornecimento;
    }

    @XmlTransient
    public List<FarmFornecimento> getFarmFornecimentoList() {
        return farmFornecimentoList;
    }

    public void setFarmFornecimentoList(List<FarmFornecimento> farmFornecimentoList) {
        this.farmFornecimentoList = farmFornecimentoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdTipoFornecimento != null ? pkIdTipoFornecimento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FarmTipoFornecimento)) {
            return false;
        }
        FarmTipoFornecimento other = (FarmTipoFornecimento) object;
        if ((this.pkIdTipoFornecimento == null && other.pkIdTipoFornecimento != null) || (this.pkIdTipoFornecimento != null && !this.pkIdTipoFornecimento.equals(other.pkIdTipoFornecimento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.FarmTipoFornecimento[ pkIdTipoFornecimento=" + pkIdTipoFornecimento + " ]";
    }
    
}
