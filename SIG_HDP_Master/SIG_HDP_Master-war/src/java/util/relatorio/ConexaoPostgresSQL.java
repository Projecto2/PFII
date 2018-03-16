package util.relatorio;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author banguela
 */
public class ConexaoPostgresSQL implements Serializable{
    
     //Vamos definir algumas contantes
    private static final String DRIVER = "org.postgresql.Driver";
    private static final String URL = "jdbc:postgresql://localhost:5432/sigh_db";
    private static final String USUARIO = "postgres";
    private static final String SENHA =  "postgres";
    private static Connection con = null;
    private PreparedStatement ps;
    private ResultSet resultado;

    public ConexaoPostgresSQL() {
        
        try{
            
            // Carregando o Driver de conexao JBDC, com o método estático forName da classe class.
            Class.forName(DRIVER);
            
            //Abrindo uma conexão com o Driver específico
            con = DriverManager.getConnection(URL, USUARIO, SENHA);
             System.out.println("Conexão feita com Sucesso!");
           // statement = (Statement)conexao.createStatement(); 
            
        }
        catch(ClassNotFoundException ex){
            
            //System.out.println("Erro ao carregar o Driver JBDC");
            ex.printStackTrace();
            
        }
        catch(SQLException ex){
            
            System.out.println("Erro na conexão com o Banco de Dados");
            ex.printStackTrace();
           
        }
        
    }
    
    
    // Método que retorna a conexao
    public Connection getConnection(){
       
        return con;
    }
    
    //Aqui a interf Connection prepara um objecto Statemant para executar ums inst sql 
    public void criarStatement(String sql){
        
        try{
            
            ps = con.prepareStatement(sql);
            
        }
        catch(SQLException ex){
            System.out.println("Erro ao criar o objecto Statement");
            ex.printStackTrace();
        }   
        
    }
    
    //retorna o objecto Statement criado acima...
    public PreparedStatement getStatement(){
        
        return ps;
    }
    
    //Método q executa inst sql(insert,delete, update)..
    public void executarUpdate(){
        try{
            
            ps.executeUpdate();
        }
        catch(SQLException ex){
            
            System.out.println("Erro ao inserir o registro");
            ex.printStackTrace();
            
            
        }
    }
    
    
    public ResultSet executarQuery() throws SQLException{
        
        return ps.executeQuery();
    }
    
    
    
    //Fechamos a conexão...
    public void closeConnection(){
        
        try{
            
            con.close();
           
        }
        catch(SQLException ex){
            
            ex.printStackTrace();
          
    }
    
    }
    
    //Retorna um objecto do tipo ResultSet
    public ResultSet getResultSet(){
        
        return resultado;
    }
    
}
