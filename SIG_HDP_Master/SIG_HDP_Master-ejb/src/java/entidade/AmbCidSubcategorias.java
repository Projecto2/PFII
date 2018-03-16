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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "amb_cid_subcategorias", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AmbCidSubcategorias.findAll", query = "SELECT a FROM AmbCidSubcategorias a"),
    @NamedQuery(name = "AmbCidSubcategorias.findByPkIdSubcategorias", query = "SELECT a FROM AmbCidSubcategorias a WHERE a.pkIdSubcategorias = :pkIdSubcategorias"),
    @NamedQuery(name = "AmbCidSubcategorias.findByNome", query = "SELECT a FROM AmbCidSubcategorias a WHERE a.nome = :nome")})
public class AmbCidSubcategorias implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 7)
    @Column(name = "pk_id_subcategorias", nullable = false, length = 7)
    private String pkIdSubcategorias;
    @Size(max = 512)
    @Column(name = "nome", length = 512)
    private String nome;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdCidSubcategorias")
    private List<InterDoencaInternamentoPaciente> interDoencaInternamentoPacienteList;
    @OneToMany(mappedBy = "fkIdSubcategorias")
    private List<AmbDiagnosticoHipoteseHasDoenca> ambDiagnosticoHipoteseHasDoencaList;
    @OneToMany(mappedBy = "fkIdSubcategorias")
    private List<AmbDiagnosticoHasDoenca> ambDiagnosticoHasDoencaList;
    @JoinColumn(name = "fk_id_categorias", referencedColumnName = "pk_id_categorias", nullable = false)
    @ManyToOne(optional = false)
    private AmbCidCategorias fkIdCategorias;
    @OneToMany(mappedBy = "fkIdSubcategorias")
    private List<AmbCidPerfisDoencas> ambCidPerfisDoencasList;

    public AmbCidSubcategorias() {
    }

    public AmbCidSubcategorias(String pkIdSubcategorias) {
        this.pkIdSubcategorias = pkIdSubcategorias;
    }

    public String getPkIdSubcategorias() {
        return pkIdSubcategorias;
    }

    public void setPkIdSubcategorias(String pkIdSubcategorias) {
        this.pkIdSubcategorias = pkIdSubcategorias;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @XmlTransient
    public List<InterDoencaInternamentoPaciente> getInterDoencaInternamentoPacienteList() {
        return interDoencaInternamentoPacienteList;
    }

    public void setInterDoencaInternamentoPacienteList(List<InterDoencaInternamentoPaciente> interDoencaInternamentoPacienteList) {
        this.interDoencaInternamentoPacienteList = interDoencaInternamentoPacienteList;
    }

    @XmlTransient
    public List<AmbDiagnosticoHipoteseHasDoenca> getAmbDiagnosticoHipoteseHasDoencaList() {
        return ambDiagnosticoHipoteseHasDoencaList;
    }

    public void setAmbDiagnosticoHipoteseHasDoencaList(List<AmbDiagnosticoHipoteseHasDoenca> ambDiagnosticoHipoteseHasDoencaList) {
        this.ambDiagnosticoHipoteseHasDoencaList = ambDiagnosticoHipoteseHasDoencaList;
    }

    @XmlTransient
    public List<AmbDiagnosticoHasDoenca> getAmbDiagnosticoHasDoencaList() {
        return ambDiagnosticoHasDoencaList;
    }

    public void setAmbDiagnosticoHasDoencaList(List<AmbDiagnosticoHasDoenca> ambDiagnosticoHasDoencaList) {
        this.ambDiagnosticoHasDoencaList = ambDiagnosticoHasDoencaList;
    }

    public AmbCidCategorias getFkIdCategorias() {
        return fkIdCategorias;
    }

    public void setFkIdCategorias(AmbCidCategorias fkIdCategorias) {
        this.fkIdCategorias = fkIdCategorias;
    }

    @XmlTransient
    public List<AmbCidPerfisDoencas> getAmbCidPerfisDoencasList() {
        return ambCidPerfisDoencasList;
    }

    public void setAmbCidPerfisDoencasList(List<AmbCidPerfisDoencas> ambCidPerfisDoencasList) {
        this.ambCidPerfisDoencasList = ambCidPerfisDoencasList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdSubcategorias != null ? pkIdSubcategorias.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AmbCidSubcategorias)) {
            return false;
        }
        AmbCidSubcategorias other = (AmbCidSubcategorias) object;
        if ((this.pkIdSubcategorias == null && other.pkIdSubcategorias != null) || (this.pkIdSubcategorias != null && !this.pkIdSubcategorias.equals(other.pkIdSubcategorias))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.AmbCidSubcategorias[ pkIdSubcategorias=" + pkIdSubcategorias + " ]";
    }
    
}
