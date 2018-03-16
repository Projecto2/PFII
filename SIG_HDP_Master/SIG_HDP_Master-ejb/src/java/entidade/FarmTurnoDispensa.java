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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author amed
 */
@Entity
@Table(name = "farm_turno_dispensa", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FarmTurnoDispensa.findAll", query = "SELECT f FROM FarmTurnoDispensa f"),
    @NamedQuery(name = "FarmTurnoDispensa.findByPkIdTurnoDispensa", query = "SELECT f FROM FarmTurnoDispensa f WHERE f.pkIdTurnoDispensa = :pkIdTurnoDispensa"),
    @NamedQuery(name = "FarmTurnoDispensa.findByDataHoraFecho", query = "SELECT f FROM FarmTurnoDispensa f WHERE f.dataHoraFecho = :dataHoraFecho"),
    @NamedQuery(name = "FarmTurnoDispensa.findByDataAbertura", query = "SELECT f FROM FarmTurnoDispensa f WHERE f.dataAbertura = :dataAbertura")})
public class FarmTurnoDispensa implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_turno_dispensa", nullable = false)
    private Integer pkIdTurnoDispensa;
    @Column(name = "data_hora_fecho")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataHoraFecho;
    @Basic(optional = false)
    @NotNull
    @Column(name = "data_abertura", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataAbertura;
    @JoinColumn(name = "fk_id_local_de_atendimento", referencedColumnName = "pk_id_local_armazenamento", nullable = false)
    @ManyToOne(optional = false)
    private FarmLocalArmazenamento fkIdLocalDeAtendimento;
    @JoinColumn(name = "fk_id_turno", referencedColumnName = "pk_id_turno", nullable = false)
    @ManyToOne(optional = false)
    private FarmTurno fkIdTurno;
    @JoinColumn(name = "fk_id_funcionario_que_abriu", referencedColumnName = "pk_id_funcionario", nullable = false)
    @ManyToOne(optional = false)
    private RhFuncionario fkIdFuncionarioQueAbriu;
    @JoinColumn(name = "fk_id_funcionario_que_fechou", referencedColumnName = "pk_id_funcionario")
    @ManyToOne
    private RhFuncionario fkIdFuncionarioQueFechou;
    @OneToMany(mappedBy = "fkIdTurnoDispensa")
    private List<FarmDispensa> farmDispensaList;

    public FarmTurnoDispensa() {
    }

    public FarmTurnoDispensa(Integer pkIdTurnoDispensa) {
        this.pkIdTurnoDispensa = pkIdTurnoDispensa;
    }

    public FarmTurnoDispensa(Integer pkIdTurnoDispensa, Date dataAbertura) {
        this.pkIdTurnoDispensa = pkIdTurnoDispensa;
        this.dataAbertura = dataAbertura;
    }

    public Integer getPkIdTurnoDispensa() {
        return pkIdTurnoDispensa;
    }

    public void setPkIdTurnoDispensa(Integer pkIdTurnoDispensa) {
        this.pkIdTurnoDispensa = pkIdTurnoDispensa;
    }

    public Date getDataHoraFecho() {
        return dataHoraFecho;
    }

    public void setDataHoraFecho(Date dataHoraFecho) {
        this.dataHoraFecho = dataHoraFecho;
    }

    public Date getDataAbertura() {
        return dataAbertura;
    }

    public void setDataAbertura(Date dataAbertura) {
        this.dataAbertura = dataAbertura;
    }

    public FarmLocalArmazenamento getFkIdLocalDeAtendimento() {
        return fkIdLocalDeAtendimento;
    }

    public void setFkIdLocalDeAtendimento(FarmLocalArmazenamento fkIdLocalDeAtendimento) {
        this.fkIdLocalDeAtendimento = fkIdLocalDeAtendimento;
    }

    public FarmTurno getFkIdTurno() {
        return fkIdTurno;
    }

    public void setFkIdTurno(FarmTurno fkIdTurno) {
        this.fkIdTurno = fkIdTurno;
    }

    public RhFuncionario getFkIdFuncionarioQueAbriu() {
        return fkIdFuncionarioQueAbriu;
    }

    public void setFkIdFuncionarioQueAbriu(RhFuncionario fkIdFuncionarioQueAbriu) {
        this.fkIdFuncionarioQueAbriu = fkIdFuncionarioQueAbriu;
    }

    public RhFuncionario getFkIdFuncionarioQueFechou() {
        return fkIdFuncionarioQueFechou;
    }

    public void setFkIdFuncionarioQueFechou(RhFuncionario fkIdFuncionarioQueFechou) {
        this.fkIdFuncionarioQueFechou = fkIdFuncionarioQueFechou;
    }

    @XmlTransient
    public List<FarmDispensa> getFarmDispensaList() {
        return farmDispensaList;
    }

    public void setFarmDispensaList(List<FarmDispensa> farmDispensaList) {
        this.farmDispensaList = farmDispensaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdTurnoDispensa != null ? pkIdTurnoDispensa.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FarmTurnoDispensa)) {
            return false;
        }
        FarmTurnoDispensa other = (FarmTurnoDispensa) object;
        if ((this.pkIdTurnoDispensa == null && other.pkIdTurnoDispensa != null) || (this.pkIdTurnoDispensa != null && !this.pkIdTurnoDispensa.equals(other.pkIdTurnoDispensa))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.FarmTurnoDispensa[ pkIdTurnoDispensa=" + pkIdTurnoDispensa + " ]";
    }
    
}
