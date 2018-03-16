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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author amed
 */
@Entity
@Table(name = "diag_tipagem_doente", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DiagTipagemDoente.findAll", query = "SELECT d FROM DiagTipagemDoente d"),
    @NamedQuery(name = "DiagTipagemDoente.findByPkIdTipagemDoente", query = "SELECT d FROM DiagTipagemDoente d WHERE d.pkIdTipagemDoente = :pkIdTipagemDoente"),
    @NamedQuery(name = "DiagTipagemDoente.findByAntiA", query = "SELECT d FROM DiagTipagemDoente d WHERE d.antiA = :antiA"),
    @NamedQuery(name = "DiagTipagemDoente.findByAntiB", query = "SELECT d FROM DiagTipagemDoente d WHERE d.antiB = :antiB"),
    @NamedQuery(name = "DiagTipagemDoente.findByAntiD", query = "SELECT d FROM DiagTipagemDoente d WHERE d.antiD = :antiD"),
    @NamedQuery(name = "DiagTipagemDoente.findByA1", query = "SELECT d FROM DiagTipagemDoente d WHERE d.a1 = :a1"),
    @NamedQuery(name = "DiagTipagemDoente.findByB", query = "SELECT d FROM DiagTipagemDoente d WHERE d.b = :b"),
    @NamedQuery(name = "DiagTipagemDoente.findByO", query = "SELECT d FROM DiagTipagemDoente d WHERE d.o = :o"),
    @NamedQuery(name = "DiagTipagemDoente.findByDataTipagem", query = "SELECT d FROM DiagTipagemDoente d WHERE d.dataTipagem = :dataTipagem"),
    @NamedQuery(name = "DiagTipagemDoente.findByAntiAb", query = "SELECT d FROM DiagTipagemDoente d WHERE d.antiAb = :antiAb"),
    @NamedQuery(name = "DiagTipagemDoente.findByDu", query = "SELECT d FROM DiagTipagemDoente d WHERE d.du = :du")})
public class DiagTipagemDoente implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_tipagem_doente", nullable = false)
    private Integer pkIdTipagemDoente;
    @Size(max = 45)
    @Column(name = "anti_a", length = 45)
    private String antiA;
    @Size(max = 45)
    @Column(name = "anti_b", length = 45)
    private String antiB;
    @Size(max = 45)
    @Column(name = "anti_d", length = 45)
    private String antiD;
    @Size(max = 45)
    @Column(name = "a1", length = 45)
    private String a1;
    @Size(max = 45)
    @Column(name = "b", length = 45)
    private String b;
    @Size(max = 45)
    @Column(name = "o", length = 45)
    private String o;
    @Column(name = "data_tipagem")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataTipagem;
    @Size(max = 45)
    @Column(name = "anti_ab", length = 45)
    private String antiAb;
    @Size(max = 45)
    @Column(name = "du", length = 45)
    private String du;
    @JoinColumn(name = "fk_id_paciente", referencedColumnName = "pk_id_paciente")
    @ManyToOne
    private AdmsPaciente fkIdPaciente;
    @JoinColumn(name = "conclusao", referencedColumnName = "pk_id_grupo_sanguineo")
    @ManyToOne
    private DiagGrupoSanguineo conclusao;
    @JoinColumn(name = "fk_id_solicitacao_tipagem_doente", referencedColumnName = "pk_id_solicitacao_tipagem_doente")
    @ManyToOne
    private DiagSolicitacaoTipagemDoente fkIdSolicitacaoTipagemDoente;
    @JoinColumn(name = "fk_realizado_por", referencedColumnName = "pk_id_funcionario")
    @ManyToOne
    private RhFuncionario fkRealizadoPor;

    public DiagTipagemDoente() {
    }

    public DiagTipagemDoente(Integer pkIdTipagemDoente) {
        this.pkIdTipagemDoente = pkIdTipagemDoente;
    }

    public Integer getPkIdTipagemDoente() {
        return pkIdTipagemDoente;
    }

    public void setPkIdTipagemDoente(Integer pkIdTipagemDoente) {
        this.pkIdTipagemDoente = pkIdTipagemDoente;
    }

    public String getAntiA() {
        return antiA;
    }

    public void setAntiA(String antiA) {
        this.antiA = antiA;
    }

    public String getAntiB() {
        return antiB;
    }

    public void setAntiB(String antiB) {
        this.antiB = antiB;
    }

    public String getAntiD() {
        return antiD;
    }

    public void setAntiD(String antiD) {
        this.antiD = antiD;
    }

    public String getA1() {
        return a1;
    }

    public void setA1(String a1) {
        this.a1 = a1;
    }

    public String getB() {
        return b;
    }

    public void setB(String b) {
        this.b = b;
    }

    public String getO() {
        return o;
    }

    public void setO(String o) {
        this.o = o;
    }

    public Date getDataTipagem() {
        return dataTipagem;
    }

    public void setDataTipagem(Date dataTipagem) {
        this.dataTipagem = dataTipagem;
    }

    public String getAntiAb() {
        return antiAb;
    }

    public void setAntiAb(String antiAb) {
        this.antiAb = antiAb;
    }

    public String getDu() {
        return du;
    }

    public void setDu(String du) {
        this.du = du;
    }

    public AdmsPaciente getFkIdPaciente() {
        return fkIdPaciente;
    }

    public void setFkIdPaciente(AdmsPaciente fkIdPaciente) {
        this.fkIdPaciente = fkIdPaciente;
    }

    public DiagGrupoSanguineo getConclusao() {
        return conclusao;
    }

    public void setConclusao(DiagGrupoSanguineo conclusao) {
        this.conclusao = conclusao;
    }

    public DiagSolicitacaoTipagemDoente getFkIdSolicitacaoTipagemDoente() {
        return fkIdSolicitacaoTipagemDoente;
    }

    public void setFkIdSolicitacaoTipagemDoente(DiagSolicitacaoTipagemDoente fkIdSolicitacaoTipagemDoente) {
        this.fkIdSolicitacaoTipagemDoente = fkIdSolicitacaoTipagemDoente;
    }

    public RhFuncionario getFkRealizadoPor() {
        return fkRealizadoPor;
    }

    public void setFkRealizadoPor(RhFuncionario fkRealizadoPor) {
        this.fkRealizadoPor = fkRealizadoPor;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdTipagemDoente != null ? pkIdTipagemDoente.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DiagTipagemDoente)) {
            return false;
        }
        DiagTipagemDoente other = (DiagTipagemDoente) object;
        if ((this.pkIdTipagemDoente == null && other.pkIdTipagemDoente != null) || (this.pkIdTipagemDoente != null && !this.pkIdTipagemDoente.equals(other.pkIdTipagemDoente))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.DiagTipagemDoente[ pkIdTipagemDoente=" + pkIdTipagemDoente + " ]";
    }
    
}
