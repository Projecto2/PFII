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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "tb_subclassificacao_doenca", catalog = "sigh_db", schema = "public", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"descricao"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TbSubclassificacaoDoenca.findAll", query = "SELECT t FROM TbSubclassificacaoDoenca t"),
    @NamedQuery(name = "TbSubclassificacaoDoenca.findByPkSubclassificacaoDoenca", query = "SELECT t FROM TbSubclassificacaoDoenca t WHERE t.pkSubclassificacaoDoenca = :pkSubclassificacaoDoenca"),
    @NamedQuery(name = "TbSubclassificacaoDoenca.findByDescricao", query = "SELECT t FROM TbSubclassificacaoDoenca t WHERE t.descricao = :descricao")})
public class TbSubclassificacaoDoenca implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "pk_subclassificacao_doenca", nullable = false)
    private Integer pkSubclassificacaoDoenca;
    @Size(max = 35)
    @Column(name = "descricao", length = 35)
    private String descricao;
    @JoinColumn(name = "fk_classificacao_doenca", referencedColumnName = "pk_classificacao_doenca")
    @ManyToOne
    private TbClassificacaoDoenca fkClassificacaoDoenca;

    public TbSubclassificacaoDoenca() {
    }

    public TbSubclassificacaoDoenca(Integer pkSubclassificacaoDoenca) {
        this.pkSubclassificacaoDoenca = pkSubclassificacaoDoenca;
    }

    public Integer getPkSubclassificacaoDoenca() {
        return pkSubclassificacaoDoenca;
    }

    public void setPkSubclassificacaoDoenca(Integer pkSubclassificacaoDoenca) {
        this.pkSubclassificacaoDoenca = pkSubclassificacaoDoenca;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public TbClassificacaoDoenca getFkClassificacaoDoenca() {
        return fkClassificacaoDoenca;
    }

    public void setFkClassificacaoDoenca(TbClassificacaoDoenca fkClassificacaoDoenca) {
        this.fkClassificacaoDoenca = fkClassificacaoDoenca;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkSubclassificacaoDoenca != null ? pkSubclassificacaoDoenca.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TbSubclassificacaoDoenca)) {
            return false;
        }
        TbSubclassificacaoDoenca other = (TbSubclassificacaoDoenca) object;
        if ((this.pkSubclassificacaoDoenca == null && other.pkSubclassificacaoDoenca != null) || (this.pkSubclassificacaoDoenca != null && !this.pkSubclassificacaoDoenca.equals(other.pkSubclassificacaoDoenca))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.TbSubclassificacaoDoenca[ pkSubclassificacaoDoenca=" + pkSubclassificacaoDoenca + " ]";
    }
    
}
