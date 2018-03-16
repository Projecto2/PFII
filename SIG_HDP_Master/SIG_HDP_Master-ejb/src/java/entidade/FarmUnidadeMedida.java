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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "farm_unidade_medida", catalog = "sigh_db", schema = "public", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"descricao"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FarmUnidadeMedida.findAll", query = "SELECT f FROM FarmUnidadeMedida f"),
    @NamedQuery(name = "FarmUnidadeMedida.findByPkIdUnidadeMedida", query = "SELECT f FROM FarmUnidadeMedida f WHERE f.pkIdUnidadeMedida = :pkIdUnidadeMedida"),
    @NamedQuery(name = "FarmUnidadeMedida.findByAbreviatura", query = "SELECT f FROM FarmUnidadeMedida f WHERE f.abreviatura = :abreviatura"),
    @NamedQuery(name = "FarmUnidadeMedida.findByDescricao", query = "SELECT f FROM FarmUnidadeMedida f WHERE f.descricao = :descricao")})
public class FarmUnidadeMedida implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "pk_id_unidade_medida", nullable = false)
    private Integer pkIdUnidadeMedida;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "abreviatura", nullable = false, length = 10)
    private String abreviatura;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "descricao", nullable = false, length = 50)
    private String descricao;
    @OneToMany(mappedBy = "fkIdUnidadeMedida")
    private List<FarmProduto> farmProdutoList;
    @JoinColumn(name = "fk_id_tipo_unidade_medida", referencedColumnName = "pk_id_tipo_unidade_medida")
    @ManyToOne
    private FarmTipoUnidadeMedida fkIdTipoUnidadeMedida;
    @OneToMany(mappedBy = "fkIdUnidadeMedida")
    private List<DiagExame> diagExameList;

    public FarmUnidadeMedida() {
    }

    public FarmUnidadeMedida(Integer pkIdUnidadeMedida) {
        this.pkIdUnidadeMedida = pkIdUnidadeMedida;
    }

    public FarmUnidadeMedida(Integer pkIdUnidadeMedida, String abreviatura, String descricao) {
        this.pkIdUnidadeMedida = pkIdUnidadeMedida;
        this.abreviatura = abreviatura;
        this.descricao = descricao;
    }

    public Integer getPkIdUnidadeMedida() {
        return pkIdUnidadeMedida;
    }

    public void setPkIdUnidadeMedida(Integer pkIdUnidadeMedida) {
        this.pkIdUnidadeMedida = pkIdUnidadeMedida;
    }

    public String getAbreviatura() {
        return abreviatura;
    }

    public void setAbreviatura(String abreviatura) {
        this.abreviatura = abreviatura;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @XmlTransient
    public List<FarmProduto> getFarmProdutoList() {
        return farmProdutoList;
    }

    public void setFarmProdutoList(List<FarmProduto> farmProdutoList) {
        this.farmProdutoList = farmProdutoList;
    }

    public FarmTipoUnidadeMedida getFkIdTipoUnidadeMedida() {
        return fkIdTipoUnidadeMedida;
    }

    public void setFkIdTipoUnidadeMedida(FarmTipoUnidadeMedida fkIdTipoUnidadeMedida) {
        this.fkIdTipoUnidadeMedida = fkIdTipoUnidadeMedida;
    }

    @XmlTransient
    public List<DiagExame> getDiagExameList() {
        return diagExameList;
    }

    public void setDiagExameList(List<DiagExame> diagExameList) {
        this.diagExameList = diagExameList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdUnidadeMedida != null ? pkIdUnidadeMedida.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FarmUnidadeMedida)) {
            return false;
        }
        FarmUnidadeMedida other = (FarmUnidadeMedida) object;
        if ((this.pkIdUnidadeMedida == null && other.pkIdUnidadeMedida != null) || (this.pkIdUnidadeMedida != null && !this.pkIdUnidadeMedida.equals(other.pkIdUnidadeMedida))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.FarmUnidadeMedida[ pkIdUnidadeMedida=" + pkIdUnidadeMedida + " ]";
    }
    
}
