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
@Table(name = "emg_tipo_tratamento", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EmgTipoTratamento.findAll", query = "SELECT e FROM EmgTipoTratamento e"),
    @NamedQuery(name = "EmgTipoTratamento.findByPkIdTipoTratamento", query = "SELECT e FROM EmgTipoTratamento e WHERE e.pkIdTipoTratamento = :pkIdTipoTratamento"),
    @NamedQuery(name = "EmgTipoTratamento.findByDescricao", query = "SELECT e FROM EmgTipoTratamento e WHERE e.descricao = :descricao")})
public class EmgTipoTratamento implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_tipo_tratamento", nullable = false)
    private Integer pkIdTipoTratamento;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "descricao", nullable = false, length = 2147483647)
    private String descricao;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "emgTipoTratamento")
    private List<EmgTratamento> emgTratamentoList;

    public EmgTipoTratamento() {
    }

    public EmgTipoTratamento(Integer pkIdTipoTratamento) {
        this.pkIdTipoTratamento = pkIdTipoTratamento;
    }

    public EmgTipoTratamento(Integer pkIdTipoTratamento, String descricao) {
        this.pkIdTipoTratamento = pkIdTipoTratamento;
        this.descricao = descricao;
    }

    public Integer getPkIdTipoTratamento() {
        return pkIdTipoTratamento;
    }

    public void setPkIdTipoTratamento(Integer pkIdTipoTratamento) {
        this.pkIdTipoTratamento = pkIdTipoTratamento;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @XmlTransient
    public List<EmgTratamento> getEmgTratamentoList() {
        return emgTratamentoList;
    }

    public void setEmgTratamentoList(List<EmgTratamento> emgTratamentoList) {
        this.emgTratamentoList = emgTratamentoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdTipoTratamento != null ? pkIdTipoTratamento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EmgTipoTratamento)) {
            return false;
        }
        EmgTipoTratamento other = (EmgTipoTratamento) object;
        if ((this.pkIdTipoTratamento == null && other.pkIdTipoTratamento != null) || (this.pkIdTipoTratamento != null && !this.pkIdTipoTratamento.equals(other.pkIdTipoTratamento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.EmgTipoTratamento[ pkIdTipoTratamento=" + pkIdTipoTratamento + " ]";
    }
    
}
