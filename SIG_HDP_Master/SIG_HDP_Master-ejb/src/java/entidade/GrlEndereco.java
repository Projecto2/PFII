/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entidade;

import java.io.Serializable;
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
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author amed
 */
@Entity
@Table(name = "grl_endereco", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GrlEndereco.findAll", query = "SELECT g FROM GrlEndereco g"),
    @NamedQuery(name = "GrlEndereco.findByPkIdEndereco", query = "SELECT g FROM GrlEndereco g WHERE g.pkIdEndereco = :pkIdEndereco"),
    @NamedQuery(name = "GrlEndereco.findByBairro", query = "SELECT g FROM GrlEndereco g WHERE g.bairro = :bairro"),
    @NamedQuery(name = "GrlEndereco.findByNumeroCasa", query = "SELECT g FROM GrlEndereco g WHERE g.numeroCasa = :numeroCasa"),
    @NamedQuery(name = "GrlEndereco.findByRua", query = "SELECT g FROM GrlEndereco g WHERE g.rua = :rua"),
    @NamedQuery(name = "GrlEndereco.findByCodigoPostal", query = "SELECT g FROM GrlEndereco g WHERE g.codigoPostal = :codigoPostal")})
public class GrlEndereco implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_endereco", nullable = false)
    private Long pkIdEndereco;
    @Size(max = 50)
    @Column(name = "bairro", length = 50)
    private String bairro;
    @Size(max = 50)
    @Column(name = "numero_casa", length = 50)
    private String numeroCasa;
    @Size(max = 50)
    @Column(name = "rua", length = 50)
    private String rua;
    @Size(max = 100)
    @Column(name = "codigo_postal", length = 100)
    private String codigoPostal;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdEndereco")
    private List<GrlInstituicao> grlInstituicaoList;
    @OneToMany(mappedBy = "fkIdEnderecoTemporario")
    private List<AdmsPaciente> admsPacienteList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdEndereco")
    private List<SupiFormacao> supiFormacaoList;
    @JoinColumn(name = "fk_id_comuna", referencedColumnName = "pk_id__comuna")
    @ManyToOne
    private GrlComuna fkIdComuna;
    @JoinColumn(name = "fk_id_distrito", referencedColumnName = "pk_id_distrito")
    @ManyToOne
    private GrlDistrito fkIdDistrito;
    @JoinColumn(name = "fk_id_municipio", referencedColumnName = "pk_id_municipio", nullable = false)
    @ManyToOne(optional = false)
    private GrlMunicipio fkIdMunicipio;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdEndereco")
    private List<SupiInstituicaoProveniencia> supiInstituicaoProvenienciaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdEndereco")
    private List<GrlPessoa> grlPessoaList;

    public GrlEndereco() {
    }

    public GrlEndereco(Long pkIdEndereco) {
        this.pkIdEndereco = pkIdEndereco;
    }

    public Long getPkIdEndereco() {
        return pkIdEndereco;
    }

    public void setPkIdEndereco(Long pkIdEndereco) {
        this.pkIdEndereco = pkIdEndereco;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getNumeroCasa() {
        return numeroCasa;
    }

    public void setNumeroCasa(String numeroCasa) {
        this.numeroCasa = numeroCasa;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    @XmlTransient
    public List<GrlInstituicao> getGrlInstituicaoList() {
        return grlInstituicaoList;
    }

    public void setGrlInstituicaoList(List<GrlInstituicao> grlInstituicaoList) {
        this.grlInstituicaoList = grlInstituicaoList;
    }

    @XmlTransient
    public List<AdmsPaciente> getAdmsPacienteList() {
        return admsPacienteList;
    }

    public void setAdmsPacienteList(List<AdmsPaciente> admsPacienteList) {
        this.admsPacienteList = admsPacienteList;
    }

    @XmlTransient
    public List<SupiFormacao> getSupiFormacaoList() {
        return supiFormacaoList;
    }

    public void setSupiFormacaoList(List<SupiFormacao> supiFormacaoList) {
        this.supiFormacaoList = supiFormacaoList;
    }

    public GrlComuna getFkIdComuna() {
        return fkIdComuna;
    }

    public void setFkIdComuna(GrlComuna fkIdComuna) {
        this.fkIdComuna = fkIdComuna;
    }

    public GrlDistrito getFkIdDistrito() {
        return fkIdDistrito;
    }

    public void setFkIdDistrito(GrlDistrito fkIdDistrito) {
        this.fkIdDistrito = fkIdDistrito;
    }

    public GrlMunicipio getFkIdMunicipio() {
        return fkIdMunicipio;
    }

    public void setFkIdMunicipio(GrlMunicipio fkIdMunicipio) {
        this.fkIdMunicipio = fkIdMunicipio;
    }

    @XmlTransient
    public List<SupiInstituicaoProveniencia> getSupiInstituicaoProvenienciaList() {
        return supiInstituicaoProvenienciaList;
    }

    public void setSupiInstituicaoProvenienciaList(List<SupiInstituicaoProveniencia> supiInstituicaoProvenienciaList) {
        this.supiInstituicaoProvenienciaList = supiInstituicaoProvenienciaList;
    }

    @XmlTransient
    public List<GrlPessoa> getGrlPessoaList() {
        return grlPessoaList;
    }

    public void setGrlPessoaList(List<GrlPessoa> grlPessoaList) {
        this.grlPessoaList = grlPessoaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdEndereco != null ? pkIdEndereco.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GrlEndereco)) {
            return false;
        }
        GrlEndereco other = (GrlEndereco) object;
        if ((this.pkIdEndereco == null && other.pkIdEndereco != null) || (this.pkIdEndereco != null && !this.pkIdEndereco.equals(other.pkIdEndereco))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.GrlEndereco[ pkIdEndereco=" + pkIdEndereco + " ]";
    }
    
}
