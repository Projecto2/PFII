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
@Table(name = "farm_categoria_medicamento", catalog = "sigh_db", schema = "public", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"capitulo"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FarmCategoriaMedicamento.findAll", query = "SELECT f FROM FarmCategoriaMedicamento f"),
    @NamedQuery(name = "FarmCategoriaMedicamento.findByPkIdCategoriaMedicamento", query = "SELECT f FROM FarmCategoriaMedicamento f WHERE f.pkIdCategoriaMedicamento = :pkIdCategoriaMedicamento"),
    @NamedQuery(name = "FarmCategoriaMedicamento.findByDescricao", query = "SELECT f FROM FarmCategoriaMedicamento f WHERE f.descricao = :descricao"),
    @NamedQuery(name = "FarmCategoriaMedicamento.findByCapitulo", query = "SELECT f FROM FarmCategoriaMedicamento f WHERE f.capitulo = :capitulo")})
public class FarmCategoriaMedicamento implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "pk_id_categoria_medicamento", nullable = false)
    private Integer pkIdCategoriaMedicamento;
    @Size(max = 300)
    @Column(name = "descricao", length = 300)
    private String descricao;
    @Size(max = 10)
    @Column(name = "capitulo", length = 10)
    private String capitulo;
    @OneToMany(mappedBy = "fkIdCategoriaMedicamento")
    private List<FarmProduto> farmProdutoList;
    @OneToMany(mappedBy = "fkIdCategoriaSuper")
    private List<FarmCategoriaMedicamento> farmCategoriaMedicamentoList;
    @JoinColumn(name = "fk_id_categoria_super", referencedColumnName = "pk_id_categoria_medicamento")
    @ManyToOne
    private FarmCategoriaMedicamento fkIdCategoriaSuper;

    public FarmCategoriaMedicamento() {
    }

    public FarmCategoriaMedicamento(Integer pkIdCategoriaMedicamento) {
        this.pkIdCategoriaMedicamento = pkIdCategoriaMedicamento;
    }

    public Integer getPkIdCategoriaMedicamento() {
        return pkIdCategoriaMedicamento;
    }

    public void setPkIdCategoriaMedicamento(Integer pkIdCategoriaMedicamento) {
        this.pkIdCategoriaMedicamento = pkIdCategoriaMedicamento;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getCapitulo() {
        return capitulo;
    }

    public void setCapitulo(String capitulo) {
        this.capitulo = capitulo;
    }

    @XmlTransient
    public List<FarmProduto> getFarmProdutoList() {
        return farmProdutoList;
    }

    public void setFarmProdutoList(List<FarmProduto> farmProdutoList) {
        this.farmProdutoList = farmProdutoList;
    }

    @XmlTransient
    public List<FarmCategoriaMedicamento> getFarmCategoriaMedicamentoList() {
        return farmCategoriaMedicamentoList;
    }

    public void setFarmCategoriaMedicamentoList(List<FarmCategoriaMedicamento> farmCategoriaMedicamentoList) {
        this.farmCategoriaMedicamentoList = farmCategoriaMedicamentoList;
    }

    public FarmCategoriaMedicamento getFkIdCategoriaSuper() {
        return fkIdCategoriaSuper;
    }

    public void setFkIdCategoriaSuper(FarmCategoriaMedicamento fkIdCategoriaSuper) {
        this.fkIdCategoriaSuper = fkIdCategoriaSuper;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdCategoriaMedicamento != null ? pkIdCategoriaMedicamento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FarmCategoriaMedicamento)) {
            return false;
        }
        FarmCategoriaMedicamento other = (FarmCategoriaMedicamento) object;
        if ((this.pkIdCategoriaMedicamento == null && other.pkIdCategoriaMedicamento != null) || (this.pkIdCategoriaMedicamento != null && !this.pkIdCategoriaMedicamento.equals(other.pkIdCategoriaMedicamento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.FarmCategoriaMedicamento[ pkIdCategoriaMedicamento=" + pkIdCategoriaMedicamento + " ]";
    }
    
}
