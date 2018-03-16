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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author amed
 */
@Entity
@Table(name = "tb_triagem", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TbTriagem.findAll", query = "SELECT t FROM TbTriagem t"),
    @NamedQuery(name = "TbTriagem.findByPkTriagem", query = "SELECT t FROM TbTriagem t WHERE t.pkTriagem = :pkTriagem"),
    @NamedQuery(name = "TbTriagem.findByDataHoraTriagem", query = "SELECT t FROM TbTriagem t WHERE t.dataHoraTriagem = :dataHoraTriagem"),
    @NamedQuery(name = "TbTriagem.findByPeso", query = "SELECT t FROM TbTriagem t WHERE t.peso = :peso"),
    @NamedQuery(name = "TbTriagem.findByAltura", query = "SELECT t FROM TbTriagem t WHERE t.altura = :altura"),
    @NamedQuery(name = "TbTriagem.findByTemperatura", query = "SELECT t FROM TbTriagem t WHERE t.temperatura = :temperatura"),
    @NamedQuery(name = "TbTriagem.findByTensao", query = "SELECT t FROM TbTriagem t WHERE t.tensao = :tensao")})
public class TbTriagem implements Serializable {
    @OneToMany(mappedBy = "fkTriagem")
    private List<TbConsulta> tbConsultaList;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_triagem", nullable = false)
    private Long pkTriagem;
    @Column(name = "data_hora_triagem")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataHoraTriagem;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "peso", precision = 17, scale = 17)
    private Double peso;
    @Column(name = "altura", precision = 17, scale = 17)
    private Double altura;
    @Column(name = "temperatura", precision = 17, scale = 17)
    private Double temperatura;
    @Column(name = "tensao", precision = 17, scale = 17)
    private Double tensao;
    @JoinColumn(name = "fk_consulta", referencedColumnName = "pk_id_consulta")
    @ManyToOne
    private AmbConsulta fkConsulta;
    @JoinColumn(name = "fk_estado", referencedColumnName = "pk_id_estado")
    @ManyToOne
    private AmbEstado fkEstado;
    @JoinColumn(name = "fk_enfermeiro", referencedColumnName = "pk_id_funcionario")
    @ManyToOne
    private RhFuncionario fkEnfermeiro;
    @JoinColumn(name = "fk_observacao", referencedColumnName = "pk_observacao")
    @ManyToOne
    private TbObservacao fkObservacao;

    public TbTriagem() {
    }

    public TbTriagem(Long pkTriagem) {
        this.pkTriagem = pkTriagem;
    }

    public Long getPkTriagem() {
        return pkTriagem;
    }

    public void setPkTriagem(Long pkTriagem) {
        this.pkTriagem = pkTriagem;
    }

    public Date getDataHoraTriagem() {
        return dataHoraTriagem;
    }

    public void setDataHoraTriagem(Date dataHoraTriagem) {
        this.dataHoraTriagem = dataHoraTriagem;
    }

    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public Double getAltura() {
        return altura;
    }

    public void setAltura(Double altura) {
        this.altura = altura;
    }

    public Double getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(Double temperatura) {
        this.temperatura = temperatura;
    }

    public Double getTensao() {
        return tensao;
    }

    public void setTensao(Double tensao) {
        this.tensao = tensao;
    }

    public AmbConsulta getFkConsulta() {
        return fkConsulta;
    }

    public void setFkConsulta(AmbConsulta fkConsulta) {
        this.fkConsulta = fkConsulta;
    }

    public AmbEstado getFkEstado() {
        return fkEstado;
    }

    public void setFkEstado(AmbEstado fkEstado) {
        this.fkEstado = fkEstado;
    }

    public RhFuncionario getFkEnfermeiro() {
        return fkEnfermeiro;
    }

    public void setFkEnfermeiro(RhFuncionario fkEnfermeiro) {
        this.fkEnfermeiro = fkEnfermeiro;
    }

    public TbObservacao getFkObservacao() {
        return fkObservacao;
    }

    public void setFkObservacao(TbObservacao fkObservacao) {
        this.fkObservacao = fkObservacao;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkTriagem != null ? pkTriagem.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TbTriagem)) {
            return false;
        }
        TbTriagem other = (TbTriagem) object;
        if ((this.pkTriagem == null && other.pkTriagem != null) || (this.pkTriagem != null && !this.pkTriagem.equals(other.pkTriagem))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.TbTriagem[ pkTriagem=" + pkTriagem + " ]";
    }

    @XmlTransient
    public List<TbConsulta> getTbConsultaList() {
        return tbConsultaList;
    }

    public void setTbConsultaList(List<TbConsulta> tbConsultaList) {
        this.tbConsultaList = tbConsultaList;
    }
    
}
