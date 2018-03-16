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
@Table(name = "tb_sintoma", catalog = "sigh_db", schema = "public", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"descricao"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TbSintoma.findAll", query = "SELECT t FROM TbSintoma t"),
    @NamedQuery(name = "TbSintoma.findByPkSintoma", query = "SELECT t FROM TbSintoma t WHERE t.pkSintoma = :pkSintoma"),
    @NamedQuery(name = "TbSintoma.findByDescricao", query = "SELECT t FROM TbSintoma t WHERE t.descricao = :descricao")})
public class TbSintoma implements Serializable {
    @OneToMany(mappedBy = "fkSintoma")
    private List<TbConsultaHasSintoma> tbConsultaHasSintomaList;
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "pk_sintoma", nullable = false)
    private Integer pkSintoma;
    @Size(max = 35)
    @Column(name = "descricao", length = 35)
    private String descricao;

    public TbSintoma() {
    }

    public TbSintoma(Integer pkSintoma) {
        this.pkSintoma = pkSintoma;
    }

    public Integer getPkSintoma() {
        return pkSintoma;
    }

    public void setPkSintoma(Integer pkSintoma) {
        this.pkSintoma = pkSintoma;
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
        hash += (pkSintoma != null ? pkSintoma.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TbSintoma)) {
            return false;
        }
        TbSintoma other = (TbSintoma) object;
        if ((this.pkSintoma == null && other.pkSintoma != null) || (this.pkSintoma != null && !this.pkSintoma.equals(other.pkSintoma))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.TbSintoma[ pkSintoma=" + pkSintoma + " ]";
    }

    @XmlTransient
    public List<TbConsultaHasSintoma> getTbConsultaHasSintomaList() {
        return tbConsultaHasSintomaList;
    }

    public void setTbConsultaHasSintomaList(List<TbConsultaHasSintoma> tbConsultaHasSintomaList) {
        this.tbConsultaHasSintomaList = tbConsultaHasSintomaList;
    }
    
}
