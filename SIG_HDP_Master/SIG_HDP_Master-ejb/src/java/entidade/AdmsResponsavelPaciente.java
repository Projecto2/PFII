/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entidade;

import java.io.Serializable;
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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author amed
 */
@Entity
@Table(name = "adms_responsavel_paciente", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AdmsResponsavelPaciente.findAll", query = "SELECT a FROM AdmsResponsavelPaciente a"),
    @NamedQuery(name = "AdmsResponsavelPaciente.findByPkIdResponsavelPaciente", query = "SELECT a FROM AdmsResponsavelPaciente a WHERE a.pkIdResponsavelPaciente = :pkIdResponsavelPaciente"),
    @NamedQuery(name = "AdmsResponsavelPaciente.findByNomeCompleto", query = "SELECT a FROM AdmsResponsavelPaciente a WHERE a.nomeCompleto = :nomeCompleto"),
    @NamedQuery(name = "AdmsResponsavelPaciente.findByTelefone1", query = "SELECT a FROM AdmsResponsavelPaciente a WHERE a.telefone1 = :telefone1"),
    @NamedQuery(name = "AdmsResponsavelPaciente.findByTelefone2", query = "SELECT a FROM AdmsResponsavelPaciente a WHERE a.telefone2 = :telefone2")})
public class AdmsResponsavelPaciente implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_responsavel_paciente", nullable = false)
    private Long pkIdResponsavelPaciente;
    @Size(max = 2147483647)
    @Column(name = "nome_completo", length = 2147483647)
    private String nomeCompleto;
    @Size(max = 2147483647)
    @Column(name = "telefone1", length = 2147483647)
    private String telefone1;
    @Size(max = 2147483647)
    @Column(name = "telefone2", length = 2147483647)
    private String telefone2;
    @OneToMany(mappedBy = "fkIdResponsavelPaciente")
    private List<AdmsSolicitacao> admsSolicitacaoList;
    @JoinColumn(name = "fk_id_grau_parentesco", referencedColumnName = "pk_id_grau_parentesco")
    @ManyToOne
    private GrlGrauParentesco fkIdGrauParentesco;
    @JoinColumn(name = "fk_id_pessoa", referencedColumnName = "pk_id_pessoa")
    @ManyToOne
    private GrlPessoa fkIdPessoa;

    public AdmsResponsavelPaciente() {
    }

    public AdmsResponsavelPaciente(Long pkIdResponsavelPaciente) {
        this.pkIdResponsavelPaciente = pkIdResponsavelPaciente;
    }

    public Long getPkIdResponsavelPaciente() {
        return pkIdResponsavelPaciente;
    }

    public void setPkIdResponsavelPaciente(Long pkIdResponsavelPaciente) {
        this.pkIdResponsavelPaciente = pkIdResponsavelPaciente;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public String getTelefone1() {
        return telefone1;
    }

    public void setTelefone1(String telefone1) {
        this.telefone1 = telefone1;
    }

    public String getTelefone2() {
        return telefone2;
    }

    public void setTelefone2(String telefone2) {
        this.telefone2 = telefone2;
    }

    @XmlTransient
    public List<AdmsSolicitacao> getAdmsSolicitacaoList() {
        return admsSolicitacaoList;
    }

    public void setAdmsSolicitacaoList(List<AdmsSolicitacao> admsSolicitacaoList) {
        this.admsSolicitacaoList = admsSolicitacaoList;
    }

    public GrlGrauParentesco getFkIdGrauParentesco() {
        return fkIdGrauParentesco;
    }

    public void setFkIdGrauParentesco(GrlGrauParentesco fkIdGrauParentesco) {
        this.fkIdGrauParentesco = fkIdGrauParentesco;
    }

    public GrlPessoa getFkIdPessoa() {
        return fkIdPessoa;
    }

    public void setFkIdPessoa(GrlPessoa fkIdPessoa) {
        this.fkIdPessoa = fkIdPessoa;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdResponsavelPaciente != null ? pkIdResponsavelPaciente.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AdmsResponsavelPaciente)) {
            return false;
        }
        AdmsResponsavelPaciente other = (AdmsResponsavelPaciente) object;
        if ((this.pkIdResponsavelPaciente == null && other.pkIdResponsavelPaciente != null) || (this.pkIdResponsavelPaciente != null && !this.pkIdResponsavelPaciente.equals(other.pkIdResponsavelPaciente))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.AdmsResponsavelPaciente[ pkIdResponsavelPaciente=" + pkIdResponsavelPaciente + " ]";
    }
    
}
