/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package util;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author gemix
 */
@ManagedBean
@SessionScoped
public class MensagemArmazenadaBean
{
    
    private String mensagem = "";

    /**
     * Creates a new instance of MensagemArmazenadaBean
     */
    public MensagemArmazenadaBean()
    {
    }

    public String getMensagem()
    {
        return mensagem;
    }

    public void setMensagem(String mensagem)
    {
        this.mensagem = mensagem;
    }
    
    public void verificarMensagemArmazenada()
    {
        if(!mensagem.isEmpty())
        {
            Mensagem.erroMsg(""+mensagem);
            mensagem = "";
        }
    }
    
}
