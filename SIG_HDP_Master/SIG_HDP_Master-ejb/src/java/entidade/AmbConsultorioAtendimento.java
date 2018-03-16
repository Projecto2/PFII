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
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author amed
 */
@Entity
@Table(name = "amb_consultorio_atendimento", catalog = "sigh_db", schema = "public", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"data_hora_cadastro", "fk_id_funcionario"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AmbConsultorioAtendimento.findAll", query = "SELECT a FROM AmbConsultorioAtendimento a"),
    @NamedQuery(name = "AmbConsultorioAtendimento.findByPkIdConsultorioAtendimento", query = "SELECT a FROM AmbConsultorioAtendimento a WHERE a.pkIdConsultorioAtendimento = :pkIdConsultorioAtendimento"),
    @NamedQuery(name = "AmbConsultorioAtendimento.findByDataHoraCadastro", query = "SELECT a FROM AmbConsultorioAtendimento a WHERE a.dataHoraCadastro = :dataHoraCadastro")})
public class AmbConsultorioAtendimento implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_consultorio_atendimento", nullable = false)
    private Long pkIdConsultorioAtendimento;
    @Column(name = "data_hora_cadastro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataHoraCadastro;
    @OneToMany(mappedBy = "fkIdConsultorioAtendimento")
    private List<AmbTransferencia> ambTransferenciaList;
    @OneToMany(mappedBy = "fkIdConsultorioAtendimento")
    private List<AmbConsulta> ambConsultaList;
    @JoinColumn(name = "fk_id_triagem", referencedColumnName = "pk_id_triagem")
    @ManyToOne
    private AmbTriagem fkIdTriagem;
    @JoinColumn(name = "fk_id_funcionario", referencedColumnName = "pk_id_funcionario")
    @ManyToOne
    private RhFuncionario fkIdFuncionario;
    @JoinColumn(name = "fk_id_medico_has_escala", referencedColumnName = "pk_id_medico_escala")
    @ManyToOne
    private SupiMedicoHasEscala fkIdMedicoHasEscala;

    public AmbConsultorioAtendimento() {
    }

    public AmbConsultorioAtendimento(Long pkIdConsultorioAtendimento) {
        this.pkIdConsultorioAtendimento = pkIdConsultorioAtendimento;
    }

    public Long getPkIdConsultorioAtendimento() {
        return pkIdConsultorioAtendimento;
    }

    public void setPkIdConsultorioAtendimento(Long pkIdConsultorioAtendimento) {
        this.pkIdConsultorioAtendimento = pkIdConsultorioAtendimento;
    }

    public Date getDataHoraCadastro() {
        return dataHoraCadastro;
    }

    public void setDataHoraCadastro(Date dataHoraCadastro) {
        this.dataHoraCadastro = dataHoraCadastro;
    }

    @XmlTransient
    public List<AmbTransferencia> getAmbTransferenciaList() {
        return ambTransferenciaList;
    }

    public void setAmbTransferenciaList(List<AmbTransferencia> ambTransferenciaList) {
        this.ambTransferenciaList = ambTransferenciaList;
    }

    @XmlTransient
    public List<AmbConsulta> getAmbConsultaList() {
        return ambConsultaList;
    }

    public void setAmbConsultaList(List<AmbConsulta> ambConsultaList) {
        this.ambConsultaList = ambConsultaList;
    }

    public AmbTriagem getFkIdTriagem() {
        return fkIdTriagem;
    }

    public void setFkIdTriagem(AmbTriagem fkIdTriagem) {
        this.fkIdTriagem = fkIdTriagem;
    }

    public RhFuncionario getFkIdFuncionario() {
        return fkIdFuncionario;
    }

    public void setFkIdFuncionario(RhFuncionario fkIdFuncionario) {
        this.fkIdFuncionario = fkIdFuncionario;
    }

    public SupiMedicoHasEscala getFkIdMedicoHasEscala() {
        return fkIdMedicoHasEscala;
    }

    public void setFkIdMedicoHasEscala(SupiMedicoHasEscala fkIdMedicoHasEscala) {
        this.fkIdMedicoHasEscala = fkIdMedicoHasEscala;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdConsultorioAtendimento != null ? pkIdConsultorioAtendimento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AmbConsultorioAtendimento)) {
            return false;
        }
        AmbConsultorioAtendimento other = (AmbConsultorioAtendimento) object;
        if ((this.pkIdConsultorioAtendimento == null && other.pkIdConsultorioAtendimento != null) || (this.pkIdConsultorioAtendimento != null && !this.pkIdConsultorioAtendimento.equals(other.pkIdConsultorioAtendimento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.AmbConsultorioAtendimento[ pkIdConsultorioAtendimento=" + pkIdConsultorioAtendimento + " ]";
    }
    
}
