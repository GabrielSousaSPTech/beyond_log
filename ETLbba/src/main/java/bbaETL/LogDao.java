package bbaETL;

import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class LogDao {

    private final JdbcTemplate jdbcTemplate;
    public LogDao(Env env) {
        this.jdbcTemplate = new Connection(env).getJdbcTemplate();
    }
    String boldOn = "\033[1m";
    String boldOff = "\033[0m";
    public void insertLog(String tipo, String log){
        if(!log.isBlank()){
            try{
                String comandoSQL = "INSERT INTO TB_ETL_LOG(TIPO_LOG, DESCRICAO) VALUES(?,?)";
                jdbcTemplate.update(comandoSQL, tipo, log);
                ZoneId spZone = ZoneId.of("America/Sao_Paulo");
                ZonedDateTime spDateTime = ZonedDateTime.now(spZone);
                LocalDateTime timestamp = spDateTime.toLocalDateTime();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

                if(tipo.equalsIgnoreCase("ERROR")){
                    System.out.println(timestamp.format(formatter) + " - " + boldOn+ tipo +boldOff + " - "+ log);
                }else {
                    System.out.println(timestamp.format(formatter) + " - " + tipo + " - "+ log);
                }

            }catch(Exception e){
                System.out.println("Erro ao inserir log: " + e.getMessage());
            }
        }
    }
}
