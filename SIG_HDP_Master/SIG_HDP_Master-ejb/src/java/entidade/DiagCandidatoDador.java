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
@Table(name = "diag_candidato_dador", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DiagCandidatoDador.findAll", query = "SELECT d FROM DiagCandidatoDador d"),
    @NamedQuery(name = "DiagCandidatoDador.findByPkIdCandidatoDador", query = "SELECT d FROM DiagCandidatoDador d WHERE d.pkIdCandidatoDador = :pkIdCandidatoDador"),
    @NamedQuery(name = "DiagCandidatoDador.findByDataCadastro", query = "SELECT d FROM DiagCandidatoDador d WHERE d.dataCadastro = :dataCadastro"),
    @NamedQuery(name = "DiagCandidatoDador.findByNumeroDadivasAnteriores", query = "SELECT d FROM DiagCandidatoDador d WHERE d.numeroDadivasAnteriores = :numeroDadivasAnteriores"),
    @NamedQuery(name = "DiagCandidatoDador.findByDataUltimaDadiva", query = "SELECT d FROM DiagCandidatoDador d WHERE d.dataUltimaDadiva = :dataUltimaDadiva"),
    @NamedQuery(name = "DiagCandidatoDador.findByCodigoCandidatoDador", query = "SELECT d FROM DiagCandidatoDador d WHERE d.codigoCandidatoDador = :codigoCandidatoDador")})
public class DiagCandidatoDador implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_candidato_dador", nullable = false)
    private Integer pkIdCandidatoDador;
    @Column(name = "data_cadastro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataCadastro;
    @Column(name = "numero_dadivas_anteriores")
    private Integer numeroDadivasAnteriores;
    @Column(name = "data_ultima_dadiva")
    @Temporal(TemporalType.DATE)
    private Date dataUltimaDadiva;
    @Size(max = 10)
    @Column(name = "codigo_candidato_dador", length = 10)
    private String codigoCandidatoDador;
    @JoinColumn(name = "fk_id_numero_doacao", referencedColumnName = "pk_id_numero_doacao")
    @ManyToOne
    private DiagNumeroDoacao fkIdNumeroDoacao;
    @JoinColumn(name = "fk_id_tipo_doacao", referencedColumnName = "pk_id_tipo_doacao")
    @ManyToOne
    private DiagTipoDoacao fkIdTipoDoacao;
    @JoinColumn(name = "fk_id_instituicao_ultima_dadiva", referencedColumnName = "pk_id_instituicao")
    @ManyToOne
    private GrlInstituicao fkIdInstituicaoUltimaDadiva;
    @JoinColumn(name = "fk_id_pessoa", referencedColumnName = "pk_id_pessoa")
    @ManyToOne
    private GrlPessoa fkIdPessoa;
    @OneToMany(mappedBy = "fkIdCandidatoDador")
    private List<DiagBolsaSangue> diagBolsaSangueList;
    @OneToMany(mappedBy = "fkIdCandidatoDador")
    private List<DiagTriagem> diagTriagemList;
    @OneToMany(mappedBy = "fkIdCandidatoDador")
    private List<DiagTipagemDador> diagTipagemDadorList;
    @OneToMany(mappedBy = "fkIdCandidatoDador")
    private List<DiagExameCandidato> diagExameCandidatoList;

    public DiagCandidatoDador() {
    }

    public DiagCandidatoDador(Integer pkIdCandidatoDador) {
        this.pkIdCandidatoDador = pkIdCandidatoDador;
    }

    public Integer getPkIdCandidatoDador() {
        return pkIdCandidatoDador;
    }

    public void setPkIdCandidatoDador(Integer pkIdCandidatoDador) {
        this.pkIdCandidatoDador = pkIdCandidatoDador;
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public Integer getNumeroDadivasAnteriores() {
        return numeroDadivasAnteriores;
    }

    public void setNumeroDadivasAnteriores(Integer numeroDadivasAnteriores) {
        this.numeroDadivasAnteriores = numeroDadivasAnteriores;
    }

    public Date getDataUltimaDadiva() {
        return dataUltimaDadiva;
    }

    public void setDataUltimaDadiva(Date dataUltimaDadiva) {
        this.dataUltimaDadiva = dataUltimaDadiva;
    }

    public String getCodigoCandidatoDador() {
        return codigoCandidatoDador;
    }

    public void setCodigoCandidatoDador(String codigoCandidatoDador) {
        this.codigoCandidatoDador = codigoCandidatoDador;
    }

    public DiagNumeroDoacao getFkIdNumeroDoacao() {
        return fkIdNumeroDoacao;
    }

    public void setFkIdNumeroDoacao(DiagNumeroDoacao fkIdNumeroDoacao) {
        this.fkIdNumeroDoacao = fkIdNumeroDoacao;
    }

    public DiagTipoDoacao getFkIdTipoDoacao() {
        return fkIdTipoDoacao;
    }

    public void setFkIdTipoDoacao(DiagTipoDoacao fkIdTipoDoacao) {
        this.fkIdTipoDoacao = fkIdTipoDoacao;
    }

    public GrlInstituicao getFkIdInstituicaoUltimaDadiva() {
        return fkIdInstituicaoUltimaDadiva;
    }

    public void setFkIdInstituicaoUltimaDadiva(GrlInstituicao fkIdInstituicaoUltimaDadiva) {
        this.fkIdInstituicaoUltimaDadiva = fkIdInstituicaoUltimaDadiva;
    }

    public GrlPessoa getFkIdPessoa() {
        return fkIdPessoa;
    }

    public void setFkIdPessoa(GrlPessoa fkIdPessoa) {
        this.fkIdPessoa = fkIdPessoa;
    }

    @XmlTransient
    public List<DiagBolsaSangue> getDiagBolsaSangueList() {
        return diagBolsaSangueList;
    }

    public void setDiagBolsaSangueList(List<DiagBolsaSangue> diagBolsaSangueList) {
        this.diagBolsaSangueList = diagBolsaSangueList;
    }

    @XmlTransient
    public List<DiagTriagem> getDiagTriagemList() {
        return diagTriagemList;
    }

    public void setDiagTriagemList(List<DiagTriagem> diagTriagemList) {
        this.diagTriagemList = diagTriagemList;
    }

    @XmlTransient
    public List<DiagTipagemDador> getDiagTipagemDadorList() {
        return diagTipagemDadorList;
    }

    public void setDiagTipagemDadorList(List<DiagTipagemDador> diagTipagemDadorList) {
        this.diagTipagemDadorList = diagTipagemDadorList;
    }

    @XmlTransient
    public List<DiagExameCandidato> getDiagExameCandidatoList() {
        return diagExameCandidatoList;
    }

    public void setDiagExameCandidatoList(List<DiagExameCandidato> diagExameCandidatoList) {
        this.diagExameCandidatoList = diagExameCandidatoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdCandidatoDador != null ? pkIdCandidatoDador.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DiagCandidatoDador)) {
            return false;
        }
        DiagCandidatoDador other = (DiagCandidatoDador) object;
        if ((this.pkIdCandidatoDador == null && other.pkIdCandidatoDador != null) || (this.pkIdCandidatoDador != null && !this.pkIdCandidatoDador.equals(other.pkIdCandidatoDador))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.DiagCandidatoDador[ pkIdCandidatoDador=" + pkIdCandidatoDador + " ]";
    }
    
}
