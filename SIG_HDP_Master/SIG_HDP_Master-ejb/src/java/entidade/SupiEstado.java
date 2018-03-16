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
@Table(name = "supi_estado", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SupiEstado.findAll", query = "SELECT s FROM SupiEstado s"),
    @NamedQuery(name = "SupiEstado.findByPkIdEstado", query = "SELECT s FROM SupiEstado s WHERE s.pkIdEstado = :pkIdEstado"),
    @NamedQuery(name = "SupiEstado.findByDescricao", query = "SELECT s FROM SupiEstado s WHERE s.descricao = :descricao"),
    @NamedQuery(name = "SupiEstado.findByCor", query = "SELECT s FROM SupiEstado s WHERE s.cor = :cor")})
public class SupiEstado implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_estado", nullable = false)
    private Integer pkIdEstado;
    @Size(max = 50)
    @Column(name = "descricao", length = 50)
    private String descricao;
    @Size(max = 50)
    @Column(name = "cor", length = 50)
    private String cor;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdEstado")
    private List<SupiFormacaoFuncionario> supiFormacaoFuncionarioList;

    public SupiEstado() {
    }

    public SupiEstado(Integer pkIdEstado) {
        this.pkIdEstado = pkIdEstado;
    }

    public Integer getPkIdEstado() {
        return pkIdEstado;
    }

    public void setPkIdEstado(Integer pkIdEstado) {
        this.pkIdEstado = pkIdEstado;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    @XmlTransient
    public List<SupiFormacaoFuncionario> getSupiFormacaoFuncionarioList() {
        return supiFormacaoFuncionarioList;
    }

    public void setSupiFormacaoFuncionarioList(List<SupiFormacaoFuncionario> supiFormacaoFuncionarioList) {
        this.supiFormacaoFuncionarioList = supiFormacaoFuncionarioList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdEstado != null ? pkIdEstado.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SupiEstado)) {
            return false;
        }
        SupiEstado other = (SupiEstado) object;
        if ((this.pkIdEstado == null && other.pkIdEstado != null) || (this.pkIdEstado != null && !this.pkIdEstado.equals(other.pkIdEstado))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.SupiEstado[ pkIdEstado=" + pkIdEstado + " ]";
    }
    
}
