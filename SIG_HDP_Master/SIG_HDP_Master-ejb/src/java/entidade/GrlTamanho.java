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
@Table(name = "grl_tamanho", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GrlTamanho.findAll", query = "SELECT g FROM GrlTamanho g"),
    @NamedQuery(name = "GrlTamanho.findByPkIdTamanho", query = "SELECT g FROM GrlTamanho g WHERE g.pkIdTamanho = :pkIdTamanho"),
    @NamedQuery(name = "GrlTamanho.findByDescricao", query = "SELECT g FROM GrlTamanho g WHERE g.descricao = :descricao")})
public class GrlTamanho implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "pk_id_tamanho", nullable = false)
    private Integer pkIdTamanho;
    @Size(max = 7)
    @Column(name = "descricao", length = 7)
    private String descricao;
    @OneToMany(mappedBy = "fkIdTamanho")
    private List<AmbConsulta> ambConsultaList;

    public GrlTamanho() {
    }

    public GrlTamanho(Integer pkIdTamanho) {
        this.pkIdTamanho = pkIdTamanho;
    }

    public Integer getPkIdTamanho() {
        return pkIdTamanho;
    }

    public void setPkIdTamanho(Integer pkIdTamanho) {
        this.pkIdTamanho = pkIdTamanho;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @XmlTransient
    public List<AmbConsulta> getAmbConsultaList() {
        return ambConsultaList;
    }

    public void setAmbConsultaList(List<AmbConsulta> ambConsultaList) {
        this.ambConsultaList = ambConsultaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdTamanho != null ? pkIdTamanho.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GrlTamanho)) {
            return false;
        }
        GrlTamanho other = (GrlTamanho) object;
        if ((this.pkIdTamanho == null && other.pkIdTamanho != null) || (this.pkIdTamanho != null && !this.pkIdTamanho.equals(other.pkIdTamanho))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.GrlTamanho[ pkIdTamanho=" + pkIdTamanho + " ]";
    }
    
}
