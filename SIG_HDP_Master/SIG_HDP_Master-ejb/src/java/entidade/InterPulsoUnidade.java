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
@Table(name = "inter_pulso_unidade", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "InterPulsoUnidade.findAll", query = "SELECT i FROM InterPulsoUnidade i"),
    @NamedQuery(name = "InterPulsoUnidade.findByDescricao", query = "SELECT i FROM InterPulsoUnidade i WHERE i.descricao = :descricao"),
    @NamedQuery(name = "InterPulsoUnidade.findByCodigo", query = "SELECT i FROM InterPulsoUnidade i WHERE i.codigo = :codigo"),
    @NamedQuery(name = "InterPulsoUnidade.findByPkIdPulsoUnidade", query = "SELECT i FROM InterPulsoUnidade i WHERE i.pkIdPulsoUnidade = :pkIdPulsoUnidade")})
public class InterPulsoUnidade implements Serializable {
    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "descricao", nullable = false, length = 45)
    private String descricao;
    @Size(max = 5)
    @Column(name = "codigo", length = 5)
    private String codigo;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "pk_id_pulso_unidade", nullable = false)
    private Integer pkIdPulsoUnidade;
    @OneToMany(mappedBy = "fkIdPulsoUnidade")
    private List<InterControloParametrosVitais> interControloParametrosVitaisList;

    public InterPulsoUnidade() {
    }

    public InterPulsoUnidade(Integer pkIdPulsoUnidade) {
        this.pkIdPulsoUnidade = pkIdPulsoUnidade;
    }

    public InterPulsoUnidade(Integer pkIdPulsoUnidade, String descricao) {
        this.pkIdPulsoUnidade = pkIdPulsoUnidade;
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Integer getPkIdPulsoUnidade() {
        return pkIdPulsoUnidade;
    }

    public void setPkIdPulsoUnidade(Integer pkIdPulsoUnidade) {
        this.pkIdPulsoUnidade = pkIdPulsoUnidade;
    }

    @XmlTransient
    public List<InterControloParametrosVitais> getInterControloParametrosVitaisList() {
        return interControloParametrosVitaisList;
    }

    public void setInterControloParametrosVitaisList(List<InterControloParametrosVitais> interControloParametrosVitaisList) {
        this.interControloParametrosVitaisList = interControloParametrosVitaisList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdPulsoUnidade != null ? pkIdPulsoUnidade.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InterPulsoUnidade)) {
            return false;
        }
        InterPulsoUnidade other = (InterPulsoUnidade) object;
        if ((this.pkIdPulsoUnidade == null && other.pkIdPulsoUnidade != null) || (this.pkIdPulsoUnidade != null && !this.pkIdPulsoUnidade.equals(other.pkIdPulsoUnidade))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.InterPulsoUnidade[ pkIdPulsoUnidade=" + pkIdPulsoUnidade + " ]";
    }
    
}
