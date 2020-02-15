package nju.miaosha;

import nju.miaosha.dao.UserDOMapper;
import nju.miaosha.dataobject.UserDO;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Hello world!
 *
 */
@SpringBootApplication(scanBasePackages = {"nju.miaosha"})
@RestController
@MapperScan("nju.miaosha.dao")
public class App 
{

    public static void main( String[] args )
    {

        System.out.println( "Hello World!！！" );
        SpringApplication.run(App.class,args);
    }
}
