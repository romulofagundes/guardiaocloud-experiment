package main

import groovy.json.JsonBuilder


/**
 * Created by romulofc on 28/05/16.
 */
class MestradoAnalytics {

    public static void main(String[] args) {

        def execs = []
        def results = []
        execs.add([counter: 1, start: 100, inc: 10]) //exec1
        execs.add([counter: 2, start: 1000, inc: 50]) //exec2
        execs.add([counter: 3, start: 2000, inc: 50]) //exec2
        //execs.add([counter: 4, start: 500, inc: 50]) //exec4

        def pos = ["timeStamp":0,"elapsed":1,"responseCode":3]

        execs.each { map ->
            def data = ["round":[],"min":[],"max":[],"avg":[],"median":[],"erros":[],"size":[]]
            def resultAnalytics = "/exec_result/result_exec${map.counter}.csv"
            def resultJSON = "/exec_result/js/result_exec${map.counter}.json"
            new PrintWriter(resultAnalytics).close()
            def arquivoResultAnalytics = new File(resultAnalytics)
            def arquivoResultJSON = new File(resultJSON)
            if(arquivoResultAnalytics.exists()){
                arquivoResultAnalytics.delete()
            }
            if(arquivoResultJSON.exists()){
                arquivoResultJSON.delete()
            }
            arquivoResultAnalytics << "time,size,unique,min,max,avg,median,error\n"
            (1..10).each { count ->
                def tempoRef = 0
                def caminho = "/exec_result/temp/exec${map.counter}/exec${map.counter}_${count}.csv"
                def arquivo = new File(caminho)
                if(arquivo.exists()){
                    arquivo.eachLine{ linha,countLinha ->
                        if(countLinha>1){
                            def splitLinha = linha.split(",")
                            if(countLinha==2){
                                tempoRef = splitLinha[pos.timeStamp].toLong()
                            }
                            def tempo = ((splitLinha[pos.timeStamp].toLong()-tempoRef)/1000).toInteger()
                            def resultFind = results?.find{it.id==tempo}
                            def elapsed = splitLinha[pos.elapsed].toLong()
                            def returnCode = splitLinha[pos.responseCode]
                            if(!resultFind){
                                if(!returnCode.equals("200")){
                                    results.add([id:tempo,min:elapsed,max:elapsed,element:[elapsed],error:1])
                                }else{
                                    results.add([id:tempo,min:elapsed,max:elapsed,element:[elapsed],error:0])
                                }
                            }else{
                                if(elapsed<resultFind.min){
                                    resultFind.min = elapsed
                                }
                                if(elapsed>resultFind.max){
                                    resultFind.max = elapsed
                                }
                                if(!returnCode.equals("200")){
                                    resultFind.error++;
                                }
                                resultFind.element.add(elapsed)

                            }
                        }
                    }
                }
            }
            results = results.sort{it.id}
            results.each {
                it.element = it.element.sort()
                it.size = it.element.size()
                it.avg = it.element.sum()/it.element.size()
                def midNumber = (int)(it.element.size()/2)
                it.median = it.element.size() %2 != 0 ? it.element[midNumber] : (it.element[midNumber] + it.element[midNumber-1])/2
                it.unique = it.element.unique().size()
                arquivoResultAnalytics << "${it.id},${it.size},${it.unique},${it.min},${it.max},${it.avg},${it.median},${it.error}\n"
                data.min.add(it.min)
                data.max.add(it.max)
                data.avg.add(it.avg)
                data.median.add(it.median)
                data.round.add(it.id)
                data.erros.add(it.error)
                data.size.add(it.size)
            }
            arquivoResultJSON << new JsonBuilder([data:data]).toPrettyString()
            //println results
        }

    }
}
