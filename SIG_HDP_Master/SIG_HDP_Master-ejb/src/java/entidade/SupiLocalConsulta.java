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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "supi_local_consulta", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SupiLocalConsulta.findAll", query = "SELECT s FROM SupiLocalConsulta s"),
    @NamedQuery(name = "SupiLocalConsulta.findByPkIdLocalConsulta", query = "SELECT s FROM SupiLocalConsulta s WHERE s.pkIdLocalConsulta = :pkIdLocalConsulta"),
    @NamedQuery(name = "SupiLocalConsulta.findByDescricao", query = "SELECT s FROM SupiLocalConsulta s WHERE s.descricao = :descricao")})
public class SupiLocalConsulta implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_local_consulta", nullable = false)
    private Integer pkIdLocalConsulta;
    @Size(max = 100)
    @Column(name = "descricao", length = 100)
    private String descricao;
    @JoinColumn(name = "fk_id_consultorio", referencedColumnName = "pk_id_consultorio")
    @ManyToOne
    private AmbConsultorio fkIdConsultorio;
    @JoinColumn(name = "fk_id_enfermaria", referencedColumnName = "pk_id_enfermaria")
    @ManyToOne
    private InterEnfermaria fkIdEnfermaria;
    @OneToMany(mappedBy = "fkIdLocalConsulta")
    private List<SupiMedicoHasEscala> supiMedicoHasEscalaList;

    public SupiLocalConsulta() {
    }

    public SupiLocalConsulta(Integer pkIdLocalConsulta) {
        this.pkIdLocalConsulta = pkIdLocalConsulta;
    }

    public Integer getPkIdLocalConsulta() {
        return pkIdLocalConsulta;
    }

    public void setPkIdLocalConsulta(Integer pkIdLocalConsulta) {
        this.pkIdLocalConsulta = pkIdLocalConsulta;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public AmbConsultorio getFkIdConsultorio() {
        return fkIdConsultorio;
    }

    public void setFkIdConsultorio(AmbConsultorio fkIdConsultorio) {
        this.fkIdConsultorio = fkIdConsultorio;
    }

    public InterEnfermaria getFkIdEnfermaria() {
        return fkIdEnfermaria;
    }

    public void setFkIdEnfermaria(InterEnfermaria fkIdEnfermaria) {
        this.fkIdEnfermaria = fkIdEnfermaria;
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
        hash += (pkIdLocalConsulta != null ? pkIdLocalConsulta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SupiLocalConsulta)) {
            return false;
        }
        SupiLocalConsulta other = (SupiLocalConsulta) object;
        if ((this.pkIdLocalConsulta == null && other.pkIdLocalConsulta != null) || (this.pkIdLocalConsulta != null && !this.pkIdLocalConsulta.equals(other.pkIdLocalConsulta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.SupiLocalConsulta[ pkIdLocalConsulta=" + pkIdLocalConsulta + " ]";
    }
    
}
