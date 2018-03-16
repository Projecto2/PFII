/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entidade;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author amed
 */
@Entity
@Table(name = "amb_transferencia", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AmbTransferencia.findAll", query = "SELECT a FROM AmbTransferencia a"),
    @NamedQuery(name = "AmbTransferencia.findByPkIdTransferencia", query = "SELECT a FROM AmbTransferencia a WHERE a.pkIdTransferencia = :pkIdTransferencia"),
    @NamedQuery(name = "AmbTransferencia.findByDataTransferencia", query = "SELECT a FROM AmbTransferencia a WHERE a.dataTransferencia = :dataTransferencia")})
public class AmbTransferencia implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_transferencia", nullable = false)
    private Long pkIdTransferencia;
    @Column(name = "data_transferencia")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataTransferencia;
    @JoinColumn(name = "fk_id_consultorio_atendimento", referencedColumnName = "pk_id_consultorio_atendimento")
    @ManyToOne
    private AmbConsultorioAtendimento fkIdConsultorioAtendimento;
    @JoinColumn(name = "fk_id_instituicao", referencedColumnName = "pk_id_instituicao")
    @ManyToOne
    private GrlInstituicao fkIdInstituicao;
    @JoinColumn(name = "fk_id_funcionario", referencedColumnName = "pk_id_funcionario")
    @ManyToOne
    private RhFuncionario fkIdFuncionario;

    public AmbTransferencia() {
    }

    public AmbTransferencia(Long pkIdTransferencia) {
        this.pkIdTransferencia = pkIdTransferencia;
    }

    public Long getPkIdTransferencia() {
        return pkIdTransferencia;
    }

    public void setPkIdTransferencia(Long pkIdTransferencia) {
        this.pkIdTransferencia = pkIdTransferencia;
    }

    public Date getDataTransferencia() {
        return dataTransferencia;
    }

    public void setDataTransferencia(Date dataTransferencia) {
        this.dataTransferencia = dataTransferencia;
    }

    public AmbConsultorioAtendimento getFkIdConsultorioAtendimento() {
        return fkIdConsultorioAtendimento;
    }

    public void setFkIdConsultorioAtendimento(AmbConsultorioAtendimento fkIdConsultorioAtendimento) {
        this.fkIdConsultorioAtendimento = fkIdConsultorioAtendimento;
    }

    public GrlInstituicao getFkIdInstituicao() {
        return fkIdInstituicao;
    }

    public void setFkIdInstituicao(GrlInstituicao fkIdInstituicao) {
        this.fkIdInstituicao = fkIdInstituicao;
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
        hash += (pkIdTransferencia != null ? pkIdTransferencia.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AmbTransferencia)) {
            return false;
        }
        AmbTransferencia other = (AmbTransferencia) object;
        if ((this.pkIdTransferencia == null && other.pkIdTransferencia != null) || (this.pkIdTransferencia != null && !this.pkIdTransferencia.equals(other.pkIdTransferencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.AmbTransferencia[ pkIdTransferencia=" + pkIdTransferencia + " ]";
    }
    
}
