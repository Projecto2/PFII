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
@Table(name = "diag_tipagem_mensal", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DiagTipagemMensal.findAll", query = "SELECT d FROM DiagTipagemMensal d"),
    @NamedQuery(name = "DiagTipagemMensal.findByPkIdTipagemMensal", query = "SELECT d FROM DiagTipagemMensal d WHERE d.pkIdTipagemMensal = :pkIdTipagemMensal"),
    @NamedQuery(name = "DiagTipagemMensal.findByMes", query = "SELECT d FROM DiagTipagemMensal d WHERE d.mes = :mes"),
    @NamedQuery(name = "DiagTipagemMensal.findByAno", query = "SELECT d FROM DiagTipagemMensal d WHERE d.ano = :ano"),
    @NamedQuery(name = "DiagTipagemMensal.findByDataRealizacao", query = "SELECT d FROM DiagTipagemMensal d WHERE d.dataRealizacao = :dataRealizacao")})
public class DiagTipagemMensal implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_tipagem_mensal", nullable = false)
    private Integer pkIdTipagemMensal;
    @Size(max = 30)
    @Column(name = "mes", length = 30)
    private String mes;
    @Column(name = "ano")
    private Integer ano;
    @Column(name = "data_realizacao")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataRealizacao;
    @OneToMany(mappedBy = "fkIdTipagemMensal")
    private List<DiagTipagemMensalHasReagente> diagTipagemMensalHasReagenteList;

    public DiagTipagemMensal() {
    }

    public DiagTipagemMensal(Integer pkIdTipagemMensal) {
        this.pkIdTipagemMensal = pkIdTipagemMensal;
    }

    public Integer getPkIdTipagemMensal() {
        return pkIdTipagemMensal;
    }

    public void setPkIdTipagemMensal(Integer pkIdTipagemMensal) {
        this.pkIdTipagemMensal = pkIdTipagemMensal;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    public Date getDataRealizacao() {
        return dataRealizacao;
    }

    public void setDataRealizacao(Date dataRealizacao) {
        this.dataRealizacao = dataRealizacao;
    }

    @XmlTransient
    public List<DiagTipagemMensalHasReagente> getDiagTipagemMensalHasReagenteList() {
        return diagTipagemMensalHasReagenteList;
    }

    public void setDiagTipagemMensalHasReagenteList(List<DiagTipagemMensalHasReagente> diagTipagemMensalHasReagenteList) {
        this.diagTipagemMensalHasReagenteList = diagTipagemMensalHasReagenteList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdTipagemMensal != null ? pkIdTipagemMensal.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DiagTipagemMensal)) {
            return false;
        }
        DiagTipagemMensal other = (DiagTipagemMensal) object;
        if ((this.pkIdTipagemMensal == null && other.pkIdTipagemMensal != null) || (this.pkIdTipagemMensal != null && !this.pkIdTipagemMensal.equals(other.pkIdTipagemMensal))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.DiagTipagemMensal[ pkIdTipagemMensal=" + pkIdTipagemMensal + " ]";
    }
    
}
