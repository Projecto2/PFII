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
@Table(name = "amb_consultorio", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AmbConsultorio.findAll", query = "SELECT a FROM AmbConsultorio a"),
    @NamedQuery(name = "AmbConsultorio.findByPkIdConsultorio", query = "SELECT a FROM AmbConsultorio a WHERE a.pkIdConsultorio = :pkIdConsultorio"),
    @NamedQuery(name = "AmbConsultorio.findByEspecificacao", query = "SELECT a FROM AmbConsultorio a WHERE a.especificacao = :especificacao"),
    @NamedQuery(name = "AmbConsultorio.findByNome", query = "SELECT a FROM AmbConsultorio a WHERE a.nome = :nome")})
public class AmbConsultorio implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "pk_id_consultorio", nullable = false)
    private Integer pkIdConsultorio;
    @Size(max = 100)
    @Column(name = "especificacao", length = 100)
    private String especificacao;
    @Column(name = "nome")
    private Integer nome;
    @OneToMany(mappedBy = "fkIdConsultorio")
    private List<SupiLocalConsulta> supiLocalConsultaList;
    @OneToMany(mappedBy = "fkIdConsultorio")
    private List<SupiMedicoHasEscala> supiMedicoHasEscalaList;

    public AmbConsultorio() {
    }

    public AmbConsultorio(Integer pkIdConsultorio) {
        this.pkIdConsultorio = pkIdConsultorio;
    }

    public Integer getPkIdConsultorio() {
        return pkIdConsultorio;
    }

    public void setPkIdConsultorio(Integer pkIdConsultorio) {
        this.pkIdConsultorio = pkIdConsultorio;
    }

    public String getEspecificacao() {
        return especificacao;
    }

    public void setEspecificacao(String especificacao) {
        this.especificacao = especificacao;
    }

    public Integer getNome() {
        return nome;
    }

    public void setNome(Integer nome) {
        this.nome = nome;
    }

    @XmlTransient
    public List<SupiLocalConsulta> getSupiLocalConsultaList() {
        return supiLocalConsultaList;
    }

    public void setSupiLocalConsultaList(List<SupiLocalConsulta> supiLocalConsultaList) {
        this.supiLocalConsultaList = supiLocalConsultaList;
    }

    @XmlTransient
    public List<SupiMedicoHasEscala> getSupiMedicoHasEscalaList() {
        return supiMedicoHasEscalaList;
    }

    public void setSupiMedicoHasEscalaList(List<SupiMedicoHasEscala> supiMedicoHasEscalaList) {
        this.supiMedicoHasEscalaList = supiMedicoHasEscalaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdConsultorio != null ? pkIdConsultorio.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AmbConsultorio)) {
            return false;
        }
        AmbConsultorio other = (AmbConsultorio) object;
        if ((this.pkIdConsultorio == null && other.pkIdConsultorio != null) || (this.pkIdConsultorio != null && !this.pkIdConsultorio.equals(other.pkIdConsultorio))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.AmbConsultorio[ pkIdConsultorio=" + pkIdConsultorio + " ]";
    }
    
}
