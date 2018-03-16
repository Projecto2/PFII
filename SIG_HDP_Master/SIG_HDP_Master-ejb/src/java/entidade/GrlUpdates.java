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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author amed
 */
@Entity
@Table(name = "grl_updates", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GrlUpdates.findAll", query = "SELECT g FROM GrlUpdates g"),
    @NamedQuery(name = "GrlUpdates.findByPkIdGrlUpdates", query = "SELECT g FROM GrlUpdates g WHERE g.pkIdGrlUpdates = :pkIdGrlUpdates"),
    @NamedQuery(name = "GrlUpdates.findByPais", query = "SELECT g FROM GrlUpdates g WHERE g.pais = :pais"),
    @NamedQuery(name = "GrlUpdates.findByProvincia", query = "SELECT g FROM GrlUpdates g WHERE g.provincia = :provincia"),
    @NamedQuery(name = "GrlUpdates.findByMunicipio", query = "SELECT g FROM GrlUpdates g WHERE g.municipio = :municipio"),
    @NamedQuery(name = "GrlUpdates.findByDistrito", query = "SELECT g FROM GrlUpdates g WHERE g.distrito = :distrito"),
    @NamedQuery(name = "GrlUpdates.findByComuna", query = "SELECT g FROM GrlUpdates g WHERE g.comuna = :comuna"),
    @NamedQuery(name = "GrlUpdates.findByTipoDocumentoIdentificacao", query = "SELECT g FROM GrlUpdates g WHERE g.tipoDocumentoIdentificacao = :tipoDocumentoIdentificacao"),
    @NamedQuery(name = "GrlUpdates.findByAreaInterna", query = "SELECT g FROM GrlUpdates g WHERE g.areaInterna = :areaInterna"),
    @NamedQuery(name = "GrlUpdates.findByMarcaProduto", query = "SELECT g FROM GrlUpdates g WHERE g.marcaProduto = :marcaProduto"),
    @NamedQuery(name = "GrlUpdates.findByDiaSemana", query = "SELECT g FROM GrlUpdates g WHERE g.diaSemana = :diaSemana"),
    @NamedQuery(name = "GrlUpdates.findByEspecialidade", query = "SELECT g FROM GrlUpdates g WHERE g.especialidade = :especialidade"),
    @NamedQuery(name = "GrlUpdates.findByEstadoCivil", query = "SELECT g FROM GrlUpdates g WHERE g.estadoCivil = :estadoCivil"),
    @NamedQuery(name = "GrlUpdates.findBySexo", query = "SELECT g FROM GrlUpdates g WHERE g.sexo = :sexo"),
    @NamedQuery(name = "GrlUpdates.findByCentroHospitalar", query = "SELECT g FROM GrlUpdates g WHERE g.centroHospitalar = :centroHospitalar"),
    @NamedQuery(name = "GrlUpdates.findByReligiao", query = "SELECT g FROM GrlUpdates g WHERE g.religiao = :religiao"),
    @NamedQuery(name = "GrlUpdates.findByGrauParentesco", query = "SELECT g FROM GrlUpdates g WHERE g.grauParentesco = :grauParentesco"),
    @NamedQuery(name = "GrlUpdates.findByTamanho", query = "SELECT g FROM GrlUpdates g WHERE g.tamanho = :tamanho")})
