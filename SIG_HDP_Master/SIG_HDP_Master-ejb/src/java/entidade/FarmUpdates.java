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
@Table(name = "farm_updates", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FarmUpdates.findAll", query = "SELECT f FROM FarmUpdates f"),
    @NamedQuery(name = "FarmUpdates.findByPkIdUpdade", query = "SELECT f FROM FarmUpdates f WHERE f.pkIdUpdade = :pkIdUpdade"),
    @NamedQuery(name = "FarmUpdates.findByAviso", query = "SELECT f FROM FarmUpdates f WHERE f.aviso = :aviso"),
    @NamedQuery(name = "FarmUpdates.findByCategoriaMedicamento", query = "SELECT f FROM FarmUpdates f WHERE f.categoriaMedicamento = :categoriaMedicamento"),
    @NamedQuery(name = "FarmUpdates.findByContraIndicacao", query = "SELECT f FROM FarmUpdates f WHERE f.contraIndicacao = :contraIndicacao"),
    @NamedQuery(name = "FarmUpdates.findByEfeitoSecundario", query = "SELECT f FROM FarmUpdates f WHERE f.efeitoSecundario = :efeitoSecundario"),
    @NamedQuery(name = "FarmUpdates.findByEstado", query = "SELECT f FROM FarmUpdates f WHERE f.estado = :estado"),
    @NamedQuery(name = "FarmUpdates.findByEstadoPedido", query = "SELECT f FROM FarmUpdates f WHERE f.estadoPedido = :estadoPedido"),
    @NamedQuery(name = "FarmUpdates.findByFarmaco", query = "SELECT f FROM FarmUpdates f WHERE f.farmaco = :farmaco"),
    @NamedQuery(name = "FarmUpdates.findByFormaFarmaceutica", query = "SELECT f FROM FarmUpdates f WHERE f.formaFarmaceutica = :formaFarmaceutica"),
    @NamedQuery(name = "FarmUpdates.findByIndicacao", query = "SELECT f FROM FarmUpdates f WHERE f.indicacao = :indicacao"),
    @NamedQuery(name = "FarmUpdates.findByLocalArmazenamento", query = "SELECT f FROM FarmUpdates f WHERE f.localArmazenamento = :localArmazenamento"),
    @NamedQuery(name = "FarmUpdates.findByObservacao", query = "SELECT f FROM FarmUpdates f WHERE f.observacao = :observacao"),
    @NamedQuery(name = "FarmUpdates.findByOutroComponente", query = "SELECT f FROM FarmUpdates f WHERE f.outroComponente = :outroComponente"),
    @NamedQuery(name = "FarmUpdates.findByTipoFornecimento", query = "SELECT f FROM FarmUpdates f WHERE f.tipoFornecimento = :tipoFornecimento"),
    @NamedQuery(name = "FarmUpdates.findByTipoLocalArmazenamento", query = "SELECT f FROM FarmUpdates f WHERE f.tipoLocalArmazenamento = :tipoLocalArmazenamento"),
    @NamedQuery(name = "FarmUpdates.findByTipoNotificacao", query = "SELECT f FROM FarmUpdates f WHERE f.tipoNotificacao = :tipoNotificacao"),
    @NamedQuery(name = "FarmUpdates.findByTipoProduto", query = "SELECT f FROM FarmUpdates f WHERE f.tipoProduto = :tipoProduto"),
    @NamedQuery(name = "FarmUpdates.findByTipoQuantidade", query = "SELECT f FROM FarmUpdates f WHERE f.tipoQuantidade = :tipoQuantidade"),
    @NamedQuery(name = "FarmUpdates.findByTipoTransferencia", query = "SELECT f FROM FarmUpdates f WHERE f.tipoTransferencia = :tipoTransferencia"),
    @NamedQuery(name = "FarmUpdates.findByTipoUnidadeMedida", query = "SELECT f FROM FarmUpdates f WHERE f.tipoUnidadeMedida = :tipoUnidadeMedida"),
    @NamedQuery(name = "FarmUpdates.findByTurno", query = "SELECT f FROM FarmUpdates f WHERE f.turno = :turno"),
    @NamedQuery(name = "FarmUpdates.findByUnidadeMedida", query = "SELECT f FROM FarmUpdates f WHERE f.unidadeMedida = :unidadeMedida"),
    @NamedQuery(name = "FarmUpdates.findByViaAdministracao", query = "SELECT f FROM FarmUpdates f WHERE f.viaAdministracao = :viaAdministracao")})
