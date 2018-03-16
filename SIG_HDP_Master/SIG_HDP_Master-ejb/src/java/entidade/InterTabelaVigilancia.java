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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author amed
 */
@Entity
@Table(name = "inter_tabela_vigilancia", catalog = "sigh_db", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "InterTabelaVigilancia.findAll", query = "SELECT i FROM InterTabelaVigilancia i"),
    @NamedQuery(name = "InterTabelaVigilancia.findByPkIdTabelaVigilancia", query = "SELECT i FROM InterTabelaVigilancia i WHERE i.pkIdTabelaVigilancia = :pkIdTabelaVigilancia"),
    @NamedQuery(name = "InterTabelaVigilancia.findByFezes", query = "SELECT i FROM InterTabelaVigilancia i WHERE i.fezes = :fezes"),
    @NamedQuery(name = "InterTabelaVigilancia.findByVomitos", query = "SELECT i FROM InterTabelaVigilancia i WHERE i.vomitos = :vomitos"),
    @NamedQuery(name = "InterTabelaVigilancia.findByRespMin", query = "SELECT i FROM InterTabelaVigilancia i WHERE i.respMin = :respMin"),
    @NamedQuery(name = "InterTabelaVigilancia.findByTosse", query = "SELECT i FROM InterTabelaVigilancia i WHERE i.tosse = :tosse"),
    @NamedQuery(name = "InterTabelaVigilancia.findByDesidrat", query = "SELECT i FROM InterTabelaVigilancia i WHERE i.desidrat = :desidrat"),
    @NamedQuery(name = "InterTabelaVigilancia.findByChoqSep", query = "SELECT i FROM InterTabelaVigilancia i WHERE i.choqSep = :choqSep"),
    @NamedQuery(name = "InterTabelaVigilancia.findByAnemia", query = "SELECT i FROM InterTabelaVigilancia i WHERE i.anemia = :anemia"),
    @NamedQuery(name = "InterTabelaVigilancia.findByTMatinAxRec", query = "SELECT i FROM InterTabelaVigilancia i WHERE i.tMatinAxRec = :tMatinAxRec"),
    @NamedQuery(name = "InterTabelaVigilancia.findByTNoitinhaAxRec", query = "SELECT i FROM InterTabelaVigilancia i WHERE i.tNoitinhaAxRec = :tNoitinhaAxRec"),
    @NamedQuery(name = "InterTabelaVigilancia.findBySarna", query = "SELECT i FROM InterTabelaVigilancia i WHERE i.sarna = :sarna")})
public class InterTabelaVigilancia implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_tabela_vigilancia", nullable = false)
    private Long pkIdTabelaVigilancia;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "fezes", nullable = false, length = 45)
    private String fezes;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "vomitos", nullable = false, length = 45)
    private String vomitos;
    @Basic(optional = false)
    @NotNull
    @Column(name = "resp_min", nullable = false)
    private int respMin;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "tosse", nullable = false, length = 45)
    private String tosse;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "desidrat", nullable = false, length = 45)
    private String desidrat;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "choq_sep", nullable = false, length = 45)
    private String choqSep;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "anemia", nullable = false, length = 45)
    private String anemia;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "t_matin_ax_rec", nullable = false, length = 45)
    private String tMatinAxRec;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "t_noitinha_ax_rec", nullable = false, length = 45)
    private String tNoitinhaAxRec;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "sarna", nullable = false, length = 45)
    private String sarna;
    @JoinColumn(name = "fk_id_tratamento_malnutricao", referencedColumnName = "pk_id_tratamento_malnutricao", nullable = false)
    @ManyToOne(optional = false)
    private InterTratamentoMalnutricao fkIdTratamentoMalnutricao;

    public InterTabelaVigilancia() {
    }

    public InterTabelaVigilancia(Long pkIdTabelaVigilancia) {
        this.pkIdTabelaVigilancia = pkIdTabelaVigilancia;
    }

    public InterTabelaVigilancia(Long pkIdTabelaVigilancia, String fezes, String vomitos, int respMin, String tosse, String desidrat, String choqSep, String anemia, String tMatinAxRec, String tNoitinhaAxRec, String sarna) {
        this.pkIdTabelaVigilancia = pkIdTabelaVigilancia;
        this.fezes = fezes;
        this.vomitos = vomitos;
        this.respMin = respMin;
        this.tosse = tosse;
        this.desidrat = desidrat;
        this.choqSep = choqSep;
        this.anemia = anemia;
        this.tMatinAxRec = tMatinAxRec;
        this.tNoitinhaAxRec = tNoitinhaAxRec;
        this.sarna = sarna;
    }

    public Long getPkIdTabelaVigilancia() {
        return pkIdTabelaVigilancia;
    }

    public void setPkIdTabelaVigilancia(Long pkIdTabelaVigilancia) {
        this.pkIdTabelaVigilancia = pkIdTabelaVigilancia;
    }

    public String getFezes() {
        return fezes;
    }

    public void setFezes(String fezes) {
        this.fezes = fezes;
    }

    public String getVomitos() {
        return vomitos;
    }

    public void setVomitos(String vomitos) {
        this.vomitos = vomitos;
    }

    public int getRespMin() {
        return respMin;
    }

    public void setRespMin(int respMin) {
        this.respMin = respMin;
    }

    public String getTosse() {
        return tosse;
    }

    public void setTosse(String tosse) {
        this.tosse = tosse;
    }

    public String getDesidrat() {
        return desidrat;
    }

    public void setDesidrat(String desidrat) {
        this.desidrat = desidrat;
    }

    public String getChoqSep() {
        return choqSep;
    }

    public void setChoqSep(String choqSep) {
        this.choqSep = choqSep;
    }

    public String getAnemia() {
        return anemia;
    }

    public void setAnemia(String anemia) {
        this.anemia = anemia;
    }

    public String getTMatinAxRec() {
        return tMatinAxRec;
    }

    public void setTMatinAxRec(String tMatinAxRec) {
        this.tMatinAxRec = tMatinAxRec;
    }

    public String getTNoitinhaAxRec() {
        return tNoitinhaAxRec;
    }

    public void setTNoitinhaAxRec(String tNoitinhaAxRec) {
        this.tNoitinhaAxRec = tNoitinhaAxRec;
    }

    public String getSarna() {
        return sarna;
    }

    public void setSarna(String sarna) {
        this.sarna = sarna;
    }

    public InterTratamentoMalnutricao getFkIdTratamentoMalnutricao() {
        return fkIdTratamentoMalnutricao;
    }

    public void setFkIdTratamentoMalnutricao(InterTratamentoMalnutricao fkIdTratamentoMalnutricao) {
        this.fkIdTratamentoMalnutricao = fkIdTratamentoMalnutricao;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdTabelaVigilancia != null ? pkIdTabelaVigilancia.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InterTabelaVigilancia)) {
            return false;
        }
        InterTabelaVigilancia other = (InterTabelaVigilancia) object;
        if ((this.pkIdTabelaVigilancia == null && other.pkIdTabelaVigilancia != null) || (this.pkIdTabelaVigilancia != null && !this.pkIdTabelaVigilancia.equals(other.pkIdTabelaVigilancia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.InterTabelaVigilancia[ pkIdTabelaVigilancia=" + pkIdTabelaVigilancia + " ]";
    }
    
}
