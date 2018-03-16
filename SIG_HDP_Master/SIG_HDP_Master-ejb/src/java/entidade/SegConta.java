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
@Table(name = "seg_conta", catalog = "sigh_db", schema = "public", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"fk_id_funcionario"}),
    @UniqueConstraint(columnNames = {"nome_utilizador"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SegConta.findAll", query = "SELECT s FROM SegConta s"),
    @NamedQuery(name = "SegConta.findByPkIdConta", query = "SELECT s FROM SegConta s WHERE s.pkIdConta = :pkIdConta"),
    @NamedQuery(name = "SegConta.findByNomeUtilizador", query = "SELECT s FROM SegConta s WHERE s.nomeUtilizador = :nomeUtilizador"),
    @NamedQuery(name = "SegConta.findByPalavraPasse", query = "SELECT s FROM SegConta s WHERE s.palavraPasse = :palavraPasse"),
    @NamedQuery(name = "SegConta.findByDataCadastro", query = "SELECT s FROM SegConta s WHERE s.dataCadastro = :dataCadastro"),
    @NamedQuery(name = "SegConta.findByEstadoConta", query = "SELECT s FROM SegConta s WHERE s.estadoConta = :estadoConta"),
    @NamedQuery(name = "SegConta.findByPrimeiroLoginConta", query = "SELECT s FROM SegConta s WHERE s.primeiroLoginConta = :primeiroLoginConta"),
    @NamedQuery(name = "SegConta.findByUltimoAcessoConta", query = "SELECT s FROM SegConta s WHERE s.ultimoAcessoConta = :ultimoAcessoConta")})
public class SegConta implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_conta", nullable = false)
    private Integer pkIdConta;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "nome_utilizador", nullable = false, length = 50)
    private String nomeUtilizador;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "palavra_passe", nullable = false, length = 200)
    private String palavraPasse;
    @Basic(optional = false)
    @NotNull
    @Column(name = "data_cadastro", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataCadastro;
    @Column(name = "estado_conta")
    private Boolean estadoConta;
    @Column(name = "primeiro_login_conta")
    private Boolean primeiroLoginConta;
    @Column(name = "ultimo_acesso_conta")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ultimoAcessoConta;
    @OneToMany(mappedBy = "fkIdConta")
    private List<SegLogAcesso> segLogAcessoList;
    @OneToMany(mappedBy = "fkIdDono")
    private List<AmbCidPerfis> ambCidPerfisList;
    @OneToMany(mappedBy = "fkIdConta")
    private List<SegHistoricoSessao> segHistoricoSessaoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdConta")
    private List<SegAuditoria> segAuditoriaList;
    @JoinColumn(name = "fk_id_funcionario", referencedColumnName = "pk_id_funcionario")
    @OneToOne
    private RhFuncionario fkIdFuncionario;
    @JoinColumn(name = "fk_id_perfil", referencedColumnName = "pk_id_perfil")
    @ManyToOne
    private SegPerfil fkIdPerfil;

    public SegConta() {
    }

    public SegConta(Integer pkIdConta) {
        this.pkIdConta = pkIdConta;
    }

    public SegConta(Integer pkIdConta, String nomeUtilizador, String palavraPasse, Date dataCadastro) {
        this.pkIdConta = pkIdConta;
        this.nomeUtilizador = nomeUtilizador;
        this.palavraPasse = palavraPasse;
        this.dataCadastro = dataCadastro;
    }

    public Integer getPkIdConta() {
        return pkIdConta;
    }

    public void setPkIdConta(Integer pkIdConta) {
        this.pkIdConta = pkIdConta;
    }

    public String getNomeUtilizador() {
        return nomeUtilizador;
    }

    public void setNomeUtilizador(String nomeUtilizador) {
        this.nomeUtilizador = nomeUtilizador;
    }

    public String getPalavraPasse() {
        return palavraPasse;
    }

    public void setPalavraPasse(String palavraPasse) {
        this.palavraPasse = palavraPasse;
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public Boolean getEstadoConta() {
        return estadoConta;
    }

    public void setEstadoConta(Boolean estadoConta) {
        this.estadoConta = estadoConta;
    }

    public Boolean getPrimeiroLoginConta() {
        return primeiroLoginConta;
    }

    public void setPrimeiroLoginConta(Boolean primeiroLoginConta) {
        this.primeiroLoginConta = primeiroLoginConta;
    }

    public Date getUltimoAcessoConta() {
        return ultimoAcessoConta;
    }

    public void setUltimoAcessoConta(Date ultimoAcessoConta) {
        this.ultimoAcessoConta = ultimoAcessoConta;
    }

    @XmlTransient
    public List<SegLogAcesso> getSegLogAcessoList() {
        return segLogAcessoList;
    }

    public void setSegLogAcessoList(List<SegLogAcesso> segLogAcessoList) {
        this.segLogAcessoList = segLogAcessoList;
    }

    @XmlTransient
    public List<AmbCidPerfis> getAmbCidPerfisList() {
        return ambCidPerfisList;
    }

    public void setAmbCidPerfisList(List<AmbCidPerfis> ambCidPerfisList) {
        this.ambCidPerfisList = ambCidPerfisList;
    }

    @XmlTransient
    public List<SegHistoricoSessao> getSegHistoricoSessaoList() {
        return segHistoricoSessaoList;
    }

    public void setSegHistoricoSessaoList(List<SegHistoricoSessao> segHistoricoSessaoList) {
        this.segHistoricoSessaoList = segHistoricoSessaoList;
    }

    @XmlTransient
    public List<SegAuditoria> getSegAuditoriaList() {
        return segAuditoriaList;
    }

    public void setSegAuditoriaList(List<SegAuditoria> segAuditoriaList) {
        this.segAuditoriaList = segAuditoriaList;
    }

    public RhFuncionario getFkIdFuncionario() {
        return fkIdFuncionario;
    }

    public void setFkIdFuncionario(RhFuncionario fkIdFuncionario) {
        this.fkIdFuncionario = fkIdFuncionario;
    }

    public SegPerfil getFkIdPerfil() {
        return fkIdPerfil;
    }

    public void setFkIdPerfil(SegPerfil fkIdPerfil) {
        this.fkIdPerfil = fkIdPerfil;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdConta != null ? pkIdConta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SegConta)) {
            return false;
        }
        SegConta other = (SegConta) object;
        if ((this.pkIdConta == null && other.pkIdConta != null) || (this.pkIdConta != null && !this.pkIdConta.equals(other.pkIdConta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.SegConta[ pkIdConta=" + pkIdConta + " ]";
    }
    
}
