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
@Table(name = "tb_tipo_exame", catalog = "sigh_db", schema = "public", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"descricao"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TbTipoExame.findAll", query = "SELECT t FROM TbTipoExame t"),
    @NamedQuery(name = "TbTipoExame.findByPkTipoExame", query = "SELECT t FROM TbTipoExame t WHERE t.pkTipoExame = :pkTipoExame"),
    @NamedQuery(name = "TbTipoExame.findByDescricao", query = "SELECT t FROM TbTipoExame t WHERE t.descricao = :descricao")})
public class TbTipoExame implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "pk_tipo_exame", nullable = false)
    private Integer pkTipoExame;
    @Size(max = 35)
    @Column(name = "descricao", length = 35)
    private String descricao;

    public TbTipoExame() {
    }

    public TbTipoExame(Integer pkTipoExame) {
        this.pkTipoExame = pkTipoExame;
    }

    public Integer getPkTipoExame() {
        return pkTipoExame;
    }

    public void setPkTipoExame(Integer pkTipoExame) {
        this.pkTipoExame = pkTipoExame;
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
        hash += (pkTipoExame != null ? pkTipoExame.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TbTipoExame)) {
            return false;
        }
        TbTipoExame other = (TbTipoExame) object;
        if ((this.pkTipoExame == null && other.pkTipoExame != null) || (this.pkTipoExame != null && !this.pkTipoExame.equals(other.pkTipoExame))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.TbTipoExame[ pkTipoExame=" + pkTipoExame + " ]";
    }
    
}
