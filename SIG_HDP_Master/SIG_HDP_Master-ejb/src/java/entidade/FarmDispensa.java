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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author amed
 */
@Entity
@Table(name = "farm_dispensa", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FarmDispensa.findAll", query = "SELECT f FROM FarmDispensa f"),
    @NamedQuery(name = "FarmDispensa.findByPkIdDispensa", query = "SELECT f FROM FarmDispensa f WHERE f.pkIdDispensa = :pkIdDispensa"),
    @NamedQuery(name = "FarmDispensa.findByDataHora", query = "SELECT f FROM FarmDispensa f WHERE f.dataHora = :dataHora"),
    @NamedQuery(name = "FarmDispensa.findByPrescricaoMedica", query = "SELECT f FROM FarmDispensa f WHERE f.prescricaoMedica = :prescricaoMedica")})
public class FarmDispensa implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_dispensa", nullable = false)
    private Integer pkIdDispensa;
    @Basic(optional = false)
    @NotNull
    @Column(name = "data_hora", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataHora;
    @Size(max = 60)
    @Column(name = "prescricao_medica", length = 60)
    private String prescricaoMedica;
    @OneToMany(mappedBy = "fkIdDispensa")
    private List<FarmDispensaHasLoteProduto> farmDispensaHasLoteProdutoList;
    @JoinColumn(name = "fk_id_paciente", referencedColumnName = "pk_id_paciente", nullable = false)
    @ManyToOne(optional = false)
    private AdmsPaciente fkIdPaciente;
    @JoinColumn(name = "fk_id_turno_dispensa", referencedColumnName = "pk_id_turno_dispensa")
    @ManyToOne
    private FarmTurnoDispensa fkIdTurnoDispensa;
    @JoinColumn(name = "fk_id_funcionario", referencedColumnName = "pk_id_funcionario", nullable = false)
    @ManyToOne(optional = false)
    private RhFuncionario fkIdFuncionario;

    public FarmDispensa() {
    }

    public FarmDispensa(Integer pkIdDispensa) {
        this.pkIdDispensa = pkIdDispensa;
    }

    public FarmDispensa(Integer pkIdDispensa, Date dataHora) {
        this.pkIdDispensa = pkIdDispensa;
        this.dataHora = dataHora;
    }

    public Integer getPkIdDispensa() {
        return pkIdDispensa;
    }

    public void setPkIdDispensa(Integer pkIdDispensa) {
        this.pkIdDispensa = pkIdDispensa;
    }

    public Date getDataHora() {
        return dataHora;
    }

    public void setDataHora(Date dataHora) {
        this.dataHora = dataHora;
    }

    public String getPrescricaoMedica() {
        return prescricaoMedica;
    }

    public void setPrescricaoMedica(String prescricaoMedica) {
        this.prescricaoMedica = prescricaoMedica;
    }

    @XmlTransient
    public List<FarmDispensaHasLoteProduto> getFarmDispensaHasLoteProdutoList() {
        return farmDispensaHasLoteProdutoList;
    }

    public void setFarmDispensaHasLoteProdutoList(List<FarmDispensaHasLoteProduto> farmDispensaHasLoteProdutoList) {
        this.farmDispensaHasLoteProdutoList = farmDispensaHasLoteProdutoList;
    }

    public AdmsPaciente getFkIdPaciente() {
        return fkIdPaciente;
    }

    public void setFkIdPaciente(AdmsPaciente fkIdPaciente) {
        this.fkIdPaciente = fkIdPaciente;
    }

    public FarmTurnoDispensa getFkIdTurnoDispensa() {
        return fkIdTurnoDispensa;
    }

    public void setFkIdTurnoDispensa(FarmTurnoDispensa fkIdTurnoDispensa) {
        this.fkIdTurnoDispensa = fkIdTurnoDispensa;
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
        hash += (pkIdDispensa != null ? pkIdDispensa.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FarmDispensa)) {
            return false;
        }
        FarmDispensa other = (FarmDispensa) object;
        if ((this.pkIdDispensa == null && other.pkIdDispensa != null) || (this.pkIdDispensa != null && !this.pkIdDispensa.equals(other.pkIdDispensa))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.FarmDispensa[ pkIdDispensa=" + pkIdDispensa + " ]";
    }
    
}