public class GrlUpdates implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_grl_updates", nullable = false)
    private Integer pkIdGrlUpdates;
    @Column(name = "pais")
    @Temporal(TemporalType.TIMESTAMP)
    private Date pais;
    @Column(name = "provincia")
    @Temporal(TemporalType.TIMESTAMP)
    private Date provincia;
    @Column(name = "municipio")
    @Temporal(TemporalType.TIMESTAMP)
    private Date municipio;
    @Column(name = "distrito")
    @Temporal(TemporalType.TIMESTAMP)
    private Date distrito;
    @Column(name = "comuna")
    @Temporal(TemporalType.TIMESTAMP)
    private Date comuna;
    @Column(name = "tipo_documento_identificacao")
    @Temporal(TemporalType.TIMESTAMP)
    private Date tipoDocumentoIdentificacao;
    @Column(name = "area_interna")
    @Temporal(TemporalType.TIMESTAMP)
    private Date areaInterna;
    @Column(name = "marca_produto")
    @Temporal(TemporalType.TIMESTAMP)
    private Date marcaProduto;
    @Column(name = "dia_semana")
    @Temporal(TemporalType.TIMESTAMP)
    private Date diaSemana;
    @Column(name = "especialidade")
    @Temporal(TemporalType.TIMESTAMP)
    private Date especialidade;
    @Column(name = "estado_civil")
    @Temporal(TemporalType.TIMESTAMP)
    private Date estadoCivil;
    @Column(name = "sexo")
    @Temporal(TemporalType.TIMESTAMP)
    private Date sexo;
    @Column(name = "centro_hospitalar")
    @Temporal(TemporalType.TIMESTAMP)
    private Date centroHospitalar;
    @Column(name = "religiao")
    @Temporal(TemporalType.TIMESTAMP)
    private Date religiao;
    @Column(name = "grau_parentesco")
    @Temporal(TemporalType.TIMESTAMP)
    private Date grauParentesco;
    @Column(name = "tamanho")
    @Temporal(TemporalType.TIMESTAMP)
    private Date tamanho;

    public GrlUpdates() {
    }

    public GrlUpdates(Integer pkIdGrlUpdates) {
        this.pkIdGrlUpdates = pkIdGrlUpdates;
    }

    public Integer getPkIdGrlUpdates() {
        return pkIdGrlUpdates;
    }

    public void setPkIdGrlUpdates(Integer pkIdGrlUpdates) {
        this.pkIdGrlUpdates = pkIdGrlUpdates;
    }

    public Date getPais() {
        return pais;
    }

    public void setPais(Date pais) {
        this.pais = pais;
    }

    public Date getProvincia() {
        return provincia;
    }

    public void setProvincia(Date provincia) {
        this.provincia = provincia;
    }

    public Date getMunicipio() {
        return municipio;
    }

    public void setMunicipio(Date municipio) {
        this.municipio = municipio;
    }

    public Date getDistrito() {
        return distrito;
    }

    public void setDistrito(Date distrito) {
        this.distrito = distrito;
    }

    public Date getComuna() {
        return comuna;
    }

    public void setComuna(Date comuna) {
        this.comuna = comuna;
    }

    public Date getTipoDocumentoIdentificacao() {
        return tipoDocumentoIdentificacao;
    }

    public void setTipoDocumentoIdentificacao(Date tipoDocumentoIdentificacao) {
        this.tipoDocumentoIdentificacao = tipoDocumentoIdentificacao;
    }

    public Date getAreaInterna() {
        return areaInterna;
    }

    public void setAreaInterna(Date areaInterna) {
        this.areaInterna = areaInterna;
    }

    public Date getMarcaProduto() {
        return marcaProduto;
    }

    public void setMarcaProduto(Date marcaProduto) {
        this.marcaProduto = marcaProduto;
    }

    public Date getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(Date diaSemana) {
        this.diaSemana = diaSemana;
    }

    public Date getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(Date especialidade) {
        this.especialidade = especialidade;
    }

    public Date getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(Date estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public Date getSexo() {
        return sexo;
    }

    public void setSexo(Date sexo) {
        this.sexo = sexo;
    }

    public Date getCentroHospitalar() {
        return centroHospitalar;
    }

    public void setCentroHospitalar(Date centroHospitalar) {
        this.centroHospitalar = centroHospitalar;
    }

    public Date getReligiao() {
        return religiao;
    }

    public void setReligiao(Date religiao) {
        this.religiao = religiao;
    }

    public Date getGrauParentesco() {
        return grauParentesco;
    }

    public void setGrauParentesco(Date grauParentesco) {
        this.grauParentesco = grauParentesco;
    }

    public Date getTamanho() {
        return tamanho;
    }

    public void setTamanho(Date tamanho) {
        this.tamanho = tamanho;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdGrlUpdates != null ? pkIdGrlUpdates.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GrlUpdates)) {
            return false;
        }
        GrlUpdates other = (GrlUpdates) object;
        if ((this.pkIdGrlUpdates == null && other.pkIdGrlUpdates != null) || (this.pkIdGrlUpdates != null && !this.pkIdGrlUpdates.equals(other.pkIdGrlUpdates))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.GrlUpdates[ pkIdGrlUpdates=" + pkIdGrlUpdates + " ]";
    }
    
}
