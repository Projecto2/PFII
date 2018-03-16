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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author amed
 */
@Entity
@Table(name = "amb_diagnostico_hipotese", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AmbDiagnosticoHipotese.findAll", query = "SELECT a FROM AmbDiagnosticoHipotese a"),
    @NamedQuery(name = "AmbDiagnosticoHipotese.findByPkIdDiagnosticoHipotese", query = "SELECT a FROM AmbDiagnosticoHipotese a WHERE a.pkIdDiagnosticoHipotese = :pkIdDiagnosticoHipotese"),
    @NamedQuery(name = "AmbDiagnosticoHipotese.findByDataHoraHipoteseDiagnostico", query = "SELECT a FROM AmbDiagnosticoHipotese a WHERE a.dataHoraHipoteseDiagnostico = :dataHoraHipoteseDiagnostico")})
public class AmbDiagnosticoHipotese implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_diagnostico_hipotese", nullable = false)
    private Long pkIdDiagnosticoHipotese;
    @Column(name = "data_hora_hipotese_diagnostico")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataHoraHipoteseDiagnostico;
    @OneToMany(mappedBy = "fkIdDiagnosticoHipotese")
    private List<AmbDiagnostico> ambDiagnosticoList;
    @OneToMany(mappedBy = "fkIdDiagnosticoHipotese")
    private List<AmbDiagnosticoHipoteseHasDoenca> ambDiagnosticoHipoteseHasDoencaList;
    @JoinColumn(name = "fk_id_consulta", referencedColumnName = "pk_id_consulta")
    @ManyToOne
    private AmbConsulta fkIdConsulta;
    @JoinColumn(name = "fk_id_funcionario", referencedColumnName = "pk_id_funcionario")
    @ManyToOne
    private RhFuncionario fkIdFuncionario;

    public AmbDiagnosticoHipotese() {
    }

    public AmbDiagnosticoHipotese(Long pkIdDiagnosticoHipotese) {
        this.pkIdDiagnosticoHipotese = pkIdDiagnosticoHipotese;
    }

    public Long getPkIdDiagnosticoHipotese() {
        return pkIdDiagnosticoHipotese;
    }

    public void setPkIdDiagnosticoHipotese(Long pkIdDiagnosticoHipotese) {
        this.pkIdDiagnosticoHipotese = pkIdDiagnosticoHipotese;
    }

    public Date getDataHoraHipoteseDiagnostico() {
        return dataHoraHipoteseDiagnostico;
    }

    public void setDataHoraHipoteseDiagnostico(Date dataHoraHipoteseDiagnostico) {
        this.dataHoraHipoteseDiagnostico = dataHoraHipoteseDiagnostico;
    }

    @XmlTransient
    public List<AmbDiagnostico> getAmbDiagnosticoList() {
        return ambDiagnosticoList;
    }

    public void setAmbDiagnosticoList(List<AmbDiagnostico> ambDiagnosticoList) {
        this.ambDiagnosticoList = ambDiagnosticoList;
    }

    @XmlTransient
    public List<AmbDiagnosticoHipoteseHasDoenca> getAmbDiagnosticoHipoteseHasDoencaList() {
        return ambDiagnosticoHipoteseHasDoencaList;
    }

    public void setAmbDiagnosticoHipoteseHasDoencaList(List<AmbDiagnosticoHipoteseHasDoenca> ambDiagnosticoHipoteseHasDoencaList) {
        this.ambDiagnosticoHipoteseHasDoencaList = ambDiagnosticoHipoteseHasDoencaList;
    }

    public AmbConsulta getFkIdConsulta() {
        return fkIdConsulta;
    }

    public void setFkIdConsulta(AmbConsulta fkIdConsulta) {
        this.fkIdConsulta = fkIdConsulta;
    }

    public RhFuncionario getFkIdFuncionario() {
        return fkIdFuncionario;
    }

    public void setFkIdFuncionario(RhFuncionario fkIdFuncionario) {
        this.fkIdFuncionario = fkIdFuncionario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdDiagnosticoHipotese != null ? pkIdDiagnosticoHipotese.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AmbDiagnosticoHipotese)) {
            return false;
        }
        AmbDiagnosticoHipotese other = (AmbDiagnosticoHipotese) object;
        if ((this.pkIdDiagnosticoHipotese == null && other.pkIdDiagnosticoHipotese != null) || (this.pkIdDiagnosticoHipotese != null && !this.pkIdDiagnosticoHipotese.equals(other.pkIdDiagnosticoHipotese))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.AmbDiagnosticoHipotese[ pkIdDiagnosticoHipotese=" + pkIdDiagnosticoHipotese + " ]";
    }
    
}