public class FarmUpdates implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_updade", nullable = false)
    private Integer pkIdUpdade;
    @Column(name = "aviso")
    @Temporal(TemporalType.TIMESTAMP)
    private Date aviso;
    @Column(name = "categoria_medicamento")
    @Temporal(TemporalType.TIMESTAMP)
    private Date categoriaMedicamento;
    @Column(name = "contra_indicacao")
    @Temporal(TemporalType.TIMESTAMP)
    private Date contraIndicacao;
    @Column(name = "efeito_secundario")
    @Temporal(TemporalType.TIMESTAMP)
    private Date efeitoSecundario;
    @Column(name = "estado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date estado;
    @Column(name = "estado_pedido")
    @Temporal(TemporalType.TIMESTAMP)
    private Date estadoPedido;
    @Column(name = "farmaco")
    @Temporal(TemporalType.TIMESTAMP)
    private Date farmaco;
    @Column(name = "forma_farmaceutica")
    @Temporal(TemporalType.TIMESTAMP)
    private Date formaFarmaceutica;
    @Column(name = "indicacao")
    @Temporal(TemporalType.TIMESTAMP)
    private Date indicacao;
    @Column(name = "local_armazenamento")
    @Temporal(TemporalType.TIMESTAMP)
    private Date localArmazenamento;
    @Column(name = "observacao")
    @Temporal(TemporalType.TIMESTAMP)
    private Date observacao;
    @Column(name = "outro_componente")
    @Temporal(TemporalType.TIMESTAMP)
    private Date outroComponente;
    @Column(name = "tipo_fornecimento")
    @Temporal(TemporalType.TIMESTAMP)
    private Date tipoFornecimento;
    @Column(name = "tipo_local_armazenamento")
    @Temporal(TemporalType.TIMESTAMP)
    private Date tipoLocalArmazenamento;
    @Column(name = "tipo_notificacao")
    @Temporal(TemporalType.TIMESTAMP)
    private Date tipoNotificacao;
    @Column(name = "tipo_produto")
    @Temporal(TemporalType.TIMESTAMP)
    private Date tipoProduto;
    @Column(name = "tipo_quantidade")
    @Temporal(TemporalType.TIMESTAMP)
    private Date tipoQuantidade;
    @Column(name = "tipo_transferencia")
    @Temporal(TemporalType.TIMESTAMP)
    private Date tipoTransferencia;
    @Column(name = "tipo_unidade_medida")
    @Temporal(TemporalType.TIMESTAMP)
    private Date tipoUnidadeMedida;
    @Column(name = "turno")
    @Temporal(TemporalType.TIMESTAMP)
    private Date turno;
    @Column(name = "unidade_medida")
    @Temporal(TemporalType.TIMESTAMP)
    private Date unidadeMedida;
    @Column(name = "via_administracao")
    @Temporal(TemporalType.TIMESTAMP)
    private Date viaAdministracao;

    public FarmUpdates() {
    }

    public FarmUpdates(Integer pkIdUpdade) {
        this.pkIdUpdade = pkIdUpdade;
    }

    public Integer getPkIdUpdade() {
        return pkIdUpdade;
    }

    public void setPkIdUpdade(Integer pkIdUpdade) {
        this.pkIdUpdade = pkIdUpdade;
    }

    public Date getAviso() {
        return aviso;
    }

    public void setAviso(Date aviso) {
        this.aviso = aviso;
    }

    public Date getCategoriaMedicamento() {
        return categoriaMedicamento;
    }

    public void setCategoriaMedicamento(Date categoriaMedicamento) {
        this.categoriaMedicamento = categoriaMedicamento;
    }

    public Date getContraIndicacao() {
        return contraIndicacao;
    }

    public void setContraIndicacao(Date contraIndicacao) {
        this.contraIndicacao = contraIndicacao;
    }

    public Date getEfeitoSecundario() {
        return efeitoSecundario;
    }

    public void setEfeitoSecundario(Date efeitoSecundario) {
        this.efeitoSecundario = efeitoSecundario;
    }

    public Date getEstado() {
        return estado;
    }

    public void setEstado(Date estado) {
        this.estado = estado;
    }

    public Date getEstadoPedido() {
        return estadoPedido;
    }

    public void setEstadoPedido(Date estadoPedido) {
        this.estadoPedido = estadoPedido;
    }

    public Date getFarmaco() {
        return farmaco;
    }

    public void setFarmaco(Date farmaco) {
        this.farmaco = farmaco;
    }

    public Date getFormaFarmaceutica() {
        return formaFarmaceutica;
    }

    public void setFormaFarmaceutica(Date formaFarmaceutica) {
        this.formaFarmaceutica = formaFarmaceutica;
    }

    public Date getIndicacao() {
        return indicacao;
    }

    public void setIndicacao(Date indicacao) {
        this.indicacao = indicacao;
    }

    public Date getLocalArmazenamento() {
        return localArmazenamento;
    }

    public void setLocalArmazenamento(Date localArmazenamento) {
        this.localArmazenamento = localArmazenamento;
    }

    public Date getObservacao() {
        return observacao;
    }

    public void setObservacao(Date observacao) {
        this.observacao = observacao;
    }

    public Date getOutroComponente() {
        return outroComponente;
    }

    public void setOutroComponente(Date outroComponente) {
        this.outroComponente = outroComponente;
    }

    public Date getTipoFornecimento() {
        return tipoFornecimento;
    }

    public void setTipoFornecimento(Date tipoFornecimento) {
        this.tipoFornecimento = tipoFornecimento;
    }

    public Date getTipoLocalArmazenamento() {
        return tipoLocalArmazenamento;
    }

    public void setTipoLocalArmazenamento(Date tipoLocalArmazenamento) {
        this.tipoLocalArmazenamento = tipoLocalArmazenamento;
    }

    public Date getTipoNotificacao() {
        return tipoNotificacao;
    }

    public void setTipoNotificacao(Date tipoNotificacao) {
        this.tipoNotificacao = tipoNotificacao;
    }

    public Date getTipoProduto() {
        return tipoProduto;
    }

    public void setTipoProduto(Date tipoProduto) {
        this.tipoProduto = tipoProduto;
    }

    public Date getTipoQuantidade() {
        return tipoQuantidade;
    }

    public void setTipoQuantidade(Date tipoQuantidade) {
        this.tipoQuantidade = tipoQuantidade;
    }

    public Date getTipoTransferencia() {
        return tipoTransferencia;
    }

    public void setTipoTransferencia(Date tipoTransferencia) {
        this.tipoTransferencia = tipoTransferencia;
    }

    public Date getTipoUnidadeMedida() {
        return tipoUnidadeMedida;
    }

    public void setTipoUnidadeMedida(Date tipoUnidadeMedida) {
        this.tipoUnidadeMedida = tipoUnidadeMedida;
    }

    public Date getTurno() {
        return turno;
    }

    public void setTurno(Date turno) {
        this.turno = turno;
    }

    public Date getUnidadeMedida() {
        return unidadeMedida;
    }

    public void setUnidadeMedida(Date unidadeMedida) {
        this.unidadeMedida = unidadeMedida;
    }

    public Date getViaAdministracao() {
        return viaAdministracao;
    }

    public void setViaAdministracao(Date viaAdministracao) {
        this.viaAdministracao = viaAdministracao;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdUpdade != null ? pkIdUpdade.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FarmUpdates)) {
            return false;
        }
        FarmUpdates other = (FarmUpdates) object;
        if ((this.pkIdUpdade == null && other.pkIdUpdade != null) || (this.pkIdUpdade != null && !this.pkIdUpdade.equals(other.pkIdUpdade))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.FarmUpdates[ pkIdUpdade=" + pkIdUpdade + " ]";
    }
    
}
