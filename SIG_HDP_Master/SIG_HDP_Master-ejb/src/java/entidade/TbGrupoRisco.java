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
@Table(name = "tb_grupo_risco", catalog = "sigh_db", schema = "public", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"descricao"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TbGrupoRisco.findAll", query = "SELECT t FROM TbGrupoRisco t"),
    @NamedQuery(name = "TbGrupoRisco.findByPkGrupoRisco", query = "SELECT t FROM TbGrupoRisco t WHERE t.pkGrupoRisco = :pkGrupoRisco"),
    @NamedQuery(name = "TbGrupoRisco.findByDescricao", query = "SELECT t FROM TbGrupoRisco t WHERE t.descricao = :descricao")})
public class TbGrupoRisco implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "pk_grupo_risco", nullable = false)
    private Integer pkGrupoRisco;
    @Size(max = 45)
    @Column(name = "descricao", length = 45)
    private String descricao;

    public TbGrupoRisco() {
    }

    public TbGrupoRisco(Integer pkGrupoRisco) {
        this.pkGrupoRisco = pkGrupoRisco;
    }

    public Integer getPkGrupoRisco() {
        return pkGrupoRisco;
    }

    public void setPkGrupoRisco(Integer pkGrupoRisco) {
        this.pkGrupoRisco = pkGrupoRisco;
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
        hash += (pkGrupoRisco != null ? pkGrupoRisco.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TbGrupoRisco)) {
            return false;
        }
        TbGrupoRisco other = (TbGrupoRisco) object;
        if ((this.pkGrupoRisco == null && other.pkGrupoRisco != null) || (this.pkGrupoRisco != null && !this.pkGrupoRisco.equals(other.pkGrupoRisco))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.TbGrupoRisco[ pkGrupoRisco=" + pkGrupoRisco + " ]";
    }
    
}
