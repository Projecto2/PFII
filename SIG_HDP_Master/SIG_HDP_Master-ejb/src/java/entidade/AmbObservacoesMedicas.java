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
@Table(name = "amb_observacoes_medicas", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AmbObservacoesMedicas.findAll", query = "SELECT a FROM AmbObservacoesMedicas a"),
    @NamedQuery(name = "AmbObservacoesMedicas.findByPkIdObservacoesMedicas", query = "SELECT a FROM AmbObservacoesMedicas a WHERE a.pkIdObservacoesMedicas = :pkIdObservacoesMedicas"),
    @NamedQuery(name = "AmbObservacoesMedicas.findByDescricao", query = "SELECT a FROM AmbObservacoesMedicas a WHERE a.descricao = :descricao")})
public class AmbObservacoesMedicas implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "pk_id_observacoes_medicas", nullable = false)
    private Integer pkIdObservacoesMedicas;
    @Size(max = 45)
    @Column(name = "descricao", length = 45)
    private String descricao;
    @OneToMany(mappedBy = "fkIdObservacoesMedicas")
    private List<AmbDiagnostico> ambDiagnosticoList;
    @OneToMany(mappedBy = "fkIdObservacoesMedicas")
    private List<AmbConsulta> ambConsultaList;

    public AmbObservacoesMedicas() {
    }

    public AmbObservacoesMedicas(Integer pkIdObservacoesMedicas) {
        this.pkIdObservacoesMedicas = pkIdObservacoesMedicas;
    }

    public Integer getPkIdObservacoesMedicas() {
        return pkIdObservacoesMedicas;
    }

    public void setPkIdObservacoesMedicas(Integer pkIdObservacoesMedicas) {
        this.pkIdObservacoesMedicas = pkIdObservacoesMedicas;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @XmlTransient
    public List<AmbDiagnostico> getAmbDiagnosticoList() {
        return ambDiagnosticoList;
    }

    public void setAmbDiagnosticoList(List<AmbDiagnostico> ambDiagnosticoList) {
        this.ambDiagnosticoList = ambDiagnosticoList;
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
        hash += (pkIdObservacoesMedicas != null ? pkIdObservacoesMedicas.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AmbObservacoesMedicas)) {
            return false;
        }
        AmbObservacoesMedicas other = (AmbObservacoesMedicas) object;
        if ((this.pkIdObservacoesMedicas == null && other.pkIdObservacoesMedicas != null) || (this.pkIdObservacoesMedicas != null && !this.pkIdObservacoesMedicas.equals(other.pkIdObservacoesMedicas))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.AmbObservacoesMedicas[ pkIdObservacoesMedicas=" + pkIdObservacoesMedicas + " ]";
    }
    
}
