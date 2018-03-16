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
@Table(name = "farm_produto", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FarmProduto.findAll", query = "SELECT f FROM FarmProduto f"),
    @NamedQuery(name = "FarmProduto.findByPkIdProduto", query = "SELECT f FROM FarmProduto f WHERE f.pkIdProduto = :pkIdProduto"),
    @NamedQuery(name = "FarmProduto.findByDescricao", query = "SELECT f FROM FarmProduto f WHERE f.descricao = :descricao"),
    @NamedQuery(name = "FarmProduto.findByNomeGenerico", query = "SELECT f FROM FarmProduto f WHERE f.nomeGenerico = :nomeGenerico"),
    @NamedQuery(name = "FarmProduto.findByNomeComercial", query = "SELECT f FROM FarmProduto f WHERE f.nomeComercial = :nomeComercial"),
    @NamedQuery(name = "FarmProduto.findByDosagem", query = "SELECT f FROM FarmProduto f WHERE f.dosagem = :dosagem"),
    @NamedQuery(name = "FarmProduto.findByIndicacoes", query = "SELECT f FROM FarmProduto f WHERE f.indicacoes = :indicacoes"),
    @NamedQuery(name = "FarmProduto.findByContraIndicacoes", query = "SELECT f FROM FarmProduto f WHERE f.contraIndicacoes = :contraIndicacoes"),
    @NamedQuery(name = "FarmProduto.findByAvisos", query = "SELECT f FROM FarmProduto f WHERE f.avisos = :avisos"),
    @NamedQuery(name = "FarmProduto.findByObservacoes", query = "SELECT f FROM FarmProduto f WHERE f.observacoes = :observacoes"),
    @NamedQuery(name = "FarmProduto.findByDataHoraCadastro", query = "SELECT f FROM FarmProduto f WHERE f.dataHoraCadastro = :dataHoraCadastro")})
