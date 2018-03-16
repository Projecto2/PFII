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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author amed
 */
@Entity
@Table(name = "amb_diagnostico", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AmbDiagnostico.findAll", query = "SELECT a FROM AmbDiagnostico a"),
    @NamedQuery(name = "AmbDiagnostico.findByPkIdDiagnostico", query = "SELECT a FROM AmbDiagnostico a WHERE a.pkIdDiagnostico = :pkIdDiagnostico"),
    @NamedQuery(name = "AmbDiagnostico.findByDataHoraDiagnostico", query = "SELECT a FROM AmbDiagnostico a WHERE a.dataHoraDiagnostico = :dataHoraDiagnostico"),
    @NamedQuery(name = "AmbDiagnostico.findByObservacoes", query = "SELECT a FROM AmbDiagnostico a WHERE a.observacoes = :observacoes")})
public class AmbDiagnostico implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_diagnostico", nullable = false)
    private Long pkIdDiagnostico;
    @Column(name = "data_hora_diagnostico")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataHoraDiagnostico;
    @Size(max = 512)
    @Column(name = "observacoes", length = 512)
    private String observacoes;
    @JoinColumn(name = "fk_id_diagnostico_hipotese", referencedColumnName = "pk_id_diagnostico_hipotese")
    @ManyToOne
    private AmbDiagnosticoHipotese fkIdDiagnosticoHipotese;
    @JoinColumn(name = "fk_id_observacoes_medicas", referencedColumnName = "pk_id_observacoes_medicas")
    @ManyToOne
    private AmbObservacoesMedicas fkIdObservacoesMedicas;
    @JoinColumn(name = "fk_id_exame_realizado", referencedColumnName = "pk_id_exame_realizado")
    @ManyToOne
    private DiagExameRealizado fkIdExameRealizado;
    @JoinColumn(name = "fk_id_centro", referencedColumnName = "pk_id_centro")
    @ManyToOne
    private GrlCentroHospitalar fkIdCentro;
    @JoinColumn(name = "fk_id_funcionario", referencedColumnName = "pk_id_funcionario")
    @ManyToOne
    private RhFuncionario fkIdFuncionario;
    @OneToMany(mappedBy = "fkIdDiagnostico")
    private List<AmbDiagnosticoHasDoenca> ambDiagnosticoHasDoencaList;

    public AmbDiagnostico() {
    }

    public AmbDiagnostico(Long pkIdDiagnostico) {
        this.pkIdDiagnostico = pkIdDiagnostico;
    }

    public Long getPkIdDiagnostico() {
        return pkIdDiagnostico;
    }

    public void setPkIdDiagnostico(Long pkIdDiagnostico) {
        this.pkIdDiagnostico = pkIdDiagnostico;
    }

    public Date getDataHoraDiagnostico() {
        return dataHoraDiagnostico;
    }

    public void setDataHoraDiagnostico(Date dataHoraDiagnostico) {
        this.dataHoraDiagnostico = dataHoraDiagnostico;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public AmbDiagnosticoHipotese getFkIdDiagnosticoHipotese() {
        return fkIdDiagnosticoHipotese;
    }

    public void setFkIdDiagnosticoHipotese(AmbDiagnosticoHipotese fkIdDiagnosticoHipotese) {
        this.fkIdDiagnosticoHipotese = fkIdDiagnosticoHipotese;
    }

    public AmbObservacoesMedicas getFkIdObservacoesMedicas() {
        return fkIdObservacoesMedicas;
    }

    public void setFkIdObservacoesMedicas(AmbObservacoesMedicas fkIdObservacoesMedicas) {
        this.fkIdObservacoesMedicas = fkIdObservacoesMedicas;
    }

    public DiagExameRealizado getFkIdExameRealizado() {
        return fkIdExameRealizado;
    }

    public void setFkIdExameRealizado(DiagExameRealizado fkIdExameRealizado) {
        this.fkIdExameRealizado = fkIdExameRealizado;
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
    public List<AmbDiagnosticoHasDoenca> getAmbDiagnosticoHasDoencaList() {
        return ambDiagnosticoHasDoencaList;
    }

    public void setAmbDiagnosticoHasDoencaList(List<AmbDiagnosticoHasDoenca> ambDiagnosticoHasDoencaList) {
        this.ambDiagnosticoHasDoencaList = ambDiagnosticoHasDoencaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdDiagnostico != null ? pkIdDiagnostico.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AmbDiagnostico)) {
            return false;
        }
        AmbDiagnostico other = (AmbDiagnostico) object;
        if ((this.pkIdDiagnostico == null && other.pkIdDiagnostico != null) || (this.pkIdDiagnostico != null && !this.pkIdDiagnostico.equals(other.pkIdDiagnostico))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.AmbDiagnostico[ pkIdDiagnostico=" + pkIdDiagnostico + " ]";
    }
    
}
