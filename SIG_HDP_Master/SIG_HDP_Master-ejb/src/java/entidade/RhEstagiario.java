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
import javax.persistence.CascadeType;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author amed
 */
@Entity
@Table(name = "rh_estagiario", catalog = "sigh_db", schema = "public", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"codigo_estagiario"}),
    @UniqueConstraint(columnNames = {"fk_id_pessoa"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RhEstagiario.findAll", query = "SELECT r FROM RhEstagiario r"),
    @NamedQuery(name = "RhEstagiario.findByPkIdEstagiario", query = "SELECT r FROM RhEstagiario r WHERE r.pkIdEstagiario = :pkIdEstagiario"),
    @NamedQuery(name = "RhEstagiario.findByCodigoEstagiario", query = "SELECT r FROM RhEstagiario r WHERE r.codigoEstagiario = :codigoEstagiario"),
    @NamedQuery(name = "RhEstagiario.findByObservacoes", query = "SELECT r FROM RhEstagiario r WHERE r.observacoes = :observacoes"),
    @NamedQuery(name = "RhEstagiario.findByDataCadastro", query = "SELECT r FROM RhEstagiario r WHERE r.dataCadastro = :dataCadastro"),
    @NamedQuery(name = "RhEstagiario.findByDataInicioEstagio", query = "SELECT r FROM RhEstagiario r WHERE r.dataInicioEstagio = :dataInicioEstagio"),
    @NamedQuery(name = "RhEstagiario.findByDataFimEstagio", query = "SELECT r FROM RhEstagiario r WHERE r.dataFimEstagio = :dataFimEstagio"),
    @NamedQuery(name = "RhEstagiario.findByNumeroBi", query = "SELECT r FROM RhEstagiario r WHERE r.numeroBi = :numeroBi"),
    @NamedQuery(name = "RhEstagiario.findByAnoQueFrequenta", query = "SELECT r FROM RhEstagiario r WHERE r.anoQueFrequenta = :anoQueFrequenta"),
    @NamedQuery(name = "RhEstagiario.findByAnoConclusaoPrevisao", query = "SELECT r FROM RhEstagiario r WHERE r.anoConclusaoPrevisao = :anoConclusaoPrevisao"),
    @NamedQuery(name = "RhEstagiario.findByDataAdmissao", query = "SELECT r FROM RhEstagiario r WHERE r.dataAdmissao = :dataAdmissao")})
