/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entidade;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
@Table(name = "rh_estado_candidato", catalog = "sigh_db", schema = "public", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"descricao"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RhEstadoCandidato.findAll", query = "SELECT r FROM RhEstadoCandidato r"),
    @NamedQuery(name = "RhEstadoCandidato.findByPkIdEstadoCandidato", query = "SELECT r FROM RhEstadoCandidato r WHERE r.pkIdEstadoCandidato = :pkIdEstadoCandidato"),
    @NamedQuery(name = "RhEstadoCandidato.findByDescricao", query = "SELECT r FROM RhEstadoCandidato r WHERE r.descricao = :descricao")})
public class RhEstadoCandidato implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "pk_id_estado_candidato", nullable = false)
    private Integer pkIdEstadoCandidato;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 60)
    @Column(name = "descricao", nullable = false, length = 60)
    private String descricao;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdEstadoCandidato")
    private List<RhCandidato> rhCandidatoList;

    public RhEstadoCandidato() {
    }

    public RhEstadoCandidato(Integer pkIdEstadoCandidato) {
        this.pkIdEstadoCandidato = pkIdEstadoCandidato;
    }

    public RhEstadoCandidato(Integer pkIdEstadoCandidato, String descricao) {
        this.pkIdEstadoCandidato = pkIdEstadoCandidato;
        this.descricao = descricao;
    }

    public Integer getPkIdEstadoCandidato() {
        return pkIdEstadoCandidato;
    }

    public void setPkIdEstadoCandidato(Integer pkIdEstadoCandidato) {
        this.pkIdEstadoCandidato = pkIdEstadoCandidato;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @XmlTransient
    public List<RhCandidato> getRhCandidatoList() {
        return rhCandidatoList;
    }

    public void setRhCandidatoList(List<RhCandidato> rhCandidatoList) {
        this.rhCandidatoList = rhCandidatoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdEstadoCandidato != null ? pkIdEstadoCandidato.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RhEstadoCandidato)) {
            return false;
        }
        RhEstadoCandidato other = (RhEstadoCandidato) object;
        if ((this.pkIdEstadoCandidato == null && other.pkIdEstadoCandidato != null) || (this.pkIdEstadoCandidato != null && !this.pkIdEstadoCandidato.equals(other.pkIdEstadoCandidato))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.RhEstadoCandidato[ pkIdEstadoCandidato=" + pkIdEstadoCandidato + " ]";
    }
    
}
