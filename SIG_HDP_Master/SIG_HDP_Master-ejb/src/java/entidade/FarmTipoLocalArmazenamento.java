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
@Table(name = "farm_tipo_local_armazenamento", catalog = "sigh_db", schema = "public", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"descricao"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FarmTipoLocalArmazenamento.findAll", query = "SELECT f FROM FarmTipoLocalArmazenamento f"),
    @NamedQuery(name = "FarmTipoLocalArmazenamento.findByPkIdTipoLocalArmazenamento", query = "SELECT f FROM FarmTipoLocalArmazenamento f WHERE f.pkIdTipoLocalArmazenamento = :pkIdTipoLocalArmazenamento"),
    @NamedQuery(name = "FarmTipoLocalArmazenamento.findByDescricao", query = "SELECT f FROM FarmTipoLocalArmazenamento f WHERE f.descricao = :descricao")})
public class FarmTipoLocalArmazenamento implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "pk_id_tipo_local_armazenamento", nullable = false)
    private Integer pkIdTipoLocalArmazenamento;
    @Size(max = 100)
    @Column(name = "descricao", length = 100)
    private String descricao;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdTipoLocalArmazenamento")
    private List<FarmLocalArmazenamento> farmLocalArmazenamentoList;

    public FarmTipoLocalArmazenamento() {
    }

    public FarmTipoLocalArmazenamento(Integer pkIdTipoLocalArmazenamento) {
        this.pkIdTipoLocalArmazenamento = pkIdTipoLocalArmazenamento;
    }

    public Integer getPkIdTipoLocalArmazenamento() {
        return pkIdTipoLocalArmazenamento;
    }

    public void setPkIdTipoLocalArmazenamento(Integer pkIdTipoLocalArmazenamento) {
        this.pkIdTipoLocalArmazenamento = pkIdTipoLocalArmazenamento;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @XmlTransient
    public List<FarmLocalArmazenamento> getFarmLocalArmazenamentoList() {
        return farmLocalArmazenamentoList;
    }

    public void setFarmLocalArmazenamentoList(List<FarmLocalArmazenamento> farmLocalArmazenamentoList) {
        this.farmLocalArmazenamentoList = farmLocalArmazenamentoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdTipoLocalArmazenamento != null ? pkIdTipoLocalArmazenamento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FarmTipoLocalArmazenamento)) {
            return false;
        }
        FarmTipoLocalArmazenamento other = (FarmTipoLocalArmazenamento) object;
        if ((this.pkIdTipoLocalArmazenamento == null && other.pkIdTipoLocalArmazenamento != null) || (this.pkIdTipoLocalArmazenamento != null && !this.pkIdTipoLocalArmazenamento.equals(other.pkIdTipoLocalArmazenamento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.FarmTipoLocalArmazenamento[ pkIdTipoLocalArmazenamento=" + pkIdTipoLocalArmazenamento + " ]";
    }
    
}
