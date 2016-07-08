package main

import org.apache.jmeter.engine.StandardJMeterEngine
import org.apache.jmeter.save.SaveService
import org.apache.jmeter.util.JMeterUtils
import org.apache.jorphan.collections.HashTree

/**
 * Created by romulofc on 28/05/16.
 */
class MestradoExecs {

    public static void main(String[] args) {

        def execs = []
        execs.add([counter: 1, start: 100, inc: 10]) //exec1
        execs.add([counter: 2, start: 1000, inc: 50]) //exec2
        execs.add([counter: 3, start: 2000, inc: 50]) //exec3
        def homeDir = System.properties['user.home']
        StandardJMeterEngine jmeter = new StandardJMeterEngine()
        JMeterUtils.loadJMeterProperties("${homeDir}/Dev/apache-jmeter-2.13/bin/jmeter.properties")
        JMeterUtils.setJMeterHome("${homeDir}/Dev/apache-jmeter-2.13/")
        JMeterUtils.initLogging()
        JMeterUtils.initLocale()

        SaveService.loadProperties()

        def arquivoConf = File.createTempFile("template", ".jmx")
        execs.each { map ->
            (1..10).each { count ->
                long tempoInicio = System.currentTimeMillis()
                GenerateConfig.restartVM()
                println("-- Iniciando ${map.counter} Round: ${count}")
                def caminho = new File("/exec_result/temp/exec${map.counter}/exec${map.counter}_${count}.csv")
                if(caminho.exists()){
                    caminho.delete()
                }
                arquivoConf << GenerateConfig.generateFile(map.counter, count, map.start, map.inc)
                HashTree testPlanTree = SaveService.loadTree(arquivoConf)
                jmeter.configure(testPlanTree)
                jmeter.run()
                new PrintWriter(arquivoConf.absolutePath).close()
                println("-- Finalizando ${map.counter} Round: ${count}")
                long tempoFim = System.currentTimeMillis()
                println "Tempo: ${(tempoFim-tempoInicio)/1000}s"
            }
        }

    }
}
