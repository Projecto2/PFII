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
@Table(name = "tb_consulta", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TbConsulta.findAll", query = "SELECT t FROM TbConsulta t"),
    @NamedQuery(name = "TbConsulta.findByPkConsulta", query = "SELECT t FROM TbConsulta t WHERE t.pkConsulta = :pkConsulta"),
    @NamedQuery(name = "TbConsulta.findByDataHoraConsulta", query = "SELECT t FROM TbConsulta t WHERE t.dataHoraConsulta = :dataHoraConsulta")})
public class TbConsulta implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_consulta", nullable = false)
    private Long pkConsulta;
    @Column(name = "data_hora_consulta")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataHoraConsulta;
    @JoinColumn(name = "fk_centro", referencedColumnName = "pk_id_centro")
    @ManyToOne
    private GrlCentroHospitalar fkCentro;
    @JoinColumn(name = "fk_medico", referencedColumnName = "pk_id_medico_escala")
    @ManyToOne
    private SupiMedicoHasEscala fkMedico;
    @JoinColumn(name = "fk_observacao", referencedColumnName = "pk_observacao")
    @ManyToOne
    private TbObservacao fkObservacao;
    @JoinColumn(name = "fk_triagem", referencedColumnName = "pk_triagem")
    @ManyToOne
    private TbTriagem fkTriagem;
    @OneToMany(mappedBy = "fkConsulta")
    private List<TbConsultaHasSintoma> tbConsultaHasSintomaList;

    public TbConsulta() {
    }

    public TbConsulta(Long pkConsulta) {
        this.pkConsulta = pkConsulta;
    }

    public Long getPkConsulta() {
        return pkConsulta;
    }

    public void setPkConsulta(Long pkConsulta) {
        this.pkConsulta = pkConsulta;
    }

    public Date getDataHoraConsulta() {
        return dataHoraConsulta;
    }

    public void setDataHoraConsulta(Date dataHoraConsulta) {
        this.dataHoraConsulta = dataHoraConsulta;
    }

    public GrlCentroHospitalar getFkCentro() {
        return fkCentro;
    }

    public void setFkCentro(GrlCentroHospitalar fkCentro) {
        this.fkCentro = fkCentro;
    }

    public SupiMedicoHasEscala getFkMedico() {
        return fkMedico;
    }

    public void setFkMedico(SupiMedicoHasEscala fkMedico) {
        this.fkMedico = fkMedico;
    }

    public TbObservacao getFkObservacao() {
        return fkObservacao;
    }

    public void setFkObservacao(TbObservacao fkObservacao) {
        this.fkObservacao = fkObservacao;
    }

    public TbTriagem getFkTriagem() {
        return fkTriagem;
    }

    public void setFkTriagem(TbTriagem fkTriagem) {
        this.fkTriagem = fkTriagem;
    }

    @XmlTransient
    public List<TbConsultaHasSintoma> getTbConsultaHasSintomaList() {
        return tbConsultaHasSintomaList;
    }

    public void setTbConsultaHasSintomaList(List<TbConsultaHasSintoma> tbConsultaHasSintomaList) {
        this.tbConsultaHasSintomaList = tbConsultaHasSintomaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkConsulta != null ? pkConsulta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TbConsulta)) {
            return false;
        }
        TbConsulta other = (TbConsulta) object;
        if ((this.pkConsulta == null && other.pkConsulta != null) || (this.pkConsulta != null && !this.pkConsulta.equals(other.pkConsulta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.TbConsulta[ pkConsulta=" + pkConsulta + " ]";
    }
    
}
