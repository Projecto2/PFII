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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author amed
 */
@Entity
@Table(name = "grl_pessoa", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GrlPessoa.findAll", query = "SELECT g FROM GrlPessoa g"),
    @NamedQuery(name = "GrlPessoa.findByPkIdPessoa", query = "SELECT g FROM GrlPessoa g WHERE g.pkIdPessoa = :pkIdPessoa"),
    @NamedQuery(name = "GrlPessoa.findByDataNascimento", query = "SELECT g FROM GrlPessoa g WHERE g.dataNascimento = :dataNascimento"),
    @NamedQuery(name = "GrlPessoa.findByNomePai", query = "SELECT g FROM GrlPessoa g WHERE g.nomePai = :nomePai"),
    @NamedQuery(name = "GrlPessoa.findByNomeMae", query = "SELECT g FROM GrlPessoa g WHERE g.nomeMae = :nomeMae"),
    @NamedQuery(name = "GrlPessoa.findByDataCadastro", query = "SELECT g FROM GrlPessoa g WHERE g.dataCadastro = :dataCadastro"),
    @NamedQuery(name = "GrlPessoa.findByNumeroPessoa", query = "SELECT g FROM GrlPessoa g WHERE g.numeroPessoa = :numeroPessoa"),
    @NamedQuery(name = "GrlPessoa.findByNome", query = "SELECT g FROM GrlPessoa g WHERE g.nome = :nome"),
    @NamedQuery(name = "GrlPessoa.findByNomeDoMeio", query = "SELECT g FROM GrlPessoa g WHERE g.nomeDoMeio = :nomeDoMeio"),
    @NamedQuery(name = "GrlPessoa.findBySobreNome", query = "SELECT g FROM GrlPessoa g WHERE g.sobreNome = :sobreNome")})
