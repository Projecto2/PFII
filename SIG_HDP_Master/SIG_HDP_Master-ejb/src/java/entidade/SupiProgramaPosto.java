/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entidade;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author amed
 */
@Entity
@Table(name = "supi_programa_posto", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SupiProgramaPosto.findAll", query = "SELECT s FROM SupiProgramaPosto s"),
    @NamedQuery(name = "SupiProgramaPosto.findByPkIdProgramaPosto", query = "SELECT s FROM SupiProgramaPosto s WHERE s.pkIdProgramaPosto = :pkIdProgramaPosto"),
    @NamedQuery(name = "SupiProgramaPosto.findByDataPrograma", query = "SELECT s FROM SupiProgramaPosto s WHERE s.dataPrograma = :dataPrograma"),
    @NamedQuery(name = "SupiProgramaPosto.findByDataCadastro", query = "SELECT s FROM SupiProgramaPosto s WHERE s.dataCadastro = :dataCadastro"),
    @NamedQuery(name = "SupiProgramaPosto.findByTemaActividade", query = "SELECT s FROM SupiProgramaPosto s WHERE s.temaActividade = :temaActividade")})
public class SupiProgramaPosto implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_programa_posto", nullable = false)
    private Long pkIdProgramaPosto;
    @Basic(optional = false)
    @NotNull
    @Column(name = "data_programa", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dataPrograma;
    @Basic(optional = false)
    @NotNull
    @Column(name = "data_cadastro", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dataCadastro;
    @Size(max = 45)
    @Column(name = "tema_actividade", length = 45)
    private String temaActividade;
    @JoinColumn(name = "fk_id_centro", referencedColumnName = "pk_id_centro", nullable = false)
    @ManyToOne(optional = false)
    private GrlCentroHospitalar fkIdCentro;
    @JoinColumn(name = "fk_chefe_posto", referencedColumnName = "pk_id_funcionario", nullable = false)
    @ManyToOne(optional = false)
    private RhFuncionario fkChefePosto;
    @JoinColumn(name = "fk_id_medico", referencedColumnName = "pk_id_funcionario", nullable = false)
    @ManyToOne(optional = false)
    private RhFuncionario fkIdMedico;
    @JoinColumn(name = "fk_responsavel_actividade", referencedColumnName = "pk_id_funcionario", nullable = false)
    @ManyToOne(optional = false)
    private RhFuncionario fkResponsavelActividade;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdProgramaPosto")
    private List<SupiProgramaFuncionario> supiProgramaFuncionarioList;

    public SupiProgramaPosto() {
    }

    public SupiProgramaPosto(Long pkIdProgramaPosto) {
        this.pkIdProgramaPosto = pkIdProgramaPosto;
    }

    public SupiProgramaPosto(Long pkIdProgramaPosto, Date dataPrograma, Date dataCadastro) {
        this.pkIdProgramaPosto = pkIdProgramaPosto;
        this.dataPrograma = dataPrograma;
        this.dataCadastro = dataCadastro;
    }

    public Long getPkIdProgramaPosto() {
        return pkIdProgramaPosto;
    }

    public void setPkIdProgramaPosto(Long pkIdProgramaPosto) {
        this.pkIdProgramaPosto = pkIdProgramaPosto;
    }

    public Date getDataPrograma() {
        return dataPrograma;
    }

    public void setDataPrograma(Date dataPrograma) {
        this.dataPrograma = dataPrograma;
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public String getTemaActividade() {
        return temaActividade;
    }

    public void setTemaActividade(String temaActividade) {
        this.temaActividade = temaActividade;
    }

    public GrlCentroHospitalar getFkIdCentro() {
        return fkIdCentro;
    }

    public void setFkIdCentro(GrlCentroHospitalar fkIdCentro) {
        this.fkIdCentro = fkIdCentro;
    }

    public RhFuncionario getFkChefePosto() {
        return fkChefePosto;
    }

    public void setFkChefePosto(RhFuncionario fkChefePosto) {
        this.fkChefePosto = fkChefePosto;
    }

    public RhFuncionario getFkIdMedico() {
        return fkIdMedico;
    }

    public void setFkIdMedico(RhFuncionario fkIdMedico) {
        this.fkIdMedico = fkIdMedico;
    }

    public RhFuncionario getFkResponsavelActividade() {
        return fkResponsavelActividade;
    }

    public void setFkResponsavelActividade(RhFuncionario fkResponsavelActividade) {
        this.fkResponsavelActividade = fkResponsavelActividade;
    }

    @XmlTransient
    public List<SupiProgramaFuncionario> getSupiProgramaFuncionarioList() {
        return supiProgramaFuncionarioList;
    }

    public void setSupiProgramaFuncionarioList(List<SupiProgramaFuncionario> supiProgramaFuncionarioList) {
        this.supiProgramaFuncionarioList = supiProgramaFuncionarioList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdProgramaPosto != null ? pkIdProgramaPosto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SupiProgramaPosto)) {
            return false;
        }
        SupiProgramaPosto other = (SupiProgramaPosto) object;
        if ((this.pkIdProgramaPosto == null && other.pkIdProgramaPosto != null) || (this.pkIdProgramaPosto != null && !this.pkIdProgramaPosto.equals(other.pkIdProgramaPosto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.SupiProgramaPosto[ pkIdProgramaPosto=" + pkIdProgramaPosto + " ]";
    }
    
}
