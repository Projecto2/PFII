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
@Table(name = "amb_estado", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AmbEstado.findAll", query = "SELECT a FROM AmbEstado a"),
    @NamedQuery(name = "AmbEstado.findByPkIdEstado", query = "SELECT a FROM AmbEstado a WHERE a.pkIdEstado = :pkIdEstado"),
    @NamedQuery(name = "AmbEstado.findByDescricao", query = "SELECT a FROM AmbEstado a WHERE a.descricao = :descricao")})
public class AmbEstado implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "pk_id_estado", nullable = false)
    private Integer pkIdEstado;
    @Size(max = 9)
    @Column(name = "descricao", length = 9)
    private String descricao;
    @OneToMany(mappedBy = "fkEstado")
    private List<TbTriagem> tbTriagemList;
    @OneToMany(mappedBy = "fkIdEstado")
    private List<AmbTriagem> ambTriagemList;
    @OneToMany(mappedBy = "fkIdEstado")
    private List<AmbConsulta> ambConsultaList;

    public AmbEstado() {
    }

    public AmbEstado(Integer pkIdEstado) {
        this.pkIdEstado = pkIdEstado;
    }

    public Integer getPkIdEstado() {
        return pkIdEstado;
    }

    public void setPkIdEstado(Integer pkIdEstado) {
        this.pkIdEstado = pkIdEstado;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @XmlTransient
    public List<TbTriagem> getTbTriagemList() {
        return tbTriagemList;
    }

    public void setTbTriagemList(List<TbTriagem> tbTriagemList) {
        this.tbTriagemList = tbTriagemList;
    }

    @XmlTransient
    public List<AmbTriagem> getAmbTriagemList() {
        return ambTriagemList;
    }

    public void setAmbTriagemList(List<AmbTriagem> ambTriagemList) {
        this.ambTriagemList = ambTriagemList;
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
        hash += (pkIdEstado != null ? pkIdEstado.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AmbEstado)) {
            return false;
        }
        AmbEstado other = (AmbEstado) object;
        if ((this.pkIdEstado == null && other.pkIdEstado != null) || (this.pkIdEstado != null && !this.pkIdEstado.equals(other.pkIdEstado))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.AmbEstado[ pkIdEstado=" + pkIdEstado + " ]";
    }
    
}
