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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author amed
 */
@Entity
@Table(name = "rh_ferias", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RhFerias.findAll", query = "SELECT r FROM RhFerias r"),
    @NamedQuery(name = "RhFerias.findByPkIdFerias", query = "SELECT r FROM RhFerias r WHERE r.pkIdFerias = :pkIdFerias"),
    @NamedQuery(name = "RhFerias.findByDataInicio", query = "SELECT r FROM RhFerias r WHERE r.dataInicio = :dataInicio"),
    @NamedQuery(name = "RhFerias.findByDataTermino", query = "SELECT r FROM RhFerias r WHERE r.dataTermino = :dataTermino"),
    @NamedQuery(name = "RhFerias.findByDiasDescontar", query = "SELECT r FROM RhFerias r WHERE r.diasDescontar = :diasDescontar"),
    @NamedQuery(name = "RhFerias.findByObservacao", query = "SELECT r FROM RhFerias r WHERE r.observacao = :observacao"),
    @NamedQuery(name = "RhFerias.findByDiasGozar", query = "SELECT r FROM RhFerias r WHERE r.diasGozar = :diasGozar"),
    @NamedQuery(name = "RhFerias.findByDataCadastro", query = "SELECT r FROM RhFerias r WHERE r.dataCadastro = :dataCadastro"),
    @NamedQuery(name = "RhFerias.findByMes", query = "SELECT r FROM RhFerias r WHERE r.mes = :mes")})
public class RhFerias implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_ferias", nullable = false)
    private Integer pkIdFerias;
    @Basic(optional = false)
    @NotNull
    @Column(name = "data_inicio", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dataInicio;
    @Basic(optional = false)
    @NotNull
    @Column(name = "data_termino", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dataTermino;
    @Basic(optional = false)
    @NotNull
    @Column(name = "dias_descontar", nullable = false)
    private int diasDescontar;
    @Size(max = 2147483647)
    @Column(name = "observacao", length = 2147483647)
    private String observacao;
    @Basic(optional = false)
    @NotNull
    @Column(name = "dias_gozar", nullable = false)
    private int diasGozar;
    @Basic(optional = false)
    @NotNull
    @Column(name = "data_cadastro", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dataCadastro;
    @Column(name = "mes")
    private Integer mes;
    @JoinColumn(name = "fk_id_estado_ferias", referencedColumnName = "pk_id_estado_ferias", nullable = false)
    @ManyToOne(optional = false)
    private RhEstadoFerias fkIdEstadoFerias;
    @JoinColumn(name = "fk_id_funcionario", referencedColumnName = "pk_id_funcionario", nullable = false)
    @ManyToOne(optional = false)
    private RhFuncionario fkIdFuncionario;

    public RhFerias() {
    }

    public RhFerias(Integer pkIdFerias) {
        this.pkIdFerias = pkIdFerias;
    }

    public RhFerias(Integer pkIdFerias, Date dataInicio, Date dataTermino, int diasDescontar, int diasGozar, Date dataCadastro) {
        this.pkIdFerias = pkIdFerias;
        this.dataInicio = dataInicio;
        this.dataTermino = dataTermino;
        this.diasDescontar = diasDescontar;
        this.diasGozar = diasGozar;
        this.dataCadastro = dataCadastro;
    }

    public Integer getPkIdFerias() {
        return pkIdFerias;
    }

    public void setPkIdFerias(Integer pkIdFerias) {
        this.pkIdFerias = pkIdFerias;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Date getDataTermino() {
        return dataTermino;
    }

    public void setDataTermino(Date dataTermino) {
        this.dataTermino = dataTermino;
    }

    public int getDiasDescontar() {
        return diasDescontar;
    }

    public void setDiasDescontar(int diasDescontar) {
        this.diasDescontar = diasDescontar;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public int getDiasGozar() {
        return diasGozar;
    }

    public void setDiasGozar(int diasGozar) {
        this.diasGozar = diasGozar;
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public Integer getMes() {
        return mes;
    }

    public void setMes(Integer mes) {
        this.mes = mes;
    }

    public RhEstadoFerias getFkIdEstadoFerias() {
        return fkIdEstadoFerias;
    }

    public void setFkIdEstadoFerias(RhEstadoFerias fkIdEstadoFerias) {
        this.fkIdEstadoFerias = fkIdEstadoFerias;
    }

    public RhFuncionario getFkIdFuncionario() {
        return fkIdFuncionario;
    }

    public void setFkIdFuncionario(RhFuncionario fkIdFuncionario) {
        this.fkIdFuncionario = fkIdFuncionario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdFerias != null ? pkIdFerias.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RhFerias)) {
            return false;
        }
        RhFerias other = (RhFerias) object;
        if ((this.pkIdFerias == null && other.pkIdFerias != null) || (this.pkIdFerias != null && !this.pkIdFerias.equals(other.pkIdFerias))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.RhFerias[ pkIdFerias=" + pkIdFerias + " ]";
    }
    
}
