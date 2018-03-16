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
@Table(name = "farm_pedido", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FarmPedido.findAll", query = "SELECT f FROM FarmPedido f"),
    @NamedQuery(name = "FarmPedido.findByPkIdPedido", query = "SELECT f FROM FarmPedido f WHERE f.pkIdPedido = :pkIdPedido"),
    @NamedQuery(name = "FarmPedido.findByDataHoraPedido", query = "SELECT f FROM FarmPedido f WHERE f.dataHoraPedido = :dataHoraPedido"),
    @NamedQuery(name = "FarmPedido.findByMotivoCancelamento", query = "SELECT f FROM FarmPedido f WHERE f.motivoCancelamento = :motivoCancelamento")})
public class FarmPedido implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_pedido", nullable = false)
    private Integer pkIdPedido;
    @Basic(optional = false)
    @NotNull
    @Column(name = "data_hora_pedido", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataHoraPedido;
    @Size(max = 250)
    @Column(name = "motivo_cancelamento", length = 250)
    private String motivoCancelamento;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "fkIdPedido")
    private FarmMovimento farmMovimento;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdPedido")
    private List<FarmPedidoHasProduto> farmPedidoHasProdutoList;
    @JoinColumn(name = "fk_id_estado_pedido", referencedColumnName = "pk_id_estado_pedido", nullable = false)
    @ManyToOne(optional = false)
    private FarmEstadoPedido fkIdEstadoPedido;
    @JoinColumn(name = "fk_local_destino_pedido", referencedColumnName = "pk_id_local_armazenamento", nullable = false)
    @ManyToOne(optional = false)
    private FarmLocalArmazenamento fkLocalDestinoPedido;
    @JoinColumn(name = "fk_local_origem_pedido", referencedColumnName = "pk_id_local_armazenamento", nullable = false)
    @ManyToOne(optional = false)
    private FarmLocalArmazenamento fkLocalOrigemPedido;
    @JoinColumn(name = "fk_id_funcionario_atendeu", referencedColumnName = "pk_id_funcionario")
    @ManyToOne
    private RhFuncionario fkIdFuncionarioAtendeu;
    @JoinColumn(name = "fk_id_funcionario_solicitou", referencedColumnName = "pk_id_funcionario", nullable = false)
    @ManyToOne(optional = false)
    private RhFuncionario fkIdFuncionarioSolicitou;

    public FarmPedido() {
    }

    public FarmPedido(Integer pkIdPedido) {
        this.pkIdPedido = pkIdPedido;
    }

    public FarmPedido(Integer pkIdPedido, Date dataHoraPedido) {
        this.pkIdPedido = pkIdPedido;
        this.dataHoraPedido = dataHoraPedido;
    }

    public Integer getPkIdPedido() {
        return pkIdPedido;
    }

    public void setPkIdPedido(Integer pkIdPedido) {
        this.pkIdPedido = pkIdPedido;
    }

    public Date getDataHoraPedido() {
        return dataHoraPedido;
    }

    public void setDataHoraPedido(Date dataHoraPedido) {
        this.dataHoraPedido = dataHoraPedido;
    }

    public String getMotivoCancelamento() {
        return motivoCancelamento;
    }

    public void setMotivoCancelamento(String motivoCancelamento) {
        this.motivoCancelamento = motivoCancelamento;
    }

    public FarmMovimento getFarmMovimento() {
        return farmMovimento;
    }

    public void setFarmMovimento(FarmMovimento farmMovimento) {
        this.farmMovimento = farmMovimento;
    }

    @XmlTransient
    public List<FarmPedidoHasProduto> getFarmPedidoHasProdutoList() {
        return farmPedidoHasProdutoList;
    }

    public void setFarmPedidoHasProdutoList(List<FarmPedidoHasProduto> farmPedidoHasProdutoList) {
        this.farmPedidoHasProdutoList = farmPedidoHasProdutoList;
    }

    public FarmEstadoPedido getFkIdEstadoPedido() {
        return fkIdEstadoPedido;
    }

    public void setFkIdEstadoPedido(FarmEstadoPedido fkIdEstadoPedido) {
        this.fkIdEstadoPedido = fkIdEstadoPedido;
    }

    public FarmLocalArmazenamento getFkLocalDestinoPedido() {
        return fkLocalDestinoPedido;
    }

    public void setFkLocalDestinoPedido(FarmLocalArmazenamento fkLocalDestinoPedido) {
        this.fkLocalDestinoPedido = fkLocalDestinoPedido;
    }

    public FarmLocalArmazenamento getFkLocalOrigemPedido() {
        return fkLocalOrigemPedido;
    }

    public void setFkLocalOrigemPedido(FarmLocalArmazenamento fkLocalOrigemPedido) {
        this.fkLocalOrigemPedido = fkLocalOrigemPedido;
    }

    public RhFuncionario getFkIdFuncionarioAtendeu() {
        return fkIdFuncionarioAtendeu;
    }

    public void setFkIdFuncionarioAtendeu(RhFuncionario fkIdFuncionarioAtendeu) {
        this.fkIdFuncionarioAtendeu = fkIdFuncionarioAtendeu;
    }

    public RhFuncionario getFkIdFuncionarioSolicitou() {
        return fkIdFuncionarioSolicitou;
    }

    public void setFkIdFuncionarioSolicitou(RhFuncionario fkIdFuncionarioSolicitou) {
        this.fkIdFuncionarioSolicitou = fkIdFuncionarioSolicitou;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdPedido != null ? pkIdPedido.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FarmPedido)) {
            return false;
        }
        FarmPedido other = (FarmPedido) object;
        if ((this.pkIdPedido == null && other.pkIdPedido != null) || (this.pkIdPedido != null && !this.pkIdPedido.equals(other.pkIdPedido))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.FarmPedido[ pkIdPedido=" + pkIdPedido + " ]";
    }
    
}
