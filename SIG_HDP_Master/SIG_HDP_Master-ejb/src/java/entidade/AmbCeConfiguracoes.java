/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entidade;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author amed
 */
@Entity
@Table(name = "amb_ce_configuracoes", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AmbCeConfiguracoes.findAll", query = "SELECT a FROM AmbCeConfiguracoes a"),
    @NamedQuery(name = "AmbCeConfiguracoes.findByPkIdConfiguracoes", query = "SELECT a FROM AmbCeConfiguracoes a WHERE a.pkIdConfiguracoes = :pkIdConfiguracoes"),
    @NamedQuery(name = "AmbCeConfiguracoes.findByIdAltura", query = "SELECT a FROM AmbCeConfiguracoes a WHERE a.idAltura = :idAltura"),
    @NamedQuery(name = "AmbCeConfiguracoes.findByIdCentroHospitalarProveniencia", query = "SELECT a FROM AmbCeConfiguracoes a WHERE a.idCentroHospitalarProveniencia = :idCentroHospitalarProveniencia"),
    @NamedQuery(name = "AmbCeConfiguracoes.findByIdClassificacaoDaDor", query = "SELECT a FROM AmbCeConfiguracoes a WHERE a.idClassificacaoDaDor = :idClassificacaoDaDor"),
    @NamedQuery(name = "AmbCeConfiguracoes.findByIdConta", query = "SELECT a FROM AmbCeConfiguracoes a WHERE a.idConta = :idConta"),
    @NamedQuery(name = "AmbCeConfiguracoes.findByIdEspecialidade", query = "SELECT a FROM AmbCeConfiguracoes a WHERE a.idEspecialidade = :idEspecialidade"),
    @NamedQuery(name = "AmbCeConfiguracoes.findByIdEstadoDoPaciente", query = "SELECT a FROM AmbCeConfiguracoes a WHERE a.idEstadoDoPaciente = :idEstadoDoPaciente"),
    @NamedQuery(name = "AmbCeConfiguracoes.findByIdFrequenciaCardiaca", query = "SELECT a FROM AmbCeConfiguracoes a WHERE a.idFrequenciaCardiaca = :idFrequenciaCardiaca"),
    @NamedQuery(name = "AmbCeConfiguracoes.findByIdFrequenciaRespiratoria", query = "SELECT a FROM AmbCeConfiguracoes a WHERE a.idFrequenciaRespiratoria = :idFrequenciaRespiratoria"),
    @NamedQuery(name = "AmbCeConfiguracoes.findByIdGlicemia", query = "SELECT a FROM AmbCeConfiguracoes a WHERE a.idGlicemia = :idGlicemia"),
    @NamedQuery(name = "AmbCeConfiguracoes.findByIdPeso", query = "SELECT a FROM AmbCeConfiguracoes a WHERE a.idPeso = :idPeso"),
    @NamedQuery(name = "AmbCeConfiguracoes.findByIdPulso", query = "SELECT a FROM AmbCeConfiguracoes a WHERE a.idPulso = :idPulso"),
    @NamedQuery(name = "AmbCeConfiguracoes.findByIdSinaisSintomas", query = "SELECT a FROM AmbCeConfiguracoes a WHERE a.idSinaisSintomas = :idSinaisSintomas"),
    @NamedQuery(name = "AmbCeConfiguracoes.findByIdTaSistolica", query = "SELECT a FROM AmbCeConfiguracoes a WHERE a.idTaSistolica = :idTaSistolica"),
    @NamedQuery(name = "AmbCeConfiguracoes.findByIdTaDiastolica", query = "SELECT a FROM AmbCeConfiguracoes a WHERE a.idTaDiastolica = :idTaDiastolica"),
    @NamedQuery(name = "AmbCeConfiguracoes.findByIdTemperatura", query = "SELECT a FROM AmbCeConfiguracoes a WHERE a.idTemperatura = :idTemperatura")})
