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
@Table(name = "amb_cid_perfil_tipos", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AmbCidPerfilTipos.findAll", query = "SELECT a FROM AmbCidPerfilTipos a"),
    @NamedQuery(name = "AmbCidPerfilTipos.findByPkIdTipos", query = "SELECT a FROM AmbCidPerfilTipos a WHERE a.pkIdTipos = :pkIdTipos"),
    @NamedQuery(name = "AmbCidPerfilTipos.findByNome", query = "SELECT a FROM AmbCidPerfilTipos a WHERE a.nome = :nome")})
public class AmbCidPerfilTipos implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "pk_id_tipos", nullable = false)
    private Integer pkIdTipos;
    @Size(max = 20)
    @Column(name = "nome", length = 20)
    private String nome;
    @OneToMany(mappedBy = "fkIdTipo")
    private List<AmbCidPerfis> ambCidPerfisList;

    public AmbCidPerfilTipos() {
    }

    public AmbCidPerfilTipos(Integer pkIdTipos) {
        this.pkIdTipos = pkIdTipos;
    }

    public Integer getPkIdTipos() {
        return pkIdTipos;
    }

    public void setPkIdTipos(Integer pkIdTipos) {
        this.pkIdTipos = pkIdTipos;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @XmlTransient
    public List<AmbCidPerfis> getAmbCidPerfisList() {
        return ambCidPerfisList;
    }

    public void setAmbCidPerfisList(List<AmbCidPerfis> ambCidPerfisList) {
        this.ambCidPerfisList = ambCidPerfisList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdTipos != null ? pkIdTipos.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AmbCidPerfilTipos)) {
            return false;
        }
        AmbCidPerfilTipos other = (AmbCidPerfilTipos) object;
        if ((this.pkIdTipos == null && other.pkIdTipos != null) || (this.pkIdTipos != null && !this.pkIdTipos.equals(other.pkIdTipos))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.AmbCidPerfilTipos[ pkIdTipos=" + pkIdTipos + " ]";
    }
    
}
