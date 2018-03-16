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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author amed
 */
@Entity
@Table(name = "diag_bolsa_sangue", catalog = "sigh_db", schema = "public", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"codigo_colheita"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DiagBolsaSangue.findAll", query = "SELECT d FROM DiagBolsaSangue d"),
    @NamedQuery(name = "DiagBolsaSangue.findByPkIdBolsaSangue", query = "SELECT d FROM DiagBolsaSangue d WHERE d.pkIdBolsaSangue = :pkIdBolsaSangue"),
    @NamedQuery(name = "DiagBolsaSangue.findByCodigoColheita", query = "SELECT d FROM DiagBolsaSangue d WHERE d.codigoColheita = :codigoColheita"),
    @NamedQuery(name = "DiagBolsaSangue.findByDataExpiracao", query = "SELECT d FROM DiagBolsaSangue d WHERE d.dataExpiracao = :dataExpiracao"),
    @NamedQuery(name = "DiagBolsaSangue.findByQuantidadeColhida", query = "SELECT d FROM DiagBolsaSangue d WHERE d.quantidadeColhida = :quantidadeColhida"),
    @NamedQuery(name = "DiagBolsaSangue.findByDataCadastro", query = "SELECT d FROM DiagBolsaSangue d WHERE d.dataCadastro = :dataCadastro")})
public class DiagBolsaSangue implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_bolsa_sangue", nullable = false)
    private Integer pkIdBolsaSangue;
    @Size(max = 45)
    @Column(name = "codigo_colheita", length = 45)
    private String codigoColheita;
    @Column(name = "data_expiracao")
    @Temporal(TemporalType.DATE)
    private Date dataExpiracao;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "quantidade_colhida", precision = 8, scale = 8)
    private Float quantidadeColhida;
    @Column(name = "data_cadastro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataCadastro;
    @JoinColumn(name = "fk_id_candidato_dador", referencedColumnName = "pk_id_candidato_dador")
    @ManyToOne
    private DiagCandidatoDador fkIdCandidatoDador;
    @OneToMany(mappedBy = "fkIdBolsaSangue")
    private List<DiagTesteCompatibilidade> diagTesteCompatibilidadeList;

    public DiagBolsaSangue() {
    }

    public DiagBolsaSangue(Integer pkIdBolsaSangue) {
        this.pkIdBolsaSangue = pkIdBolsaSangue;
    }

    public Integer getPkIdBolsaSangue() {
        return pkIdBolsaSangue;
    }

    public void setPkIdBolsaSangue(Integer pkIdBolsaSangue) {
        this.pkIdBolsaSangue = pkIdBolsaSangue;
    }

    public String getCodigoColheita() {
        return codigoColheita;
    }

    public void setCodigoColheita(String codigoColheita) {
        this.codigoColheita = codigoColheita;
    }

    public Date getDataExpiracao() {
        return dataExpiracao;
    }

    public void setDataExpiracao(Date dataExpiracao) {
        this.dataExpiracao = dataExpiracao;
    }

    public Float getQuantidadeColhida() {
        return quantidadeColhida;
    }

    public void setQuantidadeColhida(Float quantidadeColhida) {
        this.quantidadeColhida = quantidadeColhida;
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public DiagCandidatoDador getFkIdCandidatoDador() {
        return fkIdCandidatoDador;
    }

    public void setFkIdCandidatoDador(DiagCandidatoDador fkIdCandidatoDador) {
        this.fkIdCandidatoDador = fkIdCandidatoDador;
    }

    @XmlTransient
    public List<DiagTesteCompatibilidade> getDiagTesteCompatibilidadeList() {
        return diagTesteCompatibilidadeList;
    }

    public void setDiagTesteCompatibilidadeList(List<DiagTesteCompatibilidade> diagTesteCompatibilidadeList) {
        this.diagTesteCompatibilidadeList = diagTesteCompatibilidadeList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdBolsaSangue != null ? pkIdBolsaSangue.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DiagBolsaSangue)) {
            return false;
        }
        DiagBolsaSangue other = (DiagBolsaSangue) object;
        if ((this.pkIdBolsaSangue == null && other.pkIdBolsaSangue != null) || (this.pkIdBolsaSangue != null && !this.pkIdBolsaSangue.equals(other.pkIdBolsaSangue))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.DiagBolsaSangue[ pkIdBolsaSangue=" + pkIdBolsaSangue + " ]";
    }
    
}