public class FarmProduto implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_produto", nullable = false)
    private Integer pkIdProduto;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 500)
    @Column(name = "descricao", nullable = false, length = 500)
    private String descricao;
    @Size(max = 500)
    @Column(name = "nome_generico", length = 500)
    private String nomeGenerico;
    @Size(max = 50)
    @Column(name = "nome_comercial", length = 50)
    private String nomeComercial;
    @Size(max = 50)
    @Column(name = "dosagem", length = 50)
    private String dosagem;
    @Size(max = 200)
    @Column(name = "indicacoes", length = 200)
    private String indicacoes;
    @Size(max = 200)
    @Column(name = "contra_indicacoes", length = 200)
    private String contraIndicacoes;
    @Size(max = 200)
    @Column(name = "avisos", length = 200)
    private String avisos;
    @Size(max = 200)
    @Column(name = "observacoes", length = 200)
    private String observacoes;
    @Column(name = "data_hora_cadastro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataHoraCadastro;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdProduto")
    private List<FarmProdutoHasObservacao> farmProdutoHasObservacaoList;
    @OneToMany(mappedBy = "fkIdProduto")
    private List<FarmNotificacao> farmNotificacaoList;
    @OneToMany(mappedBy = "fkIdProduto")
    private List<FarmProdutoHasEfeitoSecundario> farmProdutoHasEfeitoSecundarioList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdProduto")
    private List<InterMedicacaoHasFarmProduto> interMedicacaoHasFarmProdutoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdProduto")
    private List<FarmLoteProduto> farmLoteProdutoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdProduto")
    private List<FarmProdutoHasIndicacao> farmProdutoHasIndicacaoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdProduto")
    private List<FarmPedidoHasProduto> farmPedidoHasProdutoList;
    @JoinColumn(name = "fk_id_categoria_medicamento", referencedColumnName = "pk_id_categoria_medicamento")
    @ManyToOne
    private FarmCategoriaMedicamento fkIdCategoriaMedicamento;
    @JoinColumn(name = "fk_id_forma_farmaceutica", referencedColumnName = "pk_id_forma_farmaceutica")
    @ManyToOne
    private FarmFormaFarmaceutica fkIdFormaFarmaceutica;
    @JoinColumn(name = "fk_id_tipo_produto", referencedColumnName = "pk_id_tipo_produto")
    @ManyToOne
    private FarmTipoProduto fkIdTipoProduto;
    @JoinColumn(name = "fk_id_unidade_medida", referencedColumnName = "pk_id_unidade_medida")
    @ManyToOne
    private FarmUnidadeMedida fkIdUnidadeMedida;
    @JoinColumn(name = "fk_id_via_administracao", referencedColumnName = "pk_id_via_administracao")
    @ManyToOne
    private FarmViaAdministracao fkIdViaAdministracao;
    @JoinColumn(name = "fk_id_funcionario_cadastrou", referencedColumnName = "pk_id_funcionario")
    @ManyToOne
    private RhFuncionario fkIdFuncionarioCadastrou;
    @OneToMany(mappedBy = "fkIdProduto")
    private List<FarmProdutoHasFarmaco> farmProdutoHasFarmacoList;
    @OneToMany(mappedBy = "fkIdProduto")
    private List<InterNotificacao> interNotificacaoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdProduto")
    private List<FarmProdutoHasContraIndicacao> farmProdutoHasContraIndicacaoList;
    @OneToMany(mappedBy = "fkIdMaterial")
    private List<DiagControloSemanalMaterialHasMaterial> diagControloSemanalMaterialHasMaterialList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdFarmProduto")
    private List<AmbPrescricaoMedicaHasProduto> ambPrescricaoMedicaHasProdutoList;
    @OneToMany(mappedBy = "fkIdProduto")
    private List<FarmProdutoHasOutroComponente> farmProdutoHasOutroComponenteList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdProduto")
    private List<FarmProdutoHasAviso> farmProdutoHasAvisoList;

    public FarmProduto() {
    }

    public FarmProduto(Integer pkIdProduto) {
        this.pkIdProduto = pkIdProduto;
    }

    public FarmProduto(Integer pkIdProduto, String descricao) {
        this.pkIdProduto = pkIdProduto;
        this.descricao = descricao;
    }

    public Integer getPkIdProduto() {
        return pkIdProduto;
    }

    public void setPkIdProduto(Integer pkIdProduto) {
        this.pkIdProduto = pkIdProduto;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getNomeGenerico() {
        return nomeGenerico;
    }

    public void setNomeGenerico(String nomeGenerico) {
        this.nomeGenerico = nomeGenerico;
    }

    public String getNomeComercial() {
        return nomeComercial;
    }

    public void setNomeComercial(String nomeComercial) {
        this.nomeComercial = nomeComercial;
    }

    public String getDosagem() {
        return dosagem;
    }

    public void setDosagem(String dosagem) {
        this.dosagem = dosagem;
    }

    public String getIndicacoes() {
        return indicacoes;
    }

    public void setIndicacoes(String indicacoes) {
        this.indicacoes = indicacoes;
    }

    public String getContraIndicacoes() {
        return contraIndicacoes;
    }

    public void setContraIndicacoes(String contraIndicacoes) {
        this.contraIndicacoes = contraIndicacoes;
    }

    public String getAvisos() {
        return avisos;
    }

    public void setAvisos(String avisos) {
        this.avisos = avisos;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public Date getDataHoraCadastro() {
        return dataHoraCadastro;
    }

    public void setDataHoraCadastro(Date dataHoraCadastro) {
        this.dataHoraCadastro = dataHoraCadastro;
    }

    @XmlTransient
    public List<FarmProdutoHasObservacao> getFarmProdutoHasObservacaoList() {
        return farmProdutoHasObservacaoList;
    }

    public void setFarmProdutoHasObservacaoList(List<FarmProdutoHasObservacao> farmProdutoHasObservacaoList) {
        this.farmProdutoHasObservacaoList = farmProdutoHasObservacaoList;
    }

    @XmlTransient
    public List<FarmNotificacao> getFarmNotificacaoList() {
        return farmNotificacaoList;
    }

    public void setFarmNotificacaoList(List<FarmNotificacao> farmNotificacaoList) {
        this.farmNotificacaoList = farmNotificacaoList;
    }

    @XmlTransient
    public List<FarmProdutoHasEfeitoSecundario> getFarmProdutoHasEfeitoSecundarioList() {
        return farmProdutoHasEfeitoSecundarioList;
    }

    public void setFarmProdutoHasEfeitoSecundarioList(List<FarmProdutoHasEfeitoSecundario> farmProdutoHasEfeitoSecundarioList) {
        this.farmProdutoHasEfeitoSecundarioList = farmProdutoHasEfeitoSecundarioList;
    }

    @XmlTransient
    public List<InterMedicacaoHasFarmProduto> getInterMedicacaoHasFarmProdutoList() {
        return interMedicacaoHasFarmProdutoList;
    }

    public void setInterMedicacaoHasFarmProdutoList(List<InterMedicacaoHasFarmProduto> interMedicacaoHasFarmProdutoList) {
        this.interMedicacaoHasFarmProdutoList = interMedicacaoHasFarmProdutoList;
    }

    @XmlTransient
    public List<FarmLoteProduto> getFarmLoteProdutoList() {
        return farmLoteProdutoList;
    }

    public void setFarmLoteProdutoList(List<FarmLoteProduto> farmLoteProdutoList) {
        this.farmLoteProdutoList = farmLoteProdutoList;
    }

    @XmlTransient
    public List<FarmProdutoHasIndicacao> getFarmProdutoHasIndicacaoList() {
        return farmProdutoHasIndicacaoList;
    }

    public void setFarmProdutoHasIndicacaoList(List<FarmProdutoHasIndicacao> farmProdutoHasIndicacaoList) {
        this.farmProdutoHasIndicacaoList = farmProdutoHasIndicacaoList;
    }

    @XmlTransient
    public List<FarmPedidoHasProduto> getFarmPedidoHasProdutoList() {
        return farmPedidoHasProdutoList;
    }

    public void setFarmPedidoHasProdutoList(List<FarmPedidoHasProduto> farmPedidoHasProdutoList) {
        this.farmPedidoHasProdutoList = farmPedidoHasProdutoList;
    }

    public FarmCategoriaMedicamento getFkIdCategoriaMedicamento() {
        return fkIdCategoriaMedicamento;
    }

    public void setFkIdCategoriaMedicamento(FarmCategoriaMedicamento fkIdCategoriaMedicamento) {
        this.fkIdCategoriaMedicamento = fkIdCategoriaMedicamento;
    }

    public FarmFormaFarmaceutica getFkIdFormaFarmaceutica() {
        return fkIdFormaFarmaceutica;
    }

    public void setFkIdFormaFarmaceutica(FarmFormaFarmaceutica fkIdFormaFarmaceutica) {
        this.fkIdFormaFarmaceutica = fkIdFormaFarmaceutica;
    }

    public FarmTipoProduto getFkIdTipoProduto() {
        return fkIdTipoProduto;
    }

    public void setFkIdTipoProduto(FarmTipoProduto fkIdTipoProduto) {
        this.fkIdTipoProduto = fkIdTipoProduto;
    }

    public FarmUnidadeMedida getFkIdUnidadeMedida() {
        return fkIdUnidadeMedida;
    }

    public void setFkIdUnidadeMedida(FarmUnidadeMedida fkIdUnidadeMedida) {
        this.fkIdUnidadeMedida = fkIdUnidadeMedida;
    }

    public FarmViaAdministracao getFkIdViaAdministracao() {
        return fkIdViaAdministracao;
    }

    public void setFkIdViaAdministracao(FarmViaAdministracao fkIdViaAdministracao) {
        this.fkIdViaAdministracao = fkIdViaAdministracao;
    }

    public RhFuncionario getFkIdFuncionarioCadastrou() {
        return fkIdFuncionarioCadastrou;
    }

    public void setFkIdFuncionarioCadastrou(RhFuncionario fkIdFuncionarioCadastrou) {
        this.fkIdFuncionarioCadastrou = fkIdFuncionarioCadastrou;
    }

    @XmlTransient
    public List<FarmProdutoHasFarmaco> getFarmProdutoHasFarmacoList() {
        return farmProdutoHasFarmacoList;
    }

    public void setFarmProdutoHasFarmacoList(List<FarmProdutoHasFarmaco> farmProdutoHasFarmacoList) {
        this.farmProdutoHasFarmacoList = farmProdutoHasFarmacoList;
    }

    @XmlTransient
    public List<InterNotificacao> getInterNotificacaoList() {
        return interNotificacaoList;
    }

    public void setInterNotificacaoList(List<InterNotificacao> interNotificacaoList) {
        this.interNotificacaoList = interNotificacaoList;
    }

    @XmlTransient
    public List<FarmProdutoHasContraIndicacao> getFarmProdutoHasContraIndicacaoList() {
        return farmProdutoHasContraIndicacaoList;
    }

    public void setFarmProdutoHasContraIndicacaoList(List<FarmProdutoHasContraIndicacao> farmProdutoHasContraIndicacaoList) {
        this.farmProdutoHasContraIndicacaoList = farmProdutoHasContraIndicacaoList;
    }

    @XmlTransient
    public List<DiagControloSemanalMaterialHasMaterial> getDiagControloSemanalMaterialHasMaterialList() {
        return diagControloSemanalMaterialHasMaterialList;
    }

    public void setDiagControloSemanalMaterialHasMaterialList(List<DiagControloSemanalMaterialHasMaterial> diagControloSemanalMaterialHasMaterialList) {
        this.diagControloSemanalMaterialHasMaterialList = diagControloSemanalMaterialHasMaterialList;
    }

    @XmlTransient
    public List<AmbPrescricaoMedicaHasProduto> getAmbPrescricaoMedicaHasProdutoList() {
        return ambPrescricaoMedicaHasProdutoList;
    }

    public void setAmbPrescricaoMedicaHasProdutoList(List<AmbPrescricaoMedicaHasProduto> ambPrescricaoMedicaHasProdutoList) {
        this.ambPrescricaoMedicaHasProdutoList = ambPrescricaoMedicaHasProdutoList;
    }

    @XmlTransient
    public List<FarmProdutoHasOutroComponente> getFarmProdutoHasOutroComponenteList() {
        return farmProdutoHasOutroComponenteList;
    }

    public void setFarmProdutoHasOutroComponenteList(List<FarmProdutoHasOutroComponente> farmProdutoHasOutroComponenteList) {
        this.farmProdutoHasOutroComponenteList = farmProdutoHasOutroComponenteList;
    }

    @XmlTransient
    public List<FarmProdutoHasAviso> getFarmProdutoHasAvisoList() {
        return farmProdutoHasAvisoList;
    }

    public void setFarmProdutoHasAvisoList(List<FarmProdutoHasAviso> farmProdutoHasAvisoList) {
        this.farmProdutoHasAvisoList = farmProdutoHasAvisoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdProduto != null ? pkIdProduto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FarmProduto)) {
            return false;
        }
        FarmProduto other = (FarmProduto) object;
        if ((this.pkIdProduto == null && other.pkIdProduto != null) || (this.pkIdProduto != null && !this.pkIdProduto.equals(other.pkIdProduto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.FarmProduto[ pkIdProduto=" + pkIdProduto + " ]";
    }
    
}
