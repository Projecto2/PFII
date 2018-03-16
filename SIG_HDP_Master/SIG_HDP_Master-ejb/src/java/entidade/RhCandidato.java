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
@Table(name = "rh_candidato", catalog = "sigh_db", schema = "public", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"codigo_candidato"}),
    @UniqueConstraint(columnNames = {"fk_id_pessoa"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RhCandidato.findAll", query = "SELECT r FROM RhCandidato r"),
    @NamedQuery(name = "RhCandidato.findByPkIdCandidato", query = "SELECT r FROM RhCandidato r WHERE r.pkIdCandidato = :pkIdCandidato"),
    @NamedQuery(name = "RhCandidato.findByUltimoEmprego", query = "SELECT r FROM RhCandidato r WHERE r.ultimoEmprego = :ultimoEmprego"),
    @NamedQuery(name = "RhCandidato.findByObservacoes", query = "SELECT r FROM RhCandidato r WHERE r.observacoes = :observacoes"),
    @NamedQuery(name = "RhCandidato.findByDataCadastro", query = "SELECT r FROM RhCandidato r WHERE r.dataCadastro = :dataCadastro"),
    @NamedQuery(name = "RhCandidato.findByCodigoCandidato", query = "SELECT r FROM RhCandidato r WHERE r.codigoCandidato = :codigoCandidato"),
    @NamedQuery(name = "RhCandidato.findByReferenciaParaContacto", query = "SELECT r FROM RhCandidato r WHERE r.referenciaParaContacto = :referenciaParaContacto"),
    @NamedQuery(name = "RhCandidato.findByNumeroBi", query = "SELECT r FROM RhCandidato r WHERE r.numeroBi = :numeroBi"),
    @NamedQuery(name = "RhCandidato.findByDataAdmissao", query = "SELECT r FROM RhCandidato r WHERE r.dataAdmissao = :dataAdmissao")})
