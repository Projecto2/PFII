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
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author amed
 */
@Entity
@Table(name = "tb_tipo_doente", catalog = "sigh_db", schema = "public", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"descricao"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TbTipoDoente.findAll", query = "SELECT t FROM TbTipoDoente t"),
    @NamedQuery(name = "TbTipoDoente.findByPkTipoDoente", query = "SELECT t FROM TbTipoDoente t WHERE t.pkTipoDoente = :pkTipoDoente"),
    @NamedQuery(name = "TbTipoDoente.findByDescricao", query = "SELECT t FROM TbTipoDoente t WHERE t.descricao = :descricao")})
public class TbTipoDoente implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "pk_tipo_doente", nullable = false)
    private Integer pkTipoDoente;
    @Size(max = 35)
    @Column(name = "descricao", length = 35)
    private String descricao;

    public TbTipoDoente() {
    }

    public TbTipoDoente(Integer pkTipoDoente) {
        this.pkTipoDoente = pkTipoDoente;
    }

    public Integer getPkTipoDoente() {
        return pkTipoDoente;
    }

    public void setPkTipoDoente(Integer pkTipoDoente) {
        this.pkTipoDoente = pkTipoDoente;
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
        hash += (pkTipoDoente != null ? pkTipoDoente.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TbTipoDoente)) {
            return false;
        }
        TbTipoDoente other = (TbTipoDoente) object;
        if ((this.pkTipoDoente == null && other.pkTipoDoente != null) || (this.pkTipoDoente != null && !this.pkTipoDoente.equals(other.pkTipoDoente))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.TbTipoDoente[ pkTipoDoente=" + pkTipoDoente + " ]";
    }
    
}
