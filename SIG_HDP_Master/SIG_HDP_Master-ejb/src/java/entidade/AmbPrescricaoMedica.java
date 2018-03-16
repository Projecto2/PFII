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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author amed
 */
@Entity
@Table(name = "amb_prescricao_medica", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AmbPrescricaoMedica.findAll", query = "SELECT a FROM AmbPrescricaoMedica a"),
    @NamedQuery(name = "AmbPrescricaoMedica.findByPkIdPrescricaoMedica", query = "SELECT a FROM AmbPrescricaoMedica a WHERE a.pkIdPrescricaoMedica = :pkIdPrescricaoMedica"),
    @NamedQuery(name = "AmbPrescricaoMedica.findByDataHoraPrescricao", query = "SELECT a FROM AmbPrescricaoMedica a WHERE a.dataHoraPrescricao = :dataHoraPrescricao")})
public class AmbPrescricaoMedica implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_prescricao_medica", nullable = false)
    private Long pkIdPrescricaoMedica;
    @Column(name = "data_hora_prescricao")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataHoraPrescricao;
    @JoinColumn(name = "fk_id_consulta", referencedColumnName = "pk_id_consulta")
    @ManyToOne
    private AmbConsulta fkIdConsulta;
    @JoinColumn(name = "fk_id_centro", referencedColumnName = "pk_id_centro")
    @ManyToOne
    private GrlCentroHospitalar fkIdCentro;
    @JoinColumn(name = "fk_id_funcionario", referencedColumnName = "pk_id_funcionario")
    @ManyToOne
    private RhFuncionario fkIdFuncionario;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdPrescricaoMedica")
    private List<AmbPrescricaoMedicaHasProduto> ambPrescricaoMedicaHasProdutoList;

    public AmbPrescricaoMedica() {
    }

    public AmbPrescricaoMedica(Long pkIdPrescricaoMedica) {
        this.pkIdPrescricaoMedica = pkIdPrescricaoMedica;
    }

    public Long getPkIdPrescricaoMedica() {
        return pkIdPrescricaoMedica;
    }

    public void setPkIdPrescricaoMedica(Long pkIdPrescricaoMedica) {
        this.pkIdPrescricaoMedica = pkIdPrescricaoMedica;
    }

    public Date getDataHoraPrescricao() {
        return dataHoraPrescricao;
    }

    public void setDataHoraPrescricao(Date dataHoraPrescricao) {
        this.dataHoraPrescricao = dataHoraPrescricao;
    }

    public AmbConsulta getFkIdConsulta() {
        return fkIdConsulta;
    }

    public void setFkIdConsulta(AmbConsulta fkIdConsulta) {
        this.fkIdConsulta = fkIdConsulta;
    }

    public GrlCentroHospitalar getFkIdCentro() {
        return fkIdCentro;
    }

    public void setFkIdCentro(GrlCentroHospitalar fkIdCentro) {
        this.fkIdCentro = fkIdCentro;
    }

    public RhFuncionario getFkIdFuncionario() {
        return fkIdFuncionario;
    }

    public void setFkIdFuncionario(RhFuncionario fkIdFuncionario) {
        this.fkIdFuncionario = fkIdFuncionario;
    }

    @XmlTransient
    public List<AmbPrescricaoMedicaHasProduto> getAmbPrescricaoMedicaHasProdutoList() {
        return ambPrescricaoMedicaHasProdutoList;
    }

    public void setAmbPrescricaoMedicaHasProdutoList(List<AmbPrescricaoMedicaHasProduto> ambPrescricaoMedicaHasProdutoList) {
        this.ambPrescricaoMedicaHasProdutoList = ambPrescricaoMedicaHasProdutoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdPrescricaoMedica != null ? pkIdPrescricaoMedica.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AmbPrescricaoMedica)) {
            return false;
        }
        AmbPrescricaoMedica other = (AmbPrescricaoMedica) object;
        if ((this.pkIdPrescricaoMedica == null && other.pkIdPrescricaoMedica != null) || (this.pkIdPrescricaoMedica != null && !this.pkIdPrescricaoMedica.equals(other.pkIdPrescricaoMedica))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.AmbPrescricaoMedica[ pkIdPrescricaoMedica=" + pkIdPrescricaoMedica + " ]";
    }
    
}
