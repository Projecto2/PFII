/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package util.relatorio.admsRelatorio;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.util.JRLoader;
import util.relatorio.ConexaoPostgresSQL;

/**
 *
 * @author gemix
 */
@ManagedBean
@SessionScoped
public class AdmsRelatorios
{

    /**
     * Creates a new instance of AdmsRelatorios
     */
    
    private int codigoPaciente = 1;
    ConexaoPostgresSQL conexao;
    Connection con = null;
    public AdmsRelatorios()
    {
        
        this.conexao = new ConexaoPostgresSQL();
    }

    public int getCodigoPaciente()
    {
        return codigoPaciente;
    }

    public void setCodigoPaciente(int codigoPaciente)
    {
        this.codigoPaciente = codigoPaciente;
    }
    
    

    public void relatorioPacientes() {

        byte[] bytes = null;

        try {

            conexao = new ConexaoPostgresSQL();

            this.con = this.conexao.getConnection();

            FacesContext context = FacesContext.getCurrentInstance();
            context.responseComplete();

            ServletContext scontext = (ServletContext) context.getExternalContext().getContext();

            HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();

            //load report location
            FileInputStream fis = new FileInputStream(scontext.getRealPath("/WEB-INF/relatorios/adms/report1.jasper"));
            BufferedInputStream bufferedInputStream = new BufferedInputStream(fis);

            JasperReport relatorioJasper = (JasperReport) JRLoader.loadObject(bufferedInputStream);

            Map<String, Object> parametros = new HashMap();
            parametros.put("codigoPaciente", this.codigoPaciente);
            
            bytes = JasperRunManager.runReportToPdf(relatorioJasper, parametros, this.con);
            if (bytes != null && bytes.length > 0) {
                // envia o relatório em formato PDF para o browser
                response.setContentType("application/pdf");
                response.setContentLength(bytes.length);
                response.setHeader("Content-Disposition", "attachment; filename=\"relatorioPaciente.pdf\"");

                ServletOutputStream ouputStream = response.getOutputStream();

                ouputStream.write(bytes, 0, bytes.length);
                ouputStream.flush();
                ouputStream.close();
//                this.codigoConta = 0;
            }
        } catch (JRException ex) {

            ex.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //connection.close();
        }

    }

    
    
    
}