public class RhCandidato implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_candidato", nullable = false)
    private Integer pkIdCandidato;
    @Size(max = 45)
    @Column(name = "ultimo_emprego", length = 45)
    private String ultimoEmprego;
    @Size(max = 2147483647)
    @Column(name = "observacoes", length = 2147483647)
    private String observacoes;
    @Column(name = "data_cadastro")
    @Temporal(TemporalType.DATE)
    private Date dataCadastro;
    @Size(max = 2147483647)
    @Column(name = "codigo_candidato", length = 2147483647)
    private String codigoCandidato;
    @Size(max = 60)
    @Column(name = "referencia_para_contacto", length = 60)
    private String referenciaParaContacto;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "numero_bi", nullable = false, length = 50)
    private String numeroBi;
    @Column(name = "data_admissao")
    @Temporal(TemporalType.DATE)
    private Date dataAdmissao;
    @OneToMany(mappedBy = "fkIdCandidato")
    private List<RhContrato> rhContratoList;
    @JoinColumn(name = "fk_id_comprovativo_ordem_medicos_enfermeiros", referencedColumnName = "pk_id_ficheiro_anexado")
    @ManyToOne
    private GrlFicheiroAnexado fkIdComprovativoOrdemMedicosEnfermeiros;
    @JoinColumn(name = "fk_id_especialidade2", referencedColumnName = "pk_id_especialidade")
    @ManyToOne
    private GrlEspecialidade fkIdEspecialidade2;
    @JoinColumn(name = "fk_id_especialidade1", referencedColumnName = "pk_id_especialidade")
    @ManyToOne
    private GrlEspecialidade fkIdEspecialidade1;
    @JoinColumn(name = "fk_id_estado_candidato", referencedColumnName = "pk_id_estado_candidato", nullable = false)
    @ManyToOne(optional = false)
    private RhEstadoCandidato fkIdEstadoCandidato;
    @JoinColumn(name = "fk_id_profissao", referencedColumnName = "pk_id_profissao")
    @ManyToOne
    private RhProfissao fkIdProfissao;
    @JoinColumn(name = "fk_id_categoria_pretendida", referencedColumnName = "pk_id_categoria_profissional")
    @ManyToOne
    private RhCategoriaProfissional fkIdCategoriaPretendida;
    @JoinColumn(name = "fk_id_cedula_carteira_profissional", referencedColumnName = "pk_id_ficheiro_anexado")
    @ManyToOne
    private GrlFicheiroAnexado fkIdCedulaCarteiraProfissional;
    @JoinColumn(name = "fk_id_curriculum", referencedColumnName = "pk_id_ficheiro_anexado", nullable = false)
    @ManyToOne(optional = false)
    private GrlFicheiroAnexado fkIdCurriculum;
    @JoinColumn(name = "fk_id_documento_militar_homens", referencedColumnName = "pk_id_ficheiro_anexado", nullable = false)
    @ManyToOne(optional = false)
    private GrlFicheiroAnexado fkIdDocumentoMilitarHomens;
    @JoinColumn(name = "fk_id_pessoa", referencedColumnName = "pk_id_pessoa", nullable = false)
    @OneToOne(optional = false)
    private GrlPessoa fkIdPessoa;
    @JoinColumn(name = "fk_id_certificado_de_habilitacoes", referencedColumnName = "pk_id_ficheiro_anexado", nullable = false)
    @ManyToOne(optional = false)
    private GrlFicheiroAnexado fkIdCertificadoDeHabilitacoes;
    @JoinColumn(name = "fk_id_equivalencia_do_certificado", referencedColumnName = "pk_id_ficheiro_anexado")
    @ManyToOne
    private GrlFicheiroAnexado fkIdEquivalenciaDoCertificado;
    @JoinColumn(name = "fk_id_registo_criminal", referencedColumnName = "pk_id_ficheiro_anexado")
    @ManyToOne
    private GrlFicheiroAnexado fkIdRegistoCriminal;
    @JoinColumn(name = "fk_id_bi", referencedColumnName = "pk_id_ficheiro_anexado", nullable = false)
    @ManyToOne(optional = false)
    private GrlFicheiroAnexado fkIdBi;
    @JoinColumn(name = "fk_id_nivel_academico", referencedColumnName = "pk_id_nivel_academico", nullable = false)
    @ManyToOne(optional = false)
    private RhNivelAcademico fkIdNivelAcademico;
    @JoinColumn(name = "fk_id_atestado_medico", referencedColumnName = "pk_id_ficheiro_anexado", nullable = false)
    @ManyToOne(optional = false)
    private GrlFicheiroAnexado fkIdAtestadoMedico;

    public RhCandidato() {
    }

    public RhCandidato(Integer pkIdCandidato) {
        this.pkIdCandidato = pkIdCandidato;
    }

    public RhCandidato(Integer pkIdCandidato, String numeroBi) {
        this.pkIdCandidato = pkIdCandidato;
        this.numeroBi = numeroBi;
    }

    public Integer getPkIdCandidato() {
        return pkIdCandidato;
    }

    public void setPkIdCandidato(Integer pkIdCandidato) {
        this.pkIdCandidato = pkIdCandidato;
    }

    public String getUltimoEmprego() {
        return ultimoEmprego;
    }

    public void setUltimoEmprego(String ultimoEmprego) {
        this.ultimoEmprego = ultimoEmprego;
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

    public String getCodigoCandidato() {
        return codigoCandidato;
    }

    public void setCodigoCandidato(String codigoCandidato) {
        this.codigoCandidato = codigoCandidato;
    }

    public String getReferenciaParaContacto() {
        return referenciaParaContacto;
    }

    public void setReferenciaParaContacto(String referenciaParaContacto) {
        this.referenciaParaContacto = referenciaParaContacto;
    }

    public String getNumeroBi() {
        return numeroBi;
    }

    public void setNumeroBi(String numeroBi) {
        this.numeroBi = numeroBi;
    }

    public Date getDataAdmissao() {
        return dataAdmissao;
    }

    public void setDataAdmissao(Date dataAdmissao) {
        this.dataAdmissao = dataAdmissao;
    }

    @XmlTransient
    public List<RhContrato> getRhContratoList() {
        return rhContratoList;
    }

    public void setRhContratoList(List<RhContrato> rhContratoList) {
        this.rhContratoList = rhContratoList;
    }

    public GrlFicheiroAnexado getFkIdComprovativoOrdemMedicosEnfermeiros() {
        return fkIdComprovativoOrdemMedicosEnfermeiros;
    }

    public void setFkIdComprovativoOrdemMedicosEnfermeiros(GrlFicheiroAnexado fkIdComprovativoOrdemMedicosEnfermeiros) {
        this.fkIdComprovativoOrdemMedicosEnfermeiros = fkIdComprovativoOrdemMedicosEnfermeiros;
    }

    public GrlEspecialidade getFkIdEspecialidade2() {
        return fkIdEspecialidade2;
    }

    public void setFkIdEspecialidade2(GrlEspecialidade fkIdEspecialidade2) {
        this.fkIdEspecialidade2 = fkIdEspecialidade2;
    }

    public GrlEspecialidade getFkIdEspecialidade1() {
        return fkIdEspecialidade1;
    }

    public void setFkIdEspecialidade1(GrlEspecialidade fkIdEspecialidade1) {
        this.fkIdEspecialidade1 = fkIdEspecialidade1;
    }

    public RhEstadoCandidato getFkIdEstadoCandidato() {
        return fkIdEstadoCandidato;
    }

    public void setFkIdEstadoCandidato(RhEstadoCandidato fkIdEstadoCandidato) {
        this.fkIdEstadoCandidato = fkIdEstadoCandidato;
    }

    public RhProfissao getFkIdProfissao() {
        return fkIdProfissao;
    }

    public void setFkIdProfissao(RhProfissao fkIdProfissao) {
        this.fkIdProfissao = fkIdProfissao;
    }

    public RhCategoriaProfissional getFkIdCategoriaPretendida() {
        return fkIdCategoriaPretendida;
    }

    public void setFkIdCategoriaPretendida(RhCategoriaProfissional fkIdCategoriaPretendida) {
        this.fkIdCategoriaPretendida = fkIdCategoriaPretendida;
    }

    public GrlFicheiroAnexado getFkIdCedulaCarteiraProfissional() {
        return fkIdCedulaCarteiraProfissional;
    }

    public void setFkIdCedulaCarteiraProfissional(GrlFicheiroAnexado fkIdCedulaCarteiraProfissional) {
        this.fkIdCedulaCarteiraProfissional = fkIdCedulaCarteiraProfissional;
    }

    public GrlFicheiroAnexado getFkIdCurriculum() {
        return fkIdCurriculum;
    }

    public void setFkIdCurriculum(GrlFicheiroAnexado fkIdCurriculum) {
        this.fkIdCurriculum = fkIdCurriculum;
    }

    public GrlFicheiroAnexado getFkIdDocumentoMilitarHomens() {
        return fkIdDocumentoMilitarHomens;
    }

    public void setFkIdDocumentoMilitarHomens(GrlFicheiroAnexado fkIdDocumentoMilitarHomens) {
        this.fkIdDocumentoMilitarHomens = fkIdDocumentoMilitarHomens;
    }

    public GrlPessoa getFkIdPessoa() {
        return fkIdPessoa;
    }

    public void setFkIdPessoa(GrlPessoa fkIdPessoa) {
        this.fkIdPessoa = fkIdPessoa;
    }

    public GrlFicheiroAnexado getFkIdCertificadoDeHabilitacoes() {
        return fkIdCertificadoDeHabilitacoes;
    }

    public void setFkIdCertificadoDeHabilitacoes(GrlFicheiroAnexado fkIdCertificadoDeHabilitacoes) {
        this.fkIdCertificadoDeHabilitacoes = fkIdCertificadoDeHabilitacoes;
    }

    public GrlFicheiroAnexado getFkIdEquivalenciaDoCertificado() {
        return fkIdEquivalenciaDoCertificado;
    }

    public void setFkIdEquivalenciaDoCertificado(GrlFicheiroAnexado fkIdEquivalenciaDoCertificado) {
        this.fkIdEquivalenciaDoCertificado = fkIdEquivalenciaDoCertificado;
    }

    public GrlFicheiroAnexado getFkIdRegistoCriminal() {
        return fkIdRegistoCriminal;
    }

    public void setFkIdRegistoCriminal(GrlFicheiroAnexado fkIdRegistoCriminal) {
        this.fkIdRegistoCriminal = fkIdRegistoCriminal;
    }

    public GrlFicheiroAnexado getFkIdBi() {
        return fkIdBi;
    }

    public void setFkIdBi(GrlFicheiroAnexado fkIdBi) {
        this.fkIdBi = fkIdBi;
    }

    public RhNivelAcademico getFkIdNivelAcademico() {
        return fkIdNivelAcademico;
    }

    public void setFkIdNivelAcademico(RhNivelAcademico fkIdNivelAcademico) {
        this.fkIdNivelAcademico = fkIdNivelAcademico;
    }

    public GrlFicheiroAnexado getFkIdAtestadoMedico() {
        return fkIdAtestadoMedico;
    }

    public void setFkIdAtestadoMedico(GrlFicheiroAnexado fkIdAtestadoMedico) {
        this.fkIdAtestadoMedico = fkIdAtestadoMedico;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdCandidato != null ? pkIdCandidato.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RhCandidato)) {
            return false;
        }
        RhCandidato other = (RhCandidato) object;
        if ((this.pkIdCandidato == null && other.pkIdCandidato != null) || (this.pkIdCandidato != null && !this.pkIdCandidato.equals(other.pkIdCandidato))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.RhCandidato[ pkIdCandidato=" + pkIdCandidato + " ]";
    }
    
}
