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
@Table(name = "tb_classificacao_doenca", catalog = "sigh_db", schema = "public", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"descricao"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TbClassificacaoDoenca.findAll", query = "SELECT t FROM TbClassificacaoDoenca t"),
    @NamedQuery(name = "TbClassificacaoDoenca.findByPkClassificacaoDoenca", query = "SELECT t FROM TbClassificacaoDoenca t WHERE t.pkClassificacaoDoenca = :pkClassificacaoDoenca"),
    @NamedQuery(name = "TbClassificacaoDoenca.findByDescricao", query = "SELECT t FROM TbClassificacaoDoenca t WHERE t.descricao = :descricao")})
public class TbClassificacaoDoenca implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "pk_classificacao_doenca", nullable = false)
    private Integer pkClassificacaoDoenca;
    @Size(max = 35)
    @Column(name = "descricao", length = 35)
    private String descricao;
    @OneToMany(mappedBy = "fkClassificacaoDoenca")
    private List<TbSubclassificacaoDoenca> tbSubclassificacaoDoencaList;

    public TbClassificacaoDoenca() {
    }

    public TbClassificacaoDoenca(Integer pkClassificacaoDoenca) {
        this.pkClassificacaoDoenca = pkClassificacaoDoenca;
    }

    public Integer getPkClassificacaoDoenca() {
        return pkClassificacaoDoenca;
    }

    public void setPkClassificacaoDoenca(Integer pkClassificacaoDoenca) {
        this.pkClassificacaoDoenca = pkClassificacaoDoenca;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @XmlTransient
    public List<TbSubclassificacaoDoenca> getTbSubclassificacaoDoencaList() {
        return tbSubclassificacaoDoencaList;
    }

    public void setTbSubclassificacaoDoencaList(List<TbSubclassificacaoDoenca> tbSubclassificacaoDoencaList) {
        this.tbSubclassificacaoDoencaList = tbSubclassificacaoDoencaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkClassificacaoDoenca != null ? pkClassificacaoDoenca.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TbClassificacaoDoenca)) {
            return false;
        }
        TbClassificacaoDoenca other = (TbClassificacaoDoenca) object;
        if ((this.pkClassificacaoDoenca == null && other.pkClassificacaoDoenca != null) || (this.pkClassificacaoDoenca != null && !this.pkClassificacaoDoenca.equals(other.pkClassificacaoDoenca))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.TbClassificacaoDoenca[ pkClassificacaoDoenca=" + pkClassificacaoDoenca + " ]";
    }
    
}