public class AmbCeConfiguracoes implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_configuracoes", nullable = false)
    private Integer pkIdConfiguracoes;
    @Column(name = "id_altura")
    private Integer idAltura;
    @Column(name = "id_centro_hospitalar_proveniencia")
    private Integer idCentroHospitalarProveniencia;
    @Column(name = "id_classificacao_da_dor")
    private Integer idClassificacaoDaDor;
    @Column(name = "id_conta")
    private Integer idConta;
    @Column(name = "id_especialidade")
    private Integer idEspecialidade;
    @Column(name = "id_estado_do_paciente")
    private Integer idEstadoDoPaciente;
    @Column(name = "id_frequencia_cardiaca")
    private Integer idFrequenciaCardiaca;
    @Column(name = "id_frequencia_respiratoria")
    private Integer idFrequenciaRespiratoria;
    @Column(name = "id_glicemia")
    private Integer idGlicemia;
    @Column(name = "id_peso")
    private Integer idPeso;
    @Column(name = "id_pulso")
    private Integer idPulso;
    @Column(name = "id_sinais_sintomas")
    private Integer idSinaisSintomas;
    @Column(name = "id_ta_sistolica")
    private Integer idTaSistolica;
    @Column(name = "id_ta_diastolica")
    private Integer idTaDiastolica;
    @Column(name = "id_temperatura")
    private Integer idTemperatura;

    public AmbCeConfiguracoes() {
    }

    public AmbCeConfiguracoes(Integer pkIdConfiguracoes) {
        this.pkIdConfiguracoes = pkIdConfiguracoes;
    }

    public Integer getPkIdConfiguracoes() {
        return pkIdConfiguracoes;
    }

    public void setPkIdConfiguracoes(Integer pkIdConfiguracoes) {
        this.pkIdConfiguracoes = pkIdConfiguracoes;
    }

    public Integer getIdAltura() {
        return idAltura;
    }

    public void setIdAltura(Integer idAltura) {
        this.idAltura = idAltura;
    }

    public Integer getIdCentroHospitalarProveniencia() {
        return idCentroHospitalarProveniencia;
    }

    public void setIdCentroHospitalarProveniencia(Integer idCentroHospitalarProveniencia) {
        this.idCentroHospitalarProveniencia = idCentroHospitalarProveniencia;
    }

    public Integer getIdClassificacaoDaDor() {
        return idClassificacaoDaDor;
    }

    public void setIdClassificacaoDaDor(Integer idClassificacaoDaDor) {
        this.idClassificacaoDaDor = idClassificacaoDaDor;
    }

    public Integer getIdConta() {
        return idConta;
    }

    public void setIdConta(Integer idConta) {
        this.idConta = idConta;
    }

    public Integer getIdEspecialidade() {
        return idEspecialidade;
    }

    public void setIdEspecialidade(Integer idEspecialidade) {
        this.idEspecialidade = idEspecialidade;
    }

    public Integer getIdEstadoDoPaciente() {
        return idEstadoDoPaciente;
    }

    public void setIdEstadoDoPaciente(Integer idEstadoDoPaciente) {
        this.idEstadoDoPaciente = idEstadoDoPaciente;
    }

    public Integer getIdFrequenciaCardiaca() {
        return idFrequenciaCardiaca;
    }

    public void setIdFrequenciaCardiaca(Integer idFrequenciaCardiaca) {
        this.idFrequenciaCardiaca = idFrequenciaCardiaca;
    }

    public Integer getIdFrequenciaRespiratoria() {
        return idFrequenciaRespiratoria;
    }

    public void setIdFrequenciaRespiratoria(Integer idFrequenciaRespiratoria) {
        this.idFrequenciaRespiratoria = idFrequenciaRespiratoria;
    }

    public Integer getIdGlicemia() {
        return idGlicemia;
    }

    public void setIdGlicemia(Integer idGlicemia) {
        this.idGlicemia = idGlicemia;
    }

    public Integer getIdPeso() {
        return idPeso;
    }

    public void setIdPeso(Integer idPeso) {
        this.idPeso = idPeso;
    }

    public Integer getIdPulso() {
        return idPulso;
    }

    public void setIdPulso(Integer idPulso) {
        this.idPulso = idPulso;
    }

    public Integer getIdSinaisSintomas() {
        return idSinaisSintomas;
    }

    public void setIdSinaisSintomas(Integer idSinaisSintomas) {
        this.idSinaisSintomas = idSinaisSintomas;
    }

    public Integer getIdTaSistolica() {
        return idTaSistolica;
    }

    public void setIdTaSistolica(Integer idTaSistolica) {
        this.idTaSistolica = idTaSistolica;
    }

    public Integer getIdTaDiastolica() {
        return idTaDiastolica;
    }

    public void setIdTaDiastolica(Integer idTaDiastolica) {
        this.idTaDiastolica = idTaDiastolica;
    }

    public Integer getIdTemperatura() {
        return idTemperatura;
    }

    public void setIdTemperatura(Integer idTemperatura) {
        this.idTemperatura = idTemperatura;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdConfiguracoes != null ? pkIdConfiguracoes.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AmbCeConfiguracoes)) {
            return false;
        }
        AmbCeConfiguracoes other = (AmbCeConfiguracoes) object;
        if ((this.pkIdConfiguracoes == null && other.pkIdConfiguracoes != null) || (this.pkIdConfiguracoes != null && !this.pkIdConfiguracoes.equals(other.pkIdConfiguracoes))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.AmbCeConfiguracoes[ pkIdConfiguracoes=" + pkIdConfiguracoes + " ]";
    }
    
}
