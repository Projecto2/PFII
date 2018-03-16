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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author amed
 */
@Entity
@Table(name = "tb_observacao", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TbObservacao.findAll", query = "SELECT t FROM TbObservacao t"),
    @NamedQuery(name = "TbObservacao.findByPkObservacao", query = "SELECT t FROM TbObservacao t WHERE t.pkObservacao = :pkObservacao"),
    @NamedQuery(name = "TbObservacao.findByDescricao", query = "SELECT t FROM TbObservacao t WHERE t.descricao = :descricao")})
public class TbObservacao implements Serializable {
    @OneToMany(mappedBy = "fkObservacao")
    private List<TbConsulta> tbConsultaList;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_observacao", nullable = false)
    private Long pkObservacao;
    @Size(max = 2147483647)
    @Column(name = "descricao", length = 2147483647)
    private String descricao;
    @OneToMany(mappedBy = "fkObservacao")
    private List<TbTriagem> tbTriagemList;

    public TbObservacao() {
    }

    public TbObservacao(Long pkObservacao) {
        this.pkObservacao = pkObservacao;
    }

    public Long getPkObservacao() {
        return pkObservacao;
    }

    public void setPkObservacao(Long pkObservacao) {
        this.pkObservacao = pkObservacao;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkObservacao != null ? pkObservacao.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TbObservacao)) {
            return false;
        }
        TbObservacao other = (TbObservacao) object;
        if ((this.pkObservacao == null && other.pkObservacao != null) || (this.pkObservacao != null && !this.pkObservacao.equals(other.pkObservacao))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.TbObservacao[ pkObservacao=" + pkObservacao + " ]";
    }

    @XmlTransient
    public List<TbConsulta> getTbConsultaList() {
        return tbConsultaList;
    }

    public void setTbConsultaList(List<TbConsulta> tbConsultaList) {
        this.tbConsultaList = tbConsultaList;
    }
    
}