public class GrlPessoa implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_pessoa", nullable = false)
    private Integer pkIdPessoa;
    @Basic(optional = false)
    @NotNull
    @Column(name = "data_nascimento", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dataNascimento;
    @Size(max = 45)
    @Column(name = "nome_pai", length = 45)
    private String nomePai;
    @Size(max = 45)
    @Column(name = "nome_mae", length = 45)
    private String nomeMae;
    @Column(name = "data_cadastro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataCadastro;
    @Size(max = 20)
    @Column(name = "numero_pessoa", length = 20)
    private String numeroPessoa;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 75)
    @Column(name = "nome", nullable = false, length = 75)
    private String nome;
    @Size(max = 150)
    @Column(name = "nome_do_meio", length = 150)
    private String nomeDoMeio;
    @Size(max = 75)
    @Column(name = "sobre_nome", length = 75)
    private String sobreNome;
    @OneToMany(mappedBy = "fkIdPessoa")
    private List<DiagCandidatoDador> diagCandidatoDadorList;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "fkIdPessoa")
    private RhFuncionario rhFuncionario;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdPessoa")
    private List<SupiFormadorAux> supiFormadorAuxList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdPessoa")
    private List<AdmsPaciente> admsPacienteList;
    @OneToMany(mappedBy = "fkIdPessoa")
    private List<AdmsResponsavelPaciente> admsResponsavelPacienteList;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "fkIdPessoa")
    private RhCandidato rhCandidato;
    @JoinColumn(name = "fk_id_grupo_sanguineo", referencedColumnName = "pk_id_grupo_sanguineo")
    @ManyToOne
    private DiagGrupoSanguineo fkIdGrupoSanguineo;
    @JoinColumn(name = "fk_id_conjuge_pessoa", referencedColumnName = "pk_id_conjuge_pessoa")
    @ManyToOne
    private GrlConjugePessoa fkIdConjugePessoa;
    @JoinColumn(name = "fk_id_contacto", referencedColumnName = "pk_id_contacto", nullable = false)
    @ManyToOne(optional = false)
    private GrlContacto fkIdContacto;
    @JoinColumn(name = "fk_id_endereco", referencedColumnName = "pk_id_endereco", nullable = false)
    @ManyToOne(optional = false)
    private GrlEndereco fkIdEndereco;
    @JoinColumn(name = "fk_id_estado_civil", referencedColumnName = "pk_id_estado_civil", nullable = false)
    @ManyToOne(optional = false)
    private GrlEstadoCivil fkIdEstadoCivil;
    @JoinColumn(name = "fk_id_foto", referencedColumnName = "pk_id_ficheiro_anexado")
    @ManyToOne
    private GrlFicheiroAnexado fkIdFoto;
    @JoinColumn(name = "fk_id_nacionalidade", referencedColumnName = "pk_id_pais")
    @ManyToOne
    private GrlPais fkIdNacionalidade;
    @JoinColumn(name = "fk_id_religiao", referencedColumnName = "pk_id_religiao")
    @ManyToOne
    private GrlReligiao fkIdReligiao;
    @JoinColumn(name = "fk_id_sexo", referencedColumnName = "pk_id_sexo", nullable = false)
    @ManyToOne(optional = false)
    private GrlSexo fkIdSexo;
    @OneToMany(mappedBy = "fkIdPessoa")
    private List<GrlDocumentoIdentificacao> grlDocumentoIdentificacaoList;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "fkIdPessoa")
    private RhEstagiario rhEstagiario;

    public GrlPessoa() {
    }

    public GrlPessoa(Integer pkIdPessoa) {
        this.pkIdPessoa = pkIdPessoa;
    }

    public GrlPessoa(Integer pkIdPessoa, Date dataNascimento, String nome) {
        this.pkIdPessoa = pkIdPessoa;
        this.dataNascimento = dataNascimento;
        this.nome = nome;
    }

    public Integer getPkIdPessoa() {
        return pkIdPessoa;
    }

    public void setPkIdPessoa(Integer pkIdPessoa) {
        this.pkIdPessoa = pkIdPessoa;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getNomePai() {
        return nomePai;
    }

    public void setNomePai(String nomePai) {
        this.nomePai = nomePai;
    }

    public String getNomeMae() {
        return nomeMae;
    }

    public void setNomeMae(String nomeMae) {
        this.nomeMae = nomeMae;
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public String getNumeroPessoa() {
        return numeroPessoa;
    }

    public void setNumeroPessoa(String numeroPessoa) {
        this.numeroPessoa = numeroPessoa;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNomeDoMeio() {
        return nomeDoMeio;
    }

    public void setNomeDoMeio(String nomeDoMeio) {
        this.nomeDoMeio = nomeDoMeio;
    }

    public String getSobreNome() {
        return sobreNome;
    }

    public void setSobreNome(String sobreNome) {
        this.sobreNome = sobreNome;
    }

    @XmlTransient
    public List<DiagCandidatoDador> getDiagCandidatoDadorList() {
        return diagCandidatoDadorList;
    }

    public void setDiagCandidatoDadorList(List<DiagCandidatoDador> diagCandidatoDadorList) {
        this.diagCandidatoDadorList = diagCandidatoDadorList;
    }

    public RhFuncionario getRhFuncionario() {
        return rhFuncionario;
    }

    public void setRhFuncionario(RhFuncionario rhFuncionario) {
        this.rhFuncionario = rhFuncionario;
    }

    @XmlTransient
    public List<SupiFormadorAux> getSupiFormadorAuxList() {
        return supiFormadorAuxList;
    }

    public void setSupiFormadorAuxList(List<SupiFormadorAux> supiFormadorAuxList) {
        this.supiFormadorAuxList = supiFormadorAuxList;
    }

    @XmlTransient
    public List<AdmsPaciente> getAdmsPacienteList() {
        return admsPacienteList;
    }

    public void setAdmsPacienteList(List<AdmsPaciente> admsPacienteList) {
        this.admsPacienteList = admsPacienteList;
    }

    @XmlTransient
    public List<AdmsResponsavelPaciente> getAdmsResponsavelPacienteList() {
        return admsResponsavelPacienteList;
    }

    public void setAdmsResponsavelPacienteList(List<AdmsResponsavelPaciente> admsResponsavelPacienteList) {
        this.admsResponsavelPacienteList = admsResponsavelPacienteList;
    }

    public RhCandidato getRhCandidato() {
        return rhCandidato;
    }

    public void setRhCandidato(RhCandidato rhCandidato) {
        this.rhCandidato = rhCandidato;
    }

    public DiagGrupoSanguineo getFkIdGrupoSanguineo() {
        return fkIdGrupoSanguineo;
    }

    public void setFkIdGrupoSanguineo(DiagGrupoSanguineo fkIdGrupoSanguineo) {
        this.fkIdGrupoSanguineo = fkIdGrupoSanguineo;
    }

    public GrlConjugePessoa getFkIdConjugePessoa() {
        return fkIdConjugePessoa;
    }

    public void setFkIdConjugePessoa(GrlConjugePessoa fkIdConjugePessoa) {
        this.fkIdConjugePessoa = fkIdConjugePessoa;
    }

    public GrlContacto getFkIdContacto() {
        return fkIdContacto;
    }

    public void setFkIdContacto(GrlContacto fkIdContacto) {
        this.fkIdContacto = fkIdContacto;
    }

    public GrlEndereco getFkIdEndereco() {
        return fkIdEndereco;
    }

    public void setFkIdEndereco(GrlEndereco fkIdEndereco) {
        this.fkIdEndereco = fkIdEndereco;
    }

    public GrlEstadoCivil getFkIdEstadoCivil() {
        return fkIdEstadoCivil;
    }

    public void setFkIdEstadoCivil(GrlEstadoCivil fkIdEstadoCivil) {
        this.fkIdEstadoCivil = fkIdEstadoCivil;
    }

    public GrlFicheiroAnexado getFkIdFoto() {
        return fkIdFoto;
    }

    public void setFkIdFoto(GrlFicheiroAnexado fkIdFoto) {
        this.fkIdFoto = fkIdFoto;
    }

    public GrlPais getFkIdNacionalidade() {
        return fkIdNacionalidade;
    }

    public void setFkIdNacionalidade(GrlPais fkIdNacionalidade) {
        this.fkIdNacionalidade = fkIdNacionalidade;
    }

    public GrlReligiao getFkIdReligiao() {
        return fkIdReligiao;
    }

    public void setFkIdReligiao(GrlReligiao fkIdReligiao) {
        this.fkIdReligiao = fkIdReligiao;
    }

    public GrlSexo getFkIdSexo() {
        return fkIdSexo;
    }

    public void setFkIdSexo(GrlSexo fkIdSexo) {
        this.fkIdSexo = fkIdSexo;
    }

    @XmlTransient
    public List<GrlDocumentoIdentificacao> getGrlDocumentoIdentificacaoList() {
        return grlDocumentoIdentificacaoList;
    }

    public void setGrlDocumentoIdentificacaoList(List<GrlDocumentoIdentificacao> grlDocumentoIdentificacaoList) {
        this.grlDocumentoIdentificacaoList = grlDocumentoIdentificacaoList;
    }

    public RhEstagiario getRhEstagiario() {
        return rhEstagiario;
    }

    public void setRhEstagiario(RhEstagiario rhEstagiario) {
        this.rhEstagiario = rhEstagiario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdPessoa != null ? pkIdPessoa.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GrlPessoa)) {
            return false;
        }
        GrlPessoa other = (GrlPessoa) object;
        if ((this.pkIdPessoa == null && other.pkIdPessoa != null) || (this.pkIdPessoa != null && !this.pkIdPessoa.equals(other.pkIdPessoa))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.GrlPessoa[ pkIdPessoa=" + pkIdPessoa + " ]";
    }
    
}
