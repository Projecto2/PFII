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
@Table(name = "fin_tipo_preco_categoria_servico", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FinTipoPrecoCategoriaServico.findAll", query = "SELECT f FROM FinTipoPrecoCategoriaServico f"),
    @NamedQuery(name = "FinTipoPrecoCategoriaServico.findByPkIdTipoPrecoCategoriaServico", query = "SELECT f FROM FinTipoPrecoCategoriaServico f WHERE f.pkIdTipoPrecoCategoriaServico = :pkIdTipoPrecoCategoriaServico"),
    @NamedQuery(name = "FinTipoPrecoCategoriaServico.findByDescricao", query = "SELECT f FROM FinTipoPrecoCategoriaServico f WHERE f.descricao = :descricao")})
public class FinTipoPrecoCategoriaServico implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_tipo_preco_categoria_servico", nullable = false)
    private Integer pkIdTipoPrecoCategoriaServico;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "descricao", nullable = false, length = 100)
    private String descricao;
    @OneToMany(mappedBy = "fkIdTipoPrecoCategoriaServico")
    private List<FinPrecoCategoriaServico> finPrecoCategoriaServicoList;

    public FinTipoPrecoCategoriaServico() {
    }

    public FinTipoPrecoCategoriaServico(Integer pkIdTipoPrecoCategoriaServico) {
        this.pkIdTipoPrecoCategoriaServico = pkIdTipoPrecoCategoriaServico;
    }

    public FinTipoPrecoCategoriaServico(Integer pkIdTipoPrecoCategoriaServico, String descricao) {
        this.pkIdTipoPrecoCategoriaServico = pkIdTipoPrecoCategoriaServico;
        this.descricao = descricao;
    }

    public Integer getPkIdTipoPrecoCategoriaServico() {
        return pkIdTipoPrecoCategoriaServico;
    }

    public void setPkIdTipoPrecoCategoriaServico(Integer pkIdTipoPrecoCategoriaServico) {
        this.pkIdTipoPrecoCategoriaServico = pkIdTipoPrecoCategoriaServico;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @XmlTransient
    public List<FinPrecoCategoriaServico> getFinPrecoCategoriaServicoList() {
        return finPrecoCategoriaServicoList;
    }

    public void setFinPrecoCategoriaServicoList(List<FinPrecoCategoriaServico> finPrecoCategoriaServicoList) {
        this.finPrecoCategoriaServicoList = finPrecoCategoriaServicoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdTipoPrecoCategoriaServico != null ? pkIdTipoPrecoCategoriaServico.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FinTipoPrecoCategoriaServico)) {
            return false;
        }
        FinTipoPrecoCategoriaServico other = (FinTipoPrecoCategoriaServico) object;
        if ((this.pkIdTipoPrecoCategoriaServico == null && other.pkIdTipoPrecoCategoriaServico != null) || (this.pkIdTipoPrecoCategoriaServico != null && !this.pkIdTipoPrecoCategoriaServico.equals(other.pkIdTipoPrecoCategoriaServico))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.FinTipoPrecoCategoriaServico[ pkIdTipoPrecoCategoriaServico=" + pkIdTipoPrecoCategoriaServico + " ]";
    }
    
}