public class RhEstagiario implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_estagiario", nullable = false)
    private Integer pkIdEstagiario;
    @Size(max = 2147483647)
    @Column(name = "codigo_estagiario", length = 2147483647)
    private String codigoEstagiario;
    @Size(max = 2147483647)
    @Column(name = "observacoes", length = 2147483647)
    private String observacoes;
    @Column(name = "data_cadastro")
    @Temporal(TemporalType.DATE)
    private Date dataCadastro;
    @Column(name = "data_inicio_estagio")
    @Temporal(TemporalType.DATE)
    private Date dataInicioEstagio;
    @Column(name = "data_fim_estagio")
    @Temporal(TemporalType.DATE)
    private Date dataFimEstagio;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "numero_bi", nullable = false, length = 50)
    private String numeroBi;
    @Column(name = "ano_que_frequenta")
    private Integer anoQueFrequenta;
    @Column(name = "ano_conclusao_previsao")
    private Integer anoConclusaoPrevisao;
    @Column(name = "data_admissao")
    @Temporal(TemporalType.DATE)
    private Date dataAdmissao;
    @OneToMany(mappedBy = "fkIdEstagiario")
    private List<SupiControloPresenca> supiControloPresencaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdEstagiario")
    private List<SupiEstagiarioHasEscala> supiEstagiarioHasEscalaList;
    @OneToMany(mappedBy = "fkIdEstagiario")
    private List<SupiFormacaoFormando> supiFormacaoFormandoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdEstagiario")
    private List<SupiEscalaEstagiario> supiEscalaEstagiarioList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdEstagiario")
    private List<SupiTurmaEstagiario> supiTurmaEstagiarioList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdEstagiario")
    private List<SupiAvaliacaoDesempenho> supiAvaliacaoDesempenhoList;
    @JoinColumn(name = "fk_id_curriculum", referencedColumnName = "pk_id_ficheiro_anexado")
    @ManyToOne
    private GrlFicheiroAnexado fkIdCurriculum;
    @JoinColumn(name = "fk_id_documento_escolar", referencedColumnName = "pk_id_ficheiro_anexado", nullable = false)
    @ManyToOne(optional = false)
    private GrlFicheiroAnexado fkIdDocumentoEscolar;
    @JoinColumn(name = "fk_id_bi", referencedColumnName = "pk_id_ficheiro_anexado", nullable = false)
    @ManyToOne(optional = false)
    private GrlFicheiroAnexado fkIdBi;
    @JoinColumn(name = "fk_id_pessoa", referencedColumnName = "pk_id_pessoa", nullable = false)
    @OneToOne(optional = false)
    private GrlPessoa fkIdPessoa;
    @JoinColumn(name = "fk_id_tipo_estagio", referencedColumnName = "pk_id_tipo_estagio", nullable = false)
    @ManyToOne(optional = false)
    private RhTipoEstagio fkIdTipoEstagio;
    @JoinColumn(name = "fk_id_escola_universidade", referencedColumnName = "pk_id_escola_universidade")
    @ManyToOne
    private RhEscolaUniversidade fkIdEscolaUniversidade;
    @JoinColumn(name = "fk_id_especialidade_curso", referencedColumnName = "pk_id_especialidade_curso")
    @ManyToOne
    private RhEspecialidadeCurso fkIdEspecialidadeCurso;
    @JoinColumn(name = "fk_id_estado_estagiario", referencedColumnName = "pk_id_estado_estagiario", nullable = false)
    @ManyToOne(optional = false)
    private RhEstadoEstagiario fkIdEstadoEstagiario;
    @JoinColumn(name = "fk_id_nivel_academico", referencedColumnName = "pk_id_nivel_academico", nullable = false)
    @ManyToOne(optional = false)
    private RhNivelAcademico fkIdNivelAcademico;
    @JoinColumn(name = "fk_id_periodo_aulas", referencedColumnName = "pk_id_periodo_aulas")
    @ManyToOne
    private RhPeriodoAulas fkIdPeriodoAulas;
    @JoinColumn(name = "fk_id_profissao", referencedColumnName = "pk_id_profissao")
    @ManyToOne
    private RhProfissao fkIdProfissao;
    @JoinColumn(name = "fk_id_seccao_trabalho", referencedColumnName = "pk_id_seccao_trabalho")
    @ManyToOne
    private RhSeccaoTrabalho fkIdSeccaoTrabalho;

    public RhEstagiario() {
    }

    public RhEstagiario(Integer pkIdEstagiario) {
        this.pkIdEstagiario = pkIdEstagiario;
    }

    public RhEstagiario(Integer pkIdEstagiario, String numeroBi) {
        this.pkIdEstagiario = pkIdEstagiario;
        this.numeroBi = numeroBi;
    }

    public Integer getPkIdEstagiario() {
        return pkIdEstagiario;
    }

    public void setPkIdEstagiario(Integer pkIdEstagiario) {
        this.pkIdEstagiario = pkIdEstagiario;
    }

    public String getCodigoEstagiario() {
        return codigoEstagiario;
    }

    public void setCodigoEstagiario(String codigoEstagiario) {
        this.codigoEstagiario = codigoEstagiario;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public Date getDataInicioEstagio() {
        return dataInicioEstagio;
    }

    public void setDataInicioEstagio(Date dataInicioEstagio) {
        this.dataInicioEstagio = dataInicioEstagio;
    }

    public Date getDataFimEstagio() {
        return dataFimEstagio;
    }

    public void setDataFimEstagio(Date dataFimEstagio) {
        this.dataFimEstagio = dataFimEstagio;
    }

    public String getNumeroBi() {
        return numeroBi;
    }

    public void setNumeroBi(String numeroBi) {
        this.numeroBi = numeroBi;
    }

    public Integer getAnoQueFrequenta() {
        return anoQueFrequenta;
    }

    public void setAnoQueFrequenta(Integer anoQueFrequenta) {
        this.anoQueFrequenta = anoQueFrequenta;
    }

    public Integer getAnoConclusaoPrevisao() {
        return anoConclusaoPrevisao;
    }

    public void setAnoConclusaoPrevisao(Integer anoConclusaoPrevisao) {
        this.anoConclusaoPrevisao = anoConclusaoPrevisao;
    }

    public Date getDataAdmissao() {
        return dataAdmissao;
    }

    public void setDataAdmissao(Date dataAdmissao) {
        this.dataAdmissao = dataAdmissao;
    }

    @XmlTransient
    public List<SupiControloPresenca> getSupiControloPresencaList() {
        return supiControloPresencaList;
    }

    public void setSupiControloPresencaList(List<SupiControloPresenca> supiControloPresencaList) {
        this.supiControloPresencaList = supiControloPresencaList;
    }

    @XmlTransient
    public List<SupiEstagiarioHasEscala> getSupiEstagiarioHasEscalaList() {
        return supiEstagiarioHasEscalaList;
    }

    public void setSupiEstagiarioHasEscalaList(List<SupiEstagiarioHasEscala> supiEstagiarioHasEscalaList) {
        this.supiEstagiarioHasEscalaList = supiEstagiarioHasEscalaList;
    }

    @XmlTransient
    public List<SupiFormacaoFormando> getSupiFormacaoFormandoList() {
        return supiFormacaoFormandoList;
    }

    public void setSupiFormacaoFormandoList(List<SupiFormacaoFormando> supiFormacaoFormandoList) {
        this.supiFormacaoFormandoList = supiFormacaoFormandoList;
    }

    @XmlTransient
    public List<SupiEscalaEstagiario> getSupiEscalaEstagiarioList() {
        return supiEscalaEstagiarioList;
    }

    public void setSupiEscalaEstagiarioList(List<SupiEscalaEstagiario> supiEscalaEstagiarioList) {
        this.supiEscalaEstagiarioList = supiEscalaEstagiarioList;
    }

    @XmlTransient
    public List<SupiTurmaEstagiario> getSupiTurmaEstagiarioList() {
        return supiTurmaEstagiarioList;
    }

    public void setSupiTurmaEstagiarioList(List<SupiTurmaEstagiario> supiTurmaEstagiarioList) {
        this.supiTurmaEstagiarioList = supiTurmaEstagiarioList;
    }

    @XmlTransient
    public List<SupiAvaliacaoDesempenho> getSupiAvaliacaoDesempenhoList() {
        return supiAvaliacaoDesempenhoList;
    }

    public void setSupiAvaliacaoDesempenhoList(List<SupiAvaliacaoDesempenho> supiAvaliacaoDesempenhoList) {
        this.supiAvaliacaoDesempenhoList = supiAvaliacaoDesempenhoList;
    }

    public GrlFicheiroAnexado getFkIdCurriculum() {
        return fkIdCurriculum;
    }

    public void setFkIdCurriculum(GrlFicheiroAnexado fkIdCurriculum) {
        this.fkIdCurriculum = fkIdCurriculum;
    }

    public GrlFicheiroAnexado getFkIdDocumentoEscolar() {
        return fkIdDocumentoEscolar;
    }

    public void setFkIdDocumentoEscolar(GrlFicheiroAnexado fkIdDocumentoEscolar) {
        this.fkIdDocumentoEscolar = fkIdDocumentoEscolar;
    }

    public GrlFicheiroAnexado getFkIdBi() {
        return fkIdBi;
    }

    public void setFkIdBi(GrlFicheiroAnexado fkIdBi) {
        this.fkIdBi = fkIdBi;
    }

    public GrlPessoa getFkIdPessoa() {
        return fkIdPessoa;
    }

    public void setFkIdPessoa(GrlPessoa fkIdPessoa) {
        this.fkIdPessoa = fkIdPessoa;
    }

    public RhTipoEstagio getFkIdTipoEstagio() {
        return fkIdTipoEstagio;
    }

    public void setFkIdTipoEstagio(RhTipoEstagio fkIdTipoEstagio) {
        this.fkIdTipoEstagio = fkIdTipoEstagio;
    }

    public RhEscolaUniversidade getFkIdEscolaUniversidade() {
        return fkIdEscolaUniversidade;
    }

    public void setFkIdEscolaUniversidade(RhEscolaUniversidade fkIdEscolaUniversidade) {
        this.fkIdEscolaUniversidade = fkIdEscolaUniversidade;
    }

    public RhEspecialidadeCurso getFkIdEspecialidadeCurso() {
        return fkIdEspecialidadeCurso;
    }

    public void setFkIdEspecialidadeCurso(RhEspecialidadeCurso fkIdEspecialidadeCurso) {
        this.fkIdEspecialidadeCurso = fkIdEspecialidadeCurso;
    }

    public RhEstadoEstagiario getFkIdEstadoEstagiario() {
        return fkIdEstadoEstagiario;
    }

    public void setFkIdEstadoEstagiario(RhEstadoEstagiario fkIdEstadoEstagiario) {
        this.fkIdEstadoEstagiario = fkIdEstadoEstagiario;
    }

    public RhNivelAcademico getFkIdNivelAcademico() {
        return fkIdNivelAcademico;
    }

    public void setFkIdNivelAcademico(RhNivelAcademico fkIdNivelAcademico) {
        this.fkIdNivelAcademico = fkIdNivelAcademico;
    }

    public RhPeriodoAulas getFkIdPeriodoAulas() {
        return fkIdPeriodoAulas;
    }

    public void setFkIdPeriodoAulas(RhPeriodoAulas fkIdPeriodoAulas) {
        this.fkIdPeriodoAulas = fkIdPeriodoAulas;
    }

    public RhProfissao getFkIdProfissao() {
        return fkIdProfissao;
    }

    public void setFkIdProfissao(RhProfissao fkIdProfissao) {
        this.fkIdProfissao = fkIdProfissao;
    }

    public RhSeccaoTrabalho getFkIdSeccaoTrabalho() {
        return fkIdSeccaoTrabalho;
    }

    public void setFkIdSeccaoTrabalho(RhSeccaoTrabalho fkIdSeccaoTrabalho) {
        this.fkIdSeccaoTrabalho = fkIdSeccaoTrabalho;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdEstagiario != null ? pkIdEstagiario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RhEstagiario)) {
            return false;
        }
        RhEstagiario other = (RhEstagiario) object;
        if ((this.pkIdEstagiario == null && other.pkIdEstagiario != null) || (this.pkIdEstagiario != null && !this.pkIdEstagiario.equals(other.pkIdEstagiario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.RhEstagiario[ pkIdEstagiario=" + pkIdEstagiario + " ]";
    }
    
}
