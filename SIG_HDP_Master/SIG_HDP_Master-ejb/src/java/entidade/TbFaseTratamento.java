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
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author amed
 */
@Entity
@Table(name = "tb_fase_tratamento", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TbFaseTratamento.findAll", query = "SELECT t FROM TbFaseTratamento t"),
    @NamedQuery(name = "TbFaseTratamento.findByPkFaseTratamento", query = "SELECT t FROM TbFaseTratamento t WHERE t.pkFaseTratamento = :pkFaseTratamento"),
    @NamedQuery(name = "TbFaseTratamento.findByDescricao", query = "SELECT t FROM TbFaseTratamento t WHERE t.descricao = :descricao")})
public class TbFaseTratamento implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "pk_fase_tratamento", nullable = false)
    private Integer pkFaseTratamento;
    @Size(max = 25)
    @Column(name = "descricao", length = 25)
    private String descricao;

    public TbFaseTratamento() {
    }

    public TbFaseTratamento(Integer pkFaseTratamento) {
        this.pkFaseTratamento = pkFaseTratamento;
    }

    public Integer getPkFaseTratamento() {
        return pkFaseTratamento;
    }

    public void setPkFaseTratamento(Integer pkFaseTratamento) {
        this.pkFaseTratamento = pkFaseTratamento;
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
        hash += (pkFaseTratamento != null ? pkFaseTratamento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TbFaseTratamento)) {
            return false;
        }
        TbFaseTratamento other = (TbFaseTratamento) object;
        if ((this.pkFaseTratamento == null && other.pkFaseTratamento != null) || (this.pkFaseTratamento != null && !this.pkFaseTratamento.equals(other.pkFaseTratamento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.TbFaseTratamento[ pkFaseTratamento=" + pkFaseTratamento + " ]";
    }
    
}
